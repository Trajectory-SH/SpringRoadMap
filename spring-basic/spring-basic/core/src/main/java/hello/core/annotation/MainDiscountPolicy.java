package hello.core.annotation;


import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
    //여러 어노테이션을 모아서 사용해주는 것은 -> Java에서 지원해주는 것이 아니라 Spring에서 제공하는 기능이다.
}
