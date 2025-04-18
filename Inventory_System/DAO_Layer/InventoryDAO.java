package Inventory_System.DAO_Layer;

import java.util.*;
import java.sql.*;
import Inventory_System.Model_Layer.Inventory;

public class InventoryDAO {
    private Connection conn;

    //Constructor to accept database connection
    public InventoryDAO(Connection conn){
        this.conn = conn;
    }

    //CREATE method: Add items to the columns in the inventory table
    public void addItem(Inventory item) throws SQLException{
        String sql = "INSERT INTO InventoryTable (itemName, category, price, quantity, LowStockThreshold) VALUES (?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, item.get_itemName());
                stmt.setString(2, item.get_category());
                stmt.setDouble(3, item.get_price());
                stmt.setInt(4, item.get_quantity());
                stmt.setInt(5, item.get_LowStock());
            
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Inserted successfully!");
                } else {
                    System.out.println("Insert failed.");
                }
            }
        }

        
    //READ method: getting all the items from inventory table
    public List<Inventory> getAllItems() throws SQLException{
        List<Inventory> items = new ArrayList<>();

        String sql = "SELECT * FROM InventoryTable";
        try(PreparedStatement stmt = conn.prepareStatement(sql);
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
            return items;
        }
    }

    public List<List<Integer>> getLowStockThreshold() throws SQLException{
        String sql = "SELECT ItemId, LowStockThreshold FROM InventoryTable";
        List<List<Integer>> items = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    List<Integer> row = new ArrayList<>();

                    row.add(rs.getInt("itemId"));
                    row.add(rs.getInt("LowStockThreshold"));

                    items.add(row);
                }

                return items;
            }
        }
    }
    
    //UPDATE method: update the columns from inventory table wrt quantity
    public void updateItembyQuantity(int itemId, int newQuantity) throws SQLException{
        String sql  = "UPDATE InventoryTable SET quantity = ? WHERE itemId = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, itemId);
            stmt.executeUpdate();

            System.out.println("Updated Successfully!");
        }
    }

    //DELETE method : delete the record the table for the given id
    public void deleteItem(int itemId) throws SQLException{
        String sql = "DELETE FROM InventoryTable WHERE itemId = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, itemId);
            stmt.executeUpdate();
            System.out.println("Deleted Successfully");
        }
    }
}
