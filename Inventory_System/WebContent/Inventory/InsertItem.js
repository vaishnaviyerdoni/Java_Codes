console.log("🟢 InsertItem page loading");

document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM ready");

    const form = document.getElementById("InsertItemsForm");
    form.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("userID").value.trim();
        const itemName = document.getElementById("itemName").value.trim();
        const category = document.getElementById("Category").value.trim();
        const price = document.getElementById("price").value.trim();
        const quantity = document.getElementById("quantity").value.trim();
        const LowStockThreshold = document.getElementById("LowStockThreshold").value.trim();

        try{
            const response = await fetch("/InventorySystem/inventory", {
                method : "POST",
                headers : {"Content-Type" : "application/x-www-form-urlencoded"},
                body : new URLSearchParams({
                    action : "AddItemToInventory",
                    userId,
                    itemName,
                    category,
                    price,
                    quantity,
                    LowStockThreshold
                })
            })

            const result = await response.text();
            if(response.ok && result.includes("Item Added successfully")){
                alert("Item Added to the Inventory!");
            }
            else{
                console.log(result)
                document.getElementById("InsertMessage").innerText = result;
            }
        }
        catch(error){
            console.error("Failed to insert item:", error);
            document.getElementById("InsertMessage").innerText = "Server Error, try again later!";
        }
    })
})