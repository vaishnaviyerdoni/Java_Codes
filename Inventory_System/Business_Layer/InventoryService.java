package Inventory_System.Business_Layer;

import java.util.*;
import java.sql.*;
import Inventory_System.DAO_Layer.*;
import Inventory_System.Model_Layer.Inventory;
import Inventory_System.Model_Layer.User;

public class InventoryService{
    private InventoryDAO inventoryDAO;
    private UserDAO userDAO;

    //constructor
    public InventoryService(InventoryDAO inventoryDAO, UserDAO userDAO){
        this.inventoryDAO = inventoryDAO;
        this.userDAO = userDAO;
    }

    //alert message for Low Quantity
    public String AlertLowQuantity(int itemId) throws SQLException{
        try{
            int quantity = inventoryDAO.getQuantity(itemId);
            int LowStockThreshold = inventoryDAO.getLowStockThreshold(itemId);

            if(quantity <= LowStockThreshold){
                return "Low Stock!";
            }
            else{
                return "Enough Supply for " + itemId + " available!";
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return "Error occured while checking the stock!";
        }
    }

    //method to compare the quantity and lowstock threshold and update the quantity if necessary
    //rule1: quantity cannot be lesser than or equal to low stock threshold
    public void CompareQnt(int itemId) throws SQLException{
        try{
            int quantity = inventoryDAO.getQuantity(itemId);
            int LowStockThreshold = inventoryDAO.getLowStockThreshold(itemId);

            if(quantity <= LowStockThreshold){
                try{
                    inventoryDAO.updateItembyQuantity(itemId, 10);
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Enough items present in the Inventory!");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //get the user role, if user is admin then they are allowed to update price
    public void updatePrice(int itemId, int userId, int newPrice){
    
        try{
            String role = userDAO.getRole(userId);
            if (role.equalsIgnoreCase("admin")){
                try{
                    inventoryDAO.updatePricebyItemId(itemId, newPrice);
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    //to view all the items from inventory table //work on return value
    public List<Inventory> viewAllItems() throws SQLException, NullPointerException{
        try{
            List<Inventory> items = inventoryDAO.getAllItems();

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

            return items;
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    //to get the items by category
    public void viewItemsByCategory(String category) throws SQLException{
        try{
            List<String> itemsBycategory = new ArrayList<>();

            itemsBycategory = inventoryDAO.getitemByCategory(category);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //Only admin and staff can insert items in Inventory table
    public void insertItems(int itemId, int userId, String itemName, String category, Double price, int quantity, int LowStockThreshold) throws SQLException{
        try{
            String role = userDAO.getRole(userId);
            if (role.equalsIgnoreCase("staff") || role.equalsIgnoreCase("Admin")){
                try{
                    Inventory item = new Inventory(0, itemName, category, price, quantity, LowStockThreshold);
                    inventoryDAO.addItem(item);
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
            else{
                System.out.print("Customer cannot insert an item in Inventory!");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    /* 
    //to deduct the Inventory after order placement or after updating inventory quantity
    public boolean deductInventory(int itemId, int newQuantity) throws SQLException {
        int oldQuantity = inventoryDAO.getQuantity(itemId);
        int UpdateQuantity = oldQuantity - newQuantity;

        if()
    }
    */

    //Only admin can delete items from inventory table
    public void deleteByAdmin(int itemId, int userId) throws SQLException{
        try{
            String role = userDAO.getRole(userId);
            if (role.equalsIgnoreCase("Admin")){
                inventoryDAO.deleteItem(itemId);
            }
            else{
                System.out.print("Customer or Staff do not have authority to delete items from Inventory!");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
        
}

