package lsek.learning.jpablog.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private Date timestamp; // 예외 발생 시간 정보
    private String message; // 예외 메세지
    private String details; // 예외 상세정보
}
