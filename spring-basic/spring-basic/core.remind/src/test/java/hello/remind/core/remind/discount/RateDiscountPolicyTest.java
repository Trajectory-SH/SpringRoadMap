package hello.remind.core.remind.discount;

import hello.remind.core.remind.member.Grade;
import hello.remind.core.remind.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hello.remind.core.remind.member.Grade.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("고객이 VIP일 때 Test")
    void VIP_O() {
        //given
        Member member = new Member(1L, "전수환", VIP);
        //when
        int discount = discountPolicy.discount(member, 10000);
        //then
        assertThat(discount).isEqualTo(1000);
    }


    @Test    @DisplayName("고객이 VIP가 아닐 때 Test")
    //기본 조건에 대해서 반대가 되는 상황에 대해서도 항상 TEST를 진행하는 습관을 가져야한다.
    void VIP_X() {
        //given
        Member member = new Member(1L, "전수환", BASIC);
        //when
        int discount = discountPolicy.discount(member, 10000);
        //then
        assertThat(discount).isEqualTo(0);
    }
}