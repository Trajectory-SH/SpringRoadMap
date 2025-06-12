package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    //싱글톤으로 Spring Container에 등록된 객체는 같은 참조값을 반환해준다.
    //싱글톤 방식의 주의점 Singleton + @Configuration AppConfig...
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(StatefulService.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //threadA : 사용자가 10000원 주문
        statefulService1.order("userA", 10000);

        //threadB : 사용자가 20000원을 주문
        statefulService2.order("userB", 20000);

        //threadA : 사용자가 주문 금액을 조회
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);
        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);

    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();// 생성자를 return 해준다
        }
    }

}