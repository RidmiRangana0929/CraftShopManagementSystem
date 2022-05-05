package controller;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemCrudController {
    public static ArrayList<String> getItemIds() throws SQLException, ClassNotFoundException {
        ResultSet result=CrudUtil.execute("SELECT it_id FROM item");
        ArrayList<String> itIds=new ArrayList<>();
        while (result.next()){
            itIds.add(result.getString(1));
        }
        return itIds;
    }
}
