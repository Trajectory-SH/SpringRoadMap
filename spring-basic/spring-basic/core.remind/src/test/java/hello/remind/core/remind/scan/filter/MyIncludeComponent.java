package hello.remind.core.remind.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
//Type이라고 하면 클래스 래벨에 붙는 것
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
}
