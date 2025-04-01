package Inventory_System.Model_Layer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Order {

    //fields
    private int orderId;
    private User userId;
    private Date orderDate;
    private String customerName;
    private String status;

    //constructor
    public Order(){}

    public Order(int orderId, User userId, Date orderDate, String customerName, String status){
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.status = status;
    }

    //getters

    public int get_orderId(){
        return orderId;
    }

    public User get_UserId(){
        return userId;
    }

    public Date get_Orderdate(){
        return orderDate;
    }

    public String get_customerName(){
        return customerName;
    }

    public String get_status(){
        return status;
    }

    //setters

    public void set_OrderId(int orderId){
        this.orderId = orderId;
    }

    public void set_userId(User user_Id){
        this.userId = userId;
    }

    public void set_Date(Date orderDate){
        this.orderDate = orderDate;
    }

    public void set_customer(String customerName){
        this.customerName = customerName;
    }

    public void set_status(String status){
        this.status = status;
    }

    public String toString(){
        return "Order{" +
                "Order_id=" + orderId +
                ", User_id='" + userId + '\'' +
                ", Date='" + orderDate + '\'' +
                ", customer_name=" + customerName +
                ", status=" + status +
                '}';
    }

    public static void main(String[] args) {
        User user = new User(1, "vaishnavi00", "vaishnavi@gmail.com", "ABC", "admin");

        // Convert date string to Date object
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Date orderDate = null;
    try {
        orderDate = sdf.parse("19-05-2000");
    } catch (ParseException e) {
        e.printStackTrace();
    }

        // Create an inventory item
        Order order = new Order(1, user, orderDate, "Vaishnavi", "Shipped");

        // Print item details
        System.out.println("\n" + order);

    }
    
}
