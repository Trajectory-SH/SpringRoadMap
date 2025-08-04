package trajectory.springtx.propagation_re;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;


    //유저 저장하기
    @Transactional
    public void save(Member member) {
        log.info("member 저장");
        em.persist(member);
    }

    //유저 조회
    public Optional<Member> find(String username) {
        return em.createQuery("select m from Member m where m.username =:username", Member.class).setParameter("username", username).getResultList().stream().findAny();
    }



}
