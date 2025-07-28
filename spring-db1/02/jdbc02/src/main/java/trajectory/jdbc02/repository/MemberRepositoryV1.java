package trajectory.jdbc02.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;
import trajectory.jdbc02.connection.DBConnectionUtil;
import trajectory.jdbc02.domain.Member;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * DataSource + JdbcUtils 사용
 */
@Slf4j
public class MemberRepositoryV1 {

    private final DataSource dataSource;

    //외부로부터 DataSource를 주입받아서 사용 -> DriverManagerDataSource, HikariCP DataSource등 구현체 주입 가능
    public MemberRepositoryV1(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Member save(Member member) throws SQLException {
        //SQL CRUD -> CREATE
        //SQL Injection 을 막기 위해서 PreparedStatement를 사용해야 한다. -> 문자열 더하기 연산은 보안에 취약하다.
        String sql = "insert into member(member_id, money) values(?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("[DB Error]", e);
            throw e;
        }finally {
            close(con, pstmt, null);
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("Member Not Found => " + memberId);
            }
        } catch (SQLException e) {
            log.error("[DB ERROR]", e);
            throw e;
        }finally {
            close(con, pstmt, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money=? where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();

            log.info("resultSize = {}",resultSize);
        } catch (SQLException e) {
            log.error("DB ERROR", e);
            throw e;
        }finally {
            close(con, pstmt, null);
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("[DB Error]", e);
            throw e;
        }finally {
            close(con, pstmt, null);
        }
    }

    private Connection getConnection() throws SQLException {
        //dataSource로부터 Connection을 얻어온다.
        Connection con = dataSource.getConnection();
        log.info("get Connection = {}, class = {}", con, con.getClass());
        //return DBConnectionUtil.getConnection(); -> DriverManager로부터 얻어오는 Connection이 아니라 DataSource로부터 얻어오는 Connection 이용
        return con;
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        //자원을 사용한 역순으로 닫아줘야한다.
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }

}
