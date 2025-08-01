package trajectory.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

//이와 같은 테스트 상황에서는 내부적으로 h2:mem db를 생성해서 테스트에 이용하게 된다.
@SpringBootTest
public class TxLevelTest {

    @Autowired
    LevelService levelService;

    @TestConfiguration
    static class TxApplyLevelConfig {
        @Bean
        LevelService levelService() {
            return new LevelService();
        }
    }

    @Test
    void txOrderTest() {
        levelService.read();
        levelService.write();
    }

    @Slf4j
    @Transactional(readOnly = true)
    static class LevelService {

        public void read() {
            log.info("call read");
            printTxInfo();
        }

        @Transactional(readOnly = false)
        public void write() {
            log.info("call write");
            printTxInfo();
        }

        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx Active = {}", txActive);
            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("tx readOnly = {}", readOnly);
        }
    }
}
