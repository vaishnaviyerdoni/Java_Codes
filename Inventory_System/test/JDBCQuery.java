package Inventory_System.test;

import java.sql.*;
public class JDBCQuery{
    public static void main(String[] args){
        String cs = "jdbc:sqlserver://localhost;databaseName=InventoryDB;encrypt=false;integratedSecurity=true";

        try(Connection conn = DriverManager.getConnection(cs);
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery("SELECT @@VERSION")){
            System.out.println("Connected Succesfully!");
            while (res.next()){
                System.out.println("Version for sql is: " + res.getString(1));
            }
        }catch(SQLException e){
                System.out.println("Connection Failed!");
                e.printStackTrace();
        }
    }
}
