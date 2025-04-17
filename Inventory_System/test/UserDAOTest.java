package Inventory_System.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import Inventory_System.DAO_Layer.DatabaseConnection;
import Inventory_System.DAO_Layer.UserDAO;
import Inventory_System.Model_Layer.User;

public class UserDAOTest {
    public static void main(String[] args) {

        Connection conn = null;
        try{    
            conn = DatabaseConnection.getConn();
        }catch(SQLException e){
            e.printStackTrace();
        }
        UserDAO newUser = new UserDAO(conn);

        
        //To test the create method to add or register new user.
        User user = new User(0,"Michell bell", "michell23@gmail.com", "DogParty3", "customer");
         
        try{
            newUser.addUser(user);
        }catch(SQLException e){
            System.out.println("User Name has to be unique, enter another user name!");
            e.printStackTrace();
        }
        
        //to test the read method 
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
        
        
        
        //trying the delete method
        try{
            newUser.deleteUser(8);
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        // to test the update method
        try{
            newUser.updateEmail(2, "Helena2@gmsil.com", "Helena3107");
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    
    }
}
