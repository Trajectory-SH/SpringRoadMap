package hello.remind.core.remind.discount;

import hello.remind.core.remind.member.Member;

public interface DiscountPolicy {

    /**
     * @return 할인 대상 금액
     */
    int discount(Member member, int price);
}
