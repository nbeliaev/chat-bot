package dao;

import org.hibernate.Session;

@FunctionalInterface
public interface Executor<T> {
    T execute(Session session);
}
