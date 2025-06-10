package hello.hellospring.domain;

import jakarta.persistence.*;

@Entity
public class Member {
    //Db랑 Mapping...
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)//DB가 자동으로 ID값을 할당해주는 것
    private Long id;//임의의 값 -> 데이터를 구분하기 위한 시스템이 정하는 값

    //실제 db의 column값을 읽을 수 있게 만든다.
    //@Column(name = "username")
    private String name;

    //Primary Key..

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
