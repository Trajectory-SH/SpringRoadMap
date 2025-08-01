package trajectory.springtx.order;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void order(Order order) throws NotEnoughMoneyException {
        log.info("order 호출");
        orderRepository.save(order);

        log.info("결제 프로세스 진입");

        if (order.getUsername().equals("예외")) {//시스템 예외가 발생하면 그냥 => 트랜잭션 롤백
            log.info("시스템 예외 설정");
            throw new RuntimeException("시스템 예외");
        } else if (order.getUsername().equals("잔고부족")) {//비즈니스 예외라고 하면 분명히 체크예외 발생 => 트랜잭션 커밋
            log.info("잔고 부족 => 비즈니스 예외 발생");
            order.setPayStatus("대기");//order data -> 주문이 들어왔을 때 비즈니스 로직이기에 커밋되기를 기대한다.
            throw new NotEnoughMoneyException("잔고가 부족합니다.");
        } else {
            //정상 승인 로직
            log.info("정상 승인");
            order.setPayStatus("완료");
        }
        log.info("결제 프로세스 완료");
    }
}
