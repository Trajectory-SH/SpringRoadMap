package hello.remind.core.remind.order;

public class Order {
    private Long memberId;
    private String itemName;
    private int itemPrice;
    private int discountPrice;


    public Order(Long memberId, String itemName, int itemPrice, int discountPrice) {
        this.memberId = memberId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.discountPrice = discountPrice;
    }

    public int calculatePrice() {
        return itemPrice - discountPrice;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public Long getMemberId() {
        return memberId;
    }

    @Override
    public String toString() {
        return "Order{" +
               "discountPrice=" + discountPrice +
               ", memberId=" + memberId +
               ", itemName='" + itemName + '\'' +
               ", itemPrice=" + itemPrice +
               '}';
    }
}
