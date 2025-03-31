package Inventory_System.Model_Layer;

import java.util.*;

public class Orders {

    //fields
    private int order_id;
    private Users userId;
    private Date order_date;
    private String customer_name;
    private String status;

    //constructor
    public Orders(){}

    public Orders(int order_id, Users userId, Date order_date, String customer_name, String status){
        this.order_id = order_id;
        this.userId = userId;
        this.order_date = order_date;
        this.customer_name = customer_name;
        this.status = status;
    }

    //getters

    public int get_orderId(){
        return order_id;
    }

    public Users get_UserId(){
        return userId;
    }

    public Date get_Orderdate(){
        return order_date;
    }

    public String get_customerName(){
        return customer_name;
    }

    public String get_status(){
        return status;
    }

    //setters

    public void set_OrderId(int order_id){
        this.order_id = order_id;
    }

    public void set_userId(Users user_Id){
        this.userId = user_Id;
    }

    public void set_Date(Date order_date){
        this.order_date = order_date;
    }

    public void set_customer(String customer_name){
        this.customer_name = customer_name;
    }

    public void set_status(String status){
        this.status = status;
    }

    public String toString(){
        return "Order{" +
                "Order_id=" + order_id +
                ", User_id='" + userId + '\'' +
                ", Date='" + order_date + '\'' +
                ", customer_name=" + customer_name +
                ", status=" + status +
                '}';
    }

    public static void main(String[] args) {
        // Create an inventory item
        Orders order = new Orders(1, 1, "19-05-2000", "Vaishnavi", "Shipped");

        // Print item details
        System.out.println("\n" + order);

    }
    
}
