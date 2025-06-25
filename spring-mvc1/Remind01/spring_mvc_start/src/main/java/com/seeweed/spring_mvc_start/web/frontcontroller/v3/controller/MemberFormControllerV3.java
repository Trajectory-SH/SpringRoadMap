package com.seeweed.spring_mvc_start.web.frontcontroller.v3.controller;

import com.seeweed.spring_mvc_start.web.frontcontroller.ModelView;
import com.seeweed.spring_mvc_start.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {
    @Override
    public ModelView process(Map<String, String> paramMap) {
        return new ModelView("new-form");
    }
}
