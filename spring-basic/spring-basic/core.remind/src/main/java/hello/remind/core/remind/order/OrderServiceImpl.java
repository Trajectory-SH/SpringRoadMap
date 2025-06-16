package hello.remind.core.remind.order;

import hello.remind.core.remind.discount.DiscountPolicy;
import hello.remind.core.remind.discount.FixDiscountPolicy;
import hello.remind.core.remind.discount.RateDiscountPolicy;
import hello.remind.core.remind.member.Member;
import hello.remind.core.remind.member.MemberRepository;
import hello.remind.core.remind.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(DiscountPolicy discountPolicy, MemberRepository memberRepository) {
        this.discountPolicy = discountPolicy;
        this.memberRepository = memberRepository;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
