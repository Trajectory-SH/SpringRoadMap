package hello.servlet01.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet01.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@WebServlet(name = "requestBodyJsonServlet",urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);

        /**
         * [ObjectMapper의 정체는 무엇인가]
         * import com.fasterxml.jackson.databind.ObjectMapper;
         * Json결과 (String)을 파싱해서 사용이 가능한 자바 객체로 변환해주는 JSON 변환 라이브러리
         * Spring MVC -> Jackson 라이브러리 ObjectMapper를 할께 제공해준다.
         */

        System.out.println("helloData = " + helloData);
        System.out.println("helloData.getUsername() = " + helloData.getUsername());
        System.out.println("helloData.getAge() = " + helloData.getAge());

        response.getWriter().write("requestBodyJsonServlet test The End");

    }
}
