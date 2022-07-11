package lsek.learning.jpablog.exception;

public class DuplicateUniqueEmail extends RuntimeException {
    public DuplicateUniqueEmail() {
        super();
    }

    public DuplicateUniqueEmail(Throwable cause) {
        super(cause);
    }

    public DuplicateUniqueEmail(String message) {
        super(message);
    }
}
