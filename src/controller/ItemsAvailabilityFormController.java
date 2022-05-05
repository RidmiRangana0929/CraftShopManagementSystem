package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Item;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemsAvailabilityFormController {
    public AnchorPane itemsAvailabilityContext;
    public TableView tblAvailableItems;
    public TableColumn colItemId;
    public TableColumn colItemName;
    public TableColumn tblUnitPrice;
    public TableColumn colStatus;
    public TableView tblItemRec;
    public TableColumn colItemIdRec;
    public TableColumn colItemNameRec;
    public TableColumn colUnitPriceRec;

    public void initialize(){

        colItemIdRec.setCellValueFactory(new PropertyValueFactory("itemId"));
        colItemNameRec.setCellValueFactory(new PropertyValueFactory("itemName"));
        colUnitPriceRec.setCellValueFactory(new PropertyValueFactory("unitPrice"));

        try {
            loadAllItem();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllItem() throws ClassNotFoundException, SQLException {
        ResultSet result= CrudUtil.execute("SELECT * FROM item");

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
        tblItemRec.setItems(obList);

    }

}
