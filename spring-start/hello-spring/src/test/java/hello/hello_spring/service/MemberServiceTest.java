package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService = new MemberService();
    @Test
    void 회원가입() {
        //given(무언가가 주어졌을 때)
        Member member = new Member();
        member.setName("hello");

        //when(실행했을 때)
                                                    //then (결과가 이렇게 나와야해)


    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}