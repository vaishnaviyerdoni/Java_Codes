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
            List<String> roles = new ArrayList<>();

            roles = userDAO.getUserRoles();

            int cntAdmin = 0;
            int cntStaff = 0;

            if (roleUser.equalsIgnoreCase("customer")){
                userDAO.addUser(user);
            }
            else if(roleUser.equalsIgnoreCase("admin") || roleUser.equalsIgnoreCase("staff")){
                for (int i = 0; i < roles.size(); i++){
                    if (roles.get(i).equalsIgnoreCase("admin")){
                        cntAdmin++;

                        if (cntAdmin < 5){
                            userDAO.addUser(user);
                        }
                        else{
                            System.out.println("Only 5 admin registrations are allowed!");
                        }
                    }
                    else if (roles.get(i).equalsIgnoreCase("staff")){
                        cntStaff++;

                        if (cntStaff < 10){
                            userDAO.addUser(user);
                        }
                        else{
                            System.out.println("Only 10 Staff registrations are allowed");
                        }
                    }
                    else{
                        System.out.println("role should be customer, staff or admin");
                    }
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
            List<User> users = userDAO.getAllUsers();

            if (role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("staff")){
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

    //user name and passcode rules

    public static void main(String[] args) {
        try{
            Connection conn = null;
            conn = DatabaseConnection.getConn();
            UserDAO userDAO = new UserDAO(conn);
            UserService service = new UserService(userDAO);
            service.updateEmailifUserNameExits(2, "Helena2@gmail.com", "Helena3107");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
