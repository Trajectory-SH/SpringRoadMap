package hello.servlet01.web.frontController.v3.controller;

import hello.servlet01.domain.member.Member;
import hello.servlet01.domain.member.MemberRepository;
import hello.servlet01.web.frontController.ModelView;
import hello.servlet01.web.frontController.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {

        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        //Model&View 1. 논리주소 세팅 2.모델 세팅
        ModelView mv = new ModelView("save-result");
        mv.getModel().put("member", member);

        return mv;
    }
}
