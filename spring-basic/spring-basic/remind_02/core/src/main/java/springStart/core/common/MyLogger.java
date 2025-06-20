package springStart.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS)
//해당 bean은 HTTP 요청당 하나가 생성되고 client의 요청 로직이 마무리가 끝나면 close()를 한다.
//WAS(Web Application Server) -> Tomcat에서 request 객체 생성, response 객체 생성하는 것..!
//Spring container에 요청하는 시점에 생성한다.
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + requestURL + "[" + message + "]");
    }

    @PostConstruct//bean이 초기화되는 시점
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println();
        System.out.println("[" + uuid + "] request scope bean create -> " + this);
    }

    @PreDestroy//bean 종료되는 시점
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close -> " + this);
        System.out.println();
        System.out.println();
    }
}
