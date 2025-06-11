package hello.remind.core.remind.discount;

import hello.remind.core.remind.member.Grade;
import hello.remind.core.remind.member.Member;

import static hello.remind.core.remind.member.Grade.*;

public class FixDiscountPolicy implements DiscountPolicy{

    private int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
