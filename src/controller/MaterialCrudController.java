package controller;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaterialCrudController {
    public static ArrayList<String> getMaterialIds() throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT mt_id FROM material");
        ArrayList<String> mtIds=new ArrayList<>();
        while (result.next()){
            mtIds.add(result.getString(1));
        }
        return mtIds;
    }

    public static ArrayList<String> getMaterialTypes() throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT mt_Type FROM material");
        ArrayList<String> mtTypes=new ArrayList<>();
        while (result.next()){
            mtTypes.add(result.getString(1));
        }
        return mtTypes;
    }
}
