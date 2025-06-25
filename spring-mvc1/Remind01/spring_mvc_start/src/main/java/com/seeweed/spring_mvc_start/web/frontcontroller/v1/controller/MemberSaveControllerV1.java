package com.seeweed.spring_mvc_start.web.frontcontroller.v1.controller;

import com.seeweed.spring_mvc_start.domain.member.Member;
import com.seeweed.spring_mvc_start.domain.member.MemberRepository;
import com.seeweed.spring_mvc_start.web.frontcontroller.v1.ControllerV1;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberSaveControllerV1 implements ControllerV1 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //[비즈니스 로직 실행]
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        //HTTP 메시지에서 읽어온 value들은 기본적으로 String이기 때문에 Integer로 파싱해줘야한다.

        Member member = new Member(username, age);
        memberRepository.save(member);
        request.setAttribute("member", member);
        //여기서 설정한 name이라는 parameter를 통해서 jsp에서 해당 이름으로 객체에 접근한다.
        //key : value 구조


        //[view로 제어권을 넘겨주는 로직 실행] -> 사용자에게 JSP를 이용해서 view를 뿌려준다.
        String viewPath = "/WEB-INF/views/save-result.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}
