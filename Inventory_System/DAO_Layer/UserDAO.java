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

    //READ method to fetch all the user records.
    public List<User> getAllUsers() throws SQLException{
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try(PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet res = stmt.executeQuery()){
            while(res.next()){
                User user = new User(
                    res.getInt("userId"),
                    res.getString("userName"),
                    res.getString("email"),
                    res.getString("passcode"),
                    res.getString("roleUser"));

                    users.add(user);
            }

            return users;
        }
    }
    //UPDATE method to update the user details.


    //DELETE method to delete the specified user.
    public void deleteUser(int userId) throws SQLException{
        String sql = "DELETE FROM Users WHERE UserId = ?";
        try(PreparedStatement stmt  = conn.prepareStatement(sql)){
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            System.out.println("Deleted User!");
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

        /* 
        //To test the create method to add or register new user.
        User user = new User(0,"Helena3107", "Helena2@gmail.com", "hello@sql", "admin");

        try{
            newUser.addUser(user);
        }catch(SQLException e){
            e.printStackTrace();
        }
        */
        /* 
        try{
            List<User> users = newUser.getAllUsers();
            for(int i = 0; i < users.size(); i++){
                User user = users.get(i);
                System.out.println("User ID = " + user.get_userId());
                System.out.println("User name = " + user.get_userName());
                System.out.println("email id = " + user.get_email());
                System.out.println("passcode = " + user.get_passcode());
                System.out.println("role = " + user.get_role ());
                System.out.println("----------------");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        */
        /* */
        try{
            newUser.deleteUser(5);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
