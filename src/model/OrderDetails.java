package model;

public class OrderDetails {
    private String orderId;
    private String itemId;
    private int unit;
    private double totalPrice;

    public OrderDetails() {
    }

    public OrderDetails(String orderId, String itemId, int unit, double totalPrice) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.unit = unit;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", unit=" + unit +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
