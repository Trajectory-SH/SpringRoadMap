package hello.core.autowired;

import hello.core.member.Member;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

public class AutowiredTest {


    @Test
    void autowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }


    static class TestBean {

        //의존관계가 없으면 해당 메서드 자체가 호출이 안된다.
        @Autowired(required = false)
        public void setNoBean1(Member member) {
            System.out.println("member1 = " + member);

        }

        @Autowired
        public void setNoBean2(@Nullable Member member) {
            System.out.println("member2 = " + member);

        }

        @Autowired(required = false)
        public void setNoBean3(Optional<Member> member) {
            System.out.println("member3 = " + member);

        }
    }



}
