package com.seeweed.spring_mvc_start.web.frontcontroller.v3;

import com.seeweed.spring_mvc_start.web.frontcontroller.ModelView;
import com.seeweed.spring_mvc_start.web.frontcontroller.MyView;
import com.seeweed.spring_mvc_start.web.frontcontroller.v3.controller.MemberFormControllerV3;
import com.seeweed.spring_mvc_start.web.frontcontroller.v3.controller.MemberListControllerV3;
import com.seeweed.spring_mvc_start.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3",urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //WAS로 들어온 HTTP request 메시지 -> request 객체를 통해서 request.getRequestURI(); URI 문자열 추출
        String requestURI = request.getRequestURI();

        //다형성을 활용한 컨트롤러 할당 -> 매핑정보로부터
        ControllerV3 controller = controllerMap.get(requestURI);

        //검증 로직 -> 메인 비즈니스 로직을 실행하기 전에 유효한지를 확인하는 것은 좋은 pattern이다.
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }


       /* System.out.println("컨트롤러 클래스 출력");
        System.out.println(controller.getClass().getSimpleName());

        //컨트롤러는 존재한다...*/
        Map<String, String> paramMap = createParamMap(request);

        ModelView modelView = controller.process(paramMap);

        String viewName = modelView.getViewName();

        System.out.println(viewName);

        MyView view = viewResolver(viewName);
        view.render(modelView.getModel(), request, response);
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(
                paramName -> paramMap.put(paramName, request.getParameter(paramName))
        );
        return paramMap;
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }




}
