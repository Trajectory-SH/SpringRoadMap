package com.seeweed.spring_mvc_start.domain.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private Long id;
    private String username;
    private int age;

    public Member() {}

    public Member(String username, int age) {
        this.age = age;
        this.username = username;
    }
}
