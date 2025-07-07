package hello.servlet01.web.remindfrontcontroller.v3.controller;

import hello.servlet01.domain.member.Member;
import hello.servlet01.domain.member.MemberRepository;
import hello.servlet01.web.remindfrontcontroller.ModelView;
import hello.servlet01.web.remindfrontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {

        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelView mv = new ModelView("save-result");
        mv.getModel().put("member", member);
        //jsp 뷰를 랜더링 할 때 -> 해당 "member"를 Key 값으로 사용해서 객체 프로퍼티 접근법으로 가져옴
        return mv;
    }

}
