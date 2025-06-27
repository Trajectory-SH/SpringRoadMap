package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");

        return mav;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello");
        return "response/hello";
        //컨트롤러가 문자열을 반환하면 -> 논리적인 뷰 이름 -> 어댑터로 가서 뷰리졸버로 뷰 객체를 만들면서 물리적 뷰 이름을 consturct
    }

    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello");
        //spring이 컨트롤러의 경로 이름과 뷰의 논리적인 이름이 같으면 return type이 void여도 뷰를 찾아서 랜더링해준다.
    }
}
