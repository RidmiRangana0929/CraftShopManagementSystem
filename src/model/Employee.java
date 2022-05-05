package model;

public class Employee {
    private String employeeId;
    private String EmployeeName;
    private String nic;
    private String address;
    private String email;
    private String telNo;
    private String roleType;
    private double salary;

    public Employee() {
    }

    public Employee(String employeeId, String employeeName, String nic, String address, String email, String telNo, String roleType, double salary) {
        this.employeeId = employeeId;
        EmployeeName = employeeName;
        this.nic = nic;
        this.address = address;
        this.email = email;
        this.telNo = telNo;
        this.roleType = roleType;
        this.salary = salary;
    }



    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
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

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", EmployeeName='" + EmployeeName + '\'' +
                ", nic='" + nic + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", telNo='" + telNo + '\'' +
                ", roleType='" + roleType + '\'' +
                ", salary=" + salary +
                '}';
    }
}
