package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {


    @Test
    void singletonBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
        //해당 parameter가 componentClass이기 때문에 -> @Component가 붙어 있지 않아도 ComponentScan의 대상이 된다.

        SingletonBean bean1 = ac.getBean(SingletonBean.class);
        SingletonBean bean2 = ac.getBean(SingletonBean.class);



        String string = bean1.toString();
        System.out.println(string);

        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);
        assertThat(bean1).isSameAs(bean2);

        ac.close();
    }


    @Scope("singleton")
    static class SingletonBean {
        @PostConstruct
        public void init() {
            System.out.println("SingletonBean init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("SingletonBean destroy");
        }

    }
}
