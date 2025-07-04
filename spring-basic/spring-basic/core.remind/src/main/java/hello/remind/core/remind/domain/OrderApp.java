package hello.remind.core.remind.domain;

import hello.remind.core.remind.AppConfig;
import hello.remind.core.remind.member.Grade;
import hello.remind.core.remind.member.Member;
import hello.remind.core.remind.member.MemberService;
import hello.remind.core.remind.member.MemberServiceImpl;
import hello.remind.core.remind.order.Order;
import hello.remind.core.remind.order.OrderService;
import hello.remind.core.remind.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static hello.remind.core.remind.member.Grade.*;

public class OrderApp {
    public static void main(String[] args) {

        /*AppConfig appConfig = new AppConfig();
        OrderService orderService = appConfig.orderService();
        MemberService memberService = appConfig.memberService();*/

        ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = appContext.getBean("memberService", MemberService.class);
        OrderService orderService = appContext.getBean("orderService", OrderService.class);


        long memberId = 1L;
        Member member = new Member(memberId, "전수환", VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "FurchGuitar", 10000);

        System.out.println("order = " + order);
    }
}
