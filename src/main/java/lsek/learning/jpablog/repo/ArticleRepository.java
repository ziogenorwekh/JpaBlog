package lsek.learning.jpablog.repo;

import lsek.learning.jpablog.domain.Article;
import lsek.learning.jpablog.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    @Query("select a from Article a where a.member = ?1")
    Optional<Article> findArticleByMember(Member member);

    Optional<Article> findArticleByTitle(String title);
}
