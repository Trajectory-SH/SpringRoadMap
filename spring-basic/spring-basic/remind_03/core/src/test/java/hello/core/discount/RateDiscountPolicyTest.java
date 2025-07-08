package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {


    DiscountPolicy discountPolicy = new RateDiscountPolicy();


    @Test
    @DisplayName("VIP 할인 적용")
    void vip_O() {
        Member member = new Member(1L, "userA", Grade.VIP);

        int discount = discountPolicy.discount(member, 10000);

        Assertions.assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("일반 고객 할인 적용 X")
    void vip_X() {
        Member member = new Member(1L, "userA", Grade.BASIC);

        int discount = discountPolicy.discount(member, 10000);

        Assertions.assertThat(discount).isEqualTo(0);
    }
}