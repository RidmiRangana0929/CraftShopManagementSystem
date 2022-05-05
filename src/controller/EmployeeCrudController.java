package controller;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeCrudController {
    public static ArrayList<String> getEmployeeIds() throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT e_id FROM employee");
        ArrayList<String> eIds=new ArrayList<>();
        while (result.next()){
            eIds.add(result.getString(1));
        }
        return eIds;
    }
}
