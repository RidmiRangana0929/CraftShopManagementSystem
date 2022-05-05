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
import model.Item;
import model.Material;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class MaterialFormController {
    public AnchorPane addMaterialContext;
    public JFXTextField txtMaterialId;
    public JFXTextField txtMaterialType;
    public TableColumn colMaterialId;
    public TableColumn colMaterialType;
    public TableView tblAllMaterial;
    public JFXButton btnAdd;

    LinkedHashMap<TextField,Pattern> map=new LinkedHashMap<>();

    public void initialize(){

        colMaterialId.setCellValueFactory(new PropertyValueFactory("materialId"));
        colMaterialType.setCellValueFactory(new PropertyValueFactory("materialType"));

        Pattern idPattern = Pattern.compile("^(MT00)[0-9]+$");
        Pattern typePattern = Pattern.compile("^[A-z ]{3,45}$");
        map.put(txtMaterialId,idPattern);
        map.put(txtMaterialType,typePattern);

        try {
            loadAllMaterial();
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


    private void loadAllMaterial() throws ClassNotFoundException, SQLException {
        ResultSet result=CrudUtil.execute("SELECT * FROM material");

        ObservableList<Material> obList= FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Material(
                            result.getString(1),
                            result.getString(2)
                    )
            );
        }
        tblAllMaterial.setItems(obList);

    }


    public void addOnAction(ActionEvent actionEvent) throws IOException {

        Material m = new Material(
                txtMaterialId.getText(),txtMaterialType.getText()
        );
        try {
            if(CrudUtil.execute("INSERT INTO balarina_crafts.material VALUES (?,?)",m.getMaterialId(),m.getMaterialType())){
                new Alert(Alert.AlertType.CONFIRMATION,"Material Added Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Error! Please Try Again...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        txtMaterialId.clear();
        txtMaterialType.clear();

        Parent parent= FXMLLoader.load(getClass().getResource("../views/MaterialForm.fxml"));
        addMaterialContext.getChildren().add(parent);

    }

    public void updateOnAction(ActionEvent actionEvent) throws IOException {

        Material m = new Material(
                txtMaterialId.getText(),txtMaterialType.getText()
        );

        try {
            if(CrudUtil.execute("UPDATE balarina_crafts.material SET mt_type=? WHERE mt_id=?",m.getMaterialType(),m.getMaterialId())){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Update Fail!").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        txtMaterialId.clear();
        txtMaterialType.clear();

        Parent parent= FXMLLoader.load(getClass().getResource("../views/MaterialForm.fxml"));
        addMaterialContext.getChildren().add(parent);

    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("../views/MaterialSelectionForm.fxml"));
        addMaterialContext.getChildren().add(parent);
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {

        try {
            ResultSet result=CrudUtil.execute("SELECT * FROM material WHERE mt_id=?",txtMaterialId.getText());
            if(result.next()){
                txtMaterialType.setText(result.getString(2));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    public void deleteOnAction(ActionEvent actionEvent) throws IOException {

        try {
            if(CrudUtil.execute("DELETE FROM material WHERE mt_id=?",txtMaterialId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Delete Fail!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }

        txtMaterialId.clear();
        txtMaterialType.clear();

        Parent parent= FXMLLoader.load(getClass().getResource("../views/MaterialForm.fxml"));
        addMaterialContext.getChildren().add(parent);

    }

}
