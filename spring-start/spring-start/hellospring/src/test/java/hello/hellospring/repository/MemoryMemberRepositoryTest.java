package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

//해당 클래스 뒤에 Test를 붙이는 것이 관례이다.
//class레벨에서 전부 한꺼번에 Test를 돌릴 수 있다.
class MemoryMemberRepositoryTest {//Test code는 굳이 public일 필요가..

    MemoryMemberRepository repository = new MemoryMemberRepository();

    //테스트가 하나씩 끝날 때 마다 실행되는 작업...각 테스트의 독립성을 유지하기 위해서 사용된다.
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();
        //Optional에서 실제 요소를 .get()으로 꺼내올 수 있다 -> 권장되는 방법은 아니지만 일단은..!
        //내가 방금 new로 생성한 Member와 findById로 db에서 찾은 member랑 일치하면 되겠구나...!
        //Assetions!

        //Assertions.assertEquals(result, member); -> junit.jupiter
        //assertj을 사용하는 것이 조금 더 편할 것이여.. 최근에는 assertj를 조금 더 많이 사용하는 것 같다.
        assertThat(member).isEqualTo(result);
        //assert = 주장하다.
        //Assertion static Import해서 사용하는 것이 깔끔하게 ..
    }


    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);


        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        //.get()메서드 -> Optional에서 하나 까서 실제 요소를 return해준다.
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);


        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);
        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }

    //findAll()에대한 test를 진행한 뒤에 class단위로 다시 테스트를 돌렸는데 findByName() Test가 실패했다...!
    //테스트의 실행 순서는 무작위다. 독립적이어야한다... 순서에 의존적이게 설계되면 안됨..
    //Test가 끝나면 데이터를 클리어해줘야한다.
    //테스트는 서로의 의존관계없이 구성되어야한다...!


    //MemoryMemberRepository -> Test
    //순서를 테스트 먼저 작성한다면 TDD(Test Driven Develop) 먼저 검증할 수 있는 틀을 만들어 놓는다. 테스트 주도 개발



}
