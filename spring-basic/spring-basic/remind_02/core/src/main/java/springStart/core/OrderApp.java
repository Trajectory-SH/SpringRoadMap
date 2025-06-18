package springStart.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springStart.core.Order.Order;
import springStart.core.Order.OrderService;
import springStart.core.Order.OrderServiceImpl;
import springStart.core.member.Grade;
import springStart.core.member.Member;
import springStart.core.member.MemberService;
import springStart.core.member.MemberServiceImpl;

public class OrderApp {

    public static void main(String[] args) {

        //공연 기획자역할을 담당하는 AppConfig(어플리케이션 설정 정보 + 의존 관계 주입 역할만 진행)
        /*AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();*/

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        OrderService orderService = ac.getBean("orderService", OrderService.class );

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
        System.out.println("order.calculatePrice() = " + order.calculatePrice());
    }
}
