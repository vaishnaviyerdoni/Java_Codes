package Inventory_System.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import Inventory_System.DAO_Layer.DatabaseConnection;
import Inventory_System.DAO_Layer.OrderItemDAO;
import Inventory_System.Model_Layer.Inventory;
import Inventory_System.Model_Layer.Order;
import Inventory_System.Model_Layer.OrderItem;
import Inventory_System.Model_Layer.User;

public class OrderItemDAOTest {
    public static void main(String[] args){

        Connection conn = null;
        try{
            conn = DatabaseConnection.getConn();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        OrderItemDAO items = new OrderItemDAO(conn);

        
        //to test create method
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
        

        
        //to test the update quantity method
        try{
            items.UpdateQuantity(3, 10);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        

        //to test the update subtotal method
        try{
            items.updateSubtotal(5, 10);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        
        //to test the delete Method
        try{
            items.deleteOrderItem(8);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
    }
}
