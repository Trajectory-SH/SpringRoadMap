package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //content-type잡기 -> text/html
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        //서블릿으로 html을 작성하려면 위와 같은 방식으로 작성해야한다. -> 뭐 JavaCode이기 때문에 동적으로 생성이 가능하다..!
        writer.println("<html>");
        writer.println("<body>");
        writer.println("<div>이거 슨배님들은 좀 힘드셨겠구나</div>");
        writer.println("</body>");
        writer.println("</html>");

    }
}
