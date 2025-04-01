package Inventory_System.Model_Layer;

public class Inventory {
    
    //Fields
    private int itemId;
    private String itemName;
    private String category;
    private double price;
    private int quantity;
    private int LowStockThreshold;

    //empty constructor
    public Inventory(){}

    //constructor
    public Inventory(int itemId, String itemName, String category, double price, int quantity, int LowStockThreshold){
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.LowStockThreshold = LowStockThreshold;
    }

    //Getters

    public int get_itemId(){
        return itemId;
    }

    public String get_itemName(){
        return itemName;
    }

    public String get_category(){
        return category;
    }

    public double get_price(){
        return price;
    }

    public int get_quantity(){
        return quantity;
    }

    public int get_LowStock(){
        return LowStockThreshold;
    }

    //Setters

    public void set_itemId(int itemId){
        this.itemId = itemId;
    }

    public void set_itemName(String itemName){
        this.itemName = itemName;
    }

    public void set_category(String category){
        this.category = category;
    }

    public void set_quantity(int quantity){
        this.quantity = quantity;
    }

    public void set_LowStock(int LowStockThreshold){
        this.LowStockThreshold = LowStockThreshold;
    }

    public String toString(){
        return "InventoryItem{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", lowStockThreshold=" + LowStockThreshold +
                '}';
    }
}
