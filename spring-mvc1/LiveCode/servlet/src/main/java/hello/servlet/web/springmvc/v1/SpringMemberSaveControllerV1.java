package hello.servlet.web.springmvc.v1;

import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class SpringMemberSaveControllerV1 {
    private MemberRepository memberRepository = MemberRepository.getInstance();


    @RequestMapping("/springmvc/v1/members/save")
    public ModelAndView process(Map<String, String> paraMap) {
        String username = paraMap.get("username");

    }

}
