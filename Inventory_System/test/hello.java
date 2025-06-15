package Inventory_System;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import Inventory_System.Business_Layer.UserService;
import Inventory_System.DAO_Layer.DatabaseConnection;
import Inventory_System.DAO_Layer.UserDAO;

public class hello {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConn();
            UserDAO userDAO = new UserDAO(conn);
            UserService userService = new UserService(userDAO);

            Scanner scan = new Scanner(System.in);

            System.out.print("Enter userId: ");
            int userId = scan.nextInt();
            scan.nextLine(); // consume newline

            System.out.print("Enter username: ");
            String userName = scan.nextLine();

            System.out.print("Enter passcode: ");
            String passCode = scan.nextLine();

            System.out.print("Enter role: ");
            String roleUser = scan.nextLine();

            // Debug output
            System.out.println("userId: " + userId);
            System.out.println("userName: " + userName);
            System.out.println("roleUser: " + roleUser);
            System.out.println("passCode: " + passCode);

            boolean isUserValid = userService.isValidUserForLogin(userId, userName, passCode, roleUser);

            if (isUserValid) {
                System.out.println("✅ Logged IN");
            } else {
                System.out.println("❌ Login Failed");
            }

            scan.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
