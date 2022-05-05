package controller;

import com.mysql.cj.xdevapi.Result;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageFormController {
    public AnchorPane loginPageContext;
    public TextField txtUsername;
    public PasswordField pwdPassword;

    public void loginOnAction(ActionEvent actionEvent) throws IOException{
        try {
            LoginPageFormManage();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void LoginPageFormManage() throws IOException, SQLException, ClassNotFoundException {

        ResultSet username1 = CrudUtil.execute("SELECT username FROM users WHERE employee_id='E001'");
        ResultSet username2 = CrudUtil.execute("SELECT username FROM users WHERE employee_id='E002'");
        ResultSet username3 = CrudUtil.execute("SELECT username FROM users WHERE employee_id='E003'");

        ResultSet password1 = CrudUtil.execute("SELECT password FROM users WHERE employee_id='E001'");
        ResultSet password2 = CrudUtil.execute("SELECT password FROM users WHERE employee_id='E002'");
        ResultSet password3 = CrudUtil.execute("SELECT password FROM users WHERE employee_id='E003'");

        if((username1.next() && password1.next()) && (username2.next() && password2.next())){

            if(((txtUsername.getText().equals(username2.getString(1))) || ((txtUsername.getText().equals(username2.getString(1))))) && ((pwdPassword.getText().equals(password2.getString(1))) || (pwdPassword.getText().equals(password3.getString(1))))){
                Stage window = (Stage) loginPageContext.getScene().getWindow();
                window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/DashBordReceptionistForm.fxml"))));
            }else if(txtUsername.getText().equals(username1.getString(1)) && pwdPassword.getText().equals(password1.getString(1))){
                Stage window = (Stage) loginPageContext.getScene().getWindow();
                window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/DashBordManagerForm.fxml"))));
            }else{
                new Alert(Alert.AlertType.WARNING,"Login fail...! Check your username and password.").show();
                txtUsername.clear();
                pwdPassword.clear();
            }

        }
    }
}
