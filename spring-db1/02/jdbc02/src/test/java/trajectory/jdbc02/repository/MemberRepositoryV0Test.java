package trajectory.jdbc02.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import trajectory.jdbc02.domain.Member;

import javax.management.ConstructorParameters;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    //해당 테스트를 두 번 실행하면 Unique index or primary key violation 에러가 발생한다.
    @Test
    void crud() throws SQLException {
        Member member = new Member("MemberV0", 10000);
        repository.save(member);

        Member findMember = repository.findById(member.getMemberId());


        log.info("find Member = {}", findMember);
        assertThat(findMember).isEqualTo(member);

        //update: money : 10,000 -> 20,000
        repository.update(member.getMemberId(), 20000);
        Member updatedMember = repository.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);

        //delete
        repository.delete(member.getMemberId());
        //repository.findById(member.getMemberId()); java.util.NoSuchElementException: Member Not Found
        assertThrows(NoSuchElementException.class, () -> repository.findById(member.getMemberId()));
    }





}