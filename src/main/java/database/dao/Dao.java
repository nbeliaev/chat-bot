package database.dao;

import exceptions.NotExistDataBaseException;

public interface Dao<T> {

    T findByUuid(Class<T> clazz, String uuid) throws NotExistDataBaseException;

    T findByPattern(Class<T> clazz, String fieldName, String pattern) throws NotExistDataBaseException;

    void update(T entity);

    void save(T entity);

    void delete(T entity);

    void deleteAll();
}
