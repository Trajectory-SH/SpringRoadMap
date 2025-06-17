package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//Application의 설정정보/구성정보 -> 스프링 컨테이너에 등록된다.
//사실 해당 어노테이션은 싱글톤을 위해서 존재하는 것이다...!
public class AppConfig {

    //@Bean -> memberservice -> memberRepository -> new MemoryMemberRepository()
    //@Bean -> new OrderServiceImpl... -> new MemoryMemberRepository() 두 번이 호출이 되는구나...-> 싱글톤이 깨지지 않는가?!
    //스프링 컨테이너는 이와같은 문제점을 어떻게 해결할까?

    //해당 메서드가 실행이 되면..
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
        }
    @Bean
    //Static 키워드를 붙이면 ...! Spring에서 컨테이너에 의해 프록시로 감싸지지않는다. 따라서 싱글톤이 깨지게됨
    //-> MemoryMemberRepository()가 두 번 실행되고 결국 서로 다른 인스턴스를 생성하게 된다...!

  /*        | 문제 원인 | @Bean 메서드에 static을 붙이면 CGLIB 프록시가 작동하지 않아, 싱글톤이 깨짐 |
            | 기대 동작 | 같은 memberRepository() 메서드가 호출되더라도 하나의 인스턴스만 생성됨 |
            | 해결 방법 | static 키워드를 제거해 Spring이 프록시를 적용할 수 있도록 하기 |
  */
    public  MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());

    }
    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}
