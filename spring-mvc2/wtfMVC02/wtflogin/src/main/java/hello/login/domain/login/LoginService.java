package hello.login.domain.login;


import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    //생성자가 하나뿐이라면 @Autowired를 자동으로 적용해준다.
    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)//로그인 아이디를 던져서 member를 우선 찾는다.
                .filter(member -> member.getPassword().equals(password))//Optional에 바로 필터 적용 가능 -> Optional안에 있는 Member 꺼내와서 filter 실행
                .orElse(null);
    }

}
