package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import model.Employee;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class EmployeeFormController {
    public JFXTextField txtEmployeeId;
    public JFXTextField txtName;
    public JFXTextField txtNic;
    public JFXTextField txtAddress;
    public JFXTextField txtEmail;
    public JFXTextField txtTelNo;
    public JFXTextField txtRoleType;
    public TableColumn colEmployeeId;
    public TableColumn colName;
    public TableColumn colNic;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colRoleType;
    public AnchorPane employeeContext;
    public TableView<Employee> tblEmployee;
    public TableColumn colTelNo;
    public JFXTextField txtSalary;
    public JFXButton btnSave;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){

        colEmployeeId.setCellValueFactory(new PropertyValueFactory("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory("employeeName"));
        colNic.setCellValueFactory(new PropertyValueFactory("nic"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colTelNo.setCellValueFactory(new PropertyValueFactory("telNo"));
        colRoleType.setCellValueFactory(new PropertyValueFactory("roleType"));


        try {
            loadAllEmployee();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Pattern idPattern = Pattern.compile("^(E00)[0-9]+$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,45}$");
        Pattern nicPattern = Pattern.compile("^[0-9V]{8,}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,30}$");
        Pattern emailPattern = Pattern.compile("^[A-z0-9@.]{3,}(.com)$");
        Pattern telNoPattern = Pattern.compile("^[0-9]{10,}$");
        Pattern roleTypePattern = Pattern.compile("^[A-z(),]{3,}$");
        Pattern salaryPattern = Pattern.compile("^[1-9][0-9]+$");

        map.put(txtEmployeeId,idPattern);
        map.put(txtName,namePattern);
        map.put(txtNic,nicPattern);
        map.put(txtAddress,addressPattern);
        map.put(txtEmail,emailPattern);
        map.put(txtTelNo,telNoPattern);
        map.put(txtRoleType,roleTypePattern);
        map.put(txtSalary,salaryPattern);

    }


    private void loadAllEmployee() throws ClassNotFoundException, SQLException {
        ResultSet result=CrudUtil.execute("SELECT * FROM employee");

        ObservableList<Employee> obList= FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Employee(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5),
                            result.getString(6),
                            result.getString(7),
                            result.getDouble(8)
                    )
            );
        }
        tblEmployee.setItems(obList);

    }

    public void saveOnAction(ActionEvent actionEvent) throws IOException {
        Employee e = new Employee(
                txtEmployeeId.getText(), txtName.getText(), txtNic.getText(), txtAddress.getText(), txtEmail.getText(), txtTelNo.getText(),txtRoleType.getText(),Double.parseDouble(txtSalary.getText())
        );

        try {
            if (CrudUtil.execute("INSERT INTO balarina_crafts.employee VALUES (?,?,?,?,?,?,?,?)", e.getEmployeeId(),e.getEmployeeName(),e.getNic(),e.getAddress(),e.getEmail(),e.getTelNo(),e.getRoleType(),e.getSalary())) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Information saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Error! Please Try Again...").show();
            }

        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }
        txtEmployeeId.clear();
        txtName.clear();
        txtNic.clear();
        txtAddress.clear();
        txtEmail.clear();
        txtTelNo.clear();
        txtRoleType.clear();
        txtSalary.clear();

        Parent parent = FXMLLoader.load(getClass().getResource("../views/EmployeeForm.fxml"));
        employeeContext.getChildren().add(parent);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSave);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map,btnSave);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws IOException {
        Employee e = new Employee(
                txtEmployeeId.getText(),txtName.getText(),txtNic.getText(),txtAddress.getText(),txtEmail.getText(),txtTelNo.getText(),txtRoleType.getText(),Double.parseDouble(txtSalary.getText())
        );

        try {
            if(CrudUtil.execute("UPDATE balarina_crafts.employee SET name=?,nic=?,address=?,email=?,tel_no=?,role_type=?,salary=? WHERE e_id=? ",e.getEmployeeName(),e.getNic(),e.getAddress(),e.getEmail(),e.getTelNo(),e.getRoleType(),e.getSalary(),e.getEmployeeId())){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Update Fail!").show();
            }

        }catch (SQLException | ClassNotFoundException exception){
            exception.printStackTrace();
        }
        txtEmployeeId.clear();
        txtName.clear();
        txtNic.clear();
        txtAddress.clear();
        txtEmail.clear();
        txtTelNo.clear();
        txtRoleType.clear();
        txtSalary.clear();

        Parent parent = FXMLLoader.load(getClass().getResource("../views/EmployeeForm.fxml"));
        employeeContext.getChildren().add(parent);
    }

    public void deleteOnAction(ActionEvent actionEvent) throws IOException {
        try {
            if(CrudUtil.execute("DELETE * FROM employee WHERE e_id=?",txtEmployeeId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Delete Fail!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }
        txtEmployeeId.clear();
        txtName.clear();
        txtNic.clear();
        txtAddress.clear();
        txtEmail.clear();
        txtTelNo.clear();
        txtRoleType.clear();
        txtSalary.clear();

        Parent parent = FXMLLoader.load(getClass().getResource("../views/EmployeeForm.fxml"));
        employeeContext.getChildren().add(parent);
    }
    public void txtSearchOnAction(ActionEvent actionEvent) {

        try {
            ResultSet result=CrudUtil.execute("SELECT * FROM employee WHERE e_id=?",txtEmployeeId.getText());
            if(result.next()){
                txtName.setText(result.getString(2));
                txtNic.setText(result.getString(3));
                txtAddress.setText(result.getString(4));
                txtEmail.setText(result.getString(5));
                txtTelNo.setText(result.getString(6));
                txtRoleType.setText(result.getString(7));
                txtSalary.setText(String.valueOf(result.getDouble(8)));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    public void viewSalaryDetailsOnAction(ActionEvent actionEvent) throws IOException {

        Parent parent=FXMLLoader.load(getClass().getResource("../views/EmployeeSalaryDetailsForm.fxml"));
        employeeContext.getChildren().add(parent);

    }
}
