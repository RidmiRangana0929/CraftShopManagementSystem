package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Item;
import model.Supplier;
import util.CrudUtil;

import java.io.IOException;
import java.sql.*;

public class AllSuppliersFormController {
    public TableView tblSupplierMan;
    public TableColumn colSupplierId;
    public TableColumn colSupplierName;
    public TableColumn colTelNo;
    public TableColumn colEmail;
    public TableColumn colAddress;
    public TableColumn colMaterialType;
    public TableColumn colUnitPrice;
    public AnchorPane allSuppliersContext;


    public void initialize(){

        colSupplierId.setCellValueFactory(new PropertyValueFactory("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory("supplierName"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colTelNo.setCellValueFactory(new PropertyValueFactory("telNo"));

        try {
            loadAllSupplier();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    private void loadAllSupplier() throws ClassNotFoundException, SQLException {
        ResultSet result= CrudUtil.execute("SELECT * FROM supplier");

        ObservableList<Supplier> obList= FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Supplier(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5)
                    )
            );
        }
        tblSupplierMan.setItems(obList);

    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("../views/SupplierForm.fxml"));
        allSuppliersContext.getChildren().add(parent);
    }
}
