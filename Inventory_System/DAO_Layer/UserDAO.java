package Inventory_System.DAO_Layer;

import java.sql.*;
import java.util.*;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import Inventory_System.Model_Layer.User;
import Inventory_System.DAO_Layer.DatabaseConnection;
import Inventory_System.Exceptions.UserNotFoundException;

public class UserDAO {
    private  Connection conn;
    // Constructor to accept the jdbc connection
    public UserDAO(Connection conn){
        this.conn = conn;
    }

    //CREATE method to add/register new Users.
    public int addUser(User user) throws SQLException{
        String sql = "INSERT INTO Users (UserName, email, passcode, roleUser) VALUES (?,?,?,?)";
        try{
            try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                stmt.setString(1, user.get_userName());
                stmt.setString(2, user.get_email());
                stmt.setString(3, user.get_passcode());
                stmt.setString(4, user.get_role());
                int rows = stmt.executeUpdate();
                if (rows > 0){
                    try(ResultSet res = stmt.getGeneratedKeys()){
                        if(res.next()){
                            return res.getInt(1);
                        }
                        else{
                            return 0;
                        }
                    }
                }
                else{
                    return 0;
                }
            }
        }
        catch(SQLServerException e){
            System.out.println("User name has to be unique!");
            System.out.println("Enter a new User name.");
            return -1;
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
    
    //READ method to fetch all the user ids
    public List<Integer> getUserIds() throws SQLException{
        List<Integer> userIds = new ArrayList<>();
        String sql = "SELECT userId FROM Users";

        try(PreparedStatement stm = conn.prepareStatement(sql)){
            try(ResultSet res = stm.executeQuery()){
                while(res.next()){
                    Integer userid = res.getInt("userId");

                    userIds.add(userid);
                }

                return userIds;
            }
        }
    }

    //READ method to fetch the user role 
    public String getRole(int userId) throws SQLException{
        String sql = "SELECT roleUser FROM Users WHERE userId = ?";
        String role = "";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, userId);
            try(ResultSet res = stmt.executeQuery()){
                if(res.next()){
                    role = res.getString("roleUser").trim();
                }
                return role;

            }
        }
    }

    //READ method to fetch passcode
    public String getPasscode(int userId) throws SQLException{
        String sql = "SELECT passcode FROM Users WHERE userId = ?";
        String passcode = "";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, userId);
            try(ResultSet res = stmt.executeQuery()){
                if(res.next()){
                    passcode = res.getString("passcode").trim();
                }
                return passcode;

            }
        }
    }

    //READ method to fetch username
    public String getUsername(int userId) throws SQLException{
        String sql = "SELECT userName FROM Users WHERE userId = ?";
        String username = "";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, userId);
            try(ResultSet res = stmt.executeQuery()){
                if(res.next()){
                    username = res.getString("userName").trim();
                }
                return username;
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

    //READ method to fetch the usernames
    public List<String> getAllNames() throws SQLException{
        String sql = "SELECT userName FROM Users";
        List<String> names = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            try(ResultSet res = stmt.executeQuery()){
                while(res.next()){
                    String name = res.getString("userName");

                    names.add(name);
                }

                return names;
            }
        }
    }

    //UPDATE method to update the Passcode.
    public boolean updatePassCode(int userId, String newPasscode, String name) throws SQLException, UserNotFoundException{
        String sql1 = "SELECT userName FROM Users where userId = ?";
        String sql2 = "UPDATE Users SET PassCode = ? WHERE userId = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql1)){
            stmt.setInt(1, userId);
                try(ResultSet rs = stmt.executeQuery()){
                    if (rs.next()){
                        String user_name = rs.getString("userName");
                        if(user_name.equals(name)){
                            try(PreparedStatement stm = conn.prepareStatement(sql2)){
                                stm.setString(1, newPasscode);
                                stm.setInt(2, userId);
                                int rows = stm.executeUpdate();
                                if (rows > 0){
                                    return true;
                                }
                                else{
                                    return false;
                                }
                            }
                        }
                        else{
                            throw new UserNotFoundException("user name not found");
                        }
                    }
                    else{
                        throw new UserNotFoundException("user not found");
                    }
                }
            }
        }

    //UPDATE Method to update email
    public boolean updateEmail(int userId, String newEmail, String name) throws SQLException, UserNotFoundException{
        String sql1 = "SELECT userName FROM Users WHERE userId = ?";
        String sql2 = "UPDATE Users SET email = ? WHERE userId = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql1)){
            stmt.setInt(1, userId);
                try(ResultSet rs = stmt.executeQuery()){
                    if (rs.next()){
                        String user_name = rs.getString("userName");
                        if(user_name.equals(name)){
                        try(PreparedStatement stm = conn.prepareStatement(sql2)){
                            stm.setString(1, newEmail);
                            stm.setInt(2, userId);
                            int rows = stm.executeUpdate();
                            if (rows > 0){
                                return true;
                            }
                            else{
                                return false;
                            }
                        }
                    }
                    else{
                        throw new UserNotFoundException("user name not found");
                    }
                }
                else{
                    throw new UserNotFoundException("user not found");
                }
            }
        }
    }
    

    //DELETE method to delete the specified user.
    public boolean deleteUser(int userId) throws SQLException{
        String sql = "DELETE FROM Users WHERE UserId = ?";
        try(PreparedStatement stmt  = conn.prepareStatement(sql)){
            stmt.setInt(1, userId);
            int rows = stmt.executeUpdate();
            if (rows > 0){
                return true;
            }
            else{
                return false;
            }
        }
    }
}
