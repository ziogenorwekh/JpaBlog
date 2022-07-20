package lsek.learning.jpablog.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID", nullable = false)
    private Long id;

    private String comment;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date cDate;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;


    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
        this.member.getComments().add(this);
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getcDate() {
        return cDate;
    }

    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", cDate=" + cDate +
                ", memberName=" + member.getName() +
                ", article=" + article +
                '}';
    }
}
