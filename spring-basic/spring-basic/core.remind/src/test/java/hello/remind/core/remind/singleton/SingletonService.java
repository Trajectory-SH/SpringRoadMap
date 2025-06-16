package hello.remind.core.remind.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonService {

    //1.static 영역에 객체를 딱 하나만 생성한다.
    private static final SingletonService SINGLETON_SERVICE = new SingletonService();

    //2.public으로 열어서 객체 인스턴스가 필요하면 이 static 메서드를 통해서만 조회하도록 허용한다.(클래스 이름으로 접근한다)
    public static SingletonService getInstance() {
        return SINGLETON_SERVICE;
    }

    //3.생성자를 private로 선언후 외부에서 생성자 호출을 막는다.
    private SingletonService() {
    }

    public void doSomething() {
        System.out.println("doSomethingSingletonService");
    }

    @Test
    @DisplayName("싱글톤 패턴 객체 테스트")
    public void SingletonServiceTest() {
        new SingletonService();
    }
}
