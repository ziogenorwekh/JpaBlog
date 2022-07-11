package lsek.learning.jpablog;

import lsek.learning.jpablog.domain.Member;
import lsek.learning.jpablog.service.MemberService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class JpaBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaBlogApplication.class, args);
    }

}
