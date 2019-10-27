package dao;

import exceptions.ExistDataBaseException;
import exceptions.NotExistDataBaseException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.DBService;

import javax.persistence.PersistenceException;

public abstract class AbstractDao<T> implements Dao<T> {
    private final SessionFactory factory = DBService.getFactory();

    @Override
    public T findByUuid(Class<T> clazz, String uuid) {
        return interactWithDB((session -> session.get(clazz, uuid)));
    }

    @Override
    public void update(T entity) {
        try (final Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            // TODO
            throw new NotExistDataBaseException(e.getMessage());
        }
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
    public void delete(T entity) {
        try (final Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            // TODO
            throw new NotExistDataBaseException(e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        interactWithDB((session) -> {
            session.createQuery("DELETE FROM ProductEntity").executeUpdate();
            session.createQuery("DELETE FROM StoreEntity").executeUpdate();
            return null;
        });
    }

    private T interactWithDB(Executor<T> executor) {
        try (final Session session = factory.openSession()) {
            session.beginTransaction();
            T result = executor.execute(session);
            session.getTransaction().commit();
            return result;
        } catch (PersistenceException e) {
            // TODO
            throw new NotExistDataBaseException(e.getMessage());
        }
    }
}
