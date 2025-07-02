package hello.servlet01.web.frontController;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class ModelView {
    //1. 템플릿 엔진을 통해서 View를 실제로 랜더링할 때 필요한 정보들을 담고 있는 Model
    //2. 물리적인 View 이름을 ViewResolver를 통해서 생성하기 위해서 필요한 논리적인 view이름인 viewName이 존재한다.

    private String viewName;
    private Map<String, Object> model = new HashMap<>();

    public ModelView(String viewName) {
        this.viewName = viewName;
    }
}
