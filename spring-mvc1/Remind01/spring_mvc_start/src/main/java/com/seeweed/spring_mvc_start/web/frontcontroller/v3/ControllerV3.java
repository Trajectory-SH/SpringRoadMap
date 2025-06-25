package com.seeweed.spring_mvc_start.web.frontcontroller.v3;

import com.seeweed.spring_mvc_start.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {
    ModelView process(Map<String, String> paramMap);
}
