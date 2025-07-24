package hello.jdbc.service;


import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Transaction -> 파라미터 연동, 풀을 고려한 종료
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;//DataSource에 의존 -> 구체적인 클래스에 대한 정보는 몰라도 된다. 외부에서 DataSource의 구현체를 주입받는다.
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection con = dataSource.getConnection();
        try {
            con.setAutoCommit(false);//트랜잭션 시작
            bizLogic(con, fromId, toId, money);//비즈니스 로직 실행 -> 실제 계좌 이체
            con.commit();//validation user!=ex 성공시에 커밋  => 트랜젝션 종료
        } catch (Exception e) {
            con.rollback();
            throw new IllegalStateException(e);
        } finally {
            release(con);
        }


    }

    private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(con, fromId);//해당 Connection 으로 PreparedStatement를 생성한다
        Member toMember = memberRepository.findById(con, toId);

        memberRepository.update(con, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(con, toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("계좌 이체중 Exception 발생");
        }
    }

    private void release(Connection con) throws SQLException {
        if (con != null) {
            try {
                con.setAutoCommit(true);//Connection pool에 반납하기 전에 default AutoCommit 모드로 변경한다.
                con.close();//이 때 connection pool을 사용중이라면 커넥션을 종료하는 것이 아니라 풀에 반납한다.
            } catch (Exception e) {
                log.error("[error]",e);
            }
        }
    }


}
