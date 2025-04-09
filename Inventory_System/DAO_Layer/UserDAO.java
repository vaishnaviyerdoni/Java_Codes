package Inventory_System.DAO_Layer;

import java.sql.*;
import java.util.*;
import Inventory_System.Model_Layer.User;
import Inventory_System.DAO_Layer.DatabaseConnection;

public class UserDAO {
    private  Connection conn;
    // Constructor to accept the jdbc connection
    public UserDAO(Connection conn){
        this.conn = conn;
    }

    //CREATE method to add/register new Users.
    public void addUser(User user) throws SQLException{
        String sql = "INSERT INTO Users (UserName, email, passcode, roleUser) VALUES (?,?,?,?)";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, user.get_userName());
            stmt.setString(2, user.get_email());
            stmt.setString(3, user.get_passcode());
            stmt.setString(4, user.get_role());
            int rows = stmt.executeUpdate();
            if (rows > 0){
                System.out.println("New User Registered!");
            }
            else{
                System.out.println("User Registration failed!");
            }
        }
    }

    public static void main(String[] args) {

        Connection conn = null;
        try{    
            conn = DatabaseConnection.getConn();
        }catch(SQLException e){
            e.printStackTrace();
        }
        UserDAO newUser = new UserDAO(conn);

        //To test the create method to add or register new user.
        User user = new User(0,"Helena3107", "Helena2@gmail.com", "hello@sql", "admin");

        try{
            newUser.addUser(user);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
