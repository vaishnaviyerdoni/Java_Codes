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
    public void placeOrder(String user_name, int usr_id, Order order) throws SQLException{
        String sql1 = "SELECT userId FROM Users where userName = ?";
        String sql2 = "INSERT INTO Orders (OrderDate, CustomerName, OrderStatus) VALUES (?,?,?)";

        try(PreparedStatement stmt = conn.prepareStatement(sql1)){
            stmt.setString(1, user_name);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    int uID = rs.getInt("userId");
                    if(usr_id == uID){
                        try(PreparedStatement stm = conn.prepareStatement(sql2)){
                            stm.setDate(1, java.sql.Date(order.get_Orderdate().getTime()));
                            stm.setString(2, order.get_customerName());
                            stm.setString(3, order.get_status());
                            int rows = stm.executeUpdate();
                            if (rows > 0){
                                System.out.println("Order placed Successfully!");
                            }
                            else{
                                System.out.println("Couldn't place Order!");
                            }
                        }
                    }
                    else{
                        System.out.println("User Id not matched!");
                    }

                }
                else{
                    System.out.println("User Id not found");
                }
            }
        }
        catch(SQLServerException e){
            System.out.println("Cannot place order if user does not exist!");
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
        User user = new User();
        int user_id = user.get_userId(); 
        OrderDAO obj = new OrderDAO(conn);
        Order order = new Order(0, user ,"2025-03-22", "Helena Wayne", "Shipped");
        try{
            obj.placeOrder("Helena3107", 2, order);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}