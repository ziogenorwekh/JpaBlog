package lsek.learning.jpablog.controller;

import lsek.learning.jpablog.domain.Article;
import lsek.learning.jpablog.domain.Comment;
import lsek.learning.jpablog.domain.Member;
import lsek.learning.jpablog.service.ArticleService;
import lsek.learning.jpablog.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class ArticleController {
    final private MemberService memberService;
    final private ArticleService articleService;

    public ArticleController(MemberService memberService, ArticleService articleService) {
        this.memberService = memberService;
        this.articleService = articleService;
    }

    @GetMapping("article/articleList")
    public String list(Model model) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "article/articleList";
    }

    @GetMapping("article/addArticle")
    public String createArticleForm(HttpSession session) {
        Long id = (Long) session.getAttribute("login");
        if (id == null) {
            return "redirect:/members/login";
        }
        return "article/addArticle";
    }

    @PostMapping("article/addArticle")
    public String createArticle(@ModelAttribute Article article, HttpSession session) {
        Long id = (Long) session.getAttribute("login");
        Member member = memberService.findOne(id);
        article.setMember(member);
        article.setcDate(new Date());
        articleService.save(article);
        return "redirect:/article/articleList";
    }

    @GetMapping("article/articleInfo/{id}")
    public String articleInfo(@PathVariable Long id, Model model) {
        Article one = articleService.findOne(id);
//        System.out.println("one = " + one);
        model.addAttribute("article", one);
        return "article/articleInfo";
    }

    @PostMapping("article/articleInfo/{id}")
    public String addComment(@PathVariable Long id, @RequestParam("comment") String comments, HttpSession session) {
        Long login = (Long) session.getAttribute("login");
        if (login == null) {
            return "redirect:/members/login";
        }
        Article article = articleService.findOne(id);
        Comment comment = new Comment();
        comment.setComment(comments);
        articleService.commentUpdate(article, comment, login);
        return "redirect:/article/articleInfo/{id}";
    }

    @GetMapping("article/updateArticle/{id}")
    public String updateArticleForm(@PathVariable Long id, Model model) {
        Article article = articleService.findOne(id);
        model.addAttribute("article", article);
        return "article/updateArticle";
    }

    @PostMapping("article/updateArticle/{id}")
    public String updateArticle(@ModelAttribute Article article) {
        articleService.save(article);
        return "redirect:/article/articleList";
    }

//    @PostConstruct
//    public void articleCreate() {
//
//        Article article = new Article();
//        article.setTitle("제목");
//        article.setContents("내용");
//        article.setcDate(new Date());
//        Member byEmail = memberService.findByEmail("ad@ad.com");
//        article.setMember(byEmail);
//        articleService.save(article);
//
////        Article one = articleService.findOne(8L);
//        Article one = articleService.findByTitle("제목");
//        Member member = memberService.findByEmail("ad@ad.com");
//        for (int i = 0; i < 4; i++) {
//            Comment comment = new Comment();
//            comment.setcDate(new Date());
//            comment.setArticle(one);
//            comment.setComment("내가 만든 게시글이야" + i);
//            comment.setMember(member);
////            member.getComments().add(comment);
//            one.getComments().add(comment);
////            memberService.update(member);
//            articleService.save(one);
//        }
//    }
}