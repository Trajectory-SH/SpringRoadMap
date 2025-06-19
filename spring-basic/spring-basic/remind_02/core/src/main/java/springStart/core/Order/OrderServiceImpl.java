package springStart.core.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springStart.core.discount.DiscountPolicy;
import springStart.core.discount.FixDiscountPolicy;
import springStart.core.discount.RateDiscountPolicy;
import springStart.core.member.*;

@Component
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(discountPrice, itemName, itemPrice, memberId);
    }
    //Test 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
