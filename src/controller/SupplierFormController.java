package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import model.Item;
import model.Supplier;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SupplierFormController {
    public AnchorPane supplierContext;
    public JFXTextField txtSupplierId;
    public JFXTextField txtSupplierName;
    public JFXTextField txtTelNo;
    public JFXTextField txtEmail;
    public JFXTextField txtAddress;
    public JFXButton btnSave;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){
        Pattern idPattern = Pattern.compile("^(S00)[0-9]+$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,45}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,30}$");
        Pattern emailPattern = Pattern.compile("^[A-z0-9@.]{3,}(.com)$");
        Pattern telNoPattern = Pattern.compile("^[0-9]{10,}$");;

        map.put(txtSupplierId,idPattern);
        map.put(txtSupplierName,namePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtEmail,emailPattern);
        map.put(txtTelNo,telNoPattern);
    }

    public void displayAllSuppliersOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("../views/AllSuppliersForm.fxml"));
        supplierContext.getChildren().add(parent);
    }

    public void saveOnAction(ActionEvent actionEvent) throws IOException {

        Supplier s = new Supplier(
                txtSupplierId.getText(),txtSupplierName.getText(),txtAddress.getText(),txtEmail.getText(),txtTelNo.getText()
        );

        try {
            if(CrudUtil.execute("INSERT INTO balarina_crafts.supplier VALUES (?,?,?,?,?)",s.getSupplierId(),s.getSupplierName(),s.getAddress(),s.getEmail(),s.getTelNo())){
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier Information saved Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Error! Please Try Again...").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        txtSupplierId.clear();
        txtSupplierName.clear();
        txtEmail.clear();
        txtTelNo.clear();
        txtAddress.clear();

        Parent parent= FXMLLoader.load(getClass().getResource("../views/SupplierForm.fxml"));
        supplierContext.getChildren().add(parent);

    }

    public void updateOnAction(ActionEvent actionEvent) throws IOException {

        Supplier s = new Supplier(
                txtSupplierId.getText(),txtSupplierName.getText(),txtAddress.getText(),txtEmail.getText(),txtTelNo.getText()
        );

        try {
            if(CrudUtil.execute("UPDATE balarina_crafts.supplier SET name=?,address=?,email=?,tel_no=? WHERE s_id=?  ",s.getSupplierName(),s.getAddress(),s.getEmail(),s.getTelNo(),s.getSupplierId())){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Update Fail!").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        txtSupplierId.clear();
        txtSupplierName.clear();
        txtEmail.clear();
        txtTelNo.clear();
        txtAddress.clear();

        Parent parent= FXMLLoader.load(getClass().getResource("../views/SupplierForm.fxml"));
        supplierContext.getChildren().add(parent);

    }

    public void deleteOnAction(ActionEvent actionEvent) throws IOException {
        try {
            if(CrudUtil.execute("DELETE FROM supplier WHERE s_id=?",txtSupplierId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Delete Fail!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }

        txtSupplierId.clear();
        txtSupplierName.clear();
        txtEmail.clear();
        txtTelNo.clear();
        txtAddress.clear();

        Parent parent= FXMLLoader.load(getClass().getResource("../views/SupplierForm.fxml"));
        supplierContext.getChildren().add(parent);

    }

    public void txtSearchOnAction(ActionEvent actionEvent) {

        try {
            ResultSet result=CrudUtil.execute("SELECT * FROM supplier WHERE s_id=?",txtSupplierId.getText());
            if(result.next()){
                txtSupplierName.setText(result.getString(2));
                txtAddress.setText(result.getString(3));
                txtEmail.setText(result.getString(4));
                txtTelNo.setText(result.getString(5));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSave);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map,btnSave);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }
}
