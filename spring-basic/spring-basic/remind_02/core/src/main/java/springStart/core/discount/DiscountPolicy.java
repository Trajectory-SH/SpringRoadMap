package springStart.core.discount;

import springStart.core.member.Member;

public interface DiscountPolicy {

    /**
     *
     * @return 해당 member에 대한 할인 금액
     */
    int discount(Member member, int price);

}
