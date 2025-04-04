package Inventory_System.Model_Layer;

import java.util.*;

public class User {

    // variables
    private int userId;
    private String userName;
    private String email;
    private String passcode;
    private String role;
    private List<Order> order; // one user can have many orders.

    //constructor
    public User(){}

    public User(int userId, String userName, String email, String passcode, String role){
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.passcode = passcode;
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

    public String get_passcode(){
        return passcode;
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

    public void set_passcode(String passcode){
        this.passcode = passcode;
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
                ", passcode=" + passcode +
                ", role=" + role +
                '}';
    }
}
