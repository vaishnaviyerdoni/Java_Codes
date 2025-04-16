package Inventory_System.Model_Layer;

public class OrderItem {
    private int itemsId;
    private Order OrderId;
    private Inventory inventoryId;
    private int quantity;
    private double subtotal;
    private User userid;
    
    //constructor

    public OrderItem(){}

    public OrderItem(int itemsId, Order OrderId, Inventory inventoryId, int quantity, double subtotal, User userid){
        this.itemsId = itemsId;
        this.OrderId = OrderId;
        this.inventoryId = inventoryId;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.userid = userid;
    }

    //getters
    public int get_itemsId(){
        return itemsId;
    }

    public Order get_orderid(){
        return OrderId;
    }

    public Inventory get_inventoryId(){
        return inventoryId;
    }

    public int getOrder_quantity(){
        return quantity;
    }

    public double get_subtotal(){
        return subtotal;
    }

    public User get_userid(){
        return userid;
    }

    //setters
    public void set_itemsId(int itemsId){
        this.itemsId = itemsId;
    }

    public void set_orderid(Order OrderId){
        this.OrderId = OrderId;
    }

    public void set_inventoryId(Inventory inventoryId){
        this.inventoryId = inventoryId;
    }

    public void set_ItemsQuantity(int quantity){
        this.quantity = quantity;
    }

    public void set_subtotal(double subtotal){
        this.subtotal = subtotal;
    }

    public void set_userid(User userid){
        this.userid = userid;
    }

    public String toString(){
        return "OrderItems{" +
                "itemsId=" + itemsId +
                ", orderId='" + OrderId + '\'' +
                ", inventoryID='" + inventoryId + '\'' +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                ", userid=" + userid +
                '}';                 
    }
}
