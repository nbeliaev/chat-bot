package database.dao;

import exceptions.ExceptionUtil;
import exceptions.ExistDataBaseException;
import exceptions.NotExistDataBaseException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
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
    public List<T> findByPattern(Class<T> clazz, String fieldName, String pattern) {
        return interactWithDB(session -> {
            final CriteriaQuery<T> criteria = getCriteriaQuery(clazz, session, pattern, fieldName);
            return session.createQuery(criteria).getResultList();
        });
    }

    @Override
    public T findByName(Class<T> clazz, String name) throws NotExistDataBaseException {
        final List<T> list = interactWithDB(session -> {
            final CriteriaQuery<T> criteria = getCriteriaQuery(clazz, session, name, "name");
            final T entity = session.createQuery(criteria).getSingleResult();
            if (entity == null) {
                throw new NotExistDataBaseException(String.format("Couldn't found object with name %s", name));
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
    public void save(T entity) throws ExistDataBaseException {
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
            session.createQuery("DELETE FROM PriceEntity ").executeUpdate();
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

    @NotNull
    private CriteriaQuery<T> getCriteriaQuery(Class<T> clazz, Session session, String pattern, String name) {
        final CriteriaBuilder builder = session.getCriteriaBuilder();
        final CriteriaQuery<T> criteria = builder.createQuery(clazz);
        final Root<T> root = criteria.from(clazz);
        final Predicate like = builder.like(root.get(name), "%" + pattern + "%");
        criteria.select(root).where(like);
        return criteria;
    }

}
