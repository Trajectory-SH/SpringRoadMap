package hello.servlet01.web.remindfrontcontroller.v3.controller;

import hello.servlet01.web.remindfrontcontroller.ModelView;
import hello.servlet01.web.remindfrontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {

    @Override
    public ModelView process(Map<String, String> paramMap) {
        return new ModelView("new-form");
        //jsp의 template를 찾는 기본 경로 -> webapp
    }
}
