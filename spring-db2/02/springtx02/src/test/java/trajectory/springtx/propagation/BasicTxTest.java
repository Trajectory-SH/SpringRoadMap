package trajectory.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest
public class BasicTxTest {

    @Autowired
    PlatformTransactionManager txManager;

    @TestConfiguration
    static class Config {

        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Test
    void commit() {
        log.info("트랜잭션 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());

        log.info("트랜잭션 커밋 시작");
        txManager.commit(status);
        log.info("트랜잭션 커밋 완료");
    }

    @Test
    void rollback() {
        log.info("트랜잭션 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());

        log.info("트랜잭션 롤백 시작");
        txManager.rollback(status);
        log.info("트랜잭션 롤백 완료");
    }

    @Test
    void double_commit() {
        log.info("tx 1 시작");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionDefinition());
        log.info("tx 1 커밋");
        txManager.commit(tx1);

        log.info("tx 2 시작");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionDefinition());
        log.info("tx 2 커밋");
        txManager.commit(tx2);
    }

    @Test
    void double_commit_rollback() {
        log.info("tx 1 시작");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionDefinition());
        log.info("tx 1 커밋");
        txManager.commit(tx1);

        log.info("tx 2 시작");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionDefinition());
        log.info("tx 2 롤백");
        txManager.rollback(tx2);
    }

        @Test
        void inner_commit() {
            log.info("외부 TX 시작");
            TransactionStatus outerTx = txManager.getTransaction(new DefaultTransactionDefinition());
            log.info("outer is newTX ? = {}", outerTx.isNewTransaction());//TX의 STATUS 객체로부터 new TX 속성을 읽을 수 있다.

            log.info("내부 TX 시작");//Participating in existing transaction
            TransactionStatus innerTx = txManager.getTransaction(new DefaultTransactionDefinition());
            log.info("inner is newTX ? = {}", innerTx.isNewTransaction());

            log.info("내부 TX 커밋");
            txManager.commit(innerTx);

            log.info("외부 TX 커밋");
            txManager.commit(outerTx);

        }

        @Test
        void outer_rollback() {
            log.info("외부 TX 시작");
            TransactionStatus outerTx = txManager.getTransaction(new DefaultTransactionDefinition());

            log.info("내부 TX 시작");//Participating in existing transaction
            TransactionStatus innerTx = txManager.getTransaction(new DefaultTransactionDefinition());

            log.info("내부 TX 커밋");
            txManager.commit(innerTx);

            log.info("외부 TX 롤백");
            txManager.rollback(outerTx);
        }

    @Test
    void inner_rollback() {

    }


}
