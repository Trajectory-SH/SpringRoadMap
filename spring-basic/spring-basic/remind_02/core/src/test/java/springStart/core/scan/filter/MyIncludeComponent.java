package springStart.core.scan.filter;


import java.lang.annotation.*;

//Target => TYPE은 클래스 래벨에 붙는다.
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
}
