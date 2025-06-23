package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {
    //실무에서는 동시성 고려를 해야한다..
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; // 하나씩 증가한다// ..

    private static final MemberRepository instance = new MemberRepository();

    private MemberRepository() {}

    public static MemberRepository getInstance() {
        return instance;
    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        //store에 있는 녀석들을 건드리고 싶지 않아서 그럼...
    }

    public void clearStore() {
        store.clear();
    }
}