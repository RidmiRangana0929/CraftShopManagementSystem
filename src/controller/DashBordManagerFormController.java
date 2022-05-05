package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashBordManagerFormController {
    public AnchorPane buttonPanelContext;
    public AnchorPane dashBordPanelManContext;
    public Label lblDate;
    public Label lblTime;

    public void initialize(){
        setDateTime();
    }

    private void setDateTime() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            lblTime.setText(LocalDateTime.now().format(formatter));

            SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            lblDate.setText(formatter2.format(date));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void itemsOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("../views/ItemsForm.fxml"));
        dashBordPanelManContext.getChildren().add(parent);
    }

    public void suppliersOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("../views/SupplierForm.fxml"));
        dashBordPanelManContext.getChildren().add(parent);
    }

    public void materialsOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("../views/MaterialSelectionForm.fxml"));
        dashBordPanelManContext.getChildren().add(parent);
    }

    public void reportsOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("../views/ReportsForm.fxml"));
        dashBordPanelManContext.getChildren().add(parent);
    }

    public void customersOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("../views/AllCustomersManForm.fxml"));
        dashBordPanelManContext.getChildren().add(parent);
    }

    public void employeeOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("../views/EmployeeForm.fxml"));
        dashBordPanelManContext.getChildren().add(parent);
    }

    public void salaryOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("../views/PaySalaryForm.fxml"));
        dashBordPanelManContext.getChildren().add(parent);
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) dashBordPanelManContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/loginPageForm.fxml"))));
    }


}
