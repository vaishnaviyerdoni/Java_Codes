package Inventory_System.DAO_Layer;

import java.sql.*;
import java.util.*;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.text.SimpleDateFormat;
import Inventory_System.Model_Layer.Order;
import Inventory_System.Model_Layer.User;

public class OrderDAO {
    private Connection conn;
    
    //Constructor to accept the database connection.
    public OrderDAO(Connection conn){
        this.conn = conn;
    }

    //Create method to place new orders, before placing order, the user must register themselves
    public void placeOrder(Order order) throws SQLException{
        String sql = "INSERT INTO Orders (userId, OrderDate, CustomerName, OrderStatus) VALUES  (?,?,?,?)";
        try{
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, order.get_UserId().get_userId());
                stmt.setDate(2, new java.sql.Date((order.get_Orderdate().getTime())));
                stmt.setString(3, order.get_customerName());
                stmt.setString(4, order.get_status());
                int rows = stmt.executeUpdate();
                if (rows > 0){
                    System.out.println("Order placed");
                }
                else{
                    System.out.println("Order denied");
                }
            }
        }
        catch(SQLServerException e){
            e.printStackTrace();
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

        OrderDAO obj = new OrderDAO(conn);
        User user = new User();

        
        user.set_userId(2);
        String dstr = "2025-05-19";
        java.sql.Date date = java.sql.Date.valueOf(dstr);
        Order order = new Order(0, user, date , "Helena Wayne", "Shipped" );

        try{
            obj.placeOrder(order);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}