package Inventory_System.Model_Layer;

import java.util.*;

public class User {

    // variables
    private int userId;
    private String userName;
    private String email;
    private String password;
    private String role;
    private List<Order> order; // one user can have many orders.

    //constructor
    public User(){}

    public User(int userId, String userName, String email, String password, String role){
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.order = order;
    }

    //getters
    public int get_userId(){
        return userId;
    }

    public String get_userName(){
        return userName;
    }

    public String get_email(){
        return email;
    }

    public String get_password(){
        return password;
    }

    public String get_role(){
        return role;
    }
    public List<Order> get_order(){
        return order;
    }

    //setters
    public void set_userId(int userId){
        this.userId = userId;
    }

    public void set_userName(String userName){
        this.userName = userName;
    }

    public void set_email(String email){
        this.email = email;
    }

    public void set_password(String password){
        this.password = password;
    }
    public void set_role(String role){
        this.role = role;
    }    

    public void set_order(List<Order> order){
        this.order = order;
    }

    public String toString(){
        return "User{" +
                "UserId=" + userId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password=" + password +
                ", role=" + role +
                '}';
    }

    
    public static void main(String[] args) {
        // Create an inventory item
        User user = new User(1, "vaishnavi00", "vaishnavi@gmail.com", "ABC", "admin");

        // Print item details
        System.out.println("\n" + user);

    }
}
