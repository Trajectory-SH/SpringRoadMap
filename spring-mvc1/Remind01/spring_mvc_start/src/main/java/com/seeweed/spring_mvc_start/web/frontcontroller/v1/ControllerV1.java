package com.seeweed.spring_mvc_start.web.frontcontroller.v1;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ControllerV1 {
    //Servlet에서 HTTP Request를 받았을 때 실행되는 service 메서드와 비슷하다.
    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
