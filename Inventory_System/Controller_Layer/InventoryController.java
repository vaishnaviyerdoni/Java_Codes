package Inventory_System.Controller_Layer;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.Gson;
import Inventory_System.Business_Layer.InventoryService;
import Inventory_System.Model_Layer.Inventory;

@WebServlet("/inventory")
public class InventoryController extends HttpServlet{
    private InventoryService inventoryService;

    @Override
    public void init() throws ServletException {
        this.inventoryService = new InventoryService(null, null, null);
    }

    //to handle get request
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            List<Inventory> items = inventoryService.viewAllItems();

            //converting to json
            String json = new Gson().toJson(items);
            response.setContentType("application/json");
            response.getWriter().write(json);
        }
        catch(SQLException | NullPointerException e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Something went wrong\"}");
        }
    }
}
