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

    public void addOrder(int orderId, int userID, String orderDate, String customerName, String orderStatus) throws SQLException, ItemAbsentException, UserNotFoundException{
        try{
            List<Integer> userids = userDAO.getUserIds();

            User user = new User();
            for (int i = 0; i < userids.size(); i++){
                if(userID == userids.get(i)){
                    user.set_userId(userID);
                    break;
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

    public java.sql.Date validate_Date(String orderDate){
        try{
            java.sql.Date date = java.sql.Date.valueOf(orderDate);
            return date;
        }
        catch(Exception e){
            e.printStackTrace();
            return java.sql.Date.valueOf("1999-01-01");
        }
    } 
            /* 
    public static void main(String[] args) {
        try{
            Connection conn = null;
            conn = DatabaseConnection.getConn();
            UserDAO userDAO = new UserDAO(conn);
            OrderDAO orderDAO = new OrderDAO(conn);
            InventoryDAO inventoryDAO = new InventoryDAO(conn);
        
            OrderService service =  new OrderService(orderDAO, userDAO, inventoryDAO);
            

            service.addOrder();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    } 
    */
    
}
