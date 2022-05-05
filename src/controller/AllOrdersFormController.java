package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import model.Order;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AllOrdersFormController {
    public AnchorPane allOrdersContext;
    public TableView tblAllOrdersContext;
    public TableColumn colOrderId;
    public TableColumn colDate;
    public TableColumn colCustomerId;
    public TableColumn colItemId;
    public TableColumn colUnitPrice;
    public TableColumn colUnit;
    public TableColumn colTotalPrice;
    public TableColumn colOption;
    public TableColumn colTotal;


    public void initialize() throws SQLException, ClassNotFoundException {

        colOrderId.setCellValueFactory(new PropertyValueFactory("orderId"));
        colTotal.setCellValueFactory(new PropertyValueFactory("total"));
        colDate.setCellValueFactory(new PropertyValueFactory("date"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory("customerId"));

        loadAllOrder();

    }
/*
    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("../views/OrderForm.fxml"));
        allOrdersContext.getChildren().add(parent);
    }
*/

    private void loadAllOrder() throws ClassNotFoundException, SQLException {
        ResultSet result= CrudUtil.execute("SELECT * FROM orders");

        ObservableList<Order> obList= FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Order(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4)
                    )
            );
        }
        tblAllOrdersContext.setItems(obList);

    }

}
