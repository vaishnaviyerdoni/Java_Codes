console.log("🟢 JS is loading");

document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM ready");

    const form = document.getElementById("UpdateItemPriceForm");
    form.addEventListener("submit", async(e) => {
        e.preventDefault();

        const itemId = document.getElementById("priceItemID").value.trim();
        const userId = document.getElementById("priceUserID").value.trim();
        const newPrice = document.getElementById("newPrice").value.trim();

        try{
            const response = await fetch("/InventorySystem/inventory", {
                method : "PUT",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({
                    action : "updatePrice",
                    itemId,
                    userId,
                    newPrice
                })
            })

            const result = await response.text();
            if(response.ok && result.includes("Price Updated")){
                alert("Price is updated!");
            }
            else{
                document.getElementById("PriceMessage").innerText = "Failed to update the price, try again later!";
            }
        }
        catch(error){
            document.getElementById("PriceMessage").innerText = "Server Error, try again later!";
        }
    })

    const formforStock = document.getElementById("UpdateStockForm");
    formforStock.addEventListener("submit", async(e) => {
        e.preventDefault();

        const itemId = document.getElementById("StockItemID").value.trim();
        const userId = document.getElementById("StockUserID").value.trim();
        const newQuantity = document.getElementById("newQuantity").value.trim();

        try{
            const response = await fetch("/InventorySystem/inventory", {
                method : "PUT",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({

                    action : "addtoQuantity",
                    itemId,
                    userId,
                    newQuantity
                })
            })

            const result = await response.text();
            if(response.ok && result.includes("Quantity Updated")){
                alert("Stock Updated succesfully!");
                formforStock.reset();
            }
            else{
                document.getElementById("StockMessage").innerText = "Failed to update stock, try again, later!";
            }
        }
        catch(error){
            console.error("Error while updating stock:", error);
            document.getElementById("StockMessage").innerText = "Server Error, Try again later!";
        }
    })

    const formforDeleteItem = document.getElementById("DeleteItemForm");
    formforDeleteItem.addEventListener("submit", async(e) => {
        e.preventDefault();
        
        const itemId = document.getElementById("DeleteItemID").value.trim();
        const userId = document.getElementById("DeleteUserID").value.trim();

        try{
            const response = await fetch("/InventorySystem/inventory", {
                method : "POST",
                headers : {"Content-Type" : "application/x-www-form-urlencoded"},
                body : new URLSearchParams({
                    action : "deleteItem",
                    itemId,
                    userId
                })
            })

            const result = await response.text();
            if(response.ok && result.includes("Deleted Successfully")){
                alert("Item was deleted successfully!");
            }
            else{
                document.getElementById("DeleteMessage").innerText = "Failed to delete item, try again later!";
            }
        }
        catch(error){
            console.error("Item not deleted due to error:", error);
            document.getElementById("DeleteMessage").innerText = "Server Error, try again later!";
        }
    })
})