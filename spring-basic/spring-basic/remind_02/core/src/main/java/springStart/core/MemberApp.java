package springStart.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springStart.core.member.Grade;
import springStart.core.member.Member;
import springStart.core.member.MemberService;
import springStart.core.member.MemberServiceImpl;

public class MemberApp {
    public static void main(String[] args) {

       /* AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();*/

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = ac.getBean("memberService", MemberService.class);

        System.out.println(AppConfig.class);
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);

        System.out.println("member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());




    }
}
