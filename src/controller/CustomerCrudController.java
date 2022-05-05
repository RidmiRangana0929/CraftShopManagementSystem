package controller;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerCrudController {

    public static ArrayList<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT c_id FROM customer");
        ArrayList<String> cIds=new ArrayList<>();
        while (result.next()){
            cIds.add(result.getString(1));
        }
        return cIds;
    }

}
