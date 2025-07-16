package hello.login.web.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        request.setAttribute(LOG_ID, uuid);

        //@RequestMapping => HandlerMethod
        //preHandle에는 handler 정보도 넘어온다.-> HandlerMapping에서 우선 RequestMappingInfo에 맞는 handler를 찾아옴
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;//해당 URL 요청에 호출될 컨트롤러 메서드에 대한 모든 정보들을 포함하고 있다.
        }

        log.info("REQUEST [{}] [{}] [{}]", uuid, requestURI, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);
        log.info("RESPONSE [{}] [{}]", logId, requestURI);
        if (ex != null) {
            log.error("[afterCompletion error]", ex);
        }
    }
}
