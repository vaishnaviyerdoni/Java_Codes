package Inventory_System.DAO_Layer;

import java.sql.*;

public class DatabaseConnection {
    private static String url = "jdbc:sqlserver://localhost;databaseName=InventoryDB;encrypt=false;integratedSecurity=true";

    public static Connection getConn() throws SQLException{
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found", e);
        }

        return DriverManager.getConnection(DatabaseConnection.url);
    }

}
