package Inventory_System.Controller_Layer;

import java.io.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.Action;

import com.google.gson.Gson;

import Inventory_System.Business_Layer.UserService;
import Inventory_System.DAO_Layer.DatabaseConnection;
import Inventory_System.DAO_Layer.UserDAO;
import Inventory_System.Model_Layer.User;

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
        }
    }

    //to handle the get request
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("application/json");
        Gson gson = new Gson();
        try{
            int userId = Integer.parseInt(request.getParameter("userId"));
            List<User> users = userService.ReadAllUsers(userId);
            response.getWriter().write(gson.toJson(users));
        }
        catch(SQLException | NullPointerException e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\" : \"Something went wrong\"}");
        }
    }

    //to handle Post request
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            String userName = request.getParameter("userName");
            String email = request.getParameter("email");
            String passCode = request.getParameter("passCode");
            String roleUser = request.getParameter("roleUser");

            if (userService.isValidPasscode(passCode) && userService.isValiduserName(userName)){
                if(roleUser.equalsIgnoreCase("customer")){
                    String isRegistered = userService.registerUser(0, userName, email, passCode, roleUser);
                    response.getWriter().write(isRegistered.equals("User Registered!") ? "User Registration Successfully" : "User Registration Failed");
                }

                else if (roleUser.equalsIgnoreCase("staff")){
                    String isRegistered = userService.registerUser(0, userName, email, passCode, roleUser);
                    response.getWriter().write(isRegistered.equals("User Registered!") ? "User Registration Successfully" : "User Registration Failed");
                }
                
                else if (roleUser.equalsIgnoreCase("admin")){
                    String isRegistered = userService.registerUser(0, userName, email, passCode, roleUser);
                    response.getWriter().write(isRegistered.equals("User Registered!") ? "User Registration Successfully" : "User Registration Failed");
                }

                else{
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\" : \"Something went wrong, try again later!\"}");
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Something Went Wrong\"}");
        }
    }

    //to handle put request
    @Override

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        StringBuilder str = new StringBuilder();
        try(BufferedReader reader = request.getReader()){
            String line;
            while((line = reader.readLine()) != null){
                reader.append(line);
            }
        }

        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> data = gson.toJson(reader.toString(), type);

        String action = data.get("action");

        
    }
}
