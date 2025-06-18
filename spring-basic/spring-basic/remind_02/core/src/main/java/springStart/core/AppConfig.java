package springStart.core;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@Configuration
public class AppConfig {

    //메서드 명을 보고서도 명확하게 역할을 확인할 수 있다.!!!
    //중복들이 제거된다.
    @Bean
    public MemberService memberService() {
        //생성자 주입 Dependency Injection
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public  DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

}
