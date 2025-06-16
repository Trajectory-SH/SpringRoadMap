package hello.core.scan.filter;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

import java.lang.reflect.Executable;

import static org.springframework.context.annotation.ComponentScan.*;
import static org.springframework.context.annotation.FilterType.*;

public class ComponentFilterAppConfigTest {

    @Test
    void filterTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);//스프링 컨테이너에 들어있는 객체를 해당 정보로 받아온다.
        //Component Scan 방식으로 빈을 등록할때는 해당 클래스의 이름을 기반으로 이름을 생성하는데
        //첫 글자를 소문자로 등록하는 것이 default이다.

        Assertions.assertThat(beanA).isNotNull();
        org.junit.jupiter.api.Assertions.assertThrows(NoSuchBeanDefinitionException.class,
                ()-> ac.getBean("beanB", BeanB.class));

    }

    @Configuration
    @ComponentScan(
            includeFilters = @Filter(type = ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @Filter(type = ANNOTATION, classes = MyExcludeComponent.class)
    )
    static class ComponentFilterAppConfig {

        @Bean(name = "memoryMemberRepository")
        MemberRepository memberRepository() {
            return new MemoryMemberRepository();
        }
    }
}