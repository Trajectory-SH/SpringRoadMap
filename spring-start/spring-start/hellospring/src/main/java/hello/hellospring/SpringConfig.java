package hello.hellospring;

import hello.hellospring.repository.JdbcTemplateMemberRepository;
import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
//spring이 로드될 때 이 Configuration을 읽어온다.

public class SpringConfig {
    private DataSource dataSource;
    private EntityManager em;
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }



    /*    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }*/
    //    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

   // @Bean
   // public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
       // return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);

    }


