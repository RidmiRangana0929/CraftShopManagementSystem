package model;

public class ItemDetails {
    private String itemId;
    private String materialId;
    private String quantity;

    public ItemDetails() {
    }

    public ItemDetails(String itemId, String materialId, String quantity) {
        this.itemId = itemId;
        this.materialId = materialId;
        this.quantity = quantity;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ItemDetails{" +
                "itemId='" + itemId + '\'' +
                ", materialId='" + materialId + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
