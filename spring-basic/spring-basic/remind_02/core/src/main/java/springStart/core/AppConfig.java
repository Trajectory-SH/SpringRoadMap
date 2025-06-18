package springStart.core;

import springStart.core.Order.OrderService;
import springStart.core.Order.OrderServiceImpl;
import springStart.core.discount.DiscountPolicy;
import springStart.core.discount.FixDiscountPolicy;
import springStart.core.discount.RateDiscountPolicy;
import springStart.core.member.MemberRepository;
import springStart.core.member.MemberService;
import springStart.core.member.MemberServiceImpl;
import springStart.core.member.MemoryMemberRepository;

//AppConfig를 통해서 메서드를 호출
public class AppConfig {

    //메서드 명을 보고서도 명확하게 역할을 확인할 수 있다.
    //중복들이 제거된다.

    public MemberService memberService() {
        //생성자 주입 Dependency Injection
        return new MemberServiceImpl(memberRepository());
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    private DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

}
