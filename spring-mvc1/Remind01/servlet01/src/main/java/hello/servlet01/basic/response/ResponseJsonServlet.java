package hello.servlet01.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet01.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "responseJsonServlet",urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setHeader("content-type", "application/json");
        response.setCharacterEncoding("utf-8");


        HelloData data = new HelloData();
        data.setAge(20);
        data.setUsername("전수환");
        System.out.println("data = " + data);

        //Java 객체를 다시 Json 형식으로 변환하기 위해서도 ObjectMapper를 사용해야한다. ->Value As String
        String responseMessageBody = objectMapper.writeValueAsString(data);
        response.getWriter().write(responseMessageBody);

    }
}
