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
    public void UpdateQuantity(int Newquantity, int id) throws SQLException{
        String sql = "UPDATE OrderItems SET quantity = ? WHERE ItemsId = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, Newquantity);
            stmt.setInt(2, id);
            stmt.executeUpdate();

            System.out.println("Updated the quantity Successfully");
        }
    }

    
    //Update method for updating the subtotal because if quantity changes, subtotal changes
    public void updateSubtotal(int itemId, int itemsId) throws SQLException{
        String sql1 = "SELECT price FROM InventoryTable WHERE itemId =?";
        String sql2 = "SELECT quantity FROM OrderItems WHERE itemsId = ?";
        String sql3 = "UPDATE OrderItems SET Subtotal = ? WHERE itemsId = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql1)){
            stmt.setInt(1, itemId);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    int price = rs.getInt("price");

                    try(PreparedStatement st = conn.prepareStatement(sql2)){
                        st.setInt(1, itemsId);
                        try(ResultSet res = st.executeQuery()){
                            if(res.next()){
                                int quantity = res.getInt("quantity");

                                try(PreparedStatement stm = conn.prepareStatement(sql3)){
                                    Double subtotal = (double)(price * quantity);

                                    stm.setDouble(1, subtotal);
                                    stm.setInt(2, itemsId);

                                    stm.executeUpdate();

                                    System.out.println("Updated the Subtotal Successfully!");
                                }
                            }
                            else{
                                System.out.println("Quantity not found!");
                            }
                        }
                    }
                }
                else{
                    System.out.println("Price not found!");
                }                
            }
        }
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
}
