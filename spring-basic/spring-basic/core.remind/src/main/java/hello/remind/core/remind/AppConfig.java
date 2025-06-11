package hello.remind.core.remind;

import hello.remind.core.remind.discount.DiscountPolicy;
import hello.remind.core.remind.discount.RateDiscountPolicy;
import hello.remind.core.remind.member.MemberRepository;
import hello.remind.core.remind.member.MemberService;
import hello.remind.core.remind.member.MemberServiceImpl;
import hello.remind.core.remind.member.MemoryMemberRepository;
import hello.remind.core.remind.order.OrderService;
import hello.remind.core.remind.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//설정정보
public class AppConfig {

    /*public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new RateDiscountPolicy(), new MemoryMemberRepository());
    }*/
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(discountPolicy(), memberRepository());
    }
    @Bean
    private static MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    @Bean
    private static DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}
