package hello.core.singleton;

public class SingletonService {


    //자기 자신을 선언 => 클래스 레벨에 올라가 있기 떄문에 단 하나만 생성 -> Static영역에 대해서 공부!


    private static final SingletonService instance = new SingletonService();
    //이 메서드를 호출하면 항상 같은 인스턴스만 반환한다.
    public static SingletonService getInstance() {
        return instance;
    }

    //외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 막는다.
    private SingletonService() {
        //private 생성자로 외부에서 새로운 인스턴스의 생성을 막아버린다.
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }


}
