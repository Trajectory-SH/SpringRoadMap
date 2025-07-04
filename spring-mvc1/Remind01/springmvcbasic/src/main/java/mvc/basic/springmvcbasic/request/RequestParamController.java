package mvc.basic.springmvcbasic.request;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import mvc.basic.springmvcbasic.basic.HelloData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {


    /**
     * 반환 타입이 없으면서 이렇게 응답에 값을 직접 집어넣으면, view 조회X
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }


    /**
     * @RequestParam 사용
     * - 파라미터 이름으로 바인딩
     * @ResponseBody 추가
     * - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,//request.getParameter("username")
            @RequestParam("age") int memberAge,
            HttpServletRequest request) {

     /*   String username = request.getParameter("username");
        System.out.println(username);
        request.getParameterNames().asIterator().forEachRemaining(name ->
                System.out.println("name = " + name));*/

        log.info("username = {}, age = {}", memberName, memberAge);
        return "test ok!";
    }

    /**
     * @RequestParam 사용
     * HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {
        log.info("username = {}, age = {}", username, age);

        return "ok";
    }

    /**
     * @RequestParam 사용
     * String, int 등의 단순 타입이면 @RequestParam 도 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username = {}, age = {}", username, age);
        return "v4 ok ";
    }


    /**
     * @RequestParam.required <hr>
     * /request-param-required -> username이 없으므로 예외<br>
     * <p>
     * /request-param-required?username= -> 빈문자로 통과  주의!<br>
     * /request-param-required <br>
     * int age -> null을 int에 입력하는 것은 불가능, 따라서 Integer 변경해야 함(또는 다음에 나오는defaultValue 사용)<br>
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {

        //파라미터 필수에 대해서 이야기합니다.
        log.info("username = {}, age = {}", username, age);
        return "required Param";
    }

    /**
     * @RequestParam <b>- defaultValue 사용</b><br>
     * <p>
     * 참고: defaultValue는 빈 문자의 경우에도 적용<br>
     * /request-param-default?username=<br>
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            //@RequestParam의 파라미터로 value와 name이 존재한다. 둘다 동일하지만(@AliasFor)
            //value가 default로 동작하고 name은 목적성이 조금 더 확실할 때 사용된다.
            //[@RequestMapping의 value, path와 동일한 논의]
            @RequestParam(defaultValue = "guest") String username,
            @RequestParam(defaultValue = "-1") int age) {
        log.info("username = {}, age = {}", username, age);

        return "requestParamDefault";
    }

    /**
     * @RequestParam Map, MultiValueMap<br>
     * Map(key=value)<br>
     * MultiValueMap(key=[value1, value2, ...]) ex) (key=userIds, value=[id1, id2])<br>
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username = {}, age = {}", paramMap.get("username"), paramMap.get("age"));


        Object username = paramMap.get("username");
        System.out.println(username.getClass());

        log.info(username.getClass().getName());
        if (username instanceof String) {
            String o = (String) username;
            log.info("username은 String이었습니다." + o.getClass().getName());
        }
        return "requestParamMap";
    }

    /**
     * 설명
     *
     * @ModelAttribute 사용
     * 참고: model.addAttribute(helloData) 코드도 함께 자동 적용됨, 뒤에 model을 설명할 때 자세히
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "modelAttributeV1 ok!";
    }

    /**
     * @ModelAttribute 생략 가능
     * String, int 같은 단순 타입 = @RequestParam
     * argument resolver 로 지정해둔 타입 외 = @ModelAttribute
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "v2 ok";
    }
}
