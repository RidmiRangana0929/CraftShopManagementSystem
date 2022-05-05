package controller;

import model.Order;
import model.OrderDetails;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderCrudController {

    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Orders VALUES(?,?,?,?)",
                order.getOrderId(), order.getTotal(), order.getDate(),order.getCustomerId());
    }


    public boolean saveOrderDetails(ArrayList<OrderDetails> details) throws SQLException, ClassNotFoundException {
        for (OrderDetails det:details
        ) {
            boolean isDetailsSaved=CrudUtil.execute("INSERT INTO order_detail VALUES(?,?,?,?)",
                    det.getOrderId(),det.getItemId(),det.getUnit(),det.getTotalPrice());
        }
        return true;
    }


    public static String getOrderId(int column) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT o_id FROM orders ORDER BY o_id DESC LIMIT 1");

        if (set.next()) {
            String id = set.getString(column);
            String[] splitted = id.split("(OR)");
            return String.format("OR%03d", (Integer.valueOf(splitted[1]) + 1));
        }
        return "OR001";
    }

}
