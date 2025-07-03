package mvc.basic.springmvcbasic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {
    private Logger log = LoggerFactory.getLogger(getClass());


    /**
     * <b>기본 요청</b><br>
     * <hr>
     * 둘다 허용 /hello-basic, /hello-basic/<br>
     * HTTP 메서드 모두 허용 GET, HEAD, POST, PUT, PATCH, DELETE<br>
     */
    @RequestMapping("/hello-basic")
    public String helloBasic() {
        log.info("helloBasic");
        return "helloBasic RequestMapping";
    }

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "mapping-get-v1 success";
    }

    //HTTP 메서드를 축약한 애노테이션을 사용하는 것이 더 직관적이다.

    /**
     * <b>RequestMappingHandlerMapping</b><br>
     * <hr>
     * 살제로 해당 컨트롤러가 스프링 빈에 등록이 되고 메서드별로 리플렉션으로 메서드 객체를 생성<br>
     * 해당 핸들러 목록은 Map형태로 저장되는데<HandlerInfo,HandlerMethod> 타입으로 저장된다.<br>
     * 이 떄 핸들러를 식별할 수 있는 Handler Info에는 <i>url정보 + HTTP 메서드 정보</i>가 들어있다.
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "mapping-get-v2";
    }

    //PathVariable
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        log.info("mappingPath userId={}", data);
        return "mappingPath -> PathVariable";
    }

    //PathVariable Multi
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "mappingPath";
    }
    //Parameter에서 지정해준 타입과 맞춰주지 않으면 400 Bad Request
    // MethodArgumentTypeMismatchException 발생 -> LOG에서 어떤 부분이 문제가 있는지 자세하게 알려준다.

    //특정 파라미터 조건 매핑하기 -> 잘 사용하지 않는다.
    //해당 어노테이션의 파라미터로 path, value가 존재하는데 동일하게 작동한다.
    //의도성을 가지고 Path를 사용하는 경우도 있다 path . method. params
    @GetMapping(value = "/mapping-param", params = "mode-debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "mappingParam";
    }

    //특정 헤더 조건 매핑 -> 헤더에 해당 정보가 들어있어야지 수락된다.
    //해당 조건이 존재하지 않으면 404 not found 리턴
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }


    //지정한 Content-Type이 아니면 415 Unsupported Media Type을 return한다.
    @PostMapping(path = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    //AcceptHeader 기반을 사용해서 다양한 조건을 붙인 설계도 가능하다.
    @PostMapping(value = "/mapping-produce", produces = "text/html")
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}
