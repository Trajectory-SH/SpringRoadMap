package springStart.core.lifecycle;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);

    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작시 호출 메서드
    public void connect() {
        System.out.println("connect = " + url);
    }

    //실제로 연결이 되면 Message를 던질 수 있음.
    public void call(String message) {
        System.out.println("call : " + url + " message =" + message);
    }

    //서비스 종료시 안전한 종료
    public void disconnect() {
        System.out.println("close: "+ url);
    }

    @PreDestroy
    public void close() throws Exception {
        System.out.println("NetworkClient.close");
        disconnect();
    }

    @PostConstruct
    public void init() throws Exception {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지 생성 및 전송");
    }
}
