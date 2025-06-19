package springStart.core.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springStart.core.AppConfig;
import springStart.core.Order.OrderService;
import springStart.core.Order.OrderServiceImpl;
import springStart.core.discount.FixDiscountPolicy;
import springStart.core.member.MemoryMemberRepository;

public class MyOrderTest {

    @Test
    @DisplayName("직접 객체 생성 vs 스프링 컨테이너")
    void TestAddress() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        OrderService containerBean = ac.getBean(OrderServiceImpl.class);

        System.out.println("===컨테이너에서 생성한 Bean===");
        System.out.println("containerBean = " + containerBean);
        Assertions.assertThat(containerBean).isInstanceOf(OrderService.class);


        //스프링 컨테이너를 사용하지 않은 순수 JAVA TestCode!!
        System.out.println("===직접 생성한 객체===");
        OrderService myInstance = new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
        System.out.println("myInstance = " + myInstance);
        Assertions.assertThat(containerBean).isNotSameAs(myInstance);
    }
}
