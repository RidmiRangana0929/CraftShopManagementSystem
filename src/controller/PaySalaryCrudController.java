package controller;


import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaySalaryCrudController {

    public static String getSalaryStatusId(int column) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT ss_id FROM salary_status ORDER BY ss_id DESC LIMIT 1");

        if (set.next()) {
            String id = set.getString(column);
            String[] splitted = id.split("(SS)");
            return String.format("SS%03d", (Integer.valueOf(splitted[1]) + 1));
        }
        return "SS001";
    }

    public static ArrayList<String> getEmployeeIds() throws SQLException, ClassNotFoundException {
        ResultSet result=CrudUtil.execute("SELECT e_id FROM employee");
        ArrayList<String> eIds=new ArrayList<>();
        while (result.next()){
            eIds.add(result.getString(1));
        }
        return eIds;
    }

}
