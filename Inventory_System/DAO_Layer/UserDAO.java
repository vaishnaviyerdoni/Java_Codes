package Inventory_System.DAO_Layer;

import java.sql.*;
import java.util.*;

import javax.naming.spi.DirStateFactory.Result;

import com.microsoft.sqlserver.jdbc.SQLServerException;
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
        try{
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
        catch(SQLServerException e){
            System.out.println("User name has to be unique!");
            System.out.println("Enter a new User name.");
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

    //READ method to fetch the user role 
    public String getRole(int userId) throws SQLException{
        String sql = "SELECT roleUser FROM Users WHERE userId = ?";
        String role = " ";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, userId);
            try(ResultSet res = stmt.executeQuery()){
                if(res.next()){
                    role = res.getString("roleUser");
                }
                return role;

            }
        }
    }

    //READ method to fetch the user roles
    public List<String> getUserRoles() throws SQLException{
        String sql = "SELECT roleUser FROM Users";
        List<String> admins = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            try(ResultSet res = stmt.executeQuery()){
                while(res.next()){
                    String role = res.getString("roleUser");

                    admins.add(role);
                }

                return admins;
            }
        }
    }

    //UPDATE method to update the user details.
    public void updatePassCode(int userId, String newPasscode, String name) throws SQLException{
        String sql1 = "SELECT userName FROM Users where userId = ?";
        String sql2 = "UPDATE Users SET passcode = ? WHERE userId = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql1)){
            stmt.setInt(1, userId);
                try(ResultSet rs = stmt.executeQuery()){
                    if (rs.next()){
                        String user_name = rs.getString("userName");
                        if(user_name.equalsIgnoreCase(name)){
                            try(PreparedStatement stm = conn.prepareStatement(sql2)){
                                stm.setString(1, newPasscode);
                                stm.setInt(2, userId);
                                stm.executeUpdate();
                                System.out.println("Updated Successfully!");
                            }
                        }
                        else{
                            System.out.println("Username not matched!");
                        }
                    }
                    else{
                        System.out.println("User not found!");
                    }
                }
            }
        }

        //UPDATE Method to update email
        public void updateEmail(int userId, String newEmail, String name) throws SQLException{
            String sql1 = "SELECT userName FROM Users WHERE userId = ?";
            String sql2 = "UPDATE Users SET email = ? WHERE userId = ?";
            
            try(PreparedStatement stmt = conn.prepareStatement(sql1)){
                stmt.setInt(1, userId);
                try(ResultSet rs = stmt.executeQuery()){
                    if (rs.next()){
                        String user_name = rs.getString("userName");
                        if (user_name.equalsIgnoreCase(name)){
                            try(PreparedStatement stm = conn.prepareStatement(sql2)){
                                stm.setString(1, newEmail);
                                stm.setInt(2, userId);
                                stm.executeUpdate();
                                System.out.println("Updated Successfully");
                            }
                        }
                        else{
                        System.out.println("Username not matched");
                        }
                    }
                    else{
                        System.out.println("User not found");
                    }
                }
            }
        }
    

    //DELETE method to delete the specified user.
    public void deleteUser(int userId) throws SQLException{
        String sql = "DELETE FROM Users WHERE UserId = ?";
            try(PreparedStatement stmt  = conn.prepareStatement(sql)){
                stmt.setInt(1, userId);
                stmt.executeUpdate();
                System.out.println("Deleted User!");
            }
        }
}
