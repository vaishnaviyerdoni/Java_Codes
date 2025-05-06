package Inventory_System.Business_Layer;

import java.util.*;
import java.sql.*;
import Inventory_System.DAO_Layer.*;
import Inventory_System.Exceptions.ItemAbsentException;
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
    public String addToQuantity(int itemId, int userId, int QuantityToAdd) throws SQLException{ 
        try{
            String role = userDAO.getRole(userId);
                if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("staff")){
                
                    boolean isLow = isLowStock(itemId, userId);
                    if (isLow){
                        if (inventoryDAO.updateItembyQuantity(itemId, QuantityToAdd)){
                            return "Inventory Updated!";
                        }
                        else{
                            return "Couldn't update inventory!";
                        }
                    }
                    else{
                        return "Enough items are in inventory";
                    }
                }
                else{
                    return "Customer is not authorized to update inventory!";
                }
            }
        catch(SQLException e){
            e.printStackTrace();
            return "Error Occurred during updating inventory!";
        }
    }

    //get the user role, if user is admin then they are allowed to update price
    public String updatePricebyAdmin(int itemId, int userId, int newPrice) throws SQLException{
        try{
            String role = userDAO.getRole(userId);
            if (role.equalsIgnoreCase("Admin")){
                if(inventoryDAO.updatePricebyItemId(itemId, newPrice)){
                    return "Price Updated!";
                }
                else{
                    return "Couldnt update price!";
                }
            }
            else{
                return "Only admin can update price!";
            }
        }    
        catch(SQLException e){
            e.printStackTrace();
            return "Error occurred while updating price!";
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

    //to get the items by category
    public List<String> viewItemsByCategory(String category) throws SQLException{
        try{
            List<String> itemsBycategory = inventoryDAO.getitemByCategory(category);
            return itemsBycategory;
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    //Only admin and staff can insert items in Inventory table
    public String insertItems(int itemId, int userId, String itemName, String category, Double price, int quantity, int LowStockThreshold) throws SQLException{
        try{
            String role = userDAO.getRole(userId);
            if (role.equalsIgnoreCase("staff") || role.equalsIgnoreCase("Admin")){
               
                Inventory item = new Inventory(0, itemName, category, price, quantity, LowStockThreshold);
                if(inventoryDAO.addItem(item)){
                    return "Item Added to the Inventory";
                }
            }
            else{
                return "Customer cannot insert an item in Inventory!";
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return "Error while adding item to Inventory";
        }
        return "Items couldn't be added!";
    }

    
    //to deduct the Inventory after order placement or after updating inventory quantity
    public boolean deductInventory(int itemId, int newQuantity) throws SQLException {
        int oldQuantity = inventoryDAO.getQuantity(itemId);
        int UpdatedQuantity = oldQuantity - newQuantity;

        try{
            if(inventoryDAO.updateItembyQuantity(itemId, UpdatedQuantity)){
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
    public String deleteByAdmin(int itemId, int userId) throws SQLException{
        try{
            String role = userDAO.getRole(userId);
            if (role.equalsIgnoreCase("Admin")){
                if(inventoryDAO.deleteItem(itemId)){
                    return "Deleted the row Successfully";
                }
                else{
                    return "Row could not be deleted!";
                }
            }
            else{
                return "Only admin is authorized to delete inventory items!";
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return "Error occured when deleting inventory records";
        }
    }  
}

