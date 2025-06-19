package springStart.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.context.annotation.ComponentScan.*;
import static org.springframework.context.annotation.FilterType.*;


public class ComponentFilterAppConfigTest {



    @Test
    @DisplayName("ComponentScan > IncludeFilters VS ExcludeFilters")
    void filterScan() {

        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);

        //when
        BeanA beanA = ac.getBean("beanA", BeanA.class);

        //then
        assertThat(beanA).isNotNull();

        //excludeFilter로 인해서 컴포넌트 스캔 대상이 되지 않음
        //NoSuchBeanDefinitionException
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class));
    }


    @Configuration
    @ComponentScan(
            includeFilters = @Filter(type = ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @Filter(type = ANNOTATION, classes = MyExcludeComponent.class)
    )
    static class ComponentFilterAppConfig {


    }
}