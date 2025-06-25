package Inventory_System.DAO_Layer;

import java.sql.*;
import java.util.*;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.text.SimpleDateFormat;
import Inventory_System.Model_Layer.Order;
import Inventory_System.Model_Layer.User;

public class OrderDAO {
    private Connection conn;
    
    //Constructor to accept the database connection.
    public OrderDAO(Connection conn){
        this.conn = conn;
    }

    //Create method to place new orders, before placing order, the user must register themselves
    public int addOrderInDatabase(Order order) throws SQLException{
        String sql = "INSERT INTO Orders (userId, OrderDate, CustomerName, OrderStatus, total_Price) VALUES  (?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, order.get_UserId().get_userId());
            stmt.setDate(2, new java.sql.Date((order.get_Orderdate().getTime())));
            stmt.setString(3, order.get_customerName());
            stmt.setString(4, order.get_status());
            stmt.setDouble(5, order.get_total());
            
            int rows = stmt.executeUpdate();
            if(rows > 0){
                try(ResultSet rs = stmt.getGeneratedKeys()){
                    if(rs.next()){
                        return rs.getInt(1); //this is auto generated order Id
                    }
                }
            }
        }
        return -1;
    }
    
    //to fetch all the orders from the database
    public List<Order> fetchAllOrders() throws SQLException{
        String sql = "SELECT * FROM Orders";
        List<Order> orders = new ArrayList<>();
            try(PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs  = stmt.executeQuery()){
                while(rs.next()){
                    //to get to the foreign key, which is the user id in the users table
                    int userid = rs.getInt("userId");
                    User user = new User();
                    user.set_userId(userid);

                    //getting the columns from orders table
                    Order order = new Order(
                    rs.getInt("orderId"),
                    user,
                    rs.getDate("orderDate"),
                    rs.getString("customerName"),
                    rs.getString("orderStatus"),
                    rs.getDouble("total_Price"));


                    orders.add(order);
                }
                return orders;
            }
        }
         
    //to fetch the order by its order id
    public Order fetchOrderbyId(int orderId) throws SQLException{
        String sql = "SELECT * FROM Orders WHERE orderId = ?";
        Order order = null;
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, orderId);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    //to get the user id which is a foreign key
                    int userid = rs.getInt("userId");
                    User user = new User();
                    user.set_userId(userid);

                    //to get the rest of the columns from orders
                    order = new Order(
                        rs.getInt("orderId"),
                        user,
                        rs.getDate("orderDate"),
                        rs.getString("customerName"),
                        rs.getString("orderStatus"),
                        rs.getDouble("total_Price"));
                    }
                return order;
            }
        }
    }

    //to fetch the OrderId for the given UserID
    public boolean isUserValid(int userId) throws SQLException{
        String sql = "SELECT orderId FROM Orders WHERE userId = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, userId);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
    }

    //to fetch Price by the orderId
    public Double getPricebyOrder(int orderId) throws SQLException {
        String sql = "SELECT total_Price FROM Orders WHERE orderId = ?";
        Double priceTotal = 0d;

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, orderId);
            try(ResultSet res = stmt.executeQuery()){
                if(res.next()){
                    priceTotal = res.getDouble("total_Price");
                }
                return priceTotal;
            }
        }
    }

    //to fetch the total_Price by the userId
    public Double getPrice(int userId) throws SQLException {
    String sql = "SELECT total_Price FROM Orders WHERE userId = ?";
    Double priceTotal = 0d;
    try(PreparedStatement stmt = conn.prepareStatement(sql)){
        stmt.setInt(1, userId);
        try(ResultSet res = stmt.executeQuery()){
            if (res.next()){
                priceTotal = res.getDouble("total_Price");
            }
           return priceTotal;
            }
        }
    }

    //to fetch order its userId
    public List<Order> fetchOrderbyUser(int userId) throws SQLException{
        String sql = "SELECT * FROM Orders WHERE userId = ?";
        List<Order> orders = new ArrayList<>();
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, userId);
            try(ResultSet res = stmt.executeQuery()){
                while(res.next()){
                    //to get the userId, which is a foreign key
                    int userid = res.getInt("userId");
                    User user = new User();
                    user.set_userId(userid);

                    //to get the rest of the columns from the order
                    Order order = new Order(
                        res.getInt("orderId"),
                        user,
                        res.getDate("orderDate"),
                        res.getString("customerName"),
                        res.getString("orderStatus"),
                        res.getDouble("total_price"));

                        orders.add(order);
                    }
                return orders;
            }
        }
    }

    //Update Price method to update the price
    public void updateTotal(Double newPrice, int orderId) throws SQLException {
        String sql = "UPDATE Orders SET total_Price = ? WHERE orderId = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setDouble(1, newPrice);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();

            System.out.println("price updated Successfully!");
            
        }
    }

    //Update the order status method
    public void updateStatus(String newStatus, int order_Id) throws SQLException{
        String sql = "UPDATE Orders SET orderStatus = ? WHERE orderId = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, newStatus);
            stmt.setInt(2, order_Id);
            stmt.executeUpdate();

            System.out.println("Your Order is " + newStatus);
        }
    }

    //Delete the Order
    public boolean deleteOrder(int orderID) throws SQLException{
        String sql = "DELETE FROM Orders WHERE orderId = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, orderID);
            int rows = stmt.executeUpdate();

            if(rows > 0){
                return true;
            } 
            else{
                return false;
            }
        }
    }
}