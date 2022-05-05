package model;

public class MaterialDetails {

    private String mtId;
    private String sId;
    private double unitPrice;
    private int unit;
    private double totalCost;

    public MaterialDetails() {
    }

    public MaterialDetails(String mtId, String sId, double unitPrice, int unit, double totalCost) {
        this.mtId = mtId;
        this.sId = sId;
        this.unitPrice = unitPrice;
        this.unit = unit;
        this.totalCost = totalCost;
    }

    public String getMtId() {
        return mtId;
    }

    public void setMtId(String mtId) {
        this.mtId = mtId;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
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

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "MaterialDetails{" +
                "mtId='" + mtId + '\'' +
                ", sId='" + sId + '\'' +
                ", unitPrice=" + unitPrice +
                ", unit=" + unit +
                ", totalCost=" + totalCost +
                '}';
    }
}
