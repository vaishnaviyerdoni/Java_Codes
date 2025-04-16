package Inventory_System.DAO_Layer;

import Inventory_System.DAO_Layer.*;
import Inventory_System.Model_Layer.*;
import java.util.*;
import java.sql.*;


public class OrderItemDAO {
    //constructor for database connection
    private Connection conn;

    public OrderItemDAO(Connection conn){
        this.conn = conn;
    }

    //Create method for adding the order items 
    public void addOrderItem(OrderItem items) throws SQLException{
        String sql = "INSERT INTO OrderItems (OrderId, inventoryId, quantity, Subtotal, userid) VALUES (?,?,?,?,?)";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, items.get_orderid().get_orderId());
            stmt.setInt(2, items.get_inventoryId().get_itemId());
            stmt.setInt(3, items.getOrder_quantity());
            stmt.setDouble(4, items.get_subtotal());
            stmt.setInt(5, items.get_userid().get_userId());

            int rows = stmt.executeUpdate();
            if (rows > 0){
                System.out.println("Items are placed!");
            }
            else{
                System.out.println("Items are not placed!");
            }
        }
    }

    //Read method to fetch all the Ordered Item details
    public List<OrderItem> fetchAll() throws SQLException{
        String sql = "SELECT * FROM OrderItems";
        List<OrderItem> items = new ArrayList<>();

        try(PreparedStatement stmt  = conn.prepareStatement(sql)){
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //to fetch the foreign keys
                    int orderid = rs.getInt("orderId");
                    Order order = new Order();
                    order.set_OrderId(orderid);

                    int inventoryid = rs.getInt("inventoryId");
                    Inventory inventory = new Inventory();
                    inventory.set_itemId(inventoryid);

                    int userid = rs.getInt("userId");
                    User user = new User();
                    user.set_userId(userid);

                    //to fetch other Orders Items
                    OrderItem item = new OrderItem(
                        rs.getInt("itemsId"),
                        order,
                        inventory,
                        rs.getInt("quantity"),
                        rs.getDouble("Subtotal"),
                        user);

                    items.add(item);
                }
                return items;
            }
        }
    }
    
    //Update method for updating the quantity and hence subtotal
    public void UpdateQuantityAndTotal(int quantity, double Newsubtotal) throws SQLException{

    }

    //Delete the record for given items number
    public void deleteOrderItem(int itemsId) throws SQLException{
        String sql = "DELETE FROM OrderItems WHERE ItemsID = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, itemsId);
            stmt.executeUpdate();

            System.out.println("Deleted Successfully!");
        }
    }

    public static void main(String[] args){

        Connection conn = null;
        try{
            conn = DatabaseConnection.getConn();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        OrderItemDAO items = new OrderItemDAO(conn);

        /* 
        Inventory inventory = new Inventory();
        inventory.set_itemId(5);
        User user = new User();
        user.set_userId(14);
        Order order = new Order();
        order.set_OrderId(6);
        OrderItem item = new OrderItem(0, order, inventory, 1, 14000, user);
        try{
            items.addOrderItem(item);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        */
        
        /* 
        //to test read function
        try{
            List<OrderItem> item = items.fetchAll();
            for(int i = 0; i < item.size(); i++){
                OrderItem myItem = item.get(i);
                System.out.println("Items id: " + myItem.get_itemsId());
                System.out.println("Order id: " + myItem.get_orderid().get_orderId());
                System.out.println("Inventory item id: "+ myItem.get_inventoryId().get_itemId());
                System.out.println("Quantity: " + myItem.getOrder_quantity());
                System.out.println("Subtotal: " + myItem.get_subtotal());
                System.out.println("User id: " + myItem.get_userid().get_userId());
                System.out.println("--------------");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        */

        /* 
        //to test the delete Method
        try{
            items.deleteOrderItem(8);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        */ 
    }
}
