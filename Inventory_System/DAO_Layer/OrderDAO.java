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
    public void placeOrder() throws SQLException{
        String sql1 = "SELECT userId FROM Users";
        String sql2 = "INSERT INTO Orders (OrderDate, CustomerName, OrderStatus) VALUES (?,?,?)";

        List<User> userID = new ArrayList<>();
        try(PreparedStatement stmt = conn.prepareStatement(sql1)){
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    User user = new User(
                        rs.getInt("userId"),
                    rs.getDate("orderDate")
                    rs.getString());

                    userID.add(user);
                }
            }
            return userID;

        }catch(SQLException e){
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
        try{
            List<User> userIDs = obj.placeOrder();
            for(int i = 0; i < userIDs.size(); i++){
                User user = userIDs.get(i);
                System.out.println(user.get_userId());
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}