package hello.remind.core.remind.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


//DB 접근 기술 영역 => 실제 데이터 베이스에 도메인 영역의 데이터들을 집어넣는다.
@Component
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);

    }

    @Override
    public Member findById(Long memberID) {
        return store.get(memberID);
    }
}
