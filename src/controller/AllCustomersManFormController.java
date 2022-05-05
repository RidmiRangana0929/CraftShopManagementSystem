package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AllCustomersManFormController {
    public AnchorPane allCustomerContext;
    public TableView tblCustomerMan;
    public TableColumn colCustomerIdMan;
    public TableColumn colCustomerNameMan;
    public TableColumn colNicMan;
    public TableColumn colTelNoMan;
    public TableColumn colAddressMan;

    public void initialize() throws SQLException {
        colCustomerIdMan.setCellValueFactory(new PropertyValueFactory("customerId"));
        colCustomerNameMan.setCellValueFactory(new PropertyValueFactory("customerName"));
        colNicMan.setCellValueFactory(new PropertyValueFactory("nic"));
        colTelNoMan.setCellValueFactory(new PropertyValueFactory("telNo"));
        colAddressMan.setCellValueFactory(new PropertyValueFactory("address"));
        try {
            loadAllCustomer();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void loadAllCustomer() throws ClassNotFoundException, SQLException {
        ResultSet result= CrudUtil.execute("SELECT * FROM customer");

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
        tblCustomerMan.setItems(obList);
    }
}
