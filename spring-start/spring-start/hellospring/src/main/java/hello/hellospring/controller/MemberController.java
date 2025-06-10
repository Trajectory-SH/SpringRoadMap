package hello.hellospring.controller;


import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
//spring container에 해당 애노테이션이 있으면 멤버 컨트롤러 객체를 생성해서 스프링이 관리를한다.
//스프링 컨테이너에서 스프링 빈이 관리된다...!
//스프링 컨테이너에 등록하고 해당 컨테이너로부터 받아서 사용할 수 있게 바꿔서 사용해야한다.
public class MemberController {
    //다른 여러 컨트롤러들이 memberService()를 가져다가 쓸 수 있어야한다만... 여러 인스턴스를 생성할 필요가 없다.
    //스프링 컨테이너에 1개만 등록해서 사용하자 ! !

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        //spring container -> Dependency Injection
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
