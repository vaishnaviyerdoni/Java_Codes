import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class test {
    public static void main(String[] args) {
        // Connection string for SQL Server with Windows Authentication
        String connectionString = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=Tables1;encrypt=false;trustServerCertificate=true";

        // Windows Authentication: no username and password required
        // Make sure sqljdbc_auth.dll is added to your PATH environment variable
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            if (connection != null) {
                System.out.println("Connection Established Successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
    }
}
