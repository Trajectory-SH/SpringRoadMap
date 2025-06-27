package hello.springmvc.basic.request;


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
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;


/**
 * request message body에 단순한 문자열들이 보내졌을 때 (Content-type: text/plain) Spring에서 처라하는 여러가지 방식들
 */
@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);//(inputStream, characterSet)
        //Stream은 항상 Byte-Code로 이루어져 있다. 따라서 문자열로 바꿀 때는 항상 Character Set 인코딩 정보를 넣어줘야한다.
        //해당 인코딩 타입 정보를 넣어주지 않는다면 해당 바이트 코드를 문자열로 바꿀 수 없다.

        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");
    }

    //InputStream, Reader를 바로 받아서 사용할 수 있다.
    //OutputStream, Writer를 바로 받아서 사용할 수 있다.
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    //Http message Converter
    //message-body에 있는 데이터를 뽑아내는 방법
    //view 조회 당연히 안함
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        //String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("v3-ok");
    }


    //[Best Practice]
    //Http message converter가 자동으로 문자들을 변환해준다.
    //어노테이션 기반 Spring MVC -> @RequestBody 사용
    //Request 메시지에 있는 바디를 읽어서 input stream -> utf-8로 인코딩해서 String messageBody 파라미터에 바로 넣어준다.
    @PostMapping("/request-body-string-v4")
    @ResponseBody // 해당 어노테이션이 없으면 return String을 논리적인 view 이름으로 생각하고 Process가 진행된다.
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}", messageBody);

        return "Ok-v4";
    }


}
