package lsek.learning.jpablog.service;

import lsek.learning.jpablog.domain.Comment;
import lsek.learning.jpablog.domain.Member;
import lsek.learning.jpablog.exception.AccessError;
import lsek.learning.jpablog.exception.DuplicateUniqueEmail;
import lsek.learning.jpablog.exception.NotFoundMember;
import lsek.learning.jpablog.repo.ArticleRepository;
import lsek.learning.jpablog.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public Long save(Member member) {
        validateUniqueEmail(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateUniqueEmail(Member member) {
        memberRepository.findMembersByEmail(member.getEmail()).ifPresent(m ->
        {
            throw new DuplicateUniqueEmail("회원 이메일 중복");
        });
    }

    // primary Key 를 제외한 모든 정보는 변경 가능
    // 유니크 키의 정보를 확인하고 변경해야 한다.
    public Long update(Member member) {
        memberRepository.findById(member.getId());
        memberRepository.save(member);
        return member.getId();
    }

    public Member login(String email, String pwd) {
        Optional<Member> containsEmpty = memberRepository.findMembersByEmailAndPassword(email, pwd);
        Member member = containsEmpty.orElseThrow(AccessError::new);
        return member;
//        if (member.isPresent()) {
//            return member;
//        }
//        return Optional.empty();
    }

    public Member findByEmail(String email) {
        Optional<Member> findByEmail = memberRepository.findMembersByEmail(email);
        Member member = findByEmail.orElseThrow(NotFoundMember::new);
        return member;
    }

    public Member findOne(Long id) {
        Optional<Member> containsEmpty = memberRepository.findById(id);
        Member member = containsEmpty.orElseThrow(NotFoundMember::new);
        return member;
    }

    public List<Member> findMembers(Pageable pageable) {
//        PageRequest pageRequest = PageRequest.of(0, 6);
        Page<Member> all = memberRepository.findAll(pageable);
        System.out.println("all.getTotalElements() = " + all.getTotalElements());
        System.out.println("all.getTotalPages() = " + all.getTotalPages());
        List<Member> content = all.getContent();
        return content;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}

