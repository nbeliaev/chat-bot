package dao;

import exceptions.ExistDataBaseException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.DBService;

import javax.persistence.PersistenceException;

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
        } catch (PersistenceException e) {
            // TODO
            throw new ExistDataBaseException(e.getMessage());
        }
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public void deleteAll() {
        try (final Session session = factory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM ProductEntity").executeUpdate();
            session.createQuery("DELETE FROM StoreEntity").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
