package dao;

import entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Dao<K extends Serializable, E extends BaseEntity<K>> {
    void save(E entity);
    Optional<E> findById(K id);
    void update(E entity);
    void delete(K id);
    List<E> findAll();
}
