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
import java.util.Collections;
import java.util.List;

public abstract class AbstractDaoImpl<T> implements Dao<T> {
    private final SessionFactory factory = DBService.getFactory();

    @Override
    public T findByUuid(Class<T> clazz, String uuid) throws NotExistDataBaseException {
        final List<T> list = interactWithDB((session -> {
            final T entity = session.get(clazz, uuid);
            if (entity == null) {
                throw new NotExistDataBaseException(String.format("Couldn't found object with uuid %s", uuid));
            }
            return Collections.singletonList(entity);
        }));
        return list.get(0);
    }

    @Override
    public T findByPattern(Class<T> clazz, String fieldName, String pattern) throws NotExistDataBaseException {
        final List<T> list = interactWithDB(session -> {
            final CriteriaBuilder builder = session.getCriteriaBuilder();
            final CriteriaQuery<T> criteria = builder.createQuery(clazz);
            final Root<T> root = criteria.from(clazz);
            final Predicate predicate = builder.like(root.get(fieldName), pattern);
            criteria.select(root).where(predicate);
            final T entity = session.createQuery(criteria).getSingleResult();
            if (entity == null) {
                throw new NotExistDataBaseException(String.format("Couldn't found object with %s %s", fieldName, pattern));
            }
            return Collections.singletonList(entity);
        });
        return list.get(0);
    }

    @Override
    public List<T> getAll(Class<T> clazz) {
        return interactWithDB((session -> {
            final CriteriaBuilder builder = session.getCriteriaBuilder();
            final CriteriaQuery<T> criteria = builder.createQuery(clazz);
            final Root<T> root = criteria.from(clazz);
            criteria.select(root);
            return session.createQuery(criteria).getResultList();
        }));
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

    private List<T> interactWithDB(Executor<T> executor) {
        try (final Session session = factory.openSession()) {
            session.beginTransaction();
            List<T> result = executor.execute(session);
            session.getTransaction().commit();
            return result;
        } catch (OptimisticLockException | NoResultException e) {
            throw new NotExistDataBaseException(e.getMessage());
        } catch (PersistenceException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

}
