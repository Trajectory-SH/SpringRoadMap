package hello.core;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        //스프링 빈으로 등록하는 것중에 빼야할 것
        //@Component 어노테이션이 있는 모든 것들을 Spring Bean으로 자동 등록해준다.
        //보통 설정 정보를 컴포넌트 스캔 대상에서 제외하지는 않는다. 기존 예제코드를 유지하기 위해서...
        //@Configuration을 들어가보면 @Component가 들어있다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}