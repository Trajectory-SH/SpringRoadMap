package hello.servlet.web.servletmvc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);//컨트롤러에서 뷰로 이동시킬 때 사용한다.
        dispatcher.forward(request,response);//서블릿에서 jsp를 호출한다.
        //클라이언트(웹브라우저)에 왔다 갔다 하는 것은 아니다 -> 리다이렉트가 아님
        //WEB-INF -> 컨트롤러를 항상 거쳐서만 접근이 가능했으면 좋겠다. => WAS에서의 약속이다.
        //WEB-INF에 존재하는 자원들은 기본적으로 외부에서 호출한다고 해도 호출이 되지 않는다.
        //항상 컨트롤러를 거쳐서 -> forward등을 통해서 접근을 해야지만 접근이 가능하다. -> 웹 브라우저에서 경로를 찾는다고해도 응답하지 않는다.
    }
}
