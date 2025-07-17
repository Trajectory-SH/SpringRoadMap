package hello.exception.api;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiExceptionController {

    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        //{id}와 파라미터명 id가 같다면 생략이 가능하다.
        if (id.equals("ex")) {
            throw new RuntimeException("[올바르지 않은 사용자 접속]");
        }
        return new MemberDto(id, "hello" + id);
        
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        //DTO -> Data Transfer Object
        private String memberId;
        private String name;
    }
}
