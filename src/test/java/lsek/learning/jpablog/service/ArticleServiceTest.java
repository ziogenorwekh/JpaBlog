package lsek.learning.jpablog.service;

import lsek.learning.jpablog.domain.Article;
import lsek.learning.jpablog.domain.Comment;
import lsek.learning.jpablog.domain.Member;
import lsek.learning.jpablog.exception.NotFoundArticle;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
//@SpringJUnitConfig(locations = "/application.properties"
@Transactional
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;
    @Autowired
    MemberService memberService;

    @Test
    public void 글하나저장() {
        Article article = new Article();
        article.setcDate(new Date());
        article.setTitle("제목");
        article.setContents("내용");

        articleService.save(article);
    }

//    @BeforeEach
//    public void 아티클디폴트() {
//        Article article = new Article();
//        article.setMember(memberService.findByEmail("ad@ad.com"));
//        article.setTitle("제목");
//        article.setContents("내용");
//        articleService.save(article);
//    }

    @Test
    public void 글하나찾기() {
        Member member = memberService.findByEmail("ad@ad.com");
        Article articleByMemberId = articleService.findArticleByMemberId(member.getId());

        assertThat(articleByMemberId).isNotNull();
        assertThat(articleByMemberId.getTitle()).isEqualTo("제목");
        assertThat(articleByMemberId.getContents()).isEqualTo("내용");
    }

    @Test
    public void 존재하지않는멤버가게시물을가지고있을까() {
        org.junit.jupiter.api.Assertions.assertThrows(NotFoundArticle.class,
                ()->articleService.findArticleByMemberId(1000000L),"게시물 없음");
    }

    @Test
    public void 게시글업데이트() {
        Member member = new Member();
        member.setEmail("메일");
        member.setName("이름");
        memberService.save(member);
        Article article = new Article();
        article.setTitle("제목입니다.");
        article.setContents("내용입니다.");
        article.setMember(member);
        articleService.save(article);
        System.out.println("article = " + article);
        Long id = article.getId();

        Article one = articleService.findOne(id);
        one.setTitle("제목 수정");
        one.setContents("내용 수정");
        articleService.save(article);

        Article one1 = articleService.findOne(one.getId());
        assertThat(one1.getTitle()).isEqualTo("제목 수정");
        assertThat(one1.getContents()).isEqualTo("내용 수정");
        assertThat(one1.getMember()).isEqualTo(member);
    }

    @Test
    public void 누구의댓글일까() {
        Article title = articleService.findByTitle("제목");
        System.out.println("title = " + title);
        for (Comment comment : title.getComments()) {
            System.out.println("comment = " + comment);
        }
    }
}