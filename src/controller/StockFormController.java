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
import model.MaterialDetails;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class StockFormController {
    public AnchorPane addStockContext;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtUnit;
    public JFXTextField txtTotalCost;
    public TableView tblStock;
    public TableColumn colUnitPrice;
    public TableColumn colUnit;
    public TableColumn colTotalCost;
    public JFXComboBox<String> cmbMaterialId;
    public JFXComboBox<String> cmbSupplierId;
    public TableColumn colMaterialId;
    public TableColumn colSupplierId;
    public JFXButton btnSave;
    LinkedHashMap<TextField,Pattern> map=new LinkedHashMap<>();


    public void initialize(){

        colMaterialId.setCellValueFactory(new PropertyValueFactory("mtId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory("mtId"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        colUnit.setCellValueFactory(new PropertyValueFactory("unit"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory("totalCost"));

        Pattern unitPricePattern = Pattern.compile("^[1-9][0-9]*(.[0-9]{2})?$");
        Pattern unitPattern = Pattern.compile("^[1-9][0-9]*$");
        map.put(txtUnitPrice,unitPricePattern);
        map.put(txtUnit,unitPattern);

        setMaterialIds();
        setSupplierIds();
        try {
            loadAllStock();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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



    private void loadAllStock() throws ClassNotFoundException, SQLException {
        ResultSet result=CrudUtil.execute("SELECT * FROM material_detail");

        ObservableList<MaterialDetails> obList= FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new MaterialDetails(
                            result.getString(1),
                            result.getString(2),
                            result.getDouble(3),
                            result.getInt(4),
                            result.getDouble(5)
                    )
            );
        }
        tblStock.setItems(obList);

    }



    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("../views/MaterialSelectionForm.fxml"));
        addStockContext.getChildren().add(parent);
    }

    private void setMaterialIds(){

        try {
            ObservableList<String > mtIdObList= FXCollections.observableArrayList(
                    MaterialCrudController.getMaterialIds()
            );
            cmbMaterialId.setItems(mtIdObList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    private void setSupplierIds() {

        try {
            ObservableList<String > sIdObList= FXCollections.observableArrayList(
                    SupplierCrudController.getSupplierIds()
            );
            cmbSupplierId.setItems(sIdObList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void saveOnAction(ActionEvent actionEvent) throws IOException {

        MaterialDetails materialDetails = new MaterialDetails(
                cmbMaterialId.getValue(),cmbSupplierId.getValue(),Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtUnit.getText()),Double.parseDouble(txtTotalCost.getText())
        );

        try {
            if(CrudUtil.execute("INSERT INTO balarina_crafts.material_detail VALUES (?,?,?,?,?)",materialDetails.getMtId(),materialDetails.getsId(),materialDetails.getUnitPrice(),materialDetails.getUnit(),materialDetails.getTotalCost())){
                new Alert(Alert.AlertType.CONFIRMATION,"Stock Information saved Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Error! Please Try Again...").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        txtUnitPrice.clear();
        txtUnit.clear();
        txtTotalCost.clear();

        Parent parent= FXMLLoader.load(getClass().getResource("../views/StockForm.fxml"));
        addStockContext.getChildren().add(parent);

    }

    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void txtCalculateTcOnAction(ActionEvent actionEvent) {
        txtTotalCost.setText(String.valueOf((Double.parseDouble(txtUnitPrice.getText()))*(Integer.parseInt(txtUnit.getText()))));
    }

}
