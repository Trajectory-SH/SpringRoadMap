package hello.remind.core.remind.member;

import hello.remind.core.remind.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hello.remind.core.remind.member.Grade.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService;

    @BeforeEach
    void setUp() {
        AppConfig appConfig = new AppConfig();
        this.memberService = appConfig.memberService();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("회원가입 테스트")
    void join() {
        //given
        Member member = new Member(1L, "전수환", VIP);

        //when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        //then
        Assertions.assertThat(member).isEqualTo(findMember);
    }

    @Test
    void findMember() {
    }
}