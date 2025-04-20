package Inventory_System.Business_Layer;
import java.util.*;
import java.sql.*;
import Inventory_System.DAO_Layer.*;

public class InventorySevice {
    public static void main(String[] args) {
        Connection conn = null;
        try{
            conn = DatabaseConnection.getConn();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        InventoryDAO dao = new InventoryDAO(conn);
        try{
            List<List<Integer>> idAndLowStock = dao.getLowStockThreshold();
            System.out.println(idAndLowStock);
            for(int i = 0; i < idAndLowStock.size(); i++){
                int itemId = idAndLowStock.get(i).get(0);
                int LowStockThreshold = idAndLowStock.get(i).get(1);
                //System.out.println(LowStockThreshold);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
