package lsek.learning.jpablog.exception;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@ControllerAdvice
@NoArgsConstructor
public class CustomExceptionHandler {

    @ExceptionHandler(NotFoundMember.class)
    public ExceptionResponse memberNotFound() {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "멤버 에러", "멤버가 존재하지 않습니다.");
        return exceptionResponse;
    }

    @ExceptionHandler(NotFoundArticle.class)
    public ExceptionResponse articleNotFound() {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "게시물 에러", "게시물이 존재하지 않습니다.");
        return exceptionResponse;
    }

}
