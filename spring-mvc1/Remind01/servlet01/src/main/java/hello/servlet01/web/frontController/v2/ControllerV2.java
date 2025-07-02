package hello.servlet01.web.frontController.v2;

import hello.servlet01.web.frontController.MyView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ControllerV2 {

    //컨트롤러가 MyView를 FrontController로 return한다.
    MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
