package exceptions;

public class ExistDataBaseException extends RuntimeException {

    public ExistDataBaseException(String msq) {
        super(msq);
    }
}
