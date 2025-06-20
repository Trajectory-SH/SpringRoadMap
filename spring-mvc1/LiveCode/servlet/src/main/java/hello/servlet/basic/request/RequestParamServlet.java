package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.SocketHandler;

/**
 * 1. 파라미터 전송 기능
 * username = hello & age = 20
 */

@WebServlet(name = "RequestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("RequestParamServlet.service");

        System.out.println("==전체 파라미터조회==");
        request.getParameterNames().asIterator().forEachRemaining(paramName -> System.out.println(paramName + " = " + request.getParameter(paramName)));
        System.out.println();


        System.out.println("==단일 파라미터 조회==");
        System.out.println(request.getParameter("username"));
        System.out.println(request.getParameter("age"));
        System.out.println();

        System.out.println("==복수 파라미터 조회==");
        String[] parameterValues = request.getParameterValues("username");
        for (String parameterValue : parameterValues) {
            System.out.println("parameterValue = " + parameterValue);
        }

            response.getWriter().write("okdk");
    }
}
