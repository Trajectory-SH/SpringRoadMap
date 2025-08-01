package trajectory.springtx.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    private String username;//정상 , 예외, 잔고부족(비즈니스 예외) -> 주문의 상태를 전달 실무에서는 실제 주문 객체가 들어온다.
    private String payStatus;//대기, 완료
}
