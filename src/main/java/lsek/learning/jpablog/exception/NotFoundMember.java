package lsek.learning.jpablog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
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
