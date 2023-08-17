package next.exception;

public class DataAccessException extends RuntimeException {

    public DataAccessException() {
        super();
    }

    public DataAccessException(final String message) {
        super(message);
    }
}
