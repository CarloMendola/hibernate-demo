package carlo.hibernate.demo1.service.runner;

import carlo.hibernate.demo1.repository.custom.AppContextRepository;
import carlo.hibernate.demo1.repository.custom.DynamicdataRepository;
import carlo.hibernate.demo1.repository.entities.AppContext;
import carlo.hibernate.demo1.service.threads.AppContextThread;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Service
public class LongTaskRunner implements CommandLineRunner {

    long millis = 5000;
    int maxParallelContexts = 5;
    int minutesThreshold = 1;
    int deadMinutesThreshold = 3;

    @Autowired
    AppContextRepository appContextRepository;

    @Autowired
    DynamicdataRepository dynamicdataRepository;

    ConcurrentHashMap<String, AppContextThread> threadMap = new ConcurrentHashMap();

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            if (this.threadMap.size() < maxParallelContexts) {
                while (this.threadMap.size() < maxParallelContexts) {
                    // get contexts to process using maxParallelContexts
                    List<AppContext> appContexts = appContextRepository.getContextsToProcess(maxParallelContexts, minutesThreshold);
                    // if context are available
                    // for each context create a thread to handle
                    if (appContexts != null && !appContexts.isEmpty()) {
                        log.info("got {} appContexts to process", appContexts.size());
                        appContexts.stream().parallel().forEach(appContext -> {
                            AppContextThread aThread = new AppContextThread(appContext, appContextRepository, dynamicdataRepository, threadMap, this.getRandomSimulateException());
                            threadMap.put(appContext.getId(), aThread);
                            aThread.run();
                        });
                    }
                }
            } else {
                log.debug("nothing else to process sleep for {} millis and evaluate", this.millis);
                // sleep
                doSleep(this.millis);
            }
            appContextRepository.unlockAppContextWithExpiredProcessing(deadMinutesThreshold);
        }
    }

    private boolean getRandomSimulateException() {
        boolean out = false;
        // TODO:
        return out;
    }

    @SneakyThrows
    private void doSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("got interrupt!", e);
            throw e;
        }
    }
}
