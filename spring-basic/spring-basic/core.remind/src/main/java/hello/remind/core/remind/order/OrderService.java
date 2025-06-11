package hello.remind.core.remind.order;

public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
