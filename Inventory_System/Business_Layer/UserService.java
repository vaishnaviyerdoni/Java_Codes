package Inventory_System.Business_Layer;

import Inventory_System.DAO_Layer.DatabaseConnection;
import Inventory_System.DAO_Layer.UserDAO;
import Inventory_System.Model_Layer.User;
import java.util.*;
import java.sql.*;


public class UserService {

    private UserDAO userDAO;

    //constructor
    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    //to register the new users
    public void registerUser(int userId, String userName, String email, String passcode, String roleUser) throws SQLException{
        try{
            User user  = new User(userId, userName, email, passcode, roleUser);
            List<String> roles = userDAO.getUserRoles();

            int cntAdmin = 0;
            int cntStaff = 0;

            for (int i = 0; i < roles.size(); i++){
                if(roles.get(i).equalsIgnoreCase("admin")){
                    cntAdmin++;
                }
                
                if (roles.get(i).equalsIgnoreCase("Staff")){
                    cntStaff++;
                }
            }

            if (roleUser.equalsIgnoreCase("customer")){
                userDAO.addUser(user);
            }
            
            if(roleUser.equalsIgnoreCase("admin")){
                if (cntAdmin < 5){
                    userDAO.addUser(user);
                }
                else{
                    System.out.println("Only 5 Admin registrations are allowed.");
                }
            }

            if (roleUser.equalsIgnoreCase("staff")){
                if (cntStaff < 10){
                    userDAO.addUser(user);
                }
                else{
                    System.out.println("Only 10 Staff registrations are allowed.");
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Only Staff and Admin can read the information about all Users
    public List<User> ReadAllUsers(int userId) throws SQLException, NullPointerException{
        try{
            String role = userDAO.getRole(userId);

            if (role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("staff")){
                List<User> users = userDAO.getAllUsers();
                for (int i = 0; i < users.size(); i++){

                    User user = users.get(i);
                    System.out.println("User ID = " + user.get_userId());
                    System.out.println("User name = " + user.get_userName());
                    System.out.println("email id = " + user.get_email());
                    System.out.println("passcode = " + user.get_passcode());
                    System.out.println("role = " + user.get_role());
                    System.out.println("----------------");
    
                }
                return users;
            }
            else{
                System.out.println("Customer cannot view User Information!");
                return null;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    //update the passcode if username is correct
    public void updatePasscodeIfuserNameExists(int userId, String nPasscode, String username) throws SQLException{
        
        try{
            List<String> names = userDAO.getAllNames();
            for (int i = 0; i < names.size(); i++){
                if (username.equals(names.get(i))){
                    //System.out.println("User name exists");
                    userDAO.updatePassCode(userId, nPasscode, username);
                }
            } 
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //Update the email if username is correct
    public void updateEmailifUserNameExits(int userId, String nEmail, String username) throws SQLException{
        
        try{
            List<String> names = userDAO.getAllNames();
            for (int i = 0; i < names.size(); i++){
                if(username.equals(names.get(i))){
                    userDAO.updateEmail(userId, nEmail, username);
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //Only admin can delete user
    public void AdminDeletesUser(int AdminUserId, int deleteUserId) throws SQLException{
        try{
            String role = userDAO.getRole(AdminUserId);
            if(role.equalsIgnoreCase("Admin")){
                userDAO.deleteUser(deleteUserId);
            }
            else{
                System.out.println("Staff and Cuustomer are not authorized to delete the user.");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //Login Logic
    public String LoginLogic(int userId, String username, String passcode, String roleUser) throws SQLException{
        try{
            String userName = userDAO.getUsername(userId);
            String passCode = userDAO.getPasscode(userId);
            String role = userDAO.getRole(userId);

            /* 
            System.out.println(userName);
            System.out.println(role);
            System.out.println(passCode);
            */

            if (userName.equals(username) && passCode.equals(passcode) && role.equalsIgnoreCase(roleUser)){
                return "Login Successful";
            }
            else{
                return "The credentials do not match!";
            }
                
        }
        catch(SQLException e){
            e.printStackTrace();
            return "-1";
        }
    }

    //user name and passcode rules
    public boolean isValiduserName(String username){
        if (username.length() < 5 || username.length() > 15){
            return false;
        }

        if (username.matches("^[a-zA-Z0-9_]{5,15}$")){
            return true;
        }

        return false;
    }

    public boolean isValidPasscode(String passcode) {
        if (passcode.length() < 8 || passcode.length() > 15){
            return false;
        }
    
        if(passcode.matches("^[a-zA-Z0-9!@#$%&_*]{8,15}$")){
            return true;
        }

        return false;
    }
}
