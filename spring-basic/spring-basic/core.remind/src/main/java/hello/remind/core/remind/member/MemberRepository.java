package hello.remind.core.remind.member;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberID);
}
