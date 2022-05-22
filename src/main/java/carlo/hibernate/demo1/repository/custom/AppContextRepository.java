package carlo.hibernate.demo1.repository.custom;

import carlo.hibernate.demo1.repository.entities.AppContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

@Repository
@Transactional(
        isolation = Isolation.READ_COMMITTED,
        propagation = Propagation.REQUIRED,
        rollbackFor = { Exception.class, RuntimeException.class })
@Log4j2
public class AppContextRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = { Exception.class, RuntimeException.class })
    public List<AppContext> getContextsToProcess(int limit, int minutesThreshold) {
        List<AppContext> out;

        TypedQuery<AppContext> q = entityManager.createQuery("select a from AppContext a where a.locked=false and a.lastUpdate < :threshold", AppContext.class);
        q.setParameter("threshold", OffsetDateTime.now().minusMinutes(minutesThreshold));
        q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        q.setMaxResults(limit);
        out = q.getResultList();
        out.forEach(appContext -> {
            appContext.setLocked(true);
            appContext.setLastUpdate(OffsetDateTime.now());
            appContext.setStatus("LOCKED");
            entityManager.merge(appContext);
        });

        return out;
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = { Exception.class, RuntimeException.class })
    public List<AppContext> unlockAppContextWithExpiredProcessing(int minutesThreshold) {
        List<AppContext> out;

        TypedQuery<AppContext> q = entityManager.createQuery("select a from AppContext a where a.locked=true and a.lastUpdate <= :threshold", AppContext.class);
        q.setParameter("threshold", OffsetDateTime.now().minusMinutes(minutesThreshold));
        q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        out = q.getResultList();
        out.forEach(appContext -> {
            appContext.setLocked(false);
            appContext.setLastUpdate(OffsetDateTime.now());
            appContext.setStatus("IDLE");
            entityManager.merge(appContext);
        });

        return out;
    }

    public void update(AppContext appContext) {
        entityManager.merge(appContext);
    }
}
