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
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashBordReceptionistFormController {
    public AnchorPane dashBordReceptionistContext;
    public AnchorPane buttonPanelContext;
    public AnchorPane dashBordPanelRecContext;
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
        Parent parent=FXMLLoader.load(getClass().getResource("../views/ItemsAvailabilityForm.fxml"));
        dashBordPanelRecContext.getChildren().add(parent);
    }

    public void customersOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("../views/CustomerForm.fxml"));
        dashBordPanelRecContext.getChildren().add(parent);
    }

    public void ordersOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("../views/OrderForm.fxml"));
        dashBordPanelRecContext.getChildren().add(parent);
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) dashBordReceptionistContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/loginPageForm.fxml"))));
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) dashBordReceptionistContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view"+location+".fxml"))));

    }

    public void allOrdersOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("../views/AllOrdersForm.fxml"));
        dashBordPanelRecContext.getChildren().add(parent);
    }

    public void paymentsOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("../views/PaymentsForm.fxml"));
        dashBordPanelRecContext.getChildren().add(parent);
    }
}
