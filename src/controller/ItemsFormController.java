package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import model.Item;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ItemsFormController {
    public TableView tblItemMan;
    public TableColumn colItemIdMan;
    public TableColumn colItemNameMan;
    public TableColumn colUnitPriceMan;
    public TableColumn colStatusMan;
    public JFXTextField txtItemId;
    public JFXTextField txtItemName;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtStatus;
    public AnchorPane itemFormContext;
    public JFXButton btnAdd;
    LinkedHashMap<TextField, Pattern> map=new LinkedHashMap<>();


    public void initialize(){

        colItemIdMan.setCellValueFactory(new PropertyValueFactory("itemId"));
        colItemNameMan.setCellValueFactory(new PropertyValueFactory("itemName"));
        colUnitPriceMan.setCellValueFactory(new PropertyValueFactory("unitPrice"));

        Pattern idPattern = Pattern.compile("^(IT00)[0-9]+$");
        Pattern namePattern = Pattern.compile("^[^?]{3,50}$");
        Pattern unitPrice = Pattern.compile("^[1-9][0-9.]+$");
        map.put(txtItemId,idPattern);
        map.put(txtItemName,namePattern);
        map.put(txtUnitPrice,unitPrice);

        try {
            loadAllItem();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnAdd);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map,btnAdd);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }


    private void loadAllItem() throws ClassNotFoundException, SQLException {
        ResultSet result=CrudUtil.execute("SELECT * FROM item");

        ObservableList<Item> obList= FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Item(
                            result.getString(1),
                            result.getString(2),
                            result.getDouble(3)
                    )
            );
        }
        tblItemMan.setItems(obList);

    }


    public void addOnAction(ActionEvent actionEvent) throws IOException {
        Item i = new Item(
                txtItemId.getText(),txtItemName.getText(),Double.parseDouble(txtUnitPrice.getText())
        );
        try {
            if(CrudUtil.execute("INSERT INTO balarina_crafts.item VALUES (?,?,?)",i.getItemId(),i.getItemName(),i.getUnitPrice())){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Added Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Error! Please Try Again...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        txtItemId.clear();
        txtItemName.clear();
        txtUnitPrice.clear();

        Parent parent= FXMLLoader.load(getClass().getResource("../views/ItemsForm.fxml"));
        itemFormContext.getChildren().add(parent);

    }

    public void updateOnAction(ActionEvent actionEvent) throws IOException {
        Item i = new Item(
                txtItemId.getText(),txtItemName.getText(),Double.parseDouble(txtUnitPrice.getText())
        );
        try {
            if(CrudUtil.execute("UPDATE balarina_crafts.item SET name=?,unit_price=? WHERE it_id=? ",i.getItemName(),i.getUnitPrice(),i.getItemId())){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Update Fail!").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        Parent parent= FXMLLoader.load(getClass().getResource("../views/ItemsForm.fxml"));
        itemFormContext.getChildren().add(parent);
    }

    public void setMaterialDetailsOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("../views/MaterialDetailsForm.fxml"));
        itemFormContext.getChildren().add(parent);
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result=CrudUtil.execute("SELECT * FROM item WHERE it_id=?",txtItemId.getText());
            if(result.next()){
                txtItemName.setText(result.getString(2));
                txtUnitPrice.setText(String.valueOf(result.getDouble(3)));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws IOException {

        try {
            if(CrudUtil.execute("DELETE FROM item WHERE it_id=?",txtItemId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Delete Fail!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }
        txtItemId.clear();
        txtItemName.clear();
        txtUnitPrice.clear();

        Parent parent= FXMLLoader.load(getClass().getResource("../views/ItemsForm.fxml"));
        itemFormContext.getChildren().add(parent);

    }


}
