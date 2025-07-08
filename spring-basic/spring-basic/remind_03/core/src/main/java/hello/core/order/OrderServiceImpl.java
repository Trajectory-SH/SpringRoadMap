package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{
    //사실 추상적인 것에만 의존하는 것처럼 보이기도 하지만 사실 구체적인 클래스에도 의존하고 있다
    // -> OCP, DIP 원칙이 잘 지켜지지 않고 있는 것


    private final DiscountPolicy discountPolicy;
    private final MemberRepository memberRepository;

    //생성자 주입을 통해서 더 이상 구체적인 클래스에 의존하지 않게 되었다.
    //실제로 주입되는 구현체는 Runtime에 결정이 되며 정적인 클래스로는 확인할 수 있는 방법이 없다 -> DIP(의존 관계의 역전)

    //의존 관계에 대한 고민은 AppConfig에 위임하고 해당 구현 클래스는 로직을 실행하는 것에만 집중하면 된다 -> SRP(단일 책임 원칙)
    public OrderServiceImpl(DiscountPolicy discountPolicy, MemberRepository memberRepository) {
        this.discountPolicy = discountPolicy;
        this.memberRepository = memberRepository;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        int discountedPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountedPrice);
    }
}
