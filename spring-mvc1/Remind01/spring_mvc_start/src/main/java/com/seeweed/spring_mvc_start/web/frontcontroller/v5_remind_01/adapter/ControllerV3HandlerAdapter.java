package com.seeweed.spring_mvc_start.web.frontcontroller.v5_remind_01.adapter;

import com.seeweed.spring_mvc_start.web.frontcontroller.ModelView;
import com.seeweed.spring_mvc_start.web.frontcontroller.v3.ControllerV3;
import com.seeweed.spring_mvc_start.web.frontcontroller.v5_remind_01.MyHandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {


    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        ControllerV3 controllerV3 = (ControllerV3) handler;

//        Map<String, String> paramMap = createParamMap(request);
        return null;
    }
}
