package carlo.hibernate.demo1;

import carlo.hibernate.demo1.repository.entities.*;
import carlo.hibernate.demo1.service.MyService;
import carlo.hibernate.demo1.service.model.DeltaEnum;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Log4j2
@SpringBootTest
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
class HibernateDemoApplicationIT {

    @Autowired
    MyService myService;
    
    @Test
    void contextLoads() {
    }
    
    @Test
    void myService1_Test() {
        try {
            Alpha a1 = getAlpha();
            String res = myService.save(a1);
            log.trace(res);

        } catch (Exception e) {
            log.error("unexpected exception",e);
        }
    }

    private Alpha getAlpha() {
        OffsetDateTime now = OffsetDateTime.now();
        Alpha a = new Alpha();
        AlphaPK apk = new AlphaPK();
        apk.setId(UUID.randomUUID().toString());
        apk.setTs(now);
        a.setId(apk);
        a.setBetas(this.getBetas(ThreadLocalRandom.current().nextInt(100,300),a));
        a.setGammas(this.getGammas(ThreadLocalRandom.current().nextInt(200,250),a));
        a.setCol1("col1-"+ThreadLocalRandom.current().nextInt());
        a.setCol2(this.getRandomBoolean());
        a.setCol3(this.getRandomInteger());
        return a;
    }

    private Set<Gamma> getGammas(int count,Alpha parent) {
        log.info("create {} gamma with random deltas",count);
        HashSet<Gamma> out = new HashSet<>();
        for (int i = 0; i < count; i++) {
            Gamma tmp = this.getGamma(parent);
            out.add(tmp);
        }
        return out;
    }

    private Gamma getGamma(Alpha parent) {
        Gamma g = new Gamma();
        g.setId(UUID.randomUUID().toString());
        g.setAlphas(new HashSet<>());
        g.getAlphas().add(parent);
        g.setCol1("rnd"+ThreadLocalRandom.current().nextInt());
        g.setCol2(ThreadLocalRandom.current().nextBoolean());
        g.setCol3(ThreadLocalRandom.current().nextInt());
        g.setDeltas(this.getDeltas(ThreadLocalRandom.current().nextInt(4,50),g));
        return g;
    }

    private Set<Delta> getDeltas(int count,Gamma parent) {
        log.trace("create {} delta",count);
        HashSet<Delta> out = new HashSet<>();
        for (int i = 0; i < count; i++) {
            Delta tmp = this.getDelta(parent);
            out.add(tmp);
        }
        return out;
    }

    private Delta getDelta(Gamma parent) {
        Delta d = new Delta();
        d.setId(UUID.randomUUID().toString());
        d.setGamma(parent);
        d.setType(this.getRandomDeltaType());
        return d;
    }

    private DeltaType getRandomDeltaType() {
        List<DeltaEnum> tmp = Arrays.asList(DeltaEnum.ONE,DeltaEnum.TWO,DeltaEnum.THREE);
        int i = ThreadLocalRandom.current().nextInt(0,tmp.size());
        DeltaType out = new DeltaType();
        out.setId(tmp.get(i).toString());
        return out;
    }


    private Set<Beta> getBetas(int count,Alpha parent) {
        log.info("create {} betas",count);
        HashSet<Beta> out = new HashSet<>();
        for (int i = 0; i < count; i++) {
            Beta tmp = this.getBeta(parent);
            out.add(tmp);
        }
        return out;
    }

    private Beta getBeta(Alpha parent) {
        Beta b = new Beta();
        b.setId(UUID.randomUUID().toString());
        b.setCol1("rnd"+ThreadLocalRandom.current().nextInt());
        b.setCol2(ThreadLocalRandom.current().nextBoolean());
        b.setCol3(ThreadLocalRandom.current().nextInt());
        b.setAlpha(parent);
        return b;
    }

    private boolean getRandomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    private Integer getRandomInteger() {
        return ThreadLocalRandom.current().nextInt();
    }
}
