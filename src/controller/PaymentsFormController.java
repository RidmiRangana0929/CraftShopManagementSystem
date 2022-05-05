package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class PaymentsFormController {
    public JFXTextField txtDate;
    public JFXTextField txtTotalPrice;
    public JFXButton btnPayAndPurchase;
    public JFXTextField txtOrderId;
    public JFXTextField txtCash;
    public JFXTextField txtBalance;
    public JFXTextField txtCustomerId;
    public JFXTextField txtTotal;
    public AnchorPane paymentContext;
    LinkedHashMap<TextField,Pattern> map=new LinkedHashMap<>();

    public void initialize(){
        Pattern idPattern = Pattern.compile("^(OR00)[0-9]+$");
        Pattern cashPattern = Pattern.compile("^[1-9][0-9.]*$");
        map.put(txtOrderId,idPattern);
        map.put(txtCash,cashPattern);
        loadDate();
    }
    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnPayAndPurchase);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map,btnPayAndPurchase);
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

    public void payAndPurchaseOnAction(ActionEvent actionEvent) throws IOException {
        String orderID = txtOrderId.getText();
        String date = txtDate.getText();
        String customerID = txtCustomerId.getText();
        String total = txtTotal.getText();
        String cash = txtCash.getText();
        String balance = txtBalance.getText();

        HashMap paramMap = new HashMap();
        paramMap.put("OrderId", orderID);
        paramMap.put("Date", date);
        paramMap.put("CustomerId", customerID);
        paramMap.put("Total", total);
        paramMap.put("Cash", cash);
        paramMap.put("Balance", balance);

        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/views/reports/PurchaseInvoice.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, paramMap, new JREmptyDataSource(1));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }

        Parent parent= FXMLLoader.load(getClass().getResource("../views/PaymentsForm.fxml"));
        paymentContext.getChildren().add(parent);

    }

    public void txtOrderIdSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result= CrudUtil.execute("SELECT total,c_id FROM orders WHERE o_id=?",txtOrderId.getText());
            if(result.next()){
                txtTotal.setText(result.getString(1));
                txtCustomerId.setText(result.getString(2));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    public void txtCashCalculateBalanceOnAction(ActionEvent actionEvent) {
        double balance=(Double.parseDouble(txtCash.getText()))-(Double.parseDouble(txtTotal.getText()));
        DecimalFormat dc= new DecimalFormat("0.00");
        dc.setMaximumFractionDigits(2);
        txtBalance.setText(dc.format(balance));
        txtCash.setText(dc.format(Double.parseDouble(txtCash.getText())));
    }

}
