package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    //Jpa는 항상 EntityManager를 통해서 동작한다...
    //Spring Boot가 자동으로 EntityManager를 생성하고 우리는 이것을 주입해서 사용하면 된다. appliction.properties를 보고 생성
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);//조회할 type과 pk를 입력해주면 된다.
        return Optional.ofNullable(member);
    }


    //PK 기준이랑 다르다면 Jpql 문을 작성해줘야한다.
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }


    //JPQL 쿼리 언어 사용...객체를 대상으로 쿼리를 생성한다.
    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member as m", Member.class).getResultList();
    }
}
