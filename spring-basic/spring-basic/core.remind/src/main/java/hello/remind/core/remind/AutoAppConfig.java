package hello.remind.core.remind;

import hello.remind.core.remind.member.MemberRepository;
import hello.remind.core.remind.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
    /*The bean 'memoryMemberRepository', defined in class path resource
     could not be registered.
    A bean with that name has already been defined in file .//
    and overriding is disabled.*/

     /**
     * Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true
     * SpingBoot의 기본 값은 false이다. -> 개발자가 의도하지 않은 오류가 발생할 수 있는 상황이 너무 많음(상황이 애매해짐)
      * 개발은 혼자하는 것이 아닙니다..!
     */

    @Bean(name = "memoryMemberRepository")
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
