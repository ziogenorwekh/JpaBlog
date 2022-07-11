package lsek.learning.jpablog.service;

import lsek.learning.jpablog.domain.Address;
import lsek.learning.jpablog.domain.Member;
import lsek.learning.jpablog.exception.DuplicateUniqueEmail;
import lsek.learning.jpablog.repo.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
//@DataJpaTest
//@SpringJUnitConfig(locations = "/application.properties")
@Transactional
@SuppressWarnings("all")
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    public void 회원가입() {
        Member member = new Member();
        member.setName("테스트");

        memberService.save(member);

        Optional<Member> membersByName = memberRepository.findMembersByName("테스트");
        assertThat(membersByName.get()).isEqualTo(member);
    }

    @Test
    public void 중복회원가입() {
        Member member = new Member();
        member.setName("중복");
        memberService.save(member);

        Member member1 = new Member();
        member1.setName("중복");
        org.junit.jupiter.api.Assertions.assertThrows(DuplicateUniqueEmail.class, ()->
                memberService.save(member1),"중복된 회원입니다.");
    }

    @Test
    public void 로그인() {
        Member member = new Member();
        member.setEmail("멜@1");
        member.setPassword("pwd");
        memberService.save(member);
        Member pwd = memberService.login("멜@1", "pwd");
        assertThat(pwd).isNotNull();
    }

    @Test
    public void 회원정보수정() {

        Member member = new Member();
        member.setName("name");
        member.setEmail("email");
        member.setPassword("pwd");
        Address address = new Address();
        address.setCity("city");
        address.setStreet("street");
        member.setAddress(address);

        memberService.save(member);

        Optional<Member> name = memberRepository.findMembersByName("name");
        Address update = new Address();
        update.setCity("newCity");
        update.setStreet("newStreet");
        name.get().setAddress(update);

        memberService.update(name.get());

        Address test = new Address();
        test.setCity("newCity");
        test.setStreet("newStreet");

        assertThat(memberRepository.findMembersByName("name").get().getAddress()).isEqualTo(test);
    }
}