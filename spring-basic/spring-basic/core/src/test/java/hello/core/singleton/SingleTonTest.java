package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SingleTonTest {


    @Test
    @DisplayName("스프링 X 순수 DI 컨테이너")
//문제점을 확인해보자
    void pureContainer() {

        AppConfig appConfig = new AppConfig();

        //1. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        //2. 조회 : 다른 클라이언트도 memberService를 호출
        MemberService memberService2 = appConfig.memberService();

        //자바 코드 console out으로 확인 -> 실무에서는 해당 방식을 지양한다.
        //Test 프레임워크로 직접 눈으로 확인하는 것이 아니라 자동화 되게 만들어야한다. ex) JUnit
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        //memberService1 != memberService2
        //isNotSameAs()와 isNotEqualsTo()의 차이란 무엇일까...?
        assertThat(memberService1).isNotSameAs(memberService2);

        //고객 트래픽이 초당 100이 나오면 초당 100개 객체가 생성되고 소멸된다.. -> 메모리 낭비가 심함...!
        // 객체를 딱 하나만 생성하는 싱글톤 패턴을 적용해보자 ! !

        //똑같은 객체 타입의 인스턴스를 딱 하나만 생성할 수 있게 되는 것...?
    }

    //@Test 어노테이션은 JUnit에서 제공하는 테스트 메서드 표시용 어노테이션이다.
    // 이 어노테이션이 붙은 메서드는 테스트 프레임워크가 실행시 자동으로 감지하고 실행함.
    //JVM이 런타임에 메타데이터로 읽어들임 => 리플렉션 API를 통해서 메서드 메탕데이터 정보들을 로딩 & 실행
    //JVM이 리플렉션으로 메서드를 찾아내고 Method.invoke()로 해당 테스트 메서드를 실행한다.
    //@Test 어노테이션이 없으면 JUnit Launcher가 테스트 메서드로 간주하지 않는다.
    @Test
    @DisplayName("싱글톤 패턴을 적요한 객체 사용")
    void singletonServiceTest() {
        /* new SingletonService();
           ERROR CODE => java: SingletonService() has private access */
        SingletonService instance1 = SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();

        System.out.println("instance1 = " + instance1);
        System.out.println("instance2 = " + instance2);

        assertThat(instance1).isSameAs(instance2);
        //same -> 참조비교 (동일성)
        //equal -> 동등성
    }

    //스프링 컨테이너를 쓰면 기본적으로 싱글톤 패턴으로 객체를 생성해서 Bean에 등록한다.
    //이전의 컨테이너 생성과정을 잘 생각해보자...
    //빈 객체를 하나씩 생성해서 스프링 저장소에 등록한다 -> 빈 이름 + 객체를 해시맵 구조로 저장 -> 싱글톤 레지스터리


    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService1 = ac.getBean(MemberService.class);
        MemberService memberService2 = ac.getBean(MemberService.class);


        //조회할 때 같은 객체를 반환해준다
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        assertThat(memberService1).isSameAs(memberService2);

        //그런데 요청할 때 마다 새로운 객체를 생성해서 반환하는 기능들도 Spring에서 제공한다... => 빈 스코프에 설명한다.
        //HTTP Session의 cycle맞추는 경우 등... 활용한다. 그러나 99%는 싱글톤 컨테이너를 사용한다.


        //싱글톤 방식의 주의점!!?! -> 정말로 중요하다 제대로 못하면 실무에서 큰 고난을 맞이할 수 있다.


    }
}
