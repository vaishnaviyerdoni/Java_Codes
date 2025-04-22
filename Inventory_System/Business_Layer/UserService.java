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
}
