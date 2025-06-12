package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        //Impl(구현타입) 으로 꺼내야지 테스트용으로 만들어놓은 get 메서드를 사용할 수 있다.
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);


        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();


        //세 개의 spring Bean이 전부 같은 주소를 참조하고 있다...?!
        System.out.println("memberService-> memberRepository1 = " + memberRepository1);
        System.out.println("orderService-> memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);


        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
        //혹시 두 번 호출이 되는 것이 아니여,.?
    }


    @Test
    void configurationDeep() {
        //AppConfig.class를 설정정보로 Spring container를 생성하면 Spring Bean으로 AppConfig도 등록이 된다.
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class); //조회가 가능한 이유는 자식 클래스인 AppConfig@CGLIB이 존재하기 때문이다!!
        //부모타입으로 조회...!!

        //@Configuration을 붙이지 않아도 Spring Bean으로도 잘 등록이 된다.
        //그러나 @Bean만 붙인다면 싱글톤을 보장해주는 CGLIB를 실행시키지 않는다 ! ! !


        //AppConfig$$SpringCGLIB$$0  ==> CGLIB !!!
        //스프링 빈을 만드는 과정에서 CGLIB 바이트코드 조작 라이브러리를 이용한다.. Instance(actual) => AppConfig@CGLIB !!
        System.out.println("bean = " + bean.getClass());

        /**
         * @Bean 이 붙어 있는 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고, 스프링 빈이 없으면 생성해서 스프링 빈으로
         * 등록한다.
         *
         * 이를 통해서 Spring Container는 Singleton을 보장할 수 있다 by CGLIB
         */


    }
}
