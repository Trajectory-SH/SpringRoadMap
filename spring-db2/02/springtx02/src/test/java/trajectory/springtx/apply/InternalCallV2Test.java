package trajectory.springtx.apply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest//AOP를 사용하려면 기본적으로 스프링 컨테이너로부터 Proxy 객체를 주입받아야한다. => 해당 어노태이션을 통해서 실제 필요한 Bean들을 등록해준다.
public class InternalCallV2Test {

    @Autowired
    CallService callService;
    @Autowired
    InternalService internalService;

    @TestConfiguration
    static class InternalCallV2Config {

        @Bean
        CallService callService() {
            return new CallService(internalService());
        }

        @Bean
        InternalService internalService() {
            return new InternalService();
        }
    }

    @Test
    void printProxy() {
        log.info("callService class = {} ", callService.getClass());
    }

    @Test
    void externalCall() {
        callService.external();
    }

    @Slf4j
    @RequiredArgsConstructor
    static class CallService {

        private final InternalService internalService;

        public void external() {
            log.info("call External");
            printTxInfo();
            internalService.internal();
        }



        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx Active = {}",txActive);
        }
    }

    @Slf4j
    static class InternalService {
        @Transactional
        public void internal() {
            log.info("call Internal");
            printTxInfo();
        }

        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx Active = {}",txActive);
        }
    }


}
