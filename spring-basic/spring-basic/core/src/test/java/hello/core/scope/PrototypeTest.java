package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototype bean1");
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototype bean1");
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);

        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);
        System.out.println("bean1 == bean2 = " + (bean1 == bean2));

        Assertions.assertThat(bean1).isNotSameAs(bean2);

        //실제로 자원을 닫는 메서드를 직접적으로 호출해줘야한다.
        /*bean1.destroy();
        bean2.destroy();*/

        ac.close();//스프링 컨테이너가 자동으로 닫아주지 않는다. 객체를 만들고 Client에 던져버림
    }


    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("Prototype init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("Prototype destroy");
        }
    }
}
