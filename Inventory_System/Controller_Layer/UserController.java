package Inventory_System.Controller_Layer;

import java.io.*;
import java.lang.reflect.Type;
import java.security.spec.ECFieldF2m;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.Action;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import Inventory_System.Business_Layer.UserService;
import Inventory_System.DAO_Layer.DatabaseConnection;
import Inventory_System.DAO_Layer.UserDAO;
import Inventory_System.Exceptions.InvalidPasscodeException;
import Inventory_System.Exceptions.UserNotFoundException;
import Inventory_System.Model_Layer.User;

@WebServlet("/user")
public class UserController extends HttpServlet{
    
    private UserService userService;
    private UserDAO userDAO;
    private Connection conn;

    @Override
    public void init() throws ServletException {
        try{
            conn = DatabaseConnection.getConn();
            userDAO = new UserDAO(conn);
            this.userService = new UserService(userDAO);
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new ServletException("Failed to initialize UserController", e);
        }
    }

    //to handle the get request
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("application/json");
        Gson gson = new Gson();
        try{
            String action = request.getParameter("action");
            if ("viewUserInfo".equals(action)){
                int userId = Integer.parseInt(request.getParameter("userId"));
                List<User> users = userService.ReadAllUsers(userId);
                response.getWriter().write(gson.toJson(users));
            }
            else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Invalid action\"}");
            }
        }
        catch(SQLException | NullPointerException e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}");
        }
    }

    //to handle Post request
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            String action = request.getParameter("action");
            if ("RegisterUser".equals(action)){ // for user registration

                Gson gson = new Gson();
                response.setContentType("application/json");

                String userName = request.getParameter("userName");
                String email = request.getParameter("email");
                String passCode = request.getParameter("passCode");
                String roleUser = request.getParameter("roleUser");
                
                System.out.println("userName: " + userName);
                System.out.println("passCode: " + passCode);
                System.out.println("email: " + email);
                System.out.println("roleUser: " + roleUser);

                System.out.println("isValidUserName: " + userService.isValiduserName(userName));
                System.out.println("isValidPasscode: " + userService.isValidPasscode(passCode));


                if (userService.isValidPasscode(passCode) && userService.isValiduserName(userName)){
                    int userId = userService.registerUser(0, userName, email, passCode, roleUser);
                    System.out.println("Returned userId from DB: " + userId );
                    Map<String, Object> MyResMap = new HashMap<>();

                    if(userId > 0){
                       MyResMap.put("UserID", userId);
                       MyResMap.put("Message", "User Registered. you can login!");
                    }
                    else{
                        MyResMap.put("Message", "Could not register user!");
                    }

                    response.getWriter().write(gson.toJson(MyResMap));
                }
                else{
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\" : \"Something went wrong, try again later!\"}");
                }
            }
            else if ("isValidUser".equals(action)){ //for user login

                response.setContentType("text/plain");

                int userId = Integer.parseInt(request.getParameter("userId"));
                String userName = request.getParameter("userName");
                String roleUser = request.getParameter("roleUser");
                String passCode = request.getParameter("passCode");
                
                // Debug output
                System.out.println("userId: " + userId);
                System.out.println("userName: " + userName);
                System.out.println("roleUser: " + roleUser);
                System.out.println("passCode: " + passCode);

                boolean isUserValid = userService.isValidUserForLogin(userId, userName, passCode, roleUser);
                response.getWriter().write(isUserValid ? "Logged In, Welcome to Inventory!" : "Login Failed, Check your credentials!");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Something Went Wrong\"}");
        }
        catch(Exception e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Server error: " + e.getMessage());
        }
    }
    

    //to handle put request
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            response.setContentType("application/json");
            Gson gson = new Gson();
            StringBuilder str = new StringBuilder();
            try(BufferedReader reader = request.getReader()){
                String line;
                while ((line = reader.readLine()) != null){
                    str.append(line);
                }
            }

            Type type = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> data = gson.fromJson(str.toString(), type);

            String action = data.get("action");
            // Send JSON format like this : { "action": "updateQuantity", "itemId": 1, "quantity": 100 }
            if ("UpdatePassCode".equals(action)){
                int userId = Integer.parseInt(data.get("userId"));
                String nPassCode = data.get("nPasscode");
                String userName = data.get("userName");

                boolean isUpdated = userService.updatePasscodeIfuserNameExists(userId, nPassCode, userName);

                response.getWriter().write(isUpdated ? "PassCode updated Successfully!" : "Failed to update the passcode!");
            }
            else if ("UpdateEmail".equals(action)){
                int userId = Integer.parseInt(data.get("userId"));
                String nEmail = data.get("nEmail");
                String userName = data.get("userName");

                boolean isUpdated = userService.updateEmailifUserNameExits(userId, nEmail, userName);

                response.getWriter().write(isUpdated ? "Email Updated" : "Failed to update the email");
            }
            else{
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                response.getWriter().write("{\"error\" : \"Invalid method to update user information!\"}");
            }
        }
        catch(SQLException | UserNotFoundException | InvalidPasscodeException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}");
        }
    }

    //to handle the delete request
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            String action = request.getParameter("action");
            if("AdminDeletesUser".equals(action)){
                int AdminUserId = Integer.parseInt(request.getParameter("AdminUserId"));
                int UserId = Integer.parseInt(request.getParameter("UserId"));

                boolean isDeleted = userService.AdminDeletesUser(AdminUserId, UserId);

                response.getWriter().write(isDeleted ? "User was deleted successfully!" : "Failed to delete user");   
            }
            else if ("UserDeletesTheirAccount".equals(action)){
                int userId = Integer.parseInt(request.getParameter("userId"));
                String userName = request.getParameter("userName");

                boolean isDeleted = userService.UserDeletesTheirAccount(userId, userName);

                response.getWriter().write(isDeleted ? "Account deleted Successfully" : "Your account was not deleted, try again later!");
            }
            else{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\" : \"Not the correct method to delete user Account!\"}");
            }
        }
        catch(SQLException | UserNotFoundException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}");
        }
    }
}
