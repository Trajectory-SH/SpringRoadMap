package mvc.basic.springmvcbasic.requestmapping;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController//논리 view이름으로 반환하는 것이 아니라 Response Body에 직접 내용을 작성한다.
@RequestMapping("/mapping/users")//Class 레벨과 Method 레벨을 조합해서 일반적으로 사용한다.
@Slf4j
public class MappingClassController {

    @GetMapping
    public String users() {
        return "get users";
    }

    @PostMapping
    public String addUser() {
        return "post user";
    }

    @GetMapping("/{userID}")
    public String findUser(@PathVariable String userID) {
        log.info("find UserID = {}", userID);
        return "get UserId = " + userID;
    }

    //PATCH, DELETE HTTP method 확인하기 ...!
    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId) {
        return "update userId = " + userId;
    }

    @DeleteMapping("/{userID}")
    public String deleteUser(@PathVariable String userID) {
        return "delete userId = " + userID;
    }
}
