package springStart.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();
    //static으로 -> 클래스 레벨에 올라감.
    //static 영역에서는 해당 클래스 단위에서 공유되기 때문에 단 하나만 메서드 영역에 존재한다.
    //final => 한 번 설정이 되면 상수라서 변하지 않는다.
    //JAVA가 JVM에 뜰 때 자동으로 인스턴스를 생성해서 메서드 영역에 올려둔다.
    private SingletonService() {}

    public static SingletonService getInstance() {
        return instance;
    }

    public void logic() {
        System.out.println("싱글톤 객체의 로직 호출");
    }

}


