package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {
    ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모타입으로 조회시. 자식이 둘 이상 있으면 중복 오류 발생")
    void findBeanByParentTypeDuplicate() {
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(DiscountPolicy.class));
    }


    @Test
    @DisplayName("부모타입 조회 -> 자식이 두 명 이상일 경우에 빈 이름을 지정하면 된다.")
    void findBeanByParentTypeBeanName() {
        DiscountPolicy bean = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 하위타입으로 조회")
    //해당 타입의 Bean이 하나밖에 존재하지 않기 때문에 Exception이 발생하지 않고 Test에 성공한다.
    void findBeanBySubType() {
        RateDiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    void findAllBeanByParentType() {
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);

        //실제 Test 로직을 돌릴때는 출력 되는 것이 없어야한다 -> 시스템이 알아서 자동화되게 내비둬야한다.
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key);
            System.out.println("value = " + beansOfType.get(key).getClass().getName());
        }
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기 -> Object")
    void findAllBeanByTypeObject() {
        //Spring 내부적으로 사용하는 Bean들도 전부 다 튀어나온다.
        //전부 다 딸려서 튀어나온다. -> 모든 자바 클래스들은 Object들을 상속받기 때문이다.
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key);
            System.out.println("value = " + beansOfType.get(key).getClass().getName());
        }

    }


    @Configuration
    static class TestConfig {

        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }
        //여기서 왜 ReturnType을 DiscountPolicy로 만들어놓을까?
        //역할과 구현을 항상 쪼개야하는데...

        /**
         * DiscountPolicy => 역할의 의미를 갖고 있다.
         * RateDiscountPolicy => 구현을 하고 있다.
         * <p>
         * 나중에 개발을 진행하는 것에 있어서 다양한 이점들이 존재한다.
         * 의존성을 파악하는 것에 있어서 큰 도움이 된다. -> 의존관계 주입에서 인터페이스를 의존하고 있어야한다.
         */

        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }

}
