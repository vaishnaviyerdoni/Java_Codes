package Inventory_System.Controller_Layer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import Inventory_System.Business_Layer.*;
import Inventory_System.DAO_Layer.*;
import Inventory_System.Exceptions.ItemAbsentException;
import Inventory_System.Exceptions.UserNotFoundException;
import Inventory_System.Model_Layer.*;

@WebServlet("/order")
public class OrderController extends HttpServlet {
    
    private OrderDAO orderDAO;
    private UserDAO userDAO;
    private InventoryDAO inventoryDAO;
    private OrderItemDAO orderItemDAO;
    private OrderService orderService;
    private OrderItemService orderItemService;
    private InventoryService inventoryService;
    private Connection conn;

    @Override
    public void init() throws ServletException {
        try{
            conn = DatabaseConnection.getConn();
            inventoryDAO = new InventoryDAO(conn);
            orderDAO = new OrderDAO(conn);
            userDAO = new UserDAO(conn);
            orderItemDAO = new OrderItemDAO(conn);

            inventoryService = new InventoryService(inventoryDAO, userDAO, orderItemDAO);
            this.orderService = new OrderService(orderDAO, userDAO, inventoryDAO);
            this.orderItemService = new OrderItemService(orderItemDAO, inventoryDAO, orderDAO, userDAO, inventoryService);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        try{
            String action = request.getParameter("action");
            if ("getAllOrders".equals(action)){

                String userIDStr = request.getParameter("userId");
                if(userIDStr == null || userIDStr.trim().isEmpty()){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\": \"Missing userId\"}");
                    return;
                }
                int userId = Integer.parseInt(userIDStr);
                List<Order> orders = orderService.getallOrders(userId);

                response.getWriter().write(gson.toJson(orders));
            }
            else if ("viewByOrderId".equals(action)){
                String userIdStr = request.getParameter("userId");
                String orderIdStr = request.getParameter("orderId");

                if (orderIdStr == null || orderIdStr.trim().isEmpty() ||userIdStr == null || userIdStr.trim().isEmpty()){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\": \"Missing userId or orderId\"}");
                    return;
                }

                int userId = Integer.parseInt(userIdStr);
                int orderId = Integer.parseInt(orderIdStr); 

                List<Order> orders = orderService.viewOrderByID(orderId, userId);
                response.getWriter().write(gson.toJson(orders));
            }
            else if ("viewByUserId".equals(action)){
                String userIdStr = request.getParameter("userId");

                if (userIdStr == null || userIdStr.trim().isEmpty()){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\": \"Missing userId\"}");
                    return;
                }
                
                int userId = Integer.parseInt(userIdStr);

                List<Order> orders = orderService.viewOrderByUserId(userId);
                response.getWriter().write(gson.toJson(orders));
            }
            else if ("getPrice".equals(action)){
                String orderIDStr = request.getParameter("orderId");

                if(orderIDStr == null || orderIDStr.trim().isEmpty()){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\": \"Missing orderId\"}");
                    return;
                }
                int orderId = Integer.parseInt(orderIDStr);

                Double totalPrice = orderService.getPricebyOrderId(orderId);
                response.getWriter().write(gson.toJson(totalPrice));
            }
            else if ("viewItems".equals(action)){
                String userIdStr = request.getParameter("userId");

                if (userIdStr == null || userIdStr.trim().isEmpty()){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\": \"Missing userId\"}");
                    return;
                }

                int userId = Integer.parseInt(userIdStr);
                List<OrderItem> itemsByUser = orderItemService.viewItems(userId);

                response.getWriter().write(gson.toJson(itemsByUser));
            }
            else if ("ItemsbyOrderId".equals(action)){
                String orderIdStr = request.getParameter("orderId");
                String userIdStr = request.getParameter("userId");

                if (orderIdStr == null || orderIdStr.trim().isEmpty() || userIdStr == null || userIdStr.trim().isEmpty()){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\": \"Missing userId or orderId\"}");
                    return;
                }

                int userId = Integer.parseInt(userIdStr);
                int orderId = Integer.parseInt(orderIdStr); 

                List<OrderItem> itemsInOrder = orderItemService.getItemsbyOrderid(userId, orderId);
                response.getWriter().write(gson.toJson(itemsInOrder));
            }
            else{

                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                response.getWriter().write("{\"error\" : \"the spectifed method is not correct\"}");
            }
        }
        catch(SQLException | NullPointerException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}");
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid userId or orderId format\"}");
        } 
        catch (UserNotFoundException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}");
        } 
        catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Something went wrong on the server\"}");
        }
    }

    //to handle the post request
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            response.setContentType("application/json");
            Gson gson = new Gson();

            String action = request.getParameter("action");
            if ("addOrder".equals(action)){
                int userId = Integer.parseInt(request.getParameter("userId"));
                String orderDate = request.getParameter("orderDate");
                String customerName = request.getParameter("customerName");
                //String orderStatus = request.getParameter("orderStatus"); default=pending
                Double totalPrice = 0.0d; //we will update it after adding items


                int orderId = orderService.addOrder(0, userId, orderDate, customerName, "pending", totalPrice);
                System.out.println("Returned Order ID from DB: " + orderId);
                Map<String, Object> myResponseMap = new HashMap<>();
                if (orderId > 0){
                    myResponseMap.put("OrderID", orderId);
                    myResponseMap.put("Message", "Order Added, Now please enter the items you like to purchase!");
                }
                else{
                    myResponseMap.put("Message","Could not add Order, try again later!");
                }

                response.getWriter().write(gson.toJson(myResponseMap));
            }

            else if ("addItems".equals(action)){
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                String itemName = request.getParameter("itemName");
                int inventoryId = inventoryService.getItemIDfromTable(itemName);
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                Double subtotal = Double.parseDouble(request.getParameter("subtotal"));
                int userId = Integer.parseInt(request.getParameter("userId")); 

                boolean itemsAdded = orderItemService.addItems(0, orderId, inventoryId, quantity, subtotal, userId);

                response.getWriter().write(itemsAdded ? "Order Placed!" : "Failed to add items to order, hence could not place order!");
            }
            else{
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                response.getWriter().write("{\"error\" : \"not a correct method to place order!\"}");
            }
        }
        catch(SQLException | ItemAbsentException | UserNotFoundException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            response.getWriter().write("{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}");
        }
    }

    //to handle the put request
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            response.setContentType("application/json");
            Gson gson = new Gson();
            StringBuilder str = new StringBuilder();
            try(BufferedReader reader = request.getReader()){
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            }

            Type type = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> data = gson.fromJson(str.toString(), type);
            String action = data.get("action");

            // Send JSON format like this : { "action": "updateQuantity", "itemId": 1, "quantity": 100 }
            if ("updateTotalPrice".equals(action)){
                Double price = Double.parseDouble(data.get("price"));
                int orderId = Integer.parseInt(data.get("orderId"));
                int userId = Integer.parseInt(data.get("userId"));

                boolean isUpdated = orderService.updateTotalPrice(price, orderId, userId);

                response.getWriter().write(isUpdated ? "total price updated" : "Failed to update the total price");
            }
            else if ("changeStatus".equals(action)){
                String status = data.get("status");
                int userId = Integer.parseInt(data.get("userId"));
                int customerId = Integer.parseInt(data.get("customerId"));

                boolean isUpdated = orderService.changeStatus(userId, status, customerId);

                response.getWriter().write(isUpdated ? "Status updated!" : "Failed to update status");
            }
            else if ("updateQuantity".equals(action)){
                int itemsId = Integer.parseInt(data.get("itemsId"));
                int userId = Integer.parseInt(data.get("userId"));
                int nQuantity = Integer.parseInt(data.get("nQuantity"));
                int inventoryId = Integer.parseInt(data.get("inventoryId"));

                boolean isUpdated = orderItemService.updateQuantityAndSubtotal(itemsId, inventoryId, userId, nQuantity);

                response.getWriter().write(isUpdated ? "quantity and subtotal updated!" : "Failed to update the quantity and subtotal!");
            }
            else{
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                response.getWriter().write("{\"error\" : \"Incoorect method to update the information\"}");
            }
        }
        catch(SQLException | UserNotFoundException | ItemAbsentException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            response.getWriter().write("{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}");
        }
    }
     
    //to handle the delete request
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            String action = request.getParameter("action");
            if ("AdminDeletesOrder".equals(action)){
                int userId = Integer.parseInt(request.getParameter("userId"));
                int orderId = Integer.parseInt(request.getParameter("orderId"));

                boolean isDeleted = orderService.AdminDeletesOrder(userId, orderId);

                response.getWriter().write(isDeleted ? "order Deleted" : "Failed to delete order");
            }
            else if ("AdminDeletesItem".equals(action)){
                int userId = Integer.parseInt(request.getParameter("userId"));
                int itemsId = Integer.parseInt(request.getParameter("itemsId"));

                boolean isDeleted = orderItemService.adminDeletesItem(userId, itemsId);

                response.getWriter().write(isDeleted ? "Item Deleted" : "Failed to delete order");
            }
            else{
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                response.getWriter().write("{\"error\" : \"incorrect method to delete information\"}");
            }
        }
        catch(SQLException | UserNotFoundException e){
            e.printStackTrace();
            System.err.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            response.getWriter().write("{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}");
        }
    }
}
