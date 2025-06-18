package springStart.core.discount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import springStart.core.member.Grade;
import springStart.core.member.Member;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static springStart.core.member.Grade.*;

class RateDiscountPolicyTest {

    DiscountPolicy discountPolicy = new RateDiscountPolicy();


    @Test
    @DisplayName("10%할인이 적용되는가?")//JUNIT5 부터 지원
    void vipDiscount() {
        //given
        Member member = new Member(1L, "전수환", VIP);
        //when
        int discount = discountPolicy.discount(member, 10000);
        //then
        assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("VIP 고객이 아니면 할인 적용이 안된다.")
    void noVipDiscount() {
        //given
        Member member = new Member(1L, "김시은", BASIC);
        //when
        int discount = discountPolicy.discount(member, 10000);
        //then
        assertThat(discount).isEqualTo(0);
    }




}