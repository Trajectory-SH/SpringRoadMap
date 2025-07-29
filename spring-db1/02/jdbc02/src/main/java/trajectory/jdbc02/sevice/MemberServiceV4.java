package trajectory.jdbc02.sevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import trajectory.jdbc02.domain.Member;
import trajectory.jdbc02.repository.MemberRepository;
import trajectory.jdbc02.repository.MemberRepositoryV3;

import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
public class MemberServiceV4 {

    private final MemberRepository memberRepository;

    @Transactional
    public void accountTransfer(String fromId, String toId, int money) {
        bizLogic(fromId, toId, money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");//RuntimeException -> 던지지 않아도 된다.
        }
    }

    private void bizLogic(String fromId, String toId, int money) {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);
        memberRepository.update( fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }
}
