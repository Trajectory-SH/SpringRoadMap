package hello.remind.core.remind.order;

import hello.remind.core.remind.AppConfig;
import hello.remind.core.remind.member.Grade;
import hello.remind.core.remind.member.Member;
import hello.remind.core.remind.member.MemberService;
import hello.remind.core.remind.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hello.remind.core.remind.member.Grade.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void setUp() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    @DisplayName("주문 생성하기")
    void createOrder() {
        long memberId = 1L;
        Member member = new Member(memberId, "전수환", VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "FurchGuitar", 100000);
        assertThat(order.getDiscountPrice()).isEqualTo(10000);
    }
}