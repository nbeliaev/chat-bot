package dao;

public interface Dao<T> {

    T findByUuid(Class<T> clazz, String uuid);

    void update(String uuid);

    void save(T entity);

    void delete(String uuid);

    void deleteAll();
}
