package lsek.learning.jpablog.service;

import lsek.learning.jpablog.domain.Article;
import lsek.learning.jpablog.domain.Comment;
import lsek.learning.jpablog.domain.Member;
import lsek.learning.jpablog.exception.NotFoundArticle;
import lsek.learning.jpablog.repo.ArticleRepository;
import lsek.learning.jpablog.repo.MemberRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ArticleService {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ArticleRepository articleRepository;

    public Long save(Article article) {
        if (article.getId() != null) {
            article.setuDate(new Date());
        }
        articleRepository.save(article);
        return article.getId();
    }

    public Long commentUpdate(Article article, Comment comment, Long memberId) {
        comment.setcDate(new Date());
        comment.setArticle(article);
        Member member = memberRepository.findById(memberId).get();
        comment.setMember(member);
        // ?
//        article.getComments().add(comment);
        member.getComments().add(comment);
        articleRepository.save(article);
        return article.getId();
    }

    public Article findOne(Long id) {
        Optional<Article> optional = articleRepository.findById(id);
        Article article = optional.orElseThrow(NotFoundArticle::new);
        return article;
    }


    public Article findByTitle(String title) {
        Optional<Article> articleOptional = articleRepository.findArticleByTitle(title);
        Article article = articleOptional.orElseThrow(NotFoundArticle::new);
        return article;
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findArticleByMemberId(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            Article article = articleRepository.findArticleByMember(member).get();
            return article;
        }
        throw new NotFoundArticle("게시물 없음");
    }
}
