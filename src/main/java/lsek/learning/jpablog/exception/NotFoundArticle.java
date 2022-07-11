package lsek.learning.jpablog.exception;

public class NotFoundArticle extends RuntimeException{

    public NotFoundArticle(Throwable cause) {
        super(cause);
    }

    public NotFoundArticle() {
        super();
    }

    public NotFoundArticle(String message) {
        super(message);
    }
}
