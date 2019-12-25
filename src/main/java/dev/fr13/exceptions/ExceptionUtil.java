package dev.fr13.exceptions;

import org.postgresql.util.PSQLException;

import javax.persistence.PersistenceException;

public class ExceptionUtil {
    public static DataBaseException convertException(PersistenceException e) {
        // TODO
        final String uniqueViolation = "23505";
        if (((PSQLException) e.getCause().getCause()).getSQLState().equals(uniqueViolation)) {
            return new ExistDataBaseException(e.getMessage());
        } else {
            return new DataBaseException(e.getMessage());
        }
    }
}
