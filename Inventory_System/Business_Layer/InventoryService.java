package Inventory_System.Business_Layer;

import java.util.*;
import java.sql.*;
import Inventory_System.DAO_Layer.*;
import Inventory_System.Exceptions.ItemAbsentException;
import Inventory_System.Exceptions.UserNotFoundException;
import Inventory_System.Model_Layer.Inventory;
import Inventory_System.Model_Layer.User;

public class InventoryService{
    private InventoryDAO inventoryDAO;
    private UserDAO userDAO;
    private OrderItemDAO orderItemDAO;

    //constructor
    public InventoryService(InventoryDAO inventoryDAO, UserDAO userDAO, OrderItemDAO orderItemDAO){
        this.inventoryDAO = inventoryDAO;
        this.userDAO = userDAO;
        this.orderItemDAO = orderItemDAO;
    }

    //check inventory and return boolean value after comparing quantity and Lowstockthreshold
    public boolean isLowStock(int itemId, int userId) throws SQLException{
        try{
            String role = userDAO.getRole(userId);
            if (role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("staff")){
                int quantity = inventoryDAO.getQuantity(itemId);
                int LowStockThreshold = inventoryDAO.getLowStockThreshold(itemId);

                if (quantity <= LowStockThreshold){
                    return true; //return true items are running low
                }
                else{
                    return false; //return false enough items are available in inventory
                }
            }
            else{
                return false;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    //method to compare the quantity and lowstock threshold and update the quantity if necessary
    //rule1: quantity cannot be lesser than or equal to low stock threshold
    public boolean addToQuantity(int itemId, int userId, int QuantityToAdd) throws SQLException, UserNotFoundException{ 
        try{
            String role = userDAO.getRole(userId);
                if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("staff")){
                
                    boolean isLow = isLowStock(itemId, userId);
                    if (isLow){
                        if (inventoryDAO.updateItembyQuantity(itemId, QuantityToAdd)){
                            return true; // if inventory is updated
                        }
                        else{
                            return false; // if inventory is not updated
                        }
                    }
                    else{
                        return false; // if inventory has enough items and does not need to be updated
                    }
                }
                else{
                    throw new UserNotFoundException("Customer is not allowed to update inventory!");
                }
            }
        catch(SQLException e){
            e.printStackTrace();
            return false; // if error occured when updating quantity.
        }
    }

    //get the user role, if user is admin then they are allowed to update price
    public boolean updatePricebyAdmin(int itemId, int userId, double newPrice) throws SQLException, UserNotFoundException{
        try{
            String role = userDAO.getRole(userId);
            if (role.equalsIgnoreCase("Admin")){
                if(inventoryDAO.updatePricebyItemId(itemId, newPrice)){
                    return true;  // return true if Price Updated!
                }
                else{
                    return false; // returns false if Couldnt update price
                }
            }
            else{
                throw new UserNotFoundException("Only admin can update price!");
            }
        }    
        catch(SQLException e){
            e.printStackTrace();
            return false; // if error was occurred when updating price
        }    
    }


    //to view all the items from inventory table //work on return value
    public List<Inventory> viewAllItems() throws SQLException{
        try{
            List<Inventory> items = inventoryDAO.getAllItems();
            /* 
            for (int i = 0; i < items.size(); i++){
                Inventory myitem = items.get(i);
                System.out.println("Item Id = " + myitem.get_itemId());
                System.out.println("item Name = " + myitem.get_itemName());
                System.out.println("category = " + myitem.get_category());
                System.out.println("price = " + myitem.get_price());
                System.out.println("quantity = " + myitem.get_quantity());
                System.out.println("Low Stock Threshold = " + myitem.get_LowStock());
                System.out.println("-----------------");
            }
            */

            return items;
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    
    //to get itemID based on the itemname passed
    public int getItemIDfromTable(String itemName) throws  SQLException, ItemAbsentException{
        try{
            Integer itemId = inventoryDAO.getItemId(itemName);
            if (itemId == null){
                throw new ItemAbsentException("ItemID for given item name not found!");
            }
            else{
                return itemId;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    //to get the Price of an item from DB
    public double getPricebyItemName(String itemName, int itemId) throws SQLException, ItemAbsentException {
        Double price = 0d;
        try{
            int itemID = getItemIDfromTable(itemName);
            if (itemID == itemId){
                price = inventoryDAO.getPriceForItem(itemID);
            }

            return price;
        }
        catch (SQLException | ItemAbsentException e){
            e.printStackTrace();
            return -1d;
        }
    }

    //To get price of an item from DB by itemName
    public Double getTotalPricebyItemName(String itemName) throws SQLException, ItemAbsentException {
        Double price = 0d;
        try{
            price = inventoryDAO.getPriceByItemName(itemName);
            return price;
        }
        catch(SQLException e){
            e.printStackTrace();
            return -1d;
        }
    }

    //to get the items by category
    public List<Inventory> viewItemsByCategory(String category) throws SQLException{
        try{
            List<Inventory> itemsBycategory = inventoryDAO.getitemByCategory(category);
            return itemsBycategory;
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    //Only admin and staff can insert items in Inventory table
    public boolean insertItems(int itemId, int userId, String itemName, String category, Double price, int quantity, int LowStockThreshold) throws SQLException, UserNotFoundException{
        try{
            String role = userDAO.getRole(userId);
            if (role.equalsIgnoreCase("staff") || role.equalsIgnoreCase("Admin")){
               
                Inventory item = new Inventory(0, itemName, category, price, quantity, LowStockThreshold);
                if(inventoryDAO.addItem(item)){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                throw new UserNotFoundException("Customer cannot update inventory");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    
    //to deduct the Inventory after order placement or after updating inventory quantity
    public boolean deductInventory(int itemId, int newQuantity) throws SQLException {
        int oldQuantity = inventoryDAO.getQuantity(itemId);
        int UpdatedQuantity = oldQuantity - newQuantity;

        try{
            if(inventoryDAO.updateForDeduction(itemId, UpdatedQuantity)){
                return true;
            }
            else{
                return false;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    

    //Only admin can delete items from inventory table
    public boolean deleteByAdmin(int itemId, int userId) throws SQLException, UserNotFoundException{
        try{
            String role = userDAO.getRole(userId);
            if (role.equalsIgnoreCase("Admin")){
                if(inventoryDAO.deleteItem(itemId)){
                    return true; // if deleted successfully
                }
                else{
                    return false; // if the records were not deleted
                }
            }
            else{
                throw new UserNotFoundException("Only Admin can delete Records");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return false; // if error occurred when deleting records
        }
    }  
}

