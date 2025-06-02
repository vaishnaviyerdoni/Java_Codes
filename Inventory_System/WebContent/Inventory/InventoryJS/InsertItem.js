document.addEventListener("DOMContentLoaded", () => {
    const form = document.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("userID").value.trim();
        const itemName = document.getElementById("itemName").value.trim();
        const category = document.getElementById("Category").value.trim();
        const price = document.getElementById("price").value.trim();
        const LowStockThreshold = document.getElementById("LowStockThreshold");

        try{
            const response = await fetch("/inventory", {
                method : "POST",
                headers : {"Content-Type" : "application/x-www-form-urlencoded"},
                body : new URLSearchParams({
                    action : "AddItemToInventory",
                    userId,
                    itemName,
                    category,
                    price,
                    LowStockThreshold
                })
            })

            const result = await response.text();
            if(response.ok && result.includes("Item Added Successfully")){
                alert("Item Added to the Inventory!");
            }
            else{
                document.getElementById("InsertMessage").innerText = "Item could not be added to the Inventory!";
            }
        }
        catch(error){
            console.error("Failed to insert item:", error);
            document.getElementById("InsertMessage").innerText = "Server Error, try again later!";
        }
    })
})