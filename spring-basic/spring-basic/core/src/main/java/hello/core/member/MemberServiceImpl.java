package hello.core.member;

public class MemberServiceImpl implements MemberService{

    //MemberRepository 인터페이스의 정보만 존재한다. -> 생성자를 통해서 주입한다.
    private final MemberRepository memberRepository;

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
}
