package model;

public class SalaryStatus {
    private String ssId;
    private String employeeId;
    private double salary;
    private String status;
    private String date;

    public SalaryStatus() {
    }

    public SalaryStatus(String ssId, String employeeId, double salary, String status, String date) {
        this.ssId = ssId;
        this.employeeId = employeeId;
        this.salary = salary;
        this.status = status;
        this.date = date;
    }


    public String getSsId() {
        return ssId;
    }

    public void setSsId(String ssId) {
        this.ssId = ssId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SalaryStatus{" +
                "ssId='" + ssId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", salary=" + salary +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
