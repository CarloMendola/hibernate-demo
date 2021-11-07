package carlo.hibernate.demo1.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;


/**
 * The Interface IBaseRepository.
 *
 * @param <T> the generic type
 *
 */
public interface IBaseRepository<T extends Object> {

    /**
     * Gets the managed entity class.
     *
     * @return the managed entity class
     */
    Class<T> getManagedEntityClass();

    /**
     * Gets the entity manager.
     *
     * @return the entity manager
     */
    EntityManager getEntityManager();

    /**
     * Creates the entity.
     *
     * @param entity the entity
     */
    default void create(T entity) {
        if (entity == null) {
            throw new RuntimeException("no entity to be persisted");
        }
        getEntityManager().persist(entity);
    }

    /**
     * Creates the entity and get it.
     *
     * @param <I> the generic type
     * @param entity the entity
     * @return the t
     */
    default T createAndGet(T entity) {
        if (entity == null) {
            throw new RuntimeException("no entity to be persisted");
        }
        getEntityManager().persist(entity);
        return entity;
    }

    /**
     * Update the entity.
     *
     * @param entity the entity
     */
    default T update(T entity) {
        if (entity == null) {
            throw new RuntimeException("no entity to be updated");
        }
        return getEntityManager().merge(entity);
    }

    /**
     * Delete the entity.
     *
     * @param entity the entity
     */
    default void delete(T entity) {
        if (entity == null) {
            throw new RuntimeException("no entity to be deleted");
        }
        getEntityManager().remove(entity);
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the t
     */
    T findById(Object id);

    /**
     * Find all entities.
     *
     * @return the list
     */
    default List<T> findAll() {
        // -----------------------------------------------------------------------------------
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getManagedEntityClass());
        // -----------------------------------------------------------------------------------
        Root<T> root = query.from(getManagedEntityClass());
        query.select(root);
        // -----------------------------------------------------------------------------------
        // Fetch
        applyFetchMode(root);
        // -----------------------------------------------------------------------------------
        // Submit Query and get results
        return getEntityManager().createQuery(query).getResultList();
    }

    /**
     * Returns a single entity matching the given {@link Specification} or {@link Optional#empty()} if none found.
     *
     * @param spec can be {@literal null}.
     * @return never {@literal null}.
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException if more than one entity found.
     */
    default Optional<T> findOne(Specification<T> spec) {
        try {
            // -----------------------------------------------------------------------------------
            CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(getManagedEntityClass());
            // -----------------------------------------------------------------------------------
            Root<T> root = applySpecificationToCriteria(spec, query);
            query.select(root);
            // -----------------------------------------------------------------------------------
            // Fetch
            applyFetchMode(root);
            // -----------------------------------------------------------------------------------
            // Submit Query and get single result
            return Optional.of(getEntityManager().createQuery(query).getSingleResult());
            //
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Find all by fetch.
     *
     * @param spec the specification
     * @return the list
     */
    default List<T> findAllByFetch(Specification<T> spec) {
        return findAllByFetch(spec, Sort.unsorted());
    }

    /**
     * Find all by fetch.
     *
     * @param spec the spec
     * @param sort the sort
     * @return the list
     */
    default List<T> findAllByFetch(Specification<T> spec, Sort sort) {
        // -----------------------------------------------------------------------------------
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getManagedEntityClass());
        // -----------------------------------------------------------------------------------
        Root<T> root = applySpecificationToCriteria(spec, query);
        query.select(root);
        // -----------------------------------------------------------------------------------
        // Fetch
        applyFetchMode(root);
        // -----------------------------------------------------------------------------------
        // Sort
        if (sort != null) {
            query.orderBy(QueryUtils.toOrders(sort, root, builder));
        }
        // -----------------------------------------------------------------------------------
        // Submit Query and get results
        return getEntityManager().createQuery(query).getResultList();
    }

    /**
     * Apply fetch mode.
     *
     * @param root the root
     */
    default void applyFetchMode(Root<T> root) {
    }

    /**
     * Apply specification to criteria.
     *
     * @param spec the spec
     * @param query the query
     * @return the root
     */
    default Root<T> applySpecificationToCriteria(Specification<T> spec, CriteriaQuery<T> query) {
        //
        Root<T> root = query.from(getManagedEntityClass());
        if (spec == null) {
            return root;
        }
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        Predicate predicate = spec.toPredicate(root, query, builder);
        if (predicate != null) {
            query.where(predicate);
        }
        return root;
    }

}
