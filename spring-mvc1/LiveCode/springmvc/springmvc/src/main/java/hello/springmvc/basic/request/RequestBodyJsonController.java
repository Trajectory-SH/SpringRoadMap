package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

/**
 * * {"username":"hello", "age":20}
 * * content-type: application/json
 */


@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody = {}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {}", helloData.getUsername(),helloData.getAge());

        response.getWriter().write("json-v1-Ok");
    }

    @PostMapping("/request-body-json-v2")
    @ResponseBody
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody = {}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {}", helloData.getUsername(),helloData.getAge());

        return "json-v2-Ok";
    }


    //이거 내가 굳이 objectMapper를 사용해서 String으로 바꿔주는 과정이 너무 귀찮은데 Spring이 대신해줄 수 없나?
    //@RequestBody에서 객체를 parameter로 넘길 때...
    //Http Entity나 @RequestBody를 사용하면 Http 메시지 컨버터가 Http 메시지 바디의 내용을 우리가 원하는 문자나 객체로 변환해준다.
    //문자를 변환해주는 메시지 컨버터 , json/application을 변환해주는 메시지 컨버터등 여러가지 종류의 컨버터들이 들어있다.
    @PostMapping("/request-body-json-v3")
    @ResponseBody
    //@RequestBody를 생략하면 내가 지정한 객체에 대해서 값이 할당되지 않는다 -> 메시지 컨버터가 작동하지 않는다.
    //생략하면 @ModelAttribute가 붙어버린다. -> 요청 파라미터가 없으니 null값이 할당된다
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {

        log.info("messageBody = {}", helloData);
        //HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {}", helloData.getUsername(),helloData.getAge());

        return "json-v3k";
    }

    @PostMapping("/request-body-json-v4")
    @ResponseBody
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) throws IOException {
        HelloData data = httpEntity.getBody();
        log.info("username = {}, age = {}", data.getUsername(), data.getAge());

        return "json-v4-ok";
    }


    @PostMapping("/request-body-json-v5")
    @ResponseBody
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) throws IOException {

        log.info("username = {}, age = {}", data.getUsername(), data.getAge());

        return data;
    }
}
