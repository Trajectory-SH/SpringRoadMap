package trajectory.springtx.order;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void complete() throws NotEnoughMoneyException {
        //given
        Order order = new Order();
        order.setUsername("정상");

        //when -> 이거 체크 Exception throws 해야한다.
        orderService.order(order);

        //then
        Order findOrder = orderRepository.findById(order.getId()).get();
        //Return이 Optional이라서 get을 통해서 실제 내부에 존재하는 객체를 꺼내온다.
        /**
         * 	•	Optional 내부에 값이 존재하면 그 값을 꺼냄
         * 	•	❗ 존재하지 않으면 (isEmpty()인 경우), NoSuchElementException 예외 발생
         */
        assertThat(findOrder.getPayStatus()).isEqualTo("완료");
    }

    @Test
    void runtimeException() {
        //given
        Order order = new Order();
        order.setUsername("예외");//주문의 이름을 예외라고 하자

        //when
        assertThatThrownBy(() -> orderService.order(order)).isInstanceOf(RuntimeException.class);

        //then
        Optional<Order> orderOptional = orderRepository.findById(order.getId());
        assertThat(orderOptional.isEmpty()).isTrue();
    }

    @Test
    void bizException() {
        //given
        Order order = new Order();
        order.setUsername("잔고부족");

        //when
        try {
            orderService.order(order);
            fail("잔고 부족 예외가 발생해야합니다.");
        } catch (NotEnoughMoneyException e) {
            log.info("고객 -> 잔고부족 입금 + 계좌 이체 안내 프로세스");
        }

        //then
        Order findOrder = orderRepository.findById(order.getId()).get();
        assertThat(findOrder.getPayStatus()).isEqualTo("대기");
    }
}