package hello.servlet01.web.frontController;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//myView는 FrontController로 부터 호출되며...실제 물리적인 view 파일의 위치를 가지고
//해당 정보를 바탕으로 해서 실제로 디스패처를 통해서 view화면 랜더링을 시작한다 -> application의 실행 흐름을 View로 넘겨준다.
public class MyView {

    private String viewPath;

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //실제 물리적인 View의 주소를 통해서 dispatcher를 생성한다.
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request,response);
    }


}
