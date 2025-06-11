package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextSameBeanFindTest {
    //ApplicationContext: Spring Container -> 같은 타입의 bean을 찾았을 때 생기는 문제점들에 대해서 확인해보자.
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회시 같은 타입 둘 이상이면 중복 오류 발생")
    void findBeanByTypeDuplicate() {
        assertThrows(NoUniqueBeanDefinitionException.class,
                ()-> ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("타입으로 조회시 같은 타입 둘 이상이면 이름으로 지정하면 됨")
    void findBeanByName() {
        MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("특정 타입을 모두 조회하는 방법")
    //한번에 조회하는 방법 -> @AutoWired에서 자동으로 적용이된다.
    void findAllBeanByType() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key);
            System.out.println("value = " + beansOfType.get(key));
        }


        int num = 0;
        for (Map.Entry<String, MemberRepository> a : beansOfType.entrySet()) {
            System.out.println("======="+ (++num)+"=======");
            System.out.println(a);
        }
        assertThat(beansOfType.size()).isEqualTo(2);
    }

    @Configuration
    //클래스 안에 클래스 -> scope를 해당 클래스 내부에서만 사용하겠다.
    static class SameBeanConfig {

        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
    // 상속관계에 있을 때는 어떻게 조회를 해야할까? => 중요... !!
}
