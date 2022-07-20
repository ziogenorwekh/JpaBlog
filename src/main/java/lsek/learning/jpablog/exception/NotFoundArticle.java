package lsek.learning.jpablog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
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
