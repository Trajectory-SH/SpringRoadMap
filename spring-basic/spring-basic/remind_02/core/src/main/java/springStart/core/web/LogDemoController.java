package springStart.core.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springStart.core.common.MyLogger;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;
    //spring을 띄우는 단계에서는 client의 http request의 요청이 들어오지 않았다.
    //Scope 'request' is not active for the current thread;


    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
         //  MyLogger myLogger = myLoggerProvider.getObject();//이 시점에 지연로딩해서 myLogger(request)가 만들어진다.
        System.out.println("myLogger.getClass() = " + myLogger.getClass());
        myLogger.setRequestURL(requestURL);
        System.out.println("myLogger.getClass() = " + myLogger.getClass());


        myLogger.log("controller Test");
        logDemoService.logic("testID");
        System.out.println("myLogger.getClass() = " + myLogger.getClass());


        return "OKDK";
    }
}
