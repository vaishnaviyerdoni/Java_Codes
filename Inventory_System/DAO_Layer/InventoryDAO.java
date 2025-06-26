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
    public boolean addItem(Inventory item) throws SQLException{
        String sql = "INSERT INTO InventoryTable (itemName, category, price, quantity, LowStockThreshold) VALUES (?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, item.get_itemName());
                stmt.setString(2, item.get_category());
                stmt.setDouble(3, item.get_price());
                stmt.setInt(4, item.get_quantity());
                stmt.setInt(5, item.get_LowStock());
            
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    return true;
                } else {
                    return false;
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

    //to get the userId
    public int getItemId(String itemName) throws SQLException{
        String sql = "SELECT itemId FROM InventoryTable WHERE itemName = ?";
        int itemID = 0;
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, itemName);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    itemID = rs.getInt("itemId");
                }

                return itemID;
            }
        }
    }

    //to get Price from DB
    public double getPriceForItem(int itemId) throws SQLException {
        String sql = "SELECT price FROM InventoryTable WHERE itemId = ?";
        double itemPrice = 0d;
        try(PreparedStatement smt = conn.prepareStatement(sql)){
            smt.setInt(1, itemId);
            try(ResultSet res = smt.executeQuery()){
                if(res.next()){
                    itemPrice = res.getDouble("price");
                }

                return itemPrice;
            }
        }
    }

    //To get the price from DB by itemName
    public double getPriceByItemName(String itemName) throws SQLException {
        String sql = "SELECT price FROM InventoryTable WHERE itemName = ?";
        double itemPrice = 0d;
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, itemName);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    itemPrice = rs.getDouble("price");
                }

                return itemPrice;
            }
        }
    }

    //To get the quantity
    public int getQuantity(int itemId) throws SQLException{
        String sql = "SELECT quantity FROM InventoryTable WHERE itemId = ?";
        int quantity = -1;
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, itemId);
            try(ResultSet res = stmt.executeQuery()){
                if(res.next()){
                    quantity = res.getInt("quantity");
                }

                return quantity;
            }
        }
    }

    //to get an item by its category
    public List<Inventory> getitemByCategory(String category) throws SQLException{
        String sql = "SELECT * FROM InventoryTable WHERE category = ?";
        List<Inventory> items = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, category);
            try(ResultSet res = stmt.executeQuery()){
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
    }

    //To get the low stock threshold
    public int getLowStockThreshold(int itemId) throws SQLException{
        String sql = "SELECT LowStockThreshold FROM InventoryTable WHERE itemId = ?";
        int LowStockThreshold = -1;
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, itemId); 
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    LowStockThreshold = rs.getInt("LowStockThreshold");
                }

                return LowStockThreshold;
            }
        }
    }
    
    //UPDATE method: update the columns from inventory table wrt quantity
    public boolean updateItembyQuantity(int itemId, int newQuantity) throws SQLException{
        String sql  = "UPDATE InventoryTable SET quantity = quantity + ? WHERE itemId = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, itemId);
            int rows = stmt.executeUpdate();

            if (rows > 0){
                return true;
            }
            else{
                return false;
            }
        }
    }

    //UPDATE method: update quantity after order is placed, so item can deducted from inventory
    public boolean updateForDeduction(int itemId, int deductedQuantity) throws SQLException{
        String sql = "UPDATE InventoryTable SET quantity = ? WHERE itemId = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, deductedQuantity);
            stmt.setInt(2, itemId);

            int rows = stmt.executeUpdate();
            if(rows > 0){
                return true;
            }
            else{
                return false;
            }
        }
    }

    //UPDATE method: update price for given itemID
    public boolean updatePricebyItemId(int itemId, double newPrice) throws SQLException{
        String sql = "UPDATE InventoryTable SET price = ? WHERE itemId = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setDouble(1, newPrice);
            stmt.setInt(2, itemId);
            int rows = stmt.executeUpdate();

            if (rows > 0){
                return true;
            }
            else{
                return false;
            }
        }
    }

    //DELETE method : delete the record the table for the given id
    public boolean deleteItem(int itemId) throws SQLException{
        String sql = "DELETE FROM InventoryTable WHERE itemId = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, itemId);
            int rows = stmt.executeUpdate();
            if(rows > 0){
                return true;
            }
            else{
                return false;
            }
            
        }
    }
}
