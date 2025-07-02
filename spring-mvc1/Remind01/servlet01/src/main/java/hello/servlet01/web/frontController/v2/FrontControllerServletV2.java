package hello.servlet01.web.frontController.v2;

import hello.servlet01.web.frontController.MyView;
import hello.servlet01.web.frontController.v2.controller.MemberFormControllerV2;
import hello.servlet01.web.frontController.v2.controller.MemberListControllerV2;
import hello.servlet01.web.frontController.v2.controller.MemberSaveControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2",urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        ControllerV2 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //FrontController에서 request에 맞는 controller를 맵핑해서 return하고 process를 실행한다.
        //ListController로 들어가면 request.setAttribute()를 통해 view에 넘겨줄 수 있는
        //모델을 생성해놓음..-> HttpServletRequest 객체는 사용자에게 필요한 정보 뿐만이 아니라
        //Http request message의  Start-line, Header정보 등 너무나도 다양하고 무거운 정보들이 존재한다.
        //request, response 객체들은 WAS가 자동으로 만들어주기 때문에 Servlet에 의존성이 강해 단위테스트를 진행하는 것에 어려움이 있다.
        MyView view = controller.process(request, response);
        view.render(request, response);
    }
}