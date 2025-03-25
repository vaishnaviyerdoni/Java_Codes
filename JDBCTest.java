import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCTest {
    public static void main(String args[]){
        String CS = "jdbc:sqlserver://localhost;databaseName=InventoryDB;encrypt=false;integratedSecurity=true";
        try{
            try(Connection connection = DriverManager.getConnection(CS)){
                if (connection != null){
                System.out.println("Connection Established Successfully!");
                }
            }
        }catch (SQLException e){
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
    }
}
