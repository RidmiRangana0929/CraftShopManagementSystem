package controller;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class ReportsFormController {
    public AnchorPane reportsContext;
    public Label lblCustomers;
    public Label lblOrders;
    public Label lblSuppliers;
    public Label lblIncome;
    public Label lblExpenditure;
    public Label lblSalaryEx;


    public void initialize(){
        try {
            setCustomerCount();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            setOrderCount();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            setSupplierCount();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            setIncome();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            setExpenditure();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            setExpenditureForSalary();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setCustomerCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(c_id) FROM customer");

        if (resultSet.next()){
            lblCustomers.setText(String.valueOf(resultSet.getInt(1)));
        }
    }

    private void setOrderCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(o_id) FROM orders");

        if (resultSet.next()){
            lblOrders.setText(String.valueOf(resultSet.getInt(1)));
        }
    }

    private void setSupplierCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(s_id) FROM supplier");

        if (resultSet.next()){
            lblSuppliers.setText(String.valueOf(resultSet.getInt(1)));
        }
    }

    private void setIncome() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT SUM(total) FROM orders");

        DecimalFormat dc= new DecimalFormat("0.00");
        dc.setMaximumFractionDigits(2);
        if (resultSet.next()){
            lblIncome.setText(dc.format(resultSet.getDouble(1)));
        }
    }

    private void setExpenditure() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT SUM(total_cost) FROM material_detail");

        DecimalFormat dc= new DecimalFormat("0.00");
        dc.setMaximumFractionDigits(2);
        if (resultSet.next()){
            lblExpenditure.setText(dc.format(resultSet.getDouble(1)));
        }
    }

    private void setExpenditureForSalary() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT SUM(salary) FROM salary_status");

        DecimalFormat dc= new DecimalFormat("0.00");
        dc.setMaximumFractionDigits(2);
        if (resultSet.next()){
            lblSalaryEx.setText(dc.format(resultSet.getDouble(1)));
        }
    }


}
