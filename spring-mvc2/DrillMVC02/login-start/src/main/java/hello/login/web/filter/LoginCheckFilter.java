package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whitelist = {"/", "/members/add", "/login", "/logout","/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 START {}", requestURI);

            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 START {}", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {

                    log.info("[미인증 사용자 REQUEST!!] {}", requestURI);
                    //로그인 창으로 redirect
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    //Location 정보를 통해서 다시 사용자가 login form으로 Redirect => 나중에 login이 되면 requestURI를 포함해서 로그인
                    // -> 유연한 사용자 경험을 제공 할 수 있다.
                    return; //미인증 사용자는 다음으로 진행하지 않고 메서드를 종료시켜버린다.
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        }finally {
            log.info("인증체크 필터 종료 {}", requestURI);
        }
    }

    //화이트 리스트인 경우 인증 체크 X
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
