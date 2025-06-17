package springStart.core.discount;

import springStart.core.member.Grade;
import springStart.core.member.Member;

public class FixDiscountPolicy implements DiscountPolicy{

    private final int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP && price >= discountFixAmount) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
