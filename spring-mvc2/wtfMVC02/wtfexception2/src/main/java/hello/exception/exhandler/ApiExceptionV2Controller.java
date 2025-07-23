package hello.exception.exhandler;

import hello.exception.api.MemberDto;
import hello.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자[EX]");
        }
        if (id.equals("bad")) {
            throw new IllegalArgumentException("입력값 오류[BAD]");
        }
        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류[USER-EX]");
        }
        return new MemberDto(id, "name" + id);
    }


    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberid;
        private String name;
    }


}
