package model;

public class Supplier {
    private String supplierId;
    private String supplierName;
    private String address;
    private String email;
    private String telNo;

    public Supplier() {
    }

    public Supplier(String supplierId, String supplierName, String address, String email, String telNo) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.address = address;
        this.email = email;
        this.telNo = telNo;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", telNo='" + telNo + '\'' +
                '}';
    }
}
