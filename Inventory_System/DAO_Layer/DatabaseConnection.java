package Inventory_System.DAO_Layer;

import java.sql.*;

public class DatabaseConnection {
    private static String url = "jdbc:sqlserver://localhost;databaseName=InventoryDB;encrypt=false;integratedSecurity=true";

    public static Connection getConn() throws SQLException{
        return DriverManager.getConnection(DatabaseConnection.url);
    }

}