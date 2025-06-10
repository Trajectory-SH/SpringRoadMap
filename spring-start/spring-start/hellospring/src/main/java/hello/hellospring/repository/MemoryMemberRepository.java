package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; // 키값을 만들어주는 녀석
    //여기서는 동시성 문제를 고려하지 않는다.
    //ID는 회원가입후에 시스템이 저장하는 값으로 넣는다;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //null이 반환될 가능성이 있다면 Optional로 감싸서 반환한다 -> client에서 다른 처리를 할 수있다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        //map에서 findByName을 하려면 memeber 객체에서 . getName...!
        //map의 values() -> Collection 형태로 반환해준다.

        //람다... 중첩 클래스 복습하기!
        return store.values().stream() //Collection을 람다를 사용할 수 있는 stream으로 변환해준다.
                //stream은 내부 반복을 통해 람다식을 사용할 수 있게 만들어준다.
                //filter는 조건을 만족하는 요소만 남기고 나머지는 제거하는 중간연산이다.
                //member(파라미터) 멤버를 사용해서
                //-> 이렇게 계산을 하겠다.
                //이후의 실행하는 본문 -> return값...
                .filter(member -> member.getName().equals(name))
                .findAny();//findAny()는 조건이 맞는 반환값 중에서 하나만 반환한다.
        //끝까지 돌았는데 Null이면 Optional에 Null을 넣어서 반환해준다..
    }

    @Override
    public List<Member> findAll() { //멤버들을 주욱 반환해줌
        //map을 리스트로
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}


// 자 이제 실제로 해당 메서드들이 실제로 동작하는지 확인해봐야한다. -> Test 작성
