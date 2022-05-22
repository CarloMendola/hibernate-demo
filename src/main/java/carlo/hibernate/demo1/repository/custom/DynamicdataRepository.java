package carlo.hibernate.demo1.repository.custom;

import carlo.hibernate.demo1.repository.entities.AppContext;
import carlo.hibernate.demo1.repository.entities.Dynamicdata;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
@Transactional(
        isolation = Isolation.READ_COMMITTED,
        propagation = Propagation.REQUIRED,
        rollbackFor = { Exception.class, RuntimeException.class })
@Log4j2
public class DynamicdataRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Delete everything by appContext id then insert all new dynamic data
     * @return
     */
    public void updateDynamicdataByAppContext(AppContext in, List<Dynamicdata> newDynamicData) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Dynamicdata> criteriaDelete = cb.createCriteriaDelete(Dynamicdata.class);
        Root<Dynamicdata> root = criteriaDelete.from(Dynamicdata.class);
        criteriaDelete.where(cb.equal(root.get("appContextId"), in.getId()));

        Integer rows = this.entityManager.createQuery(criteriaDelete).executeUpdate();
        log.info("bulk delete affected {} rows", rows);

        newDynamicData.forEach(e -> this.entityManager.persist(e));

    }


}
