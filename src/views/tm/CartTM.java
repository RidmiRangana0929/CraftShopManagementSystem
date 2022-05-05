package views.tm;

import javafx.scene.control.Button;

public class CartTM {

    private String itemId;
    private String itemName;
    private double unitPrice;
    private int unit;
    private double totalPrice;
    private Button btn;

    public CartTM() {
    }

    public CartTM(String itemId, String itemName, double unitPrice, int unit, double totalPrice, Button btn) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.unit = unit;
        this.totalPrice = totalPrice;
        this.btn = btn;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
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

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", unitPrice=" + unitPrice +
                ", unit=" + unit +
                ", totalPrice=" + totalPrice +
                ", btn=" + btn +
                '}';
    }
}
