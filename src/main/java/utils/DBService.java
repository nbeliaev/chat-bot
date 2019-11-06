package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBService {
    private static SessionFactory factory;

    public static SessionFactory getFactory() {
        if (factory == null) {
            factory = buildFactory();
        }
        return factory;
    }

    private static SessionFactory buildFactory() {
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    private DBService() {
        throw new UnsupportedOperationException();
    }
}
