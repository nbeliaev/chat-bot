package dao;

public interface Dao<T> {

    T findByUuid(Class<T> clazz, String uuid);

    T findByPattern(Class<T> clazz, String fieldName, String pattern);

    void update(T entity);

    void save(T entity);

    void delete(T entity);

    void deleteAll();
}
