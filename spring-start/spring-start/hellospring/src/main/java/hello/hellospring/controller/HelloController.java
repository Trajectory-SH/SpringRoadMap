package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!");//key  => value
        return "hello";//resources -> templates -> hello.html로 가서 랜더링하세요...
        //viewResolver가 화면을 찾아서 처리한다.
        //스프링 부터 템블릿 앤진 resources:templates/ {viewName}(hello).html이 열린다.
        //thymeleaf가 해준다.
    }

    //화면을 랜더링해서 브라우저에 넘겨주는 방법
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello" + name; // "hello {spring}" 요청한 클라이언트에 직접 그대로 내려감
        //view 이런 것 없음 html tag 같은 것 없음...
    }
    //API 방식 -> HTTP 통신 프로토콜 head / body 부분 -> 응답 body부분에 직접 넣어주겠다.


    //static class -> 내부 정적 클래스!

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

    //html tag -> xml 방식 => 최근에는 json방식으로 개발이 진행됨
    //xml은 무겁다, 열고 닫고 두 번이나 작성해야함 -> 최근에는 json 방식으로 통합이 되어있음.
    //Spring 세팅에서도 json이 default값이다.



}
