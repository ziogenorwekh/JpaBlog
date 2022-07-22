package lsek.learning.jpablog.auth;

import lombok.RequiredArgsConstructor;
import lsek.learning.jpablog.domain.Member;
import lsek.learning.jpablog.exception.NotFoundMember;
import lsek.learning.jpablog.repo.MemberRepository;
import org.springframework.nativex.hint.SerializationHint;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> members = memberRepository.findMembersByEmail(email);
        members.orElseThrow(NotFoundMember::new);
        return new CustomUserDetails(members.get());
    }
}
