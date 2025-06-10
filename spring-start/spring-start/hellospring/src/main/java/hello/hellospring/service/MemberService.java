package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//내가 만든 클래스에 대해서 테스트 코드를 작성하는 단축키 (Command + shift + T)
//Spring이 업로드 될 때 마찬가지로 Container에 등록해준다.

@Transactional
//JPA를 사용하기 위해서는 해당 애노테이션이 꼭 필요하다.
public class MemberService {

    //Repository
    private final MemberRepository memberRepository;
    //추상적인 MemberRepository 사용... 생성자를 통해서 주입
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원 가입
    public Long join(Member member) {


        long start = System.currentTimeMillis();

        try{
            //같은 이름의 중복회원은 안된다.
            //중복회원 검증 로직
            validateDuplicateMember(member);
            memberRepository.save(member);
            return member.getId();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join = " + timeMs+"ms");
        }
    }

    private void validateDuplicateMember(Member member) {
        //ifPresent Optional안에 null이 있는 것이 아니라면 아래와 같은 동작을 수행
        //client단에서 무언가 처리를 할 수 있다는 것이 아래와 같은 것..!
        //Optional에서 제공하는 여러 편의를 주는 메서드들을 제공해준다.
        //과거에는 if(member==null)...
        memberRepository.findByName(member.getName())//결과가 Optional result
                .ifPresent(m -> {//Optional이기 때문에 가능한 ifPresent..!
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);

    }


}
