package database.dao;

import org.hibernate.Session;

import java.util.List;

@FunctionalInterface
public interface Executor<T> {
    List<T> execute(Session session);
}
