package hello.servlet01.web.remindfrontcontroller.v3;

import hello.servlet01.web.remindfrontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {

    ModelView process(Map<String, String> paramMap);

    //실제로 Spring Mvc에서 Adapter는 DispatcherServlet에 ModelAndView를 return한다.
    //Model and View -> 비즈니스 로직 코드를 실행한 결과 데이터 --> 모델, 논리적인 이름의 View
    //viewresolver를 통해서 실제 물리적인 주소를 포함하고 있는 View 객체를 생성해준다.
}
