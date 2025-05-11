package Inventory_System.Controller_Layer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import Inventory_System.Business_Layer.InventoryService;
import Inventory_System.Business_Layer.OrderItemService;
import Inventory_System.Business_Layer.OrderService;
import Inventory_System.DAO_Layer.*;
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
    public init() throws ServletException {
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
            if (action.equals("getAllOrders")){
                int userId = Integer.parseInt(request.getParameter("userId"));
                Order orders = orderService.getallOrders(userId);

                response.getWriter().write(gson.toJson(orders));
            }
            else if (action.equals("viewByOrderId")){
                int userId = Integer.parseInt(request.getParameter("userId"));
                int orderId = Integer.parseInt(request.getParameter("orderId"));

                List<Order> orders = orderService.viewOrderByID(orderId, userId);

                response.getWriter().write(gson.toJson(orders));
            }
            else if (action.equals("viewByUserId")){
                int userId = Integer.parseInt(request.getParameter("userId"));

                List<Order> orders = orderService.viewOrderByUserId(userId);

                response.getWriter().write(gson.toJson(orders));
            }
            else if (action.equals("getPrice")){
                int userId = Integer.parseInt(request.getParameter("userId"));

                Double totalPrice = orderService.getPricebyUserId(userId);

                response.getWriter().write(gson.toJson(totalPrice));
            }
            else if (action.equals("viewItems")){
                int userId = Integer.parseInt(request.getParameter("userId"));
                List<OrderItem> itemsByUser = orderItemService.viewItems(userId);

                response.getWriter().write(gson.toJson(itemsByUser));
            }
            else if (action.equals("ItemsbyOrderId")){
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                int userId = Integer.parseInt(request.getParameter("userId"));

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
        }
    }

    //to handle the post request
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            String action = request.getParameter("action");
            if (action.equals("addOrder")){
                int userId = Integer.parseInt(request.getParameter("userId"));
                String orderDate = request.getParameter("orderDate");
                String customerName = request.getParameter("customerName");
                String orderStatus = request.getParameter("orderStatus");
                Double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));

                boolean isOrderAdded = orderService.addOrder(0, userId, orderDate, customerName, orderStatus, totalPrice);

                response.getWriter().write(isOrderAdded ? "Order Added, Now please enter the items you like to purchase!" : "Couldnt add order!");
            }

            else if (action.equals("addItems")){
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                String itemName = request.getParameter("itemName");
                int inventoryId = inventoryService.getItemIDfromTable(itemName);
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                Double subtotal = Double.parseDouble(request.getParameter("subtotal"));
                int userId = Integer.parseInt(request.getParameter("userId")); 

                boolean itemsAdded = orderItemService.addItems(0, orderId, inventoryId, quantity, subtotal, userId);

                response.getWriter().write(itemsAdded ? "Items added to order" : "Failed to add item to order!");
            }
        }
    }

}
