document.addEventListener("DOMContentLoaded", () => {
    const viewSection = document.getElementById("getItemsSection");
    const formToVIewAll = document.getElementById("ViewAllItemsForm");

    formToVIewAll.addEventListener("submit", async(e) => {
        e.preventDefault();
        try{
            const response = await fetch(`/inventory?action=viewAll`, {
                method : "GET"
            })

            const itemsInInventory = await response.json();
            if(response.ok){
                viewSection.innerHTML = "<h3>Inventory Information</h3>";
                for (let i = 0; i < itemsInInventory.length; i++){
                    const data = itemsInInventory[i];
                    const jsonData = `
                        <p><strong>ItemID:</strong>${data.itemId}</P>
                        <p><strong>ItemName:</strong>${data.itemName}</p>
                        <p><strong>Category:</strong>${data.category}</p>
                        <p><strong>Price:</strong>${data.price}</p>
                        <p><strong>Quantity:</strong>${data.quantity}</p>
                        <p><strong>LowStockThreshold:</strong>${data.LowStockThreshold}</p>
                        <hr>
                    `
                   viewSection.innerHTML += jsonData;
                }
            }
            else{
                document.getElementById("viewItemMessage").innerText = "Could not fetch the data, try again later!";
            }
        }
        catch(error){
            console.error("Error occurred when obtaining data", error);
            document.getElementById("viewItemMessage").innerText = "Server Error, try again later!";
        }
    })

    const formtoViewByCategory = document.getElementById("viewByCategoryForm");
    formtoViewByCategory.addEventListener("submit", async(e) => {
        e.preventDefault();
        
    })
})

