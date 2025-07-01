package hello.servlet01.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    @DisplayName("회원 저장하기")
    void save() {
        Member member = new Member("전수환", 20);

        Member saved = memberRepository.save(member);

        Member find = memberRepository.findById(saved.getId());
        assertThat(find).isEqualTo(saved);
    }

    @Test
    @DisplayName("저장된 멤버 전부 찾기")
    void findAll() {

        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 30);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findAll();
        ArrayList<Member> members = new ArrayList<>(result);

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(member1, member2);

    }

















}