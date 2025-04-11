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
    public void placeOrder(user_name, usr_id, Order order) throws SQLException{
        String sql1 = "SELECT userId FROM Users WHERE userName = ?";
        String sql2 = "INSERT INTO Orders (OrderDate, CustomerName, OrderStatus) VALUES (?,?,?)";

        try(PreparedStatement stmt = conn.prepareStatement(sql1)){
            stmt.setString(1, user_name);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    int uID = rs.getInt("userId");
                    if(usr_id == uID){
                        try(PreparedStatement stm = conn.prepareStatement(sql2)){
                            stm.setDate(1, order.get_Orderdate());
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
}