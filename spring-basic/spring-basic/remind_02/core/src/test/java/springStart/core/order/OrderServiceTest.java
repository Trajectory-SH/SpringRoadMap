package springStart.core.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springStart.core.AppConfig;
import springStart.core.Order.Order;
import springStart.core.Order.OrderService;
import springStart.core.Order.OrderServiceImpl;
import springStart.core.member.Grade;
import springStart.core.member.Member;
import springStart.core.member.MemberService;
import springStart.core.member.MemberServiceImpl;

public class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder() {
        //given
        Long memberID = 1L;
        Member member = new Member(memberID, "memberA", Grade.VIP);
        memberService.join(member);

        //when
        Order order = orderService.createOrder(memberID, "itemA", 10000);

        //then
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
