package hello.servlet01.web.frontController.v3;

import hello.servlet01.web.frontController.ModelView;
import hello.servlet01.web.frontController.MyView;
import hello.servlet01.web.frontController.v3.controller.MemberFormControllerV3;
import hello.servlet01.web.frontController.v3.controller.MemberListControllerV3;
import hello.servlet01.web.frontController.v3.controller.MemberSaveControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "frontControllerServletV3",urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new
                MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new
                MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new
                MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        ControllerV3 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String,String> paramMap= createParamMap(request);

        ModelView mv = controller.process(paramMap);

        String viewName = mv.getViewName();

        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), request, response);


    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }


    //HttpServletRequest 객체를 사용해서 Servlet 의존성이 없는 createParamMap을 만들고 해당 map이 controller에 필요한 데이터 전달
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();

        request.getParameterNames().asIterator().forEachRemaining(paramName
                -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
