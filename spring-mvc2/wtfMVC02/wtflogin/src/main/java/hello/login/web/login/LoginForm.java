package hello.login.web.login;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {
    //Member에 있는 데이터를 활용해서 로그인을 할 수 있지만 다양한 문제가 있기 때문에 해당 기능을 수행하는 전용 객체를 만들어 주는 것이 더 좋다.

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
}
