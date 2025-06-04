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

    const viewCategorySection = document.getElementById("getByCategory");
    const formtoViewByCategory = document.getElementById("viewByCategoryForm");
    formtoViewByCategory.addEventListener("submit", async(e) => {
        e.preventDefault();
        const category = document.getElementById("ItemsByCategory").value.trim();

        try{
            const res = await fetch(`/inventory?action=viewByCategory&category=${encodeURIComponent(category)}`, {
                method : "GET"
            })

            const inventoryInfobyCategory = await response.json();
            if(res.ok){
                viewCategorySection.innerHTML = "<h3>Inventory Information</h3>";
                for (let i = 0; i < inventoryInfobyCategory.length; i++){
                    const data = inventoryInfobyCategory[i];

                    const jsonCategoryData = `
                        <p><strong>ItemID:</strong>${data.itemId}</P>
                        <p><strong>ItemName:</strong>${data.itemName}</p>
                        <p><strong>Category:</strong>${data.category}</p>
                        <p><strong>Price:</strong>${data.price}</p>
                        <p><strong>Quantity:</strong>${data.quantity}</p>
                        <p><strong>LowStockThreshold:</strong>${data.LowStockThreshold}</p>
                        <hr>
                    `
                    viewCategorySection.innerHTML += jsonCategoryData;
                }
            }
            else{
                document.getElementById("viewCategorymessage").innerText = "Could not fetch data, try again later!";
            }
        }
        catch(error){
            console.error("Error occurred while fetching data:", error);
            document.getElementById("viewCategorymessage").innerText = "Server Error, try again later";
        }
    })

    const priceSection = document.getElementById("getPriceSection");
    const formforPrice = document.getElementById("viewPrice");
    formforPrice.addEventListener("submit", async(e) => {
        e.preventDefault();
        const itemId = document.getElementById("itemId").value.trim();
        const itemName = document.getElementById("itemName").value.trim();
        
        try{
            const res = await fetch(`/inventory?action=getPricebyItemID&itemId=${encodeURIComponent(itemId)}&itemName=${encodeURIComponent(itemName)}`, {
                method : "GET"
            })

            const price = await res.json();
            if(res.ok){
                priceSection.innerHTML = "<h3>Price</h3>"
                const jsonPriceData = `<P><strong>Price:</strong>${price}</p>`
                priceSection.innerHTML += jsonPriceData;
            }
            else{
                document.getElementById("PriceMessage").innerText = "Failed to retrieve price, try again later";
            }
        }
        catch(error){
            console.error("error occurred when retrieving price:", error);
            document.getElementById("PriceMessage").innerText = "Server Error, try again later!";
        }
    })
})

