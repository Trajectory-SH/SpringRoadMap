package hello.remind.core.remind.member;

//해당 인터페이스에 대해서 구현체가 하나밖에 없으면 {InterfaceName} + Impl 방식으로 이름을 만드는 것이 관례이다.
public class MemberServiceImpl implements MemberService {
    //다형성의 활용 MemoryMemberRepository를 실제 구현으로 사용
    //다만 추상적인 것과 구체적인 것 둘 다 의존하고 있는 상태이다.
    private final MemberRepository memberRepository;


    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberID) {
        return memberRepository.findById(memberID);
    }

}
