package Inventory_System.Business_Layer;

import java.util.*;
import java.sql.*;
import java.sql.Date;
import Inventory_System.DAO_Layer.*;
import Inventory_System.Exceptions.*;
import Inventory_System.Model_Layer.*;

public class OrderService {
    
    private OrderDAO orderDAO;
    private UserDAO userDAO;
    private InventoryDAO inventoryDAO;

    public OrderService(OrderDAO orderDAO, UserDAO userDAO, InventoryDAO inventoryDAO){
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.inventoryDAO = inventoryDAO;
    }

    //to place new order
    public void addOrder(int orderId, int userID, String orderDate, String customerName, String orderStatus) throws SQLException, UserNotFoundException{
        try{
            List<Integer> userids = userDAO.getUserIds();

            User user = new User();
            for (int i = 0; i < userids.size(); i++){
                if(userID == userids.get(i)){
                    user.set_userId(userID);
                    break;
                }
                else{
                    throw new UserNotFoundException("User for the" + userID + " not found!");
                }
            }
            java.sql.Date date = validate_Date(orderDate);
            Order order = new Order(orderId, user, date, customerName, "Pending");
            orderDAO.placeOrder(order);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //to convert string date to sql.Date format
    public java.sql.Date validate_Date(String orderDate){
        try{
            return java.sql.Date.valueOf(orderDate);
        }
        catch(Exception e){
            e.printStackTrace();
            return java.sql.Date.valueOf("1999-01-01");
        }
    } 

    //only admin or staff can fetch the details of all the orders
    public List<Order> getallOrdersforAdmin(int userId) throws SQLException, NullPointerException{
        try{
            String role = userDAO.getRole(userId);

            if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("staff")){
                List<Order> orders = orderDAO.fetchAllOrders();

                for (int i = 0; i < orders.size(); i++){
                    Order order = orders.get(i);
                    System.out.println("Order id: " + order.get_orderId());
                    System.out.println("User ID: " + order.get_UserId().get_userId());
                    System.out.println("Order Date: " + order.get_Orderdate());
                    System.out.println("Customer Name: " + order.get_customerName());
                    System.out.println("Status of order: " + order.get_status());
                    System.out.println("---------------");
                }
                return orders;
            }
            else{
                System.out.println("Customers is not allowed to view order information");
                return null;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
             
    public static void main(String[] args) {
        try{
            Connection conn = null;
            conn = DatabaseConnection.getConn();
            UserDAO userDAO = new UserDAO(conn);
            OrderDAO orderDAO = new OrderDAO(conn);
            InventoryDAO inventoryDAO = new InventoryDAO(conn);
        
            OrderService service =  new OrderService(orderDAO, userDAO, inventoryDAO);
            

            service.getallOrdersforAdmin(15);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
    } 
    
    
}
