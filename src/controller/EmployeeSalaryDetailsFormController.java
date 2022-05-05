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
import model.Employee;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeSalaryDetailsFormController {
    public TableView tblSalary;
    public TableColumn colEmployeeId;
    public TableColumn colName;
    public TableColumn colRoleType;
    public TableColumn colSalary;
    public AnchorPane salaryDetailsContext;

    public void initialize(){

        colEmployeeId.setCellValueFactory(new PropertyValueFactory("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory("employeeName"));
        colRoleType.setCellValueFactory(new PropertyValueFactory("roleType"));
        colSalary.setCellValueFactory(new PropertyValueFactory("salary"));

        try {
            loadAllEmployee();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void loadAllEmployee() throws ClassNotFoundException, SQLException {
        ResultSet result= CrudUtil.execute("SELECT * FROM employee");

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
        tblSalary.setItems(obList);

    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../views/EmployeeForm.fxml"));
        salaryDetailsContext.getChildren().add(parent);

    }
}
