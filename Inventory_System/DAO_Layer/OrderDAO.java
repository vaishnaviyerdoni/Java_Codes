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
    
    //to fetch all the orders from the database
    public List<Order> fetchAllOrders() throws SQLException{
        String sql = "SELECT * FROM Orders";
        List<Order> orders = new ArrayList<>();
            try(PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs  = stmt.executeQuery()){
                while(rs.next()){
                    //to get to the foreign key, which is the user id in the users table
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

        //Update the order status method
        public void updateStatus(String newStatus, int order_Id) throws SQLException{
            String sql = "UPDATE Orders SET orderStatus = ? WHERE orderId = ?";

            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, newStatus);
                stmt.setInt(2, order_Id);
                stmt.executeUpdate();

                System.out.println("Your Order is " + newStatus);
            }
        }

        //Delete the Order
        public void deleteOrder(int orderID) throws SQLException{
            String sql = "DELETE FROM Orders WHERE orderId = ?";

            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, orderID);
                stmt.executeUpdate();

                System.out.println("Your Order was deleted!");
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
        // to test the create method.
        user.set_userId(7);
        String dstr = "2025-05-21";
        java.sql.Date date = java.sql.Date.valueOf(dstr);
        Order order = new Order(0, user, date , "Frank Davis", "Pending" );

        try{
            obj.placeOrder(order);
        }catch(SQLServerException e){
            System.out.println("Cannot place order if user doesnt exist");
            System.out.println("Register as user please!");
        }
        catch(SQLException e){
            System.out.println("User not found!");
        }
        */
        
        /* 
        //To test the read all method for given userid
        try{
            List<Order> orders = obj.fetchAllOrders();
                for (int i = 0; i < orders.size(); i++){
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
        */

        /* 
        //To test the update Status method

        try{
            obj.updateStatus("Canceled", 16);
        }catch(SQLException e){
            e.printStackTrace();
        }
        */

        //To test the delete method

        try{
            obj.deleteOrder(16);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}