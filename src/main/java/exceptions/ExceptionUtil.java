package exceptions;

import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.PersistenceException;

public class ExceptionUtil {
    public static DataBaseException convertException(PersistenceException e) {
        // TODO
        final String uniqueViolation = "23505";
        if (((ConstraintViolationException) e).getSQLState().equals(uniqueViolation)) {
            return new ExistDataBaseException(e.getMessage());
        } else {
            return new DataBaseException(e.getMessage());
        }
    }
}
