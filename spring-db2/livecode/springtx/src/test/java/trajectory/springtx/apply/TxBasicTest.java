package trajectory.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class TxBasicTest {

    @Autowired//Test 환경에서는 필드 주입을 종종 사용한다.
    BasicService basicService;

    @Test
    void proxyCheck() {
        log.info("aop class={}", basicService.getClass());
        Assertions.assertThat(AopUtils.isAopProxy(basicService)).isTrue();
    }

    @Test
    void txTest() {
        basicService.tx();
        basicService.nonTx();
    }




    @TestConfiguration
    static class TxApplyBasicConfig {
        @Bean
        BasicService basicService() {
            return new BasicService();
        }
    }


    @Slf4j
    static class BasicService {
        @Transactional
        public void tx() {
            log.info("call TX");
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active ={}", txActive);
        }

        public void nonTx() {
            log.info("call nonTX");
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active ={}", txActive);
        }
    }
}
