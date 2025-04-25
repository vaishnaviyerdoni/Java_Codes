package Inventory_System.Business_Layer;

import Inventory_System.DAO_Layer.InventoryDAO;
import Inventory_System.DAO_Layer.OrderDAO;

public class OrderItemService {
    private InventoryDAO inventoryDAO;
    private OrderDAO orderDAO;
    private UserDAO userDAO;

    public OrderItemService(InventoryDAO inventoryDAO, OrderDAO orderDAO, UserDAO userDAO){
        this.inventoryDAO = inventoryDAO;
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
    }

    
}
