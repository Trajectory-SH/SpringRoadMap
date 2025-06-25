package com.seeweed.spring_mvc_start.web.frontcontroller.v5;

import com.seeweed.spring_mvc_start.web.frontcontroller.ModelView;
import com.seeweed.spring_mvc_start.web.frontcontroller.MyView;
import com.seeweed.spring_mvc_start.web.frontcontroller.v3.controller.MemberFormControllerV3;
import com.seeweed.spring_mvc_start.web.frontcontroller.v3.controller.MemberListControllerV3;
import com.seeweed.spring_mvc_start.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import com.seeweed.spring_mvc_start.web.frontcontroller.v4.controller.MemberFormControllerV4;
import com.seeweed.spring_mvc_start.web.frontcontroller.v4.controller.MemberListControllerV4;
import com.seeweed.spring_mvc_start.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import com.seeweed.spring_mvc_start.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import com.seeweed.spring_mvc_start.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
    //해당 url로 Request message가 오면 [frontControllerServletV5] 이름의 서블릿 컨테이너가 생성
    //이후에 service 메서드를 호출한다.
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    //default constructor -> WAS에 의해서 컨테이너가 생성이 될 때 자동으로 호출된다.
    public FrontControllerServletV5() {
        intHandlerMappingMap();
        initHandlerAdapters();
    }

    //servlet 컨테이너 생성시 자동으로 호출 -> 핸들러 어댑터의 목록...Configuration
    private void intHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    //ControllerV3HandlerAdapter는 MyHandlerAdapter를 구현했기 때문에 핸들러 어댑터 목록에 들어갈 수 있다.
    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    //해당 메서드 부터 실행
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        ModelView mv = adapter.handle(request, response, handler);

        MyView view = viewResolver(mv.getViewName());
        view.render(mv.getModel(), request, response);


    }


    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }


    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.support(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("해당 타입의 어댑터를 찾을 수 없습니다. handler =  " + handler);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
