package hello.servlet01.web.frontController.v1.controller;

import hello.servlet01.domain.member.Member;
import hello.servlet01.domain.member.MemberRepository;
import hello.servlet01.web.frontController.v1.ControllerV1;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.tags.shaded.org.apache.xalan.xsltc.compiler.util.IntType;

import java.io.IOException;

//해당 컨트롤러가 실행되면 유저를 repository에 저장하고 저장 결과를 보여주는 view를 랜더링해서 클라이언트에게 전송한다.
public class MemberSaveControllerV1 implements ControllerV1 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        /**
         * HTTP request 메시지로부터 오는 쿼리 파라미터는 기본적으로 String 형태로 넘어오기 때문에
         * Integer로 형 변환을 해줘야한다.
         */
        int age = Integer.parseInt(request.getParameter("age"));//Java에서 지원하는 자동 형변환

        Member member = new Member("전수환", 30);
        Member saved = memberRepository.save(member);

        request.setAttribute("member",saved);

        String viewPath = "/WEB-INF/views/save-result.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}
