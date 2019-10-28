package dao;

import exceptions.ExistDataBaseException;
import exceptions.NotExistDataBaseException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.DBService;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class AbstractDaoImpl<T> implements Dao<T> {
    private final SessionFactory factory = DBService.getFactory();

    @Override
    public T findByUuid(Class<T> clazz, String uuid) {
        return interactWithDB((session -> {
            final T entity = session.get(clazz, uuid);
            if (entity == null) {
                throw new NotExistDataBaseException("Not found");
            }
            return entity;
        }));
    }

    @Override
    public T findByDescription(Class<T> clazz, String description) {
        return interactWithDB(session -> {
            final CriteriaBuilder builder = session.getCriteriaBuilder();
            final CriteriaQuery<T> criteria = builder.createQuery(clazz);
            final Root<T> root = criteria.from(clazz);
            final Predicate predicate = builder.like(root.get("description"), description);
            criteria.select(root).where(predicate);
            final T entity = session.createQuery(criteria).getSingleResult();
            if (entity == null) {
                throw new NotExistDataBaseException("Not found");
            }
            return entity;
        });
    }

    @Override
    public void update(T entity) {
        interactWithDB((session -> {
            session.update(entity);
            if (entity == null) {
                throw new NotExistDataBaseException("Not found");
            }
            return entity;
        }));
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
