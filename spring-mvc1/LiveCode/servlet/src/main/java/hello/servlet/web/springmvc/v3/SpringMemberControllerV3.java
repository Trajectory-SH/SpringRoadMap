package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private MemberRepository memberRepository= MemberRepository.getInstance();

    //@RequestMapping(value = "/new-form",method = RequestMethod.GET)
    @GetMapping("/new-form")
    public String newForm() {
        return "new-form";
        //스프링은 인터페이스로 유연하게 설계되어있기 때문에 -> 그냥 논리적인 view의 이름을 반환해도 된다.
        //해당 case에 맞게 process가 진행된다.
    }

    //@RequestMapping(value = "/save", method = RequestMethod.POST)
    @PostMapping("/save")
    public String save(@RequestParam String username,
                       @RequestParam int age,
                       Model model) {
        //Model model -> Spring이 제공하는 model을 사용할 수 있다.
        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member", member);

        return "save-result";
    }

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public String members(Model model) {

        List<Member> members = memberRepository.findAll();

        ModelAndView mv = new ModelAndView("members");
        model.addAttribute("members", members);

        return "members";
    }
}
