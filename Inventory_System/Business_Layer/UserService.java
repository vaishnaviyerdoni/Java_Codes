package Inventory_System.Business_Layer;

import Inventory_System.DAO_Layer.DatabaseConnection;
import Inventory_System.DAO_Layer.UserDAO;
import Inventory_System.Exceptions.UserNotFoundException;
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
    public String registerUser(int userId, String userName, String email, String passcode, String roleUser) throws SQLException{
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
                if(userDAO.addUser(user)){
                    return "User Registered!";
                }
                else{
                    return "Could not register user!";
                }
            }
            else if (roleUser.equalsIgnoreCase("admin")){
                if (cntAdmin < 5){
                    if (userDAO.addUser(user)){
                        return "User Registered!";
                    }
                    else{
                        return "Could not register User!";
                    }
                }
                else{
                    return "Only 5 Admin registrations are allowed.";
                }
            }
            else if (roleUser.equalsIgnoreCase("staff")){
                if (cntStaff < 10){
                    if (userDAO.addUser(user)){
                        return "User Registered!";
                    }
                    else{
                        return "Could not register User!";
                    }
                }
                else{
                    return "Only 10 Admin registrations are allowed.";
                }
            }
            else{
                return "The user must be admin, staff or customer";
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return "Error occurred when registering the user, Try again later";
        }
    }

    // Only Staff and Admin can read the information about all Users
    public List<User> ReadAllUsers(int userId) throws SQLException, NullPointerException{
        try{
            String role = userDAO.getRole(userId);

            if (role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("staff")){
                List<User> users = userDAO.getAllUsers();
                /* 
                for (int i = 0; i < users.size(); i++){    
                    User user = users.get(i);
                    System.out.println("User ID = " + user.get_userId());
                    System.out.println("User name = " + user.get_userName());
                    System.out.println("email id = " + user.get_email());
                    System.out.println("passcode = " + user.get_passcode());
                    System.out.println("role = " + user.get_role());
                    System.out.println("----------------");
                }
                */

                return users;
            }
            else{
                throw new NullPointerException("Customer cannot view this information!");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    //update the passcode if username is correct
    public String updatePasscodeIfuserNameExists(int userId, String nPasscode, String username) throws SQLException{
        
        try{
            String name = userDAO.getUsername(userId);
            if (name.equals(username)){
                if(userDAO.updatePassCode(userId, nPasscode, name)){
                    return "Passcode Updated!";
                }
                else{
                    return "Passcode was not updated!";
                }
            }
            else{
                return "The user names do not match!";
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return "Error occurred when updating passcode!";
        }
        catch(UserNotFoundException e){
            e.getMessage();
            return "User was not found!";
        }
    }

    //Update the email if username is correct
    public String updateEmailifUserNameExits(int userId, String nEmail, String username) throws SQLException{
        
        try{
            String name = userDAO.getUsername(userId);
            if(name.equals(username)){
                if(userDAO.updateEmail(userId, nEmail, name)){
                    return "Email Updated!";
                }
                else{
                    return "Could not update the email";
                }
            }
            else{
                return "User names do not match!!";
            }       
        }
        catch(SQLException e){
            e.printStackTrace();
            return "Error Occurred when updating email.";
        }
        catch(UserNotFoundException e){
            e.getMessage();
            return "User was not found!";
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
