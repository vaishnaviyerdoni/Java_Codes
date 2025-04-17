package Inventory_System.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import Inventory_System.DAO_Layer.DatabaseConnection;
import Inventory_System.DAO_Layer.OrderDAO;
import Inventory_System.Model_Layer.Order;
import Inventory_System.Model_Layer.User;

public class OrderDAOTesr {
    public static void main(String[] args){

        //to fetch database connection
        Connection conn = null;
        try{
            conn = DatabaseConnection.getConn();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        OrderDAO obj = new OrderDAO(conn);
        User user = new User();

        
        // to test the create method.
        user.set_userId(7);
        String dstr = "2025-05-21";
        java.sql.Date date = java.sql.Date.valueOf(dstr);
        Order order = new Order(0, user, date , "Frank Davis", "Pending" );

        try{
            obj.placeOrder(order);
        }catch(SQLServerException e){
            System.out.println("Cannot place order if user doesnt exist");
            System.out.println("Register as user please!");
        }
        catch(SQLException e){
            System.out.println("User not found!");
        }
        
        //To test the read all method 
        try{
            List<Order> orders = obj.fetchAllOrders();
                for (int i = 0; i < orders.size(); i++){
                    Order order = orders.get(i);
                    System.out.println("--------------");
                    System.out.println("Order id: " + order.get_orderId());
                    System.out.println("User ID: " + order.get_UserId().get_userId());
                    System.out.println("Order Date: " + order.get_Orderdate());
                    System.out.println("Customer Name: " + order.get_customerName());
                    System.out.println("Status of order: " + order.get_status());
                    System.out.println("---------------");
                }
                
        }catch(SQLException e){
            e.printStackTrace();
        }

        //to test the fetch by OrderID method
        try{
            List<Order> orders = obj.fetchOrderbyId(15);
                for (int i = 0; i < orders.size(); i++){
                    Order order = orders.get(i);
                    System.out.println("--------------");
                    System.out.println("Order id: " + order.get_orderId());
                    System.out.println("User ID: " + order.get_UserId().get_userId());
                    System.out.println("Order Date: " + order.get_Orderdate());
                    System.out.println("Customer Name: " + order.get_customerName());
                    System.out.println("Status of order: " + order.get_status());
                    System.out.println("---------------");
                }
                
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        //To test the update Status method
        try{
            obj.updateStatus("cancelled", 15);
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        //To test the delete method
        try{
            obj.deleteOrder(16);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
    }
}
