package Inventory_System.DAO_Layer;

import java.util.*;
import java.math.BigDecimal;
import java.sql.*;
import Inventory_System.Model_Layer.Inventory;

public class InventoryDAO {
    private static final String cs = "jdbc:sqlserver://localhost;databaseName=InventoryDB;encrypt=false;integratedSecurity=true";

    //CREATE method: Add items to the columns in the inventory table
    public void addItem(Inventory item){
        String sqlQuery = "INSERT INTO InventoryTable (itemName, category, price, quantity, LowStockThreshold) VALUES (?,?,?,?,?)";
        try(Connection conn =  DriverManager.getConnection(cs);
        PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
                stmt.setString(1, item.get_itemName());
                stmt.setString(2, item.get_category());
                stmt.setDouble(3, item.get_price());
                stmt.setInt(4, item.get_quantity());
                stmt.setInt(5, item.get_LowStock());
                //stmt.executeUpdate();
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Inserted successfully!");
                } else {
                    System.out.println("Insert failed.");
                }

        }catch (SQLException e){
            e.printStackTrace();
        }
        
    }

    //READ method: getting all the items from inventory table
    public List<Inventory> getAllItems(){
        List<Inventory> items = new ArrayList<>();
        String sqlquery = "SELECT * FROM InventoryTable";
        try(Connection conn  = DriverManager.getConnection(cs);
        PreparedStatement stmt = conn.prepareStatement(sqlquery);
        ResultSet res = stmt.executeQuery()){
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

    //UPDATE method: update the columns from inventory table wrt quantity
    
    //DELETE method : delete the record the table for the given id
 
    public static void main(String[] args) {
        InventoryDAO items = new InventoryDAO();
        List<Inventory> item = items.getAllItems();

        System.out.println(item);
    }
}
