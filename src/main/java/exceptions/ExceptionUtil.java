package exceptions;

import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.PersistenceException;

public class ExceptionUtil {
    public static DataBaseException convertException(PersistenceException e) {
        // TODO
        if (e.getCause() instanceof ConstraintViolationException) {
            return new ExistDataBaseException(e.getMessage());
        } else {
            return new DataBaseException(e.getMessage());
        }
    }
}
