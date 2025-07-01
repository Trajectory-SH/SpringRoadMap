package hello.servlet01.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Servlet request에서 body message를 읽을 수 있는 메서드를 지원한다.
        //return type은 byte(ServletInputStream) -> Stream은 항상 byte type이라고 생각하자.
        ServletInputStream inputStream = request.getInputStream();
        //Java Code에서 byte type은 다루기 힘드니 해당 inputstream을 문자열로 인코딩한다.
        //스프링이 제공하는 StreamUtils의 static method copyToString(byte -> String)을 사용하자.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);

        response.getWriter().write("InputStream to String 성공!");
    }
}
