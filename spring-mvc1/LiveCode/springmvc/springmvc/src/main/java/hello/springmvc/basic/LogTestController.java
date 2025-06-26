package hello.springmvc.basic;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j//롬복이 제공하는 어노테이션
//REST API -> return을 하면 view 이름을 반환하는 것이 아니라 그냥 String을 메시지 body에 반환해버린다.
public class LogTestController {

    //private final Logger log = LoggerFactory.getLogger(getClass());
    //-> 롬복이 해당 애노테이션을 통해서 자동으로 넣어준다.
    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";
        System.out.println("name = " + name);


        //application.properties -> logging 레벨을 설정할 수 있다.
        log.trace("trace log = {}", name);//개발 단계 레벨
        log.debug("debug log = {}",name);//디버그 레벨

        log.info("info log = {}", name);//운영 서버 레벨  -> spring boot 기본 레벨
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);

        return "ok";
    }

}
