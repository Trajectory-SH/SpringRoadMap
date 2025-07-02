package hello.servlet01.web.frontController.v1.controller;

import hello.servlet01.web.frontController.v1.ControllerV1;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 해당 컨트롤러의 process(로직이 실행되면) new-form 영역으로 dispatcher를 통해 제어권을 view로 넘긴 다음에 view를 랜더링한다.
 */
public class MemberFormControllerV1 implements ControllerV1 {

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //실제 view의 물리 주소
        //new-form.jsp에서 랜더링을 할 때 비즈니스 로직에서 필요한 Model 필요가 없다.
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request,response);
    }
}
