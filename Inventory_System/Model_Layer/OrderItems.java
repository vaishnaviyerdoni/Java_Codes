package Inventory_System.Model_Layer;

public class OrderItems {
    private int items_id;
    private Orders orderId;
    private Inventory itemId_inventory;
    private int quantity;
    private double subtotal;

    //constructor

    public OrderItems(){}

    public OrderItems(int items_id, Orders orderId, Inventory itemId_inventory, int quantity, double subtotal){
        this.items_id = items_id;
        this.orderId = orderId;
        this.itemId_inventory = itemId_inventory;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    //getters
    public int get_item(){
        return items_id;
    }

    public Orders get_orderid(){
        return orderId;
    }

    public Inventory get_inventoryId(){
        return itemId_inventory;
    }

    public int getOrder_quantity(){
        return quantity;
    }

    public double get_subtotal(){
        return subtotal;
    }

    //setters
    public void set_item(int items_id){
        this.items_id = items_id;
    }

    public void set_orderid(Order orderId){
        this.orderId = orderId;
    }

    public void set_inventoryId(Inventory itemId_inventory){
        this.itemId_inventory = itemId_inventory;
    }

    public void set_ItemsQuantity(int quantity){
        this.quantity = quantity;
    }

    public void set_subtotal(double subtotal){
        this.subtotal = subtotal;
    }

    public String toString(){
        return "OrderItems{" +
                "itemsId=" + items_id +
                ", orderId='" + orderId + '\'' +
                ", inventoryID='" + itemId_inventory + '\'' +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';                 
    }

    public static void main(String[] args) {
        // Create an inventory item
        OrderItems items = new OrderItems(1, 1, 1, 5, 10);

        // Print item details
        System.out.println("\n" + items);
    
    }

}
