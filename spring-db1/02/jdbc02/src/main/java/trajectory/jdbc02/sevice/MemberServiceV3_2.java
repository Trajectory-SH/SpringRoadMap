package trajectory.jdbc02.sevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import trajectory.jdbc02.domain.Member;
import trajectory.jdbc02.repository.MemberRepositoryV3;

import java.sql.SQLException;

@Slf4j
public class MemberServiceV3_2 {

    private final TransactionTemplate txTemplate;
    private final MemberRepositoryV3 memberRepository;

    //트랜잭션 매니저를 외부에서 주입받고 트랜잭션 탬플릿을 생성한다.(관레)
    public MemberServiceV3_2(PlatformTransactionManager transactionManager , MemberRepositoryV3 memberRepository) {
        txTemplate = new TransactionTemplate(transactionManager);
        //txTemplate를 직접 주입받는 것 대신에 트랜잭션 매니저를 주입받고 내부에서 transactionTemplate를 생성한다.
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        txTemplate.executeWithoutResult((transactionStatus -> {
            //트랜잭션Status가 있어야지 해당 정보를 통해서 commit rollback이 가능해진다.
            //트랜잭션을 만들고 커밋, 롤백하는 것들을 전부 txTemplate에게 위임
            try {
                bizLogic(fromId, toId, money);
            } catch (SQLException e) {
                throw new IllegalStateException(e);//람다에서는 체크예외를 밖으로 던질 수 없다 -> 언체크 예외로 바꾸어 던진다.
            }
        }));
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");//RuntimeException -> 던지지 않아도 된다.
        }
    }

    private void bizLogic(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);
        memberRepository.update( fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

}
