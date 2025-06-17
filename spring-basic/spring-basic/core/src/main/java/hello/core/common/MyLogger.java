package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;


    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "] "    + requestURL + " : [" + message + "]");
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();//Unique한 ID가 생성 -> 거의 중복 없음
        System.out.println("[" + uuid + "] " + "request scope bean Created : " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] " + "request scope bean Closed : " + this);
    }


}


