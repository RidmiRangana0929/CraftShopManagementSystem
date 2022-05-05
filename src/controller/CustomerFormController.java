package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.applet2.AppletParameters;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import util.CrudUtil;
import util.ValidationUtil;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class CustomerFormController {
    public JFXTextField txtCustomerName;
    public JFXTextField txtNic;
    public JFXTextField txtTelNo;
    public TableView<Customer> tblCustomerRec;
    public TableColumn colCustomerIdRec;
    public TableColumn colCustomerNameRec;
    public TableColumn colNicRec;
    public TableColumn colTelNoRec;
    public TableColumn colAddressRec;
    public AnchorPane customerRecContext;
    public JFXTextField txtCustomerId;
    public JFXButton btnSave;
    public JFXTextField txtAddress;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    int customerCount=0;

    public void initialize(){

        colCustomerIdRec.setCellValueFactory(new PropertyValueFactory("customerId"));
        colCustomerNameRec.setCellValueFactory(new PropertyValueFactory("customerName"));
        colNicRec.setCellValueFactory(new PropertyValueFactory("nic"));
        colTelNoRec.setCellValueFactory(new PropertyValueFactory("telNo"));
        colAddressRec.setCellValueFactory(new PropertyValueFactory("address"));

        try {
            loadAllCustomer();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        Pattern idPattern = Pattern.compile("^(C00)[0-9]+$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,45}$");
        Pattern nicPattern = Pattern.compile("^[0-9V]{8,}$");
        Pattern telNoPattern = Pattern.compile("^[0-9]{10,}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,30}$");

        map.put(txtCustomerId,idPattern);
        map.put(txtCustomerName,namePattern);
        map.put(txtNic,nicPattern);
        map.put(txtTelNo,telNoPattern);
        map.put(txtAddress,addressPattern);

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

    private void loadAllCustomer() throws ClassNotFoundException, SQLException {
        ResultSet result=CrudUtil.execute("SELECT * FROM customer");

        ObservableList<Customer> obList= FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Customer(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5)
                    )
            );
        }
        tblCustomerRec.setItems(obList);

    }

    public void saveOnAction(ActionEvent actionEvent) throws IOException {

            Customer c = new Customer(
                    txtCustomerId.getText(), txtCustomerName.getText(), txtNic.getText(), txtTelNo.getText(), txtAddress.getText()
            );

            try {
                if(CrudUtil.execute("INSERT INTO customer VALUES (?,?,?,?,?)",c.getCustomerId(),c.getCustomerName(),c.getNic(),c.getTelNo(),c.getAddress())){

                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Information saved Successfully!").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Error! Please Try Again...").show();
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            txtCustomerId.clear();
            txtCustomerName.clear();
            txtNic.clear();
            txtTelNo.clear();
            txtAddress.clear();

            Parent parent = FXMLLoader.load(getClass().getResource("../views/CustomerForm.fxml"));
            customerRecContext.getChildren().add(parent);
    }

    public void updateOnAction(ActionEvent actionEvent) throws IOException {

        Customer c = new Customer(
                txtCustomerId.getText(),txtCustomerName.getText(),txtNic.getText(),txtTelNo.getText(),txtAddress.getText()
        );

        try {
            if(CrudUtil.execute("UPDATE balarina_crafts.customer SET name=?,nic=?,tel_no=?,address=? WHERE c_id=? ",c.getCustomerName(),c.getNic(),c.getTelNo(),c.getAddress(),c.getCustomerId())){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Update Fail!").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        txtCustomerId.clear();
        txtCustomerName.clear();
        txtNic.clear();
        txtTelNo.clear();
        txtAddress.clear();

        Parent parent= FXMLLoader.load(getClass().getResource("../views/CustomerForm.fxml"));
        customerRecContext.getChildren().add(parent);

    }


    public void txtSearchOnAction(ActionEvent actionEvent) {

        try {
            ResultSet result=CrudUtil.execute("SELECT * FROM customer WHERE c_id=?",txtCustomerId.getText());
            if(result.next()){
                txtCustomerName.setText(result.getString(2));
                txtNic.setText(result.getString(3));
                txtTelNo.setText(result.getString(4));
                txtAddress.setText(result.getString(5));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }



    }

    public void deleteOnAction(ActionEvent actionEvent) throws IOException {
        try {
            if(CrudUtil.execute("DELETE FROM customer WHERE c_id=?",txtCustomerId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Delete Fail!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }

        txtCustomerId.clear();
        txtCustomerName.clear();
        txtNic.clear();
        txtTelNo.clear();
        txtAddress.clear();

        Parent parent= FXMLLoader.load(getClass().getResource("../views/CustomerForm.fxml"));
        customerRecContext.getChildren().add(parent);

    }





}
