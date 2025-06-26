package Inventory_System.Business_Layer;

import Inventory_System.DAO_Layer.DatabaseConnection;
import Inventory_System.DAO_Layer.UserDAO;
import Inventory_System.Exceptions.InvalidPasscodeException;
import Inventory_System.Exceptions.UserNotFoundException;
import Inventory_System.Model_Layer.User;
import java.util.*;
import java.security.InvalidParameterException;
import java.sql.*;


public class UserService {

    private UserDAO userDAO;

    //constructor
    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    //to register the new users
    public int registerUser(int userId, String userName, String email, String passcode, String roleUser) throws SQLException{
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
                return userDAO.addUser(user);
            }
            else if (roleUser.equalsIgnoreCase("admin")){
                if(cntAdmin < 5){
                    return userDAO.addUser(user);
                }
                else{
                    return 0;
                }
            }
            else if (roleUser.equalsIgnoreCase("staff")){
                if (cntStaff < 10){
                    return userDAO.addUser(user);
                }
                else{
                    return 0;
                }
            }
            else{
                return -1;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return -1;
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
    public boolean updatePasscodeIfuserNameExists(int userId, String nPasscode, String username) throws SQLException, UserNotFoundException, InvalidPasscodeException {
        
        try{
            String name = userDAO.getUsername(userId);
            if (name.equals(username)){
                if(isValidPasscode(nPasscode)){
                    if(userDAO.updatePassCode(userId, nPasscode, name)){
                    return true; //"Passcode Updated!";
                    }
                    else{
                        return false; //"Passcode was not updated!";
                    }
                }
                else{
                    throw new InvalidPasscodeException("Passcode should between 8 to 15 characters");
                }
            }
            else{
                throw new UserNotFoundException("Usernames do not match!");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return false; //"Error occurred when updating passcode!";
        }
    }

    //Update the email if username is correct
    public boolean updateEmailifUserNameExits(int userId, String nEmail, String username) throws SQLException, UserNotFoundException {
        
        try{
            String name = userDAO.getUsername(userId);
            if(name.equals(username)){
                if(userDAO.updateEmail(userId, nEmail, name)){
                    return true; //"Email Updated!";
                }
                else{
                    return false; //"Could not update the email";
                }
            }
            else{
                throw new UserNotFoundException("Usernames do not match");
            }       
        }
        catch(SQLException e){
            e.printStackTrace();
            return false; //"Error Occurred when updating email.";
        }
    }
    
    //Only admin can delete user
    public boolean AdminDeletesUser(int AdminUserId, int deleteUserId) throws SQLException, UserNotFoundException{
        try{
            String role = userDAO.getRole(AdminUserId);
            if(role.equalsIgnoreCase("Admin")){
                if(userDAO.deleteUser(deleteUserId)){
                    return true; //"User deleted Successfully!";
                }
                else{
                    return false; //"User couldn't be deleted!";
                }
            }
            else{
                throw new UserNotFoundException("Staff and Cuustomer are not authorized to delete the user.");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return false; //"Error occured when deleting the user!";
        }
    }

    public boolean UserDeletesTheirAccount(int userId, String userName) throws SQLException, UserNotFoundException {
        try{
            String username = userDAO.getUsername(userId);
            
            if(username.equals(userName)){
                if(userDAO.deleteUser(userId)){
                    return true; //"Account deleted!";
                }
                else{
                    return false; //"Account couldn't be deleted!";
                }
            }
            else{
                throw new UserNotFoundException("UserName and userID do not match!");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return false; //"Error occured when deleting account!";
        }
    }

    //Login Logic
    public boolean isValidUserForLogin(int userId, String username, String passcode, String roleUser) throws SQLException{
        try{
            String userName = userDAO.getUsername(userId).trim();
            String passCode = userDAO.getPasscode(userId).trim();
            String role = userDAO.getRole(userId).trim();

            System.out.println("Input username: " + username);
            System.out.println("DB username: " + userName);

            System.out.println("Input passcode: " + passcode);
            System.out.println("DB passcode: " + passCode);

            System.out.println("Input role: " + roleUser);
            System.out.println("DB role: " + role);

            /* 
            System.out.println(userName);
            System.out.println(role);
            System.out.println(passCode);
            */

            return (userName.equals(username) && passCode.equals(passcode) && role.equalsIgnoreCase(roleUser));
                
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
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
