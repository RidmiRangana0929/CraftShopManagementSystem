package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MaterialSelectionFormController {
    public AnchorPane materialSelectionContext;

    public void addMaterialOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("../views/MaterialForm.fxml"));
        materialSelectionContext.getChildren().add(parent);
    }

    public void addStockOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("../views/StockForm.fxml"));
        materialSelectionContext.getChildren().add(parent);
    }
}
