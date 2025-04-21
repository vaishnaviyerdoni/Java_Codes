package Inventory_System.Business_Layer;
import java.util.*;
import java.sql.*;
import Inventory_System.DAO_Layer.*;

public class InventoryService{
    private InventoryDAO inventoryDAO;

    //constructor
    public InventoryService(InventoryDAO inventoryDAO){
        this.inventoryDAO = inventoryDAO;
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

    //
}

