package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class UncheckedTest {

    @Test
    void uncheck_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void unchecked_throw() {
        Service service = new Service();
        assertThrows(MyUncheckedException.class, () -> service.callThrow());
    }


    static class Service {
        Repository repository = new Repository();

        /**
         * 필요한 경우 예외를 잡아서 처리하면 된다.
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                log.info("[예외처리] message = {}", e.getMessage(), e);
            }
        }

        /**
         * 예외를 잡지 않아도 된다 => 자연스럽게 상위로 넘어간다.
         * 체크예외랑 다르게 throws 예외 선언을 하지 않아도 된다.
         */
        public void callThrow() {
            repository.call();
        }
    }


    static class Repository {
        public void call() {
            throw new MyUncheckedException("EX");
        }

    }

    static class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }
}




