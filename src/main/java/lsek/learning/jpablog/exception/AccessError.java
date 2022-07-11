package lsek.learning.jpablog.exception;

public class AccessError extends RuntimeException{
    public AccessError() {
        super();
    }

    public AccessError(String message) {
        super(message);
    }

    public AccessError(Throwable cause) {
        super(cause);
    }
}
