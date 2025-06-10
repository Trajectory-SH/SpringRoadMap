package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//자동으로 Spring Bean에 등록시켜준다. SpringJpaData 인터페이스를 만들어놓으면
public interface SpringDataJpaMemberRepository extends JpaRepository<Member,Long>,MemberRepository {

    @Override
    Optional<Member> findByName(String name);
    //자동으로 작성 findByX... 인터페이스 이름만으로도 개발이 끝난다.
    //JPQL -> select m from Member m where m.name = ?
}
