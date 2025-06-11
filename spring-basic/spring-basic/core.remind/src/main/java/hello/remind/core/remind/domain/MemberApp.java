package hello.remind.core.remind.domain;

import hello.remind.core.remind.AppConfig;
import hello.remind.core.remind.member.Grade;
import hello.remind.core.remind.member.Member;
import hello.remind.core.remind.member.MemberService;
import hello.remind.core.remind.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static hello.remind.core.remind.member.Grade.*;

public class MemberApp {
    public static void main(String[] args) {

     /*   AppConfig appConfig = new AppConfig();
        MemberService memberedService = appConfig.memberService();*/

        //이 라인이 무슨 의미를 갖고 있었는가?
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "전수환", VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("New member = " + member);
        System.out.println("Find Member = " + findMember);

    }
}

