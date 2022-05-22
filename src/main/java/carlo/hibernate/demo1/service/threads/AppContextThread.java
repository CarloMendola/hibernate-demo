package carlo.hibernate.demo1.service.threads;

import carlo.hibernate.demo1.repository.custom.AppContextRepository;
import carlo.hibernate.demo1.repository.custom.DynamicdataRepository;
import carlo.hibernate.demo1.repository.entities.AppContext;
import carlo.hibernate.demo1.repository.entities.Dynamicdata;
import lombok.extern.log4j.Log4j2;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
public class AppContextThread implements Runnable {
    AppContext appContext;

    AppContextRepository appContextRepository;

    DynamicdataRepository dynamicdataRepository;

    ConcurrentHashMap<String, AppContextThread> threadMap;

    String uuid;

    boolean simulateException;

    public AppContextThread(AppContext appContext,
                            AppContextRepository appContextRepository,
                            DynamicdataRepository dynamicdataRepository,
                            ConcurrentHashMap<String, AppContextThread> threadMap,
                            boolean simulateException) {
        this.appContext = appContext;
        this.appContextRepository = appContextRepository;
        this.threadMap = threadMap;
        this.uuid = UUID.randomUUID().toString();
        this.simulateException = simulateException;
        this.dynamicdataRepository = dynamicdataRepository;
    }

    @Override
    public void run() {
        // simulate long process
        try {
            this.appContext.setThreadId(this.uuid);
            this.appContext.setLastUpdate(OffsetDateTime.now());
            this.appContext.setStatus("PROCESSING");
            this.appContextRepository.update(appContext);
            Thread.sleep(5000); // simulate long run
            // generate and save dynamic data
            List<Dynamicdata> newDynamicData = this.getNewDynamicData(this.appContext);
            dynamicdataRepository.updateDynamicdataByAppContext(this.appContext, newDynamicData);
            if (this.simulateException) {
                throw new RuntimeException("simulated Exception");
            }
            this.appContext.setLocked(false);
            this.appContext.setLastUpdate(OffsetDateTime.now());
            this.appContext.setStatus("IDLE");
            this.appContext.setThreadId(null);
            this.appContextRepository.update(appContext);
        } catch (InterruptedException e) {
            log.error("got interrupt", e);
        } finally {
            threadMap.remove(this.appContext.getId());
            log.info("thread done with appContest: {}",this.appContext.getId());
        }
    }

    private List<Dynamicdata> getNewDynamicData(AppContext appContext) {
        List<Dynamicdata> out = new LinkedList<>();

        OffsetDateTime now = OffsetDateTime.now();
        for (int i = 0; i < 500; i++) {
            Dynamicdata tmp = new Dynamicdata();
            tmp.setAppContextId(appContext.getId());
            tmp.setDynDataId(UUID.randomUUID().toString());
            tmp.setInsertedAt(now);
            tmp.setDynValue(new Random().nextInt(100));
            out.add(tmp);
        }

        return out;
    }
}
