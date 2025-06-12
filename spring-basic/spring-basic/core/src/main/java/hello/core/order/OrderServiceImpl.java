package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //할인 정책을 변경하기 위해서는 클라이언트 코드를 고쳐야한다... 문제점 ! !
    //역할과 구현을 다형성을 활용해서 충실하게 분리했으나 문제가 발생한다... OCP, DIP 원칙을 지키지 못함
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;//인터페이스에만 의존한다.

    //생성자를 통해서 의존관계를 주입하면 된다 ! !
    //철저하게 DIP를 지키고 있다.
    @Autowired // 자동으로 주입해준다...! 생성자에서 자동으로 의존관계를 주입해준다.
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }


    //테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
