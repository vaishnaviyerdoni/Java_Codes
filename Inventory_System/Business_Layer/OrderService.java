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
    public int addOrder(int orderId, int userID, String orderDate, String customerName, String orderStatus, Double totalPrice) throws SQLException, UserNotFoundException{
        try{
            List<Integer> userids = userDAO.getUserIds();
            boolean foundUser = false;

            User user = new User();
            for (int i = 0; i < userids.size(); i++){
                if(userID == userids.get(i)){
                    user.set_userId(userID);
                    foundUser = true;
                    break;
                }
            }
            if (!foundUser){
                throw new UserNotFoundException("User with ID " + userID + " not found!");
            }
            java.sql.Date date = validate_Date(orderDate);
            Order order = new Order(orderId, user, date, customerName, "Pending", totalPrice);
            
            return orderDAO.addOrderInDatabase(order);
        }
        catch(SQLException e){
            e.printStackTrace();
            return -1; //"Error occurred while adding order!"; 
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
    public List<Order> getallOrders(int userId) throws SQLException, NullPointerException, UserNotFoundException{
        try{
            String role = userDAO.getRole(userId);

            if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("staff")){
                List<Order> orders = orderDAO.fetchAllOrders();
                /* 
                for (int i = 0; i < orders.size(); i++){
                    Order order = orders.get(i);
                    System.out.println("Order id: " + order.get_orderId());
                    System.out.println("User ID: " + order.get_UserId().get_userId());
                    System.out.println("Order Date: " + order.get_Orderdate());
                    System.out.println("Customer Name: " + order.get_customerName());
                    System.out.println("Status of order: " + order.get_status());
                    System.out.println("---------------");
                }
                */
                return orders;
            }
            else{
                throw new UserNotFoundException("Customer cannot access this information");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    //Users who have placed Orders can have view their order by order id
    public List<Order> viewOrderByID(int orderId, int userId) throws SQLException, NullPointerException, UserNotFoundException {
        try{
            List<Order> orders = new ArrayList<>();
            Order order = null;
            if (orderDAO.isUserValid(userId)){
                order = orderDAO.fetchOrderbyId(orderId);

                int userid = order.get_UserId().get_userId();
                if(userid == userId){
                    orders.add(order);
                    return orders;
                }
                else{
                    throw new UserNotFoundException("User for " + userId + " not found!");
                }
            }
            else{
                throw new UserNotFoundException("User not found for ID:" + userId);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    //user can access their user history by their userId
    public List<Order> viewOrderByUserId(int userId) throws SQLException {
        try{
            List<Order> orderByUserID = orderDAO.fetchOrderbyUser(userId);
            /* 
            for (int j = 0; j < orderByUserID.size(); j++){
                Order order = orderByUserID.get(j);
                System.out.println("-------------");
                System.out.println("Order id: " + order.get_orderId());
                System.out.println("User ID: " + order.get_UserId().get_userId());
                System.out.println("Order Date: " + order.get_Orderdate());
                System.out.println("Customer Name: " + order.get_customerName());
                System.out.println("Status of order: " + order.get_status());
                System.out.println("---------------");
                
            }
                */
            return orderByUserID;
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    //Price update method, this method will get called from order items so that the sum of subtotals of the 
    // items belonging to same order will be the total price.
    public boolean updateTotalPrice(Double price, int orderId, int userId) throws SQLException, UserNotFoundException {
        try{
            if(orderDAO.isUserValid(userId)){
                orderDAO.updateTotal(price, orderId);
                return true; //"Updated the total price successfully!";
            }
            else{
                throw new UserNotFoundException("Only registered users can update price!");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return false; //"Error occured when updating the price!";
        }
    }

    //get the  total price
    public Double getPricebyOrderId(int orderId) throws SQLException {
        Double totalPrice = 0d;
        try{
            totalPrice = orderDAO.getPricebyOrder(orderId);

            return totalPrice;
        }
        catch(SQLException e){
            e.printStackTrace();
            return -1d;
        }
    }
    
    //Admin or staff changes the status of the order
    public boolean changeStatus(int userId, String Status, int CustomerId) throws SQLException, UserNotFoundException {
        try{
            String role = userDAO.getRole(userId);

            if(role.equalsIgnoreCase("staff") || role.equalsIgnoreCase("admin")){

                orderDAO.updateStatus(Status, CustomerId);

                return true; //"Order Status Updated!";

            }
            else{
                throw new UserNotFoundException("Customer cannot update status!");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return false; //"Error Occurred while updating the Status.";
        }
    }

    //Admin can delete the records about any order
    public boolean AdminDeletesOrder(int userId, int orderId) throws SQLException, UserNotFoundException {
        try{
            String role = userDAO.getRole(userId);
            if(role.equalsIgnoreCase("admin")){
                if(orderDAO.deleteOrder(orderId)){
                    return true;
                }
                else{
                    return false;
                }
            }else{
                throw new UserNotFoundException("Only Admin can delete order!");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return false; //"error occurred when deleting the Order";
        }
    }
}
