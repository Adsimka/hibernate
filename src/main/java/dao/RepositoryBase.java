package dao;

import entity.BaseEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RepositoryBase<K extends Serializable, E extends BaseEntity<K>> implements Dao<K, E> {

    private final Class<E> clazz;
    private final EntityManager entityManager;
    @Override
    public void save(E entity) {
        entityManager.persist(entity);
    }

    @Override
    public Optional<E> findById(K id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    @Override
    public void update(E entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(K id) {
        entityManager.remove(id);
    }

    @Override
    public List<E> findAll() {
        var criteriaBuilder = entityManager
                .getCriteriaBuilder()
                .createQuery(clazz);
        criteriaBuilder.from(clazz);
        return entityManager
                .createQuery(criteriaBuilder)
                .getResultList();
    }
}
