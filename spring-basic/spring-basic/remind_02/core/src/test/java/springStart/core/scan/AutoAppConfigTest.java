package springStart.core.scan;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springStart.core.AutoAppConfig;
import springStart.core.member.MemberService;
import springStart.core.member.MemberServiceImpl;

import static org.assertj.core.api.Assertions.*;

public class AutoAppConfigTest {


    @Test
    @DisplayName("컴포넌트 스캔으로 스프링 빈 등록 결과 확인")
    void basicScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        MemberService memberService = ac.getBean(MemberService.class);//MemberServiceImpl과 같이 구체 타입으로 조회는 지양

        assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
