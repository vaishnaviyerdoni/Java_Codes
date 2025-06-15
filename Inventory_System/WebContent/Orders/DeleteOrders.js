console.log("🟢 Delete page is loading");

document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM ready");

    const deleteOrderForm = document.getElementById("DeleteOrderForm");
    deleteOrderForm.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("OrderUserID").value.trim();
        const orderId = document.getElementById("orderID").value.trim();

        try{
            const res = await fetch("/InventorySystem/order", {
                method : "POST",
                headers : {"Content-Type" : "application/x-www-form-urlencoded"},
                body : new URLSearchParams({
                    action : "AdminDeletesOrder",
                    userId,
                    orderId
                })
            })

            const result = await res.text();
            if(res.ok && result.includes("order Deleted")){
                alert("Order deleted Successfully!");
            }
            else{
                document.getElementById("DeleteOrderMessage").innerText = "Failed to delete order, try again later!";
            }
        }
        catch(error){
            console.error("Error occurred when deleting Order:", error);
            document.getElementById("DeleteOrderMessage").innerText = "Server Error, try again later!";
        }
    })

    const deleteItemsForm = document.getElementById("DeleteItemsForm");
    deleteItemsForm.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("adminID").value.trim();
        const itemsId = document.getElementById("ItemsID").value.trim();

        try{
            const res = await fetch("/InventorySystem/order", {
                method : "POST",
                headers : {"Content-Type" : "application/x-www-form-urlencoded"},
                body : new URLSearchParams({
                    action : "AdminDeletesItem",
                    userId,
                    itemsId
                })
            })

            const result = await res.text();
            if(res.ok && result.includes("Item Deleted")){
                alert("Item deleted from Order!");
            }
            else{
                document.getElementById("DeleteItemMessage").innerText = "Failed to delete item from order!";
            }
        }
        catch(error){
            console.error("Error occurred when deleting error", error);
            document.getElementById("DeleteItemMessage").innerText = "Server Error, try again later!";
        }
    })
})