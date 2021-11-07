package carlo.hibernate.demo1.repository.custom;

import carlo.hibernate.demo1.repository.entities.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;

import java.util.*;

import javax.persistence.*;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.*;


@Repository
//@formatter:off
@Transactional(
    isolation = Isolation.READ_COMMITTED,
    propagation = Propagation.REQUIRED,
    rollbackFor = { Exception.class, RuntimeException.class })
//@formatter:on
@Log4j2
public class AlphaRepository implements IAlphaRepository {

    /** The entity manager. */
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Class<Alpha> getManagedEntityClass() {
        return Alpha.class;
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public Alpha findById(Object id) {
        try {
            return entityManager.find(Alpha.class, id);
            //
        } catch (NoResultException e) {
            log.trace("Alpha.findById: No Result.", e);
            return null;
        } catch (Exception e) {
            log.error("Error occurred finding entity by Id = {}.", id, e);
            return null;
        }
    }

}
