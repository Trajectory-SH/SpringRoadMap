package trajectory.jdbc02.sevice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import trajectory.jdbc02.domain.Member;
import trajectory.jdbc02.repository.MemberRepositoryV1;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static trajectory.jdbc02.connection.ConnectionConst.*;

/**
 * 기본 동작 -> 트랜잭션이 없어서 문제 발생
 */
class MemberServiceV1Test {
    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    private MemberRepositoryV1 memberRepository;
    private MemberServiceV1 memberService;


    @BeforeEach
    void before() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV1(dataSource);
        memberService = new MemberServiceV1(memberRepository);
    }

    @AfterEach
    void after() throws SQLException {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_EX);
    }

    @Test
    @DisplayName("정상 이체")
    void accountTransfer() throws SQLException {
        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        //when
        memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);

        //then
        Member findA = memberRepository.findById(memberA.getMemberId());
        Member findB = memberRepository.findById(memberB.getMemberId());
        assertThat(findA.getMoney()).isEqualTo(8000);
        assertThat(findB.getMoney()).isEqualTo(12000);
    }

    @Test
    @DisplayName("계좌이체 예외 발생")
    void accountTransferEx() throws SQLException {
        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        //when
        assertThrows(IllegalStateException.class,
                     () -> memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000));

        //then
        Member findA = memberRepository.findById(memberA.getMemberId());
        Member findEx = memberRepository.findById(memberEx.getMemberId());

        //트랜잭션이 제대로 이루어지지 않음
        assertThat(findA.getMoney()).isEqualTo(8000);
        assertThat(findEx.getMoney()).isEqualTo(10000);
    }



}