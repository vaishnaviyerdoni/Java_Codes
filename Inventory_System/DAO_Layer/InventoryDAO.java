package Inventory_System.DAO_Layer;

import java.util.*;
import java.sql.*;
import Inventory_System.Model_Layer.Inventory;

public class InventoryDAO {
    private static final String cs = "jdbc:sqlserver://localhost;databaseName=InventoryDB;encrypt=false;integratedSecurity=true";
    public List<Inventory> getAllItems(){
        List<Inventory> items = new ArrayList<>();
        try(Connection conn  = DriverManager.getConnection(cs);
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM InventoryTable")){
            while(res.next()){
                Inventory item = new Inventory(
                    res.getInt("itemId"),
                    res.getString("itemName"),
                    res.getString("category"),
                    res.getDouble("price"),
                    res.getInt("quantity"),
                    res.getInt("LowStockThreshold"));
                    items.add(item);
                    //System.out.println("Fetched item: " + item.get_itemId());
            }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        return items;
    }
 
    public static void main(String[] args) {
            InventoryDAO obj = new InventoryDAO();
            List<Inventory> items = obj.getAllItems();

            for (int i = 0; i < items.size(); i++) {
                Inventory item = items.get(i);
                System.out.println("Item #" + (i + 1));
                System.out.println("Id: " + item.get_itemId());
                System.out.println("Name: " + item.get_itemName());
                System.out.println("Category: " + item.get_category());
                System.out.println("Price: $" + item.get_price());
                System.out.println("Quantity: " + item.get_quantity());
                System.out.println("Low Stock Threshold: " + item.get_LowStock());
                System.out.println("-----");
            }
            
            
        }
    }
