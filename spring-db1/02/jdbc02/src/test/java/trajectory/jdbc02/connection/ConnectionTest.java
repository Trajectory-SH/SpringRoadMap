package trajectory.jdbc02.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.processing.SupportedSourceVersion;
import javax.sql.DataSource;
import java.lang.constant.Constable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static trajectory.jdbc02.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

    @Test
    @DisplayName("DriverManager를 이용해 직접 DB Connection 획득")
    void driverManager() throws SQLException {
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        log.info("connection = {}, class = {}", con1, con1.getClass());
        log.info("connection = {}, class = {}", con2, con2.getClass());
    }

    @Test
    @DisplayName("DataSource 인터페이스 DriverManager -> Connection 획득")
    void dataSourceDriverManager() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        useDataSource(dataSource);
    }

    @Test
    @DisplayName("DataSource 인터페이스 Connection Pool -> Connection 획득")
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        useDataSource(dataSource);
        Thread.sleep(1000);
    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        //URL,USERNAME,PASSWORD와 같은 정보를 dataSource 객체를 생성할 때만 사용하고 이후 Connection을 얻을 때는 필요하지 않다.
        // -> 설정, 사용의 분리에 의의가 존재함 -> Repository는 DB에 대한 자세한 정보는 알지 못해도 괜찮다 -> 의존성 하락 -> DataSource에만 의존하면됨
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();

        log.info("connection = {}, class = {}", con1, con1.getClass());
        log.info("connection = {}, class = {}", con2, con2.getClass());
    }
}
