package dao;

public interface Dao<T> {

    T findByUuid(Class<T> clazz, String uuid);

    T findByDescription(Class<T> clazz, String description);

    void update(T entity);

    void save(T entity);

    void delete(T entity);

    void deleteAll();
}
