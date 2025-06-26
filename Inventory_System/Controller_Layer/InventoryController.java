package Inventory_System.Controller_Layer;

import java.io.*;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.*;
import javax.print.DocFlavor.READER;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import Inventory_System.Business_Layer.InventoryService;
import Inventory_System.DAO_Layer.DatabaseConnection;
import Inventory_System.DAO_Layer.InventoryDAO;
import Inventory_System.DAO_Layer.UserDAO;
import Inventory_System.Exceptions.ItemAbsentException;
import Inventory_System.Exceptions.UserNotFoundException;
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

            if ("viewAll".equals(action)){
                List<Inventory> items = inventoryService.viewAllItems();

                response.getWriter().write(gson.toJson(items));
            }

            else if ("viewByCategory".equals(action)){
                String category = request.getParameter("category");
                if(category == null){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\":\"category not provided\"}");
                    return;
                }

                List<Inventory> items = inventoryService.viewItemsByCategory(category);
                response.getWriter().write(gson.toJson(items));
            }

            else if ("getPricebyItemID".equals(action)){
                String itemName = request.getParameter("itemName");
                int itemId =  Integer.parseInt(request.getParameter("itemId"));

                Double price = inventoryService.getPricebyItemName(itemName, itemId);

                response.getWriter().write(gson.toJson(price));
            }

            else if ("getPriceByItemName".equals(action)){
                String itemName = request.getParameter("itemName");

                Double price = inventoryService.getTotalPricebyItemName(itemName);

                response.getWriter().write(gson.toJson(price));
            }

            else{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\" : \"Something went wrong\"}");
            }
        }
        catch(SQLException | NullPointerException | ItemAbsentException e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
            response.getWriter().write("{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}");
        }
    }

    // to handle post request
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            response.setContentType("text/plain");
            String action = request.getParameter("action");
            if ("AddItemToInventory".equals(action)){
                int userId = Integer.parseInt(request.getParameter("userId"));
                String itemName = request.getParameter("itemName");
                String category = request.getParameter("category");
                Double price = Double.parseDouble(request.getParameter("price"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                int LowStockThreshold = Integer.parseInt(request.getParameter("LowStockThreshold"));
            
                boolean isAdded = inventoryService.insertItems(0, userId, itemName, category, price, quantity, LowStockThreshold);
                response.getWriter().write(isAdded ? "Item Added succesfully" : "Failed to add an item!");
            }
        }
        catch(SQLException | UserNotFoundException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
            response.getWriter().write(e.getMessage());
        }
    }
    
    
    //to handle put request
    @Override
    @SuppressWarnings("unchecked")
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            if("updatePrice".equals(action)){
                int itemId = Integer.parseInt(data.get("itemId"));
                int userId = Integer.parseInt(data.get("userId"));
                double newPrice = Double.parseDouble(data.get("newPrice"));
                
                boolean isUpdated = inventoryService.updatePricebyAdmin(itemId, userId, newPrice);

                response.getWriter().write(isUpdated ? "Price Updated" : "Couldn't update price");
            }

            else if ("addtoQuantity".equals(action)){
                int itemId = Integer.parseInt(data.get("itemId"));
                int userId = Integer.parseInt(data.get("userId"));
                int newQuantity = Integer.parseInt(data.get("newQuantity"));

                boolean isUpdated = inventoryService.addToQuantity(itemId, userId, newQuantity);
                
                response.getWriter().write(isUpdated ? "Quantity Updated" : "Stock is sufficient, no update needed");
            }
            
            else{
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"Invalid method to update data\"}");
            }
        }
        catch(SQLException | UserNotFoundException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}");
        }
    }


    //to handle delete request
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            String action = request.getParameter("action");
            if ("deleteItem".equals(action)){
                int itemId = Integer.parseInt(request.getParameter("itemId"));
                int userId = Integer.parseInt(request.getParameter("userId"));

                boolean isDeleted = inventoryService.deleteByAdmin(itemId, userId);
                response.getWriter().write(isDeleted ? "Deleted Successfully" : "Could not be deleted!");
            }
            else{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Wrong method to delete information\"}");
            }
        }
        catch(SQLException | UserNotFoundException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
            response.getWriter().write(e.getMessage());
        }
    }
}