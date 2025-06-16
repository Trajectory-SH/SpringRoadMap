package hello.remind.core.remind.beanfind;

import hello.remind.core.remind.AppConfig;
import hello.remind.core.remind.member.MemberService;
import hello.remind.core.remind.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextBasicFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        //스프링컨테이너.getBean() 의 Return Type은 Object 타입이다... -> 불안한 DownCasting이 필요함
        MemberService bean = (MemberService) ac.getBean("memberService");
        System.out.println("bean = " + bean);
        assertThat(bean).isInstanceOf(MemberService.class);
    }

    @Test
    @DisplayName("이름 없이 타입만으로 조회")
    void findBeanBeanByType() {
        MemberService bean = ac.getBean(MemberService.class);
        assertThat(bean).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("구체적인 타입으로 조회")
    void findBeanByName2() {

        MemberServiceImpl bean = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(bean).isInstanceOf(MemberServiceImpl.class);
    }
    @Test
    @DisplayName("빈 이름으로 조회 X")
    void findBeanByNameX() {
        assertThrows(NoSuchBeanDefinitionException.class, () ->
            ac.getBean("xxxx", MemberServiceImpl.class));
    }
    /**
     * 참고 | 구체 타입으로 스프링 빈을 조회하면 유연성이 많이 떨어진다.
     */
}
