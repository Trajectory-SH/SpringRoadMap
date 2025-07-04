package mvc.basic.springmvcbasic.response;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.PushBuilder;
import lombok.extern.slf4j.Slf4j;
import mvc.basic.springmvcbasic.basic.HelloData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@Slf4j//log 출력 인터페이스 -> Spring에서 Logback을 구현체로 사용한다.
@Controller
//@RestController = @ResponseBody + @Controller
public class ResponseBodyControllerV1 {

    @GetMapping("/response-body-string-v1")
    //해당 URL(Uniform Resource Location)으로 GET 메서드로 HttpRequest 메시지가 오면 아래 존재하는 HandlerMethod invoke()
    /**
     * RequestMappingHandlerMapping
     * Map<RequestMappingInfo, HandlerMethod>
     * RequestMappingInfo => URL 정보(.path("/hello-world/v1") + HTTP Method 정보(RequestMethod.GET)
     * HandlerMethod => @Controller Class Spring Singleton Bean + Reflection Method Instance -> method.invoke()
     */
    public void responseBodyV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
        //response 객체의 Writer를 이용해서 HttpMessageBody에 직접 내용을 입력한다.
        //HandlerMethod의 return type이 void이고 별다른 처리가 되지 않는다면 request url의 정보를 참조해서 논리 뷰의 이름으로 활용
        // -> templates/response-body-string-v1   => 사용하지 말자.
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok"; //ResponseBody로 인해서 논리 view 이름의 반환이 아니라 HttpResponseMessage Body에 직접 전달한다.
    }


    //response객체에 있는 정보들을 조금 더 활용할 수 있는 responseEntity객체를 반환한다.
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setAge(20);
        helloData.setUsername("soohwan");

        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    //객체를 직접 return
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("전수환");
        helloData.setAge(20);

        return helloData;
    }
}
