package database.dao;

import exceptions.NotExistDataBaseException;

import java.util.List;

public interface Dao<T> {

    T findByUuid(Class<T> clazz, String uuid) throws NotExistDataBaseException;

    List<T> findByPattern(Class<T> clazz, String fieldName, String pattern);

    T findByName(Class<T> clazz, String name) throws NotExistDataBaseException;

    List<T> getAll(Class<T> clazz);

    void update(T entity);

    void save(T entity);

    void delete(T entity);

    void deleteAll();
}
