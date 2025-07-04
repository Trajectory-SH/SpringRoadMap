package com.seeweed.spring_mvc_start.web.frontcontroller.v4.controller;

import com.seeweed.spring_mvc_start.domain.member.Member;
import com.seeweed.spring_mvc_start.domain.member.MemberRepository;
import com.seeweed.spring_mvc_start.web.frontcontroller.v4.ControllerV4;

import java.util.List;
import java.util.Map;

public class MemberListControllerV4 implements ControllerV4 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {

        List<Member> members = memberRepository.findAll();
        model.put("members", members);

        return "members";
    }
}
