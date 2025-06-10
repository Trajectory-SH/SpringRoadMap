package hello.hellospring.service;//자동으로 만들어진다 test단에 같은 패키지 이름으로...

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.assertj.core.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    //동작하기 전에 작동하는 것
    @BeforeEach//각각의 테스트가 실행되기 전에 작동한다.
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
        //이러면 같은 메모리 멤버 리포지토리를 사용하게된다.
        //Dependency Injection!!!

    }


    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }


    //사실 테스트 코드는 과감하게 한글로 바꿔도 된다... 명료도가 올라감.
    //어차피 빌드될 때 Test코드들은 포함되지 않는다.

    //Test code를 작성할때는 Given When Then 방식으로 작성하는 것이 좋다. 이 패턴을 연습하고 나중에 변형하자.
    @Test
    void 회원가입() {

        //Given-이런 상황이 주어지고(이 데이터를 사용하는 거구나...!)
        Member member = new Member();
        member.setName("hello");

        //When-이것을 실행했을 때(이것을 검증하는거다)
        Long saveId = memberService.join(member);

        //Then-결과가 이렇게 나와야해...!
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
       /* try {
            memberService.join(member2);//예외가 터지는 것을 잡아줄 수 있는가...
            //원래는 예외가 터지면 바로 catch문으로 가야한다.
            fail();
        } catch (IllegalStateException e) {
            //예외가 터져서 성공한 것
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/

        //then

        //해당 Exception이 터지면...

        //() -> memberService.join(member2)이 실행되었을 때 IllegalStateException.class 가 터져야한다.
        //“memberService.join(member2)를 실행했을 때 IllegalStateException이 반드시 발생해야 테스트가 성공이다.”

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        //return값으로 받을 수 있다.

        //에러 메시지까지 같은지 검증해본다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //assertThrows -> Junit5에서 제공하는 예외 검증 메서드
        //assertThrows(발생해야하는 예외 클래스, excutable(실제로 실행하는 클래스) -> 람다식으로 넘긴다.)

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}