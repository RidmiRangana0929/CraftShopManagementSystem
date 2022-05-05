package model;

public class Customer {
    private String customerId;
    private String customerName;
    private String nic;
    private String telNo;
    private String address;

    public Customer() {
    }

    public Customer(String customerId, String customerName, String nic, String telNo, String address) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.nic = nic;
        this.telNo = telNo;
        this.address = address;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", nic='" + nic + '\'' +
                ", telNo='" + telNo + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
