package mvc.basic.springmvcbasic.request;

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

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody = {}", messageBody);

        response.getWriter().write("requestBodyString -> Http Servlet");
    }

    /**
     * InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회<br><br>
     * OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력<br>
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    /**
     * <b>HttpEntity: HTTP header, body 정보를 편리하게 조회</b> <br><br>
     * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)<br><br>
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용<br><br>
     * 응답에서도 HttpEntity 사용 가능<br><br>
     * - 메시지 바디 정보 직접 반환(view 조회X)<br><br>
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("ok");
    }

    /**
     * @RequestBody <br><br>
     * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)<br>
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용<br>
     * <br>
     * @ResponseBody <br><br>
     * - 메시지 바디 정보 직접 반환(view 조회X)<br>
     * - HttpMessageConverter 사용 -> StringHttp MessageConverter 적용<br>
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {
        log.info("messageBody={}", messageBody);
        return "ok";
    }


}
