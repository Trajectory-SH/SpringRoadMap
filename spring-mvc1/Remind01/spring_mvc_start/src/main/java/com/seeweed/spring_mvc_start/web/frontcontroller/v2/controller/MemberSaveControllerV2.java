package com.seeweed.spring_mvc_start.web.frontcontroller.v2.controller;

import com.seeweed.spring_mvc_start.domain.member.Member;
import com.seeweed.spring_mvc_start.domain.member.MemberRepository;
import com.seeweed.spring_mvc_start.web.frontcontroller.MyView;
import com.seeweed.spring_mvc_start.web.frontcontroller.v2.ControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberSaveControllerV2 implements ControllerV2 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        request.setAttribute("member", member);

        return new MyView("/WEB-INF/views/save-result.jsp");
    }
}
