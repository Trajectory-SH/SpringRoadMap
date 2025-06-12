package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    //MemberRepository 인터페이스의 정보만 존재한다. -> 생성자를 통해서 주입한다.
    private final MemberRepository memberRepository;

    @Autowired //의존관계를 자동으로 주입해준다...! // ac.getBean(MemberRepository.class)처럼 동작한다.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }



    //테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
