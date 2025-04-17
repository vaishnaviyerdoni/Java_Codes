package Inventory_System.test;

import java.sql.Connection;
import java.sql.SQLException;

import Inventory_System.DAO_Layer.DatabaseConnection;
import Inventory_System.DAO_Layer.InventoryDAO;

public class InventoryDAOTest {
    public static void main(String[] args) {

        //to get the sql server Connection
        Connection conn = null;
        try{
            conn = DatabaseConnection.getConn();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        InventoryDAO items = new InventoryDAO(conn);

        
        //testing the create method
        Inventory item = new Inventory(0,"Pen", "Stationary", 20.00, 1000, 100);
        try{
            items.addItem(item);
        }catch(SQLException e){
            e.printStackTrace();
        }
        

        
        //to test the read method
        try{  
            List<Inventory> item = items.getAllItems();

            for (int i = 0; i < item.size(); i++){
                Inventory myitem = item.get(i);
                System.out.println("Item Id = " + myitem.get_itemId());
                System.out.println("item Name = " + myitem.get_itemName());
                System.out.println("category = " + myitem.get_category());
                System.out.println("price = " + myitem.get_price());
                System.out.println("quantity = " + myitem.get_price());
                System.out.println("Low Stock Threshold = " + myitem.get_LowStock());
                System.out.println("-----------------");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        
        
        // test the update method
        try{
            items.updateItembyQuantity(8, 500);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        
        
        //to test the Delete method 
        try{
            items.deleteItem(8);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    
    }
}
