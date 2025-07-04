package hello.servlet01.domain.member;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Member {

    private Long id;
    private String username;
    private int age;

    public Member() {
    }

    public Member(String username, int age) {
        this.age = age;
        this.username = username;
    }
}
