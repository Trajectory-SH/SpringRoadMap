package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member {}", member);
        store.put(member.getId(), member);
        return member;
    }

    //서버에서 설정한 ID를 통해서 내부에서 찾는 logic
    public Member findById(Long id) {
        return store.get(id);
    }

    //실제 사용자가 입력한 ID를 이용해서 login하는 logic
    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()//List를 Stream이라는 것으로 바꾼다. 마치 루프를 돌듯이 동작(JAVA 8)
                .filter(member -> member.getLoginId().equals(loginId))//해당 조건에 만족하는 것들만 찾아서 다음 단계로 넘어가게된다.
                .findFirst();//위의 filter 조건에 만족하는 것에서 첫 번째로 나온 것을 바로 반환
        /*
        Optional.of(m) or Optional.empty(); -> 회원 객체가 있을수도 있고 없을수도 있다.
        null을 직접 반환하는 것이 아니라 Optional< >이라는 껍데기로 감싸서 return하는 것이 최근의 코드 스타일
        */
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
