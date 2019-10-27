package dao;

public interface Dao<T> {

    T findByUuid(Class<T> clazz, String uuid);

    void update(T entity);

    void save(T entity);

    void delete(T entity);

    void deleteAll();
}
