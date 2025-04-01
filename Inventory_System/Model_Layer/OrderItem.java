package Inventory_System.Model_Layer;

public class OrderItem {
    private int itemsId;
    private Order OrderrderId;
    private Inventory itemIdInventory;
    private int quantity;
    private double subtotal;

    //constructor

    public OrderItem(){}

    public OrderItem(int itemsId, Order OrderId, Inventory itemIdInventory, int quantity, double subtotal){
        this.itemsId = itemsId;
        this.OrderId = OrderId;
        this.itemIdInventory = itemIdInventory;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    //getters
    public int get_item(){
        return itemsId;
    }

    public Order get_orderid(){
        return OrderId;
    }

    public Inventory get_inventoryId(){
        return itemIdInventory;
    }

    public int getOrder_quantity(){
        return quantity;
    }

    public double get_subtotal(){
        return subtotal;
    }

    //setters
    public void set_itemId(int itemsId){
        this.itemsId = itemsId;
    }

    public void set_orderid(Order orderId){
        this.OrderId = OrderId;
    }

    public void set_inventoryId(Inventory itemIdInventory){
        this.itemIdInventory = itemIdInventory;
    }

    public void set_ItemsQuantity(int quantity){
        this.quantity = quantity;
    }

    public void set_subtotal(double subtotal){
        this.subtotal = subtotal;
    }

    public String toString(){
        return "OrderItems{" +
                "itemsId=" + itemsId +
                ", orderId='" + OrderId + '\'' +
                ", inventoryID='" + itemIdInventory + '\'' +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';                 
    }
}
