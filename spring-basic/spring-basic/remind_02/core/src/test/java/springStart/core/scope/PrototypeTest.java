package springStart.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        //프로토타입 빈은 의존관계 주입 후에 초기화 메서드를 호출한다. -> 실제로 클라이언트가 getBean을 호출 할 때마다 새로운 객체 생성

        /*PrototypeTest.prototypeBeanFind1
        PrototypeBean.init
        PrototypeTest.prototypeBeanFind2
        PrototypeBean.init
                bean1 = springStart.core.scope.PrototypeTest$PrototypeBean@2c22a348
                bean2 = springStart.core.scope.PrototypeTest$PrototypeBean@7bd69e82*/


        System.out.println("PrototypeTest.prototypeBeanFind1");
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);

        System.out.println("PrototypeTest.prototypeBeanFind2");
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);

        //이후에 Destory 메서드는 클라이언트가 항상 클라이언트가 직접 처리해줘야한다.
        //@Predestroy 호출이 안된다.

        //조회 할 때 스프링 빈이 생성된다.

        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);

        assertThat(bean1).isNotSameAs(bean2);


        //직접 호출해줘야한다. -> PrototypeBean의 특징...
        bean1.destroy();
        bean2.destroy();

        ac.close();
    }

    @Scope("prototype")
    //@Component가 없어도 된다. -> 직접 등록해준다면야...!
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }

    }

}
