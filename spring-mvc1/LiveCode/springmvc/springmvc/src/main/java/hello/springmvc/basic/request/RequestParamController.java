package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    /**
     * 반환 타입이 없으면서 이렇게 응답에 값을 직접 집어넣으면, view 조회X
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);
        response.getWriter().write("ok");
    }

    @ResponseBody//Http Body 메시지에 바로 넣어서 반환해버린다.@RestController와 같은 역할을 수행한다.
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName,
                                 @RequestParam("age") int memberAge) {

        log.info("username={},age={}", memberName, memberAge);

        return "ok";
        //이게 컨트롤러에 String으로 return해버리면 SpringMvc에서 논리적인 View 이름을 찾게된다.
    }

    /**
     * Best Practice
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username,
                                 @RequestParam int age) {
        //parameter name과 변수명이 일치한다면 생략이 가능하다.
        log.info("username={},age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        //parameter name과 변수명이 일치한다면 생략이 가능하다. 이렇게까지도 극단적으로 생략이 가능합니다...!
        //어노테이션이 없으면 조금 직관성이 떨어져서 약간 지양해야하지 않을까... 다른 사람들이 알아보기 힘들수도 있다.
        log.info("username={},age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = true) String username,
                                       @RequestParam(required = false) int age) {
        //required = true가 default 값이랍니다.
        //만약에 요구된 parameter가 request 객체로 오지 않는다면 약속된 HTTP spec에 맞지 않기 때문에
        /**
         * bad Request : 400(Status Code) Error를 spring이 자동으로 만들어서 respone에 넣어준다.
         * 만약에 int age 에 대한 parameter값이 넘어오지 않는다면 어떻게 될까...?-> spec에 맞게 보냈어도
         * 이거이거 자바의 기본 문법에 대한 이해가 필요합니다. 기본형에는 null값의 할당될 수 없답니다..?
         * [java 에서 기본형 int는 항상 초기화가 되어야한다 -> java 문법 오류이지요...]
         * ->Integer로 parameter로 받은 Handler method 내부에서 unboxing을 진행해서 로직을 수행하는 것이 안전하겠구나.
         * -> 500 Error가 발생한다.(Internal Server Error) -> 클라이언트의 요청을 서버가 처리할 수 없습니다 !
         */
        log.info("username={},age={}", username, age);
        return "ok";
    }

    //default value를 도입해보자!!
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
                                      @RequestParam(required = false, defaultValue = "-1") int age) {
        log.info("username={},age={}", username, age);
        return "ok";
    }

    //요청 파라미터를 한번에 Map으로 받아버릴 수도 있다.
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {

        log.info("username={},age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}
