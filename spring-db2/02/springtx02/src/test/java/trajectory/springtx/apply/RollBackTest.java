package trajectory.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class RollBackTest {

    @Autowired
    RollBackService rollBackService;

    @Test
    void runtimeException() {
        assertThatThrownBy(() -> rollBackService.runtimeException()).isInstanceOf(RuntimeException.class);
    }

    @Test
    void checkedException() throws Exception {
        assertThatThrownBy(() -> rollBackService.checkedException()).isInstanceOf(Exception.class);
    }

    @Test
    void rollBackFor() {
        assertThatThrownBy(() -> rollBackService.rollBackFor()).isInstanceOf(MyException.class);
    }


    @TestConfiguration
    static class RollbackConfig {

        @Bean
        RollBackService rollBackService() {
            return new RollBackService();
        }
    }


    @Slf4j
    static class RollBackService {

        //@Transactional -> runtimeEx => rollback이 기본 정책
        @Transactional
        public void runtimeException() {
            log.info("call RuntimeEx");
            throw new RuntimeException();
        }

        //체크예외 발생 -> commit()
        @Transactional
        public void checkedException() throws Exception {
            log.info("call CheckedEx");
            throw new Exception();
        }

        //rollbackFor 속성으로 인해서 체크 예외이지만 트랜잭션 RollBack한다.
        @Transactional(rollbackFor = MyException.class)
        public void rollBackFor() throws MyException {
            log.info("call rollBackFor");
            throw new MyException();
        }

    }

    static class MyException extends Exception {

    }
}
