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
    
    public List<Order> fetchAllOrders() throws SQLException{
        String sql = "SELECT * FROM Orders";
        List<Order> orders = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs  = stmt.executeQuery()){
            while(rs.next()){
                //to get to the foreign key, wehich is the user id in the users table
                int userid = rs.getInt("userId");
                User user = new User();
                user.set_userId(userid);

                //getting the columns from orders table
                Order order = new Order(
                rs.getInt("orderId"),
                user,
                rs.getDate("orderDate"),
                rs.getString("customerName"),
                rs.getString("orderStatus"));

                orders.add(order);
            }
            return orders;
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

        /* 
        user.set_userId(14);
        String dstr = "2025-05-21";
        java.sql.Date date = java.sql.Date.valueOf(dstr);
        Order order = new Order(0, user, date , "Selina Wayne", "Shipped" );

        try{
            obj.placeOrder(order);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        */
        try{
            List<Order> orders = obj.fetchAllOrders();
            for(int i = 0; i < orders.size(); i++){
                Order order = orders.get(i);
                System.out.println("Order id: " + order.get_orderId());
                System.out.println("User ID: " + order.get_UserId().get_userId());
                System.out.println("Order Date: " + order.get_Orderdate());
                System.out.println("Customer Name: " + order.get_customerName());
                System.out.println("Status of order: " + order.get_status());
                System.out.println("---------------");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }


    }
}