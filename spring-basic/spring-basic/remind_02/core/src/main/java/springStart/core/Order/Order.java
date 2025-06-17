package springStart.core.Order;

public class Order {
    private Long MemberId;
    private String itemName;
    private int itemPrice;
    private int discountPrice;

    public Order(int discountPrice, String itemName, int itemPrice, Long memberId) {
        this.discountPrice = discountPrice;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        MemberId = memberId;
    }

    public int calculatePrice() {
        return itemPrice - discountPrice;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Long getMemberId() {
        return MemberId;
    }

    public void setMemberId(Long memberId) {
        MemberId = memberId;
    }

    @Override
    public String toString() {
        return "Order{" +
               "discountPrice=" + discountPrice +
               ", MemberId=" + MemberId +
               ", itemName='" + itemName + '\'' +
               ", itemPrice=" + itemPrice +
               '}';
    }
}
