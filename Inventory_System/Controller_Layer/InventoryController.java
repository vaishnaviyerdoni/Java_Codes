package Inventory_System.Controller_Layer;

import java.io.*;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.*; 
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.Gson;
import Inventory_System.Business_Layer.InventoryService;
import Inventory_System.DAO_Layer.DatabaseConnection;
import Inventory_System.DAO_Layer.InventoryDAO;
import Inventory_System.DAO_Layer.UserDAO;
import Inventory_System.DAO_Layer.OrderItemDAO;
import Inventory_System.Model_Layer.Inventory;

@WebServlet("/inventory")
public class InventoryController extends HttpServlet{
    private InventoryService inventoryService;
    private InventoryDAO inventoryDAO;
    private UserDAO userDAO;
    private OrderItemDAO orderItemDAO;
    private Connection conn;

    @Override
    public void init() throws ServletException {

        try{
            conn = DatabaseConnection.getConn();
            inventoryDAO = new InventoryDAO(conn);
            userDAO = new UserDAO(conn);
            orderItemDAO = new OrderItemDAO(conn);
            this.inventoryService = new InventoryService(inventoryDAO, userDAO, orderItemDAO);
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new ServletException("Could not start controller because of Database Error!");
        }
    }

    //to handle get request
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        Gson gson = new Gson();

        try{
            String action = request.getParameter("action");

            if (action == null || action.equals("viewAll")){
                List<Inventory> items = inventoryService.viewAllItems();

                response.getWriter().write(gson.toJson(items));
            }

            else if (action.equals("viewByCategory")){
                String category = request.getParameter("category");
                if(category == null){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\":\"category not provided\"}");
                    return;
                }

                List<Inventory> items = inventoryService.viewItemsByCategory(category);
                response.getWriter().write(gson.toJson(items));
            }
        }
        catch(SQLException | NullPointerException e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
            response.getWriter().write("{\"error\":\"Something Went Wrong\"}");
        }
    }

    // to handle post request
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            int userId = Integer.parseInt(request.getParameter("userId"));
            String itemName = request.getParameter("itemName");
            String category = request.getParameter("category");
            Double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int LowStockThreshold = Integer.parseInt(request.getParameter("LowStockThreshold"));
            
            boolean isAdded = inventoryService.insertItems(0, userId, itemName, category, price, quantity, LowStockThreshold);
            response.getWriter().write(isAdded ? "Item Added succesfully" : "Failed to add an item!");
        }
        catch(SQLException e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input");
        }
    }


    /* 
    //to handle put request
    @Override
    protected void doPut(HttpServletRequest request, HttpResponse response) throws IOException, ServletException {

    }

    //to handle delete request
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
    }
    */
}