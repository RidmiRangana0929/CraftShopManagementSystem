package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import model.ItemDetails;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class MaterialDetailsFormController {
    public AnchorPane materialDetailsContext;
    public JFXComboBox<String> cmbItemId;
    public JFXComboBox<String> cmbMaterialId;
    public JFXTextField txtQuantity;
    public TableView tblMaterialDetails;
    public TableColumn colMaterialId;
    public TableColumn colQuantity;
    public TableColumn colItemId;
    public JFXButton btnAdd;
    LinkedHashMap<TextField,Pattern> map=new LinkedHashMap<>();

    public void initialize() throws SQLException, ClassNotFoundException {
        colItemId.setCellValueFactory(new PropertyValueFactory("itemId"));
        colMaterialId.setCellValueFactory(new PropertyValueFactory("materialId"));
        colQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        Pattern quantityPattern = Pattern.compile("^[1-9][0-9]*$");
        map.put(txtQuantity,quantityPattern);

        setItemIds();
        setMaterialIds();
        loadAllItemDetail();
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

    private void setItemIds(){
        try {
            ObservableList<String > itIdObList=FXCollections.observableArrayList(
                    ItemCrudController.getItemIds()
            );
            cmbItemId.setItems(itIdObList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void setMaterialIds(){
        try {
            ObservableList<String > mtIdObList=FXCollections.observableArrayList(
                    MaterialCrudController.getMaterialIds()
            );
            cmbMaterialId.setItems(mtIdObList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllItemDetail() throws ClassNotFoundException, SQLException {
        ResultSet result=CrudUtil.execute("SELECT * FROM item_detail");

        ObservableList<ItemDetails> obList= FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new ItemDetails(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3)
                    )
            );
        }
        tblMaterialDetails.setItems(obList);

    }

    public void addOnAction(ActionEvent actionEvent) throws IOException {

        ItemDetails itemDetails =new ItemDetails(
                cmbItemId.getValue(),cmbMaterialId.getValue(),txtQuantity.getText()
        );

        try {
            if(CrudUtil.execute("INSERT INTO balarina_crafts.item_detail VALUES (?,?,?)",itemDetails.getItemId(),itemDetails.getMaterialId(),itemDetails.getQuantity())){
                new Alert(Alert.AlertType.CONFIRMATION,"Material Details Added Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Error! Please Try Again...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        txtQuantity.clear();

        Parent parent= FXMLLoader.load(getClass().getResource("../views/MaterialDetailsForm.fxml"));
        materialDetailsContext.getChildren().add(parent);

    }

    public void updateOnAction(ActionEvent actionEvent) throws IOException {
        ItemDetails itemDetails =new ItemDetails(
                cmbItemId.getValue(),cmbMaterialId.getValue(),txtQuantity.getText()
        );

        try {
            if(CrudUtil.execute("UPDATE balarina_crafts.item_detail SET m_quantity=? WHERE mt_id=? ",itemDetails.getQuantity(),itemDetails.getMaterialId())){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Update Fail!").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        txtQuantity.clear();

        Parent parent= FXMLLoader.load(getClass().getResource("../views/MaterialDetailsForm.fxml"));
        materialDetailsContext.getChildren().add(parent);
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        try {
            if(CrudUtil.execute("DELETE * FROM item_detail WHERE it_id=? && mt_id=?",cmbItemId.getValue(),cmbMaterialId.getValue())){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Delete Fail!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }
    }

    public void cmbSearchOnAction(ActionEvent actionEvent) {

        try {
            ResultSet result=CrudUtil.execute("SELECT * FROM item_detail WHERE it_id=?",cmbItemId.getValue());
            if(result.next()){
                cmbMaterialId.setValue(result.getString(2));
                txtQuantity.setText(result.getString(3));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

}
