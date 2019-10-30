package database.dao;

import exceptions.ExceptionUtil;
import exceptions.NotExistDataBaseException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.DBService;

import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class AbstractDaoImpl<T> implements Dao<T> {
    private final SessionFactory factory = DBService.getFactory();

    @Override
    public T findByUuid(Class<T> clazz, String uuid) throws NotExistDataBaseException {
        return interactWithDB((session -> {
            final T entity = session.get(clazz, uuid);
            if (entity == null) {
                throw new NotExistDataBaseException(String.format("Couldn't found object with uuid %s", uuid));
            }
            return entity;
        }));
    }

    @Override
    public T findByPattern(Class<T> clazz, String fieldName, String pattern) throws NotExistDataBaseException {
        return interactWithDB(session -> {
            final CriteriaBuilder builder = session.getCriteriaBuilder();
            final CriteriaQuery<T> criteria = builder.createQuery(clazz);
            final Root<T> root = criteria.from(clazz);
            final Predicate predicate = builder.like(root.get(fieldName), pattern);
            criteria.select(root).where(predicate);
            return session.createQuery(criteria).getSingleResult();
        });
    }

    @Override
    public void update(T entity) {
        interactWithDB((session -> {
            session.update(entity);
            return null;
        }));
    }

    @Override
    public void save(T entity) {
        interactWithDB(session -> {
            session.save(entity);
            return null;
        });
    }

    @Override
    public void delete(T entity) {
        interactWithDB(session -> {
            session.delete(entity);
            return null;
        });
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
        } catch (OptimisticLockException | NoResultException e) {
            throw new NotExistDataBaseException(e.getMessage());
        } catch (PersistenceException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

}
