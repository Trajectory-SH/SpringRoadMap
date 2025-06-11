package hello.remind.core.remind.discount;

import hello.remind.core.remind.member.Grade;
import hello.remind.core.remind.member.Member;

import static hello.remind.core.remind.member.Grade.*;

public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;//10% 할인

    @Override
    public int discount(Member member, int price) {

        if (member.getGrade() == VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
