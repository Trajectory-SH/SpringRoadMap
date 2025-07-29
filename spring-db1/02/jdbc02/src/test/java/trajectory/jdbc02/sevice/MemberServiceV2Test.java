package trajectory.jdbc02.sevice;

import org.junit.jupiter.api.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import trajectory.jdbc02.connection.ConnectionConst;
import trajectory.jdbc02.domain.Member;
import trajectory.jdbc02.repository.MemberRepositoryV2;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static trajectory.jdbc02.connection.ConnectionConst.*;

/**
 * 트랜잭션 -> 커넥션 파라미터 전달 방식 동기화
 */
class MemberServiceV2Test {

    private MemberRepositoryV2 memberRepository;
    private MemberServiceV2 memberService;

    @BeforeEach
    void before() {//Test에 필요한 의존관계 주입
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV2(dataSource);
        memberService = new MemberServiceV2(dataSource, memberRepository);//서비스에서 커넥션을 얻어야하기 때문에 dataSource가 필요하다.
    }

    @AfterEach
    void after() throws SQLException {
        memberRepository.delete("memberA");
        memberRepository.delete("memberB");
        memberRepository.delete("ex");
    }

    @Test
    @DisplayName("[비정상이체]")
    void accountTransferEx() throws SQLException {
        //given
        Member memberA = new Member("memberA", 10000);
        Member memberEx = new Member("ex", 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        //when
        Assertions.assertThrows(IllegalStateException.class,
                () -> memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000));

        //then
        Member findA = memberRepository.findById(memberA.getMemberId());
        Member findEx = memberRepository.findById(memberEx.getMemberId());

        //돈이 롤백 되어야한다.
        assertThat(findA.getMoney()).isEqualTo(8000);
        assertThat(findEx.getMoney()).isEqualTo(10000);

    }
}