package hello.itemservice.web.validation;


import hello.itemservice.web.validation.form.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {

    @PostMapping("/add")
    //RequestMappingHandlerMapping -> Map<RequestMappingInfo , HandlerMethod>
    /**
     * RequestMappingInfo -> URI Info + RequestMethod(Http method)
     * HandlerMethod -> Controller Class Bean(Singleton) + Method Instance(by java Reflection)
     */
    public Object addItem(@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult) {

        log.info("API Controller Call");

        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors ={}", bindingResult);
            return bindingResult.getAllErrors();
        }

        log.info("[API Controller Logic Process Success!!]");
        return form;
    }
}
