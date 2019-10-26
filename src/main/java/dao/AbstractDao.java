package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.DBService;

public abstract class AbstractDao<T> implements Dao<T> {
    private final SessionFactory factory = DBService.getFactory();

    @Override
    public T findByUuid(Class<T> clazz, String uuid) {
        try (final Session session = factory.openSession()) {
            session.beginTransaction();
            final T entity = session.get(clazz, uuid);
            session.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public void update(String uuid) {

    }

    @Override
    public void save(T entity) {
        try (final Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(String uuid) {

    }
}
