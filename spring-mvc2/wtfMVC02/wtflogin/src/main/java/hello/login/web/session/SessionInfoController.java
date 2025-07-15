package hello.login.web.session;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
/*
@ResponseBody -> HttpMessageConverter 작동
MappingJackson2HttpMessageConverter (JSON용)
StringHttpMessageConverter (단순 문자열용)
*/
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다...";
        }

        //각종 세션 데이터들 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(
                        name -> log.info("session name = {} , value = {}", name, session.getAttribute(name)));

        log.info("sessionId = {}", session.getId());
        log.info("MaxInactiveInterval = {}", session.getMaxInactiveInterval());
        log.info("creationTime = {} ", session.getCreationTime());
        log.info("lastAccessedTime = {} ", new Date(session.getLastAccessedTime()));
        log.info("is New = {}", session.isNew());


        return "[세션 정보들 출력]";


    }
}
