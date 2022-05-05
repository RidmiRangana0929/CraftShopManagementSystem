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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import model.ItemDetails;
import model.Order;
import model.OrderDetails;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;
import util.ValidationUtil;
import views.tm.CartTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class OrderFormController {
    public AnchorPane oderRecContext;
    public JFXTextField txtOrderId;
    public JFXTextField txtDate;
    public JFXTextField txtCustomerId;
    public JFXTextField txtItemId;
    public JFXTextField txtUnit;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtTotalPrice;
    public JFXComboBox <String>cmbCustomerId;
    public JFXComboBox <String>cmbItemId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtItemName;
    public TableView<CartTM> tblOrderDetail;
    public TableColumn colItemId;
    public TableColumn colItemName;
    public TableColumn colUnitPrice;
    public TableColumn colUnit;
    public TableColumn colTotal;
    public TableColumn colOption;
    public TextField txtTotal;
    public JFXButton btnAddToCart;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){

        colItemId.setCellValueFactory(new PropertyValueFactory("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory("itemName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        colUnit.setCellValueFactory(new PropertyValueFactory("unit"));
        colTotal.setCellValueFactory(new PropertyValueFactory("totalPrice"));
        colOption.setCellValueFactory(new PropertyValueFactory("btn"));

        Pattern unitPattern = Pattern.compile("^[1-9][0-9]*$");
        map.put(txtUnit,unitPattern);

        setCustomerIds();
        setItemIds();
        loadDate();
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnAddToCart);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map,btnAddToCart);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }


    private void loadDate() {
        txtDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    ObservableList<CartTM> tmList=FXCollections.observableArrayList();

    public void addToCartOnAction(ActionEvent actionEvent) {

        double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        int unit=Integer.parseInt(txtUnit.getText());
        double total=unitPrice*unit;

        Button btn=new Button("Delete");

        CartTM tm=new CartTM(cmbItemId.getValue(),
                txtItemName.getText(),
                unitPrice,
                unit,
                total,
                btn
        );

        btn.setOnAction(e -> {
                    tmList.remove(tm);
                    tmList.remove(tm);
                    calculateTotal();
        });

        tmList.add(tm);
        tblOrderDetail.setItems(tmList);

        calculateTotal();

    }

    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            txtOrderId.setText(OrderCrudController.getOrderId(1));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Order order = new Order(
                txtOrderId.getText(),
                txtTotal.getText(),
                txtDate.getText(),
                cmbCustomerId.getValue()
        );


        ArrayList<OrderDetails> details = new ArrayList<>();
        for (CartTM tm : tmList
        ) {
            details.add(
                    new OrderDetails(
                            txtOrderId.getText(),
                            tm.getItemId(),
                            tm.getUnit(),
                            tm.getTotalPrice()
                    )
            );
        }


        Connection connection= null;

        try {
            connection= DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSaved = new OrderCrudController().saveOrder(order);
            if (isOrderSaved) {
                boolean isDetailsSaved=new OrderCrudController().saveOrderDetails(details);
                if (isDetailsSaved){
                    connection.commit();
                    new Alert(Alert.AlertType.CONFIRMATION,"Order Information saved Successfully!").show();
                }else{
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR,"Error! Please Try Again...").show();
                }
            }else{
                connection.rollback();
                new Alert(Alert.AlertType.ERROR,"Error! Please Try Again...").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }finally {
            connection.setAutoCommit(true);
        }
    }

    public void invoiceOnAction(ActionEvent actionEvent) {

        String orderID = txtOrderId.getText();
        String date = txtDate.getText();
        String customerName = txtCustomerName.getText();
        String total = txtTotal.getText();

        HashMap paramMap = new HashMap();
        paramMap.put("OrderId", orderID);
        paramMap.put("Date", date);
        paramMap.put("CustomerName", customerName);
        paramMap.put("Total", total);

        ObservableList<CartTM> tableRecords = tblOrderDetail.getItems();

        try {
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/views/reports/OrderInvoice.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport,paramMap, new JRBeanCollectionDataSource(tableRecords));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    private void setCustomerIds(){
        try {
            ObservableList<String > cIdObList= FXCollections.observableArrayList(
                    CustomerCrudController.getCustomerIds()
            );
            cmbCustomerId.setItems(cIdObList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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



    public void cmbCustomerSearchOnAction(ActionEvent actionEvent) {

        try {
            ResultSet result= CrudUtil.execute("SELECT * FROM customer WHERE c_id=?",cmbCustomerId.getValue());
            if(result.next()){
                txtCustomerName.setText(result.getString(2));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    public void cmbItemSearchOnAction(ActionEvent actionEvent) {

        try {
            ResultSet result= CrudUtil.execute("SELECT * FROM item WHERE it_id=?",cmbItemId.getValue());
            if(result.next()){
                DecimalFormat dc= new DecimalFormat("0.00");
                dc.setMaximumFractionDigits(2);
                txtItemName.setText(result.getString(2));
                txtUnitPrice.setText(String.valueOf(dc.format(result.getDouble(3))));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    public void txtCalculateTpOnAction(ActionEvent actionEvent) {
        DecimalFormat dc= new DecimalFormat("0.00");
        dc.setMaximumFractionDigits(2);
        txtTotalPrice.setText(dc.format((Integer.parseInt(txtUnit.getText()))*(Double.parseDouble(txtUnitPrice.getText()))));
    }

    private void calculateTotal(){
        double total=0;
        for (CartTM tm:tmList
             ) {
            total+=tm.getTotalPrice();

        }
        DecimalFormat dc= new DecimalFormat("0.00");
        dc.setMaximumFractionDigits(2);
        txtTotal.setText(dc.format(total));
    }

    public void refreshOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("../views/OrderForm.fxml"));
        oderRecContext.getChildren().add(parent);
    }


}
