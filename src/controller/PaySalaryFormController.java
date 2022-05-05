package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import model.Customer;
import model.SalaryStatus;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaySalaryFormController {
    public JFXTextField txtName;
    public JFXButton btnPay;
    public JFXTextField txtSalary;
    public JFXComboBox<String> cmbStatus;
    public JFXTextField txtDate;
    public JFXComboBox<String> cmbEmpId;

    public void initialize(){

        cmbStatus.getItems().add("Paid");
        cmbStatus.getItems().add("Didn't Pay");

        loadDate();
        setEmployeeIds();

    }

    private void setEmployeeIds(){
        try {
            ObservableList<String > eIdObList= FXCollections.observableArrayList(
                    PaySalaryCrudController.getEmployeeIds()
            );
            cmbEmpId.setItems(eIdObList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void cmbEmployeeSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result= CrudUtil.execute("SELECT name,salary FROM employee WHERE e_id=?",cmbEmpId.getValue());
            if(result.next()){
                txtName.setText(result.getString(1));
                txtSalary.setText(result.getString(2));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void loadDate() {
        txtDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    public void payOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        SalaryStatus ss=new SalaryStatus(
                PaySalaryCrudController.getSalaryStatusId(1),cmbEmpId.getValue(),Double.parseDouble(txtSalary.getText()),cmbStatus.getValue(),txtDate.getText()
        );
        try {
            if (CrudUtil.execute("INSERT INTO balarina_crafts.salary_status VALUES (?,?,?,?,?)", ss.getSsId(),ss.getEmployeeId(),ss.getSalary(),ss.getStatus(),ss.getDate())) {
                new Alert(Alert.AlertType.CONFIRMATION, "Information saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Error! Please Try Again...").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


}
