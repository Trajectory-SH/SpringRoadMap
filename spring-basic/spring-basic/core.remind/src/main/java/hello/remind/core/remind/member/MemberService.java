package hello.remind.core.remind.member;

public interface MemberService {
    //회원 가입
    void join(Member member);

    Member findMember(Long memberID);

}
