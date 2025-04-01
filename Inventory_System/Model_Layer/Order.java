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
    private List<OrderItem> orderItems;

    //constructor
    public Order(){}

    public Order(int orderId, User userId, Date orderDate, String customerName, String status){
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.status = status;
        this.orderItems = orderItems;
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

    public List<OrderItem> get_orderItems(){
        return orderItems;
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

    public void set_orderItems(List<OrderItem> orderItems){
        this.orderItems = orderItems;
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
    
}
