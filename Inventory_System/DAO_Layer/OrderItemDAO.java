package Inventory_System.DAO_Layer;

import Inventory_System.DAO_Layer.*;
import Inventory_System.Model_Layer.*;
import java.util.*;
import java.sql.*;

public class OrderItemDAO {
    //constructor for database connection
    private Connection conn;

    public OrderItemDAO(Connection conn){
        this.conn = conn;
    }

    //Create method for adding the order items 
    public void addOrderItem(OrderItem items) throws SQLException{
        String sql = "INSERT INTO OrderItems (OrderId, inventoryId, quantity, Subtotal, userid) VALUES (?,?,?,?,?)";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, items.get_orderid().get_orderId());
            stmt.setInt(2, items.get_inventoryId().get_itemId());
            stmt.setInt(3, items.getOrder_quantity());
            stmt.setDouble(4, items.get_subtotal());
            stmt.setInt(5, items.get_userid().get_userId());

            int rows = stmt.executeUpdate();
            if (rows > 0){
                System.out.println("Items are placed!");
            }
            else{
                System.out.println("Items are not placed!");
            }
        }
    }

    public static void main(String[] args){

        Connection conn = null;
        try{
            conn = DatabaseConnection.getConn();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        OrderItemDAO items = new OrderItemDAO(conn);
        Inventory inventory = new Inventory();
        inventory.set_itemId(5);
        User user = new User();
        user.set_userId(14);
        Order order = new Order();
        order.set_OrderId(5);
        OrderItem item = new OrderItem(0, order, inventory, 3, 42000, user);
        try{
            items.addOrderItem(item);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
