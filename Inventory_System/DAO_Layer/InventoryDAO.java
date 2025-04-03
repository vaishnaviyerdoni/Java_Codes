package Inventory_System.DAO_Layer;

import java.util.*;
import java.sql.*;
import Inventory_System.Model_Layer.Inventory;

public class InventoryDAO {
    private static final String cs = "jdbc:sqlserver://localhost;databaseName=InventoryDB;encrypt=false;integratedSecurity=true";
    public List<Inventory> getAllItems(){
        List<Inventory> items = new ArrayList<>();
        try{
            try(Connection conn  = DriverManager.getConnection(cs);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM InventoryTable")){
                while(res.next()){
                    Inventory item = new Inventory(
                        res.getInt("item_id"),
                        res.getString("item_name"),
                        res.getString("category"),
                        res.getDouble("price"),
                        res.getInt("quantity"),
                        res.getInt("LOW_STOCK_THRESHOLD"));

                    items.add(item);
                }

                return items;
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

            for(Inventory item : items){
                System.out.println("Id: " + item.get_itemId());
                System.out.println("Name: " + item.get_itemName());
                System.out.println("Category: " + item.get_category());
                System.out.println("Price: " + "$"+ item.get_price());
                System.out.println("Quantity: " + item.get_quantity());
                System.out.println("Low Stock Threshold: " + item.get_LowStock());
            }
        }
    }
