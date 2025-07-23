package hello.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

    @Test
    @DisplayName("DriverManager 연결 TEST")
    void driverManager() throws SQLException {
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        log.info("connection = {} || class = {} ", con1, con1.getClass());
        log.info("connection = {} || class = {} ", con2, con2.getClass());
    }

    @Test
    @DisplayName("DriverManagerDataSource 연결 Test")//DriverManager도 DataSource에 의존할 수 있도록 DriverManagerDataSource 제공
    void driverManagerDataSource() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        useDataSource(dataSource);
    }

    @Test
    @DisplayName("HikariDataSource 연결 Test")
    void dataSourceConnectionPool() throws InterruptedException, SQLException {
        //커넥션 풀링: HikariProxyConnection -> JdbcConnection
        //connection = HikariProxyConnection@1347016882 wrapping
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("DataSourcePool");

        useDataSource(dataSource);
        Thread.sleep(1000);//커넥션 풀 커넥션 생성 대기
    }


    @Test
    @DisplayName("커넥션 풀 초기화 로그 확인")
    void hikariInitLog() throws Exception {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:h2:tcp://localhost/~/test");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setMaximumPoolSize(5);
        dataSource.setMinimumIdle(5); // 꼭 설정해야 미리 채움
        dataSource.setPoolName("MyPool");
        dataSource.setInitializationFailTimeout(-1);

        // 커넥션 하나라도 써야 풀 생성이 시작됨
        Connection conn = dataSource.getConnection();
        log.info("커넥션 얻음: {}", conn);

        Thread.sleep(2000); // 로그 출력 시간 확보
    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();

        log.info("connection = {} | class = {} ", con1, con1.getClass());
        log.info("connection = {} | class = {} ", con2, con2.getClass());

    }
}



















