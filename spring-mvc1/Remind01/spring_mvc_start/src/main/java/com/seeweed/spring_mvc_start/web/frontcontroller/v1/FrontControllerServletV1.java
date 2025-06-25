package com.seeweed.spring_mvc_start.web.frontcontroller.v1;

import com.seeweed.spring_mvc_start.web.frontcontroller.v1.controller.MemberFormControllerV1;
import com.seeweed.spring_mvc_start.web.frontcontroller.v1.controller.MemberListControllerV1;
import com.seeweed.spring_mvc_start.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1",urlPatterns = "/front-controller/v1/*")
//  /front-controller/v1/* v1 을 포함한 여기로 들어오는 모든 request 메시지는 해당 servlet 클래스의 service()를 실행한다.
public class FrontControllerServletV1 extends HttpServlet {


    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("FrontControllerServletV1.service");
        String requestURI = request.getRequestURI();
        StringBuffer requestURL = request.getRequestURL();

        System.out.println("request message url,uri 테스트 출력 시작");
        System.out.println(requestURL.toString());
        System.out.println(requestURI);
        System.out.println("request message url,uri 테스트 출력 끝");

        ControllerV1 controller = controllerMap.get(requestURI);

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            System.out.println("컨트롤러가 지정되지 않았습니다. 포워딩 실패");
            return;
        }

        //베리 나이스합니다.
        System.out.println("포워딩 성공!!  -> controller : " + controller.getClass().getSimpleName());
        controller.process(request, response);
    }
}
