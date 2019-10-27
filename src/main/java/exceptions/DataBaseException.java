package exceptions;

public class DataBaseException extends RuntimeException {

    public DataBaseException(String msq) {
        super(msq);
    }
}
