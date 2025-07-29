package trajectory.jdbc02.sevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trajectory.jdbc02.domain.Member;
import trajectory.jdbc02.repository.MemberRepositoryV1;
import trajectory.jdbc02.repository.MemberRepositoryV2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;//Service 계층에서 트랜잭션을 시작해야하기 때문에 dataSource를 주입받는다.
    private final MemberRepositoryV2 memberRepository;


    //순수한 비즈니스 로직이었는데 -> 트랜잭션을 시작해야하기에 코드가 많이 더러워지고 특정 기술에 의존적으로 변한다.
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection con = dataSource.getConnection();//커넥션 획득
        try {
            con.setAutoCommit(false);//서비스 계층에서 트랜잭션 시작
            bizLogic(con, fromId, toId, money);//실제 서비스 비즈니스 영역
            con.commit();//Service 에서 트랜잭션의 시작과 종료 -> 이후 Connection도 닫아줘야한다.
        } catch (Exception e) {
            con.rollback();//로직 실패시 어떠한 오류가 터지는데 Exception으로 다 잡고 RuntimeError를 던진다.
            throw new IllegalStateException(e);
        }finally {
            //항상 실행되는 finally -> 자원을 닫아줘야한다.
            release(con);
        }
    }

    private void release(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true);//커넥션 풀에 다시 반납해줘야한다.
                con.close();
            } catch (Exception e) {
                log.info("[ERROR]", e);
            }
        }
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");//RuntimeException -> 던지지 않아도 된다.
        }
    }

    private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(con,fromId);
        Member toMember = memberRepository.findById(con,toId);
        memberRepository.update(con, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(con, toId, toMember.getMoney() + money);
    }

}
