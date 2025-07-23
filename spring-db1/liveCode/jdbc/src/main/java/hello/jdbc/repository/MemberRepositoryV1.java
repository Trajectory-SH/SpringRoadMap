package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

@Slf4j
public class MemberRepositoryV1 {

    private final DataSource dataSource;

    public MemberRepositoryV1(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //CRUD[CREATE]
    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values(?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            //SQL Injection을 방지하기 위해서 pstmt를 사용
            pstmt = con.prepareStatement(sql);//SQL 전달 객체
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            //준비된 SQL을 Connection(TCP/IP)을 통해서 실제 데이터베이스에 전달한다.
            //이 때 해당 메서드는 int를 반환하는데 영향받은 DB Row(Record) 수를 반환한다. -> 해당 경우에는 1을 return

            return member;
        } catch (SQLException e) {
            log.error("[DB ERROR]", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }

    //CRUD[READ]
    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        //JDBC 표준 인터페이스 import java.sql.*;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();//excuteUpdate();

            if (rs.next()) {//DB에 여러 행이 존재한다면 반복문을 돌린다.
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found member ID => " + memberId);
            }

        } catch (SQLException e) {
            log.error("[DB Error]", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
    }

    //CRUD [UPDATE]
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
            log.info("[resultSize 영향 미친 row수] = {} ", resultSize);
        } catch (SQLException e) {
            log.error("[DB ERROR]", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }

    //CRUD [DELETE]
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
            log.error("[DB ERROR]", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }










    //쿼리를 실행하면 리소스를 항상 정리해줘야하는데 사용한 순서의 역순으로 정리해야함
    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);

    }



    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        return con;
    }


}
