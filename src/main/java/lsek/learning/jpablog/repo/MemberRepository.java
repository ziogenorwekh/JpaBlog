package lsek.learning.jpablog.repo;

import lsek.learning.jpablog.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findMembersByEmail(String email);

    Optional<Member> findMembersByName(String name);

    @Query("select m from Member m where m.email = ?1 and m.password = ?2")
    Optional<Member> findMembersByEmailAndPassword(String email, String password);

}
