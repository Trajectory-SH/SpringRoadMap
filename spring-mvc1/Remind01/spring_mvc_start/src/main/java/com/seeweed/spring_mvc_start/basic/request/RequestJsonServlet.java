package com.seeweed.spring_mvc_start.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seeweed.spring_mvc_start.basic.JsonDataParsing;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestJsonServlet",urlPatterns = "/request-body-json")
public class RequestJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();



    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("message Body = " + messageBody);

        JsonDataParsing parsedData = objectMapper.readValue(messageBody, JsonDataParsing.class);

        System.out.println("parsedData.getUsername() = " + parsedData.getUsername());
        System.out.println("parsedData.getAge() = " + parsedData.getAge());

        response.getWriter().write("JsonData Parsed Complete");

    }
}
