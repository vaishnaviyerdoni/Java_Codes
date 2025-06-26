package Inventory_System.Business_Layer;

import java.sql.*;
import java.util.*;
import Inventory_System.DAO_Layer.*;
import Inventory_System.Exceptions.ItemAbsentException;
import Inventory_System.Exceptions.UserNotFoundException;
import Inventory_System.Model_Layer.*;

public class OrderItemService {
    private OrderItemDAO orderItemDAO;
    private InventoryDAO inventoryDAO;
    private OrderDAO orderDAO;
    private UserDAO userDAO;
    private InventoryService service;

    public OrderItemService(OrderItemDAO orderItemDAO, InventoryDAO inventoryDAO, OrderDAO orderDAO, UserDAO userDAO, InventoryService service){
        this.orderItemDAO = orderItemDAO;
        this.inventoryDAO = inventoryDAO;
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.service = service;
    }

    //Any registered user can place order
    public boolean addItems(int itemsId, int orderId, int inventoryId, int quantity, Double Subtotal, int userId) throws SQLException, UserNotFoundException {
        try{
            if(orderDAO.isUserValid(userId)){

                //setting the foreign keys
                Order order = new Order();
                order.set_OrderId(orderId);
                Inventory inventory = new Inventory();
                inventory.set_itemId(inventoryId);
                User user = new User();
                user.set_userId(userId);

                OrderItem items = new OrderItem(itemsId, order, inventory, quantity, Subtotal, user);
                boolean isPlaced = orderItemDAO.addOrderItem(items);
                if (isPlaced){
                    if (service.deductInventory(inventoryId, quantity)){
                        return true; // return true if inventory deducted and order placed
                    }
                    else{
                        return false; //will return false if inventory not deducted
                    }
                }
                else{
                    return false; //returns false if order not placed
                }
            }
            else{
                throw new UserNotFoundException("The user is not registered, Register to place order!");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //Only admin or staff can view all the details of the Ordered items
    public List<OrderItem> viewItems(int userId) throws SQLException, UserNotFoundException, NullPointerException {

        List<OrderItem> items = new ArrayList<>();
        try{
            String role = userDAO.getRole(userId);
            if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("staff")){
                items = orderItemDAO.fetchAll();
                return items;
            }
            else{
                throw new UserNotFoundException("Customer cannot view this information");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    //Registered users can fetch the information for their order by using their order id
    public List<OrderItem> getItemsbyOrderid(int userId, int orderId) throws SQLException, UserNotFoundException, NullPointerException {
        List<OrderItem> items = new ArrayList<>();
        try{
            if (orderDAO.isUserValid(userId)) {
                items = orderItemDAO.getItemsbyOrderid(orderId);
                return items;
            }
            else{
                throw new UserNotFoundException("Only registered user can view this information");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    //Update the quantity and update subtotal
    public boolean updateQuantityAndSubtotal(int itemsId, int inventoryId, int userId, int nQuantity) throws SQLException, UserNotFoundException, ItemAbsentException {
        try{
            if(orderDAO.isUserValid(userId)){
                boolean isUpdated = orderItemDAO.UpdateQuantity(nQuantity, itemsId);
                if(isUpdated){
                    if(orderItemDAO.updateSubtotal(inventoryId, itemsId)){
                        if(service.deductInventory(inventoryId, nQuantity)){
                            return true; // return true is quantity is updated and then subtotal is updated
                        }
                        else{
                            throw new ItemAbsentException("Failed to update inventory!");
                        }
                            
                    }
                    else{
                        return false; // return false if quantity is updated, but subtotal is not updated
                    }
                }
                else{
                    return false; // return false when quantity is not updated so we dont call the updatesubtotal method
                }  
            }
            else{
                throw new UserNotFoundException("Only registered users can update the quantity"); //error is thrown when user not found
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return false; // return false if unexpected sql error is occurred
        }
        catch(ItemAbsentException e){
            e.getMessage();
            return false; //return false if item information was absent
        }
    }
    
    //Only Admin can delete the records
    public boolean adminDeletesItem(int userId, int itemsId) throws SQLException, UserNotFoundException {
        try{
            String role = userDAO.getRole(userId);
            if(role.equalsIgnoreCase("admin")){
                if(orderItemDAO.deleteOrderItem(itemsId)){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                throw new UserNotFoundException("Only Admin can delete the records");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }    
}
