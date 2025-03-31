package Inventory_System.Model_Layer;

public class Inventory {
    
    //Fields
    private int item_id;
    private String item_name;
    private String category;
    private double price;
    private int quantity;
    private int Low_Stock_Threshold;

    //default Constructor
    public Inventory(int item_id, String item_name, String category, double price, int quantity, int Low_Stock_Threshold){
        this.item_id = item_id;
        this.item_name = item_name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.Low_Stock_Threshold = Low_Stock_Threshold;
    }

    //Getters

    public int get_item_id(){
        return item_id;
    }

    public String get_item_name(){
        return item_name;
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

    public int get_Low_Stock(){
        return Low_Stock_Threshold;
    }

    //Setters

    public void set_item_id(int item_id){
        this.item_id = item_id;
    }

    public void set_item_name(String item_name){
        this.item_name = item_name;
    }

    public void set_category(String category){
        this.category = category;
    }

    public void set_quantity(int quantity){
        this.quantity = quantity;
    }

    public void set_LowStock(int Low_Stock_Threshold){
        this.Low_Stock_Threshold = Low_Stock_Threshold;
    }

    public String toString(){
        return "InventoryItem{" +
                "itemId=" + item_id +
                ", itemName='" + item_name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", lowStockThreshold=" + Low_Stock_Threshold +
                '}';
    }

        public static void main(String[] args) {
            // Create an inventory item
            Inventory item = new Inventory(1, "Laptop", "Electronics", 1200.50, 5, 2);
    
            // Print item details
            System.out.println("\n" + item);

        }
    }
