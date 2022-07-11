package lsek.learning.jpablog.controller;

import lsek.learning.jpablog.service.ArticleService;
import lsek.learning.jpablog.service.MemberService;
import org.springframework.stereotype.Controller;

@Controller
public class CommentController {
    MemberService memberService;
    ArticleService articleService;

    public CommentController(MemberService memberService, ArticleService articleService) {
        this.memberService = memberService;
        this.articleService = articleService;
    }

//    public String
}

