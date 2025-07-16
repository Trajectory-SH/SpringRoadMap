package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //ServletRequest -> HttpServletRequest의 부모 : 처음에 필터가 설계될 때 HTTP 요청 뿐만이 아니라 다양한 것들을 받을 수 있게 설계됨
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();


        try{
            log.info("REQUEST [{}] [{}] ", uuid, requestURI);
            chain.doFilter(request, response);//해당 필터를 거친 뒤에 다음 로직을 실행한다 -> DispatchServlet -> Handler(Controller)...
        } catch (Exception e) {
            throw e;
        }finally {
            log.info("RESPONSE [{}] [{}] ", uuid, requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destory");
    }
}
