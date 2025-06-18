package springStart.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springStart.core.AppConfig;
import springStart.core.member.MemberService;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        //memberService != memberService2
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴 적용 객체 사용")
    void singletonServiceTest() {
        //구체 클래스에 의존하는데..!! DIP 원칙 위반
        SingletonService singletonInstance1 = SingletonService.getInstance();
        SingletonService singletonInstance2 = SingletonService.getInstance();

        //같은 인스턴스가 반환된다. -> 이미 java 클래스 로드가 진행될때 method 영역에 등록되어있다.
        System.out.println("singletonInstance1 = " + singletonInstance1);
        System.out.println("singletonInstance2 = " + singletonInstance2);

        assertThat(singletonInstance1).isSameAs(singletonInstance2);
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService singletonInstance1 = ac.getBean(MemberService.class);
        MemberService singletonInstance2 = ac.getBean(MemberService.class);


        //처음에 스프링 컨테이너가
        System.out.println("singletonInstance1 = " + singletonInstance1);
        System.out.println("singletonInstance2 = " + singletonInstance2);

        assertThat(singletonInstance1).isSameAs(singletonInstance2);
    }
}
