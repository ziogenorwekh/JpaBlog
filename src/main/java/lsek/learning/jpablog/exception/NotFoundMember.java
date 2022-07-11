package lsek.learning.jpablog.exception;

public class NotFoundMember extends RuntimeException{
    public NotFoundMember() {
        super();
    }

    public NotFoundMember(String message) {
        super(message);
    }

    public NotFoundMember(Throwable cause) {
        super(cause);
    }
}
