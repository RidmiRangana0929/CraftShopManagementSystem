package controller;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierCrudController {

    public static ArrayList<String> getSupplierIds() throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT s_id FROM supplier");
        ArrayList<String> sIds=new ArrayList<>();
        while (result.next()){
            sIds.add(result.getString(1));
        }
        return sIds;
    }

}
