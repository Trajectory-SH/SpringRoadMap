package springStart.core.Order;

public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
