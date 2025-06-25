package com.seeweed.spring_mvc_start.web.frontcontroller.v1.controller;

import com.seeweed.spring_mvc_start.web.frontcontroller.v1.ControllerV1;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberFormControllerV1 implements ControllerV1 {

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        //FormController가 호출되면... viewPath 경로로 포워딩시켜준다.
        //request 객체를 마치 Model 처럼 사용하고 있다.
        dispatcher.forward(request, response);
    }
}
