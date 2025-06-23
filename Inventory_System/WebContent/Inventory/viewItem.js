console.log("🟢 JS is loading");

document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM ready");

    const viewSection = document.getElementById("getItemsSection");
    const formToViewAll = document.getElementById("ViewAllItemsForm");

    formToViewAll.addEventListener("submit", async (e) => {
        e.preventDefault();
        viewSection.innerHTML = "<p>Loading...</p>";

        try {
            const response = await fetch("/InventorySystem/inventory?action=viewAll", {
                method: "GET"
            });

            if (response.ok) {
                const items = await response.json();
                viewSection.innerHTML = "<h3>Inventory Information</h3>";

                items.forEach(data => {
                    viewSection.innerHTML += `
                        <div class="inventory-item">
                            <p><strong>ItemID:</strong> ${data.itemId}</p>
                            <p><strong>ItemName:</strong> ${data.itemName}</p>
                            <p><strong>Category:</strong> ${data.category}</p>
                            <p><strong>Price:</strong> ${data.price}</p>
                            <p><strong>Quantity:</strong> ${data.quantity}</p>
                            <p><strong>LowStockThreshold:</strong> ${data.LowStockThreshold}</p>
                            <hr>
                        </div>
                    `;
                });
            } else {
                document.getElementById("viewItemMessage").innerText = "Could not fetch the data, try again later!";
            }
        } catch (error) {
            console.error("Error occurred when obtaining data", error);
            document.getElementById("viewItemMessage").innerText = "Server Error, try again later!";
        }
    });

    const viewCategorySection = document.getElementById("getByCategory");
    const formToViewByCategory = document.getElementById("viewByCategoryForm");

    formToViewByCategory.addEventListener("submit", async (e) => {
        e.preventDefault();
        const category = document.getElementById("ItemsByCategory").value.trim();
        viewCategorySection.innerHTML = "<p>Loading...</p>";

        try {
            const res = await fetch(`/InventorySystem/inventory?action=viewByCategory&category=${encodeURIComponent(category)}`, {
                method: "GET"
            });

            if (res.ok) {
                const items = await res.json();
                viewCategorySection.innerHTML = "<h3>Inventory Information</h3>";

                items.forEach(data => {
                    viewCategorySection.innerHTML += `
                        <div class="inventory-item">
                            <p><strong>ItemID:</strong> ${data.itemId}</p>
                            <p><strong>ItemName:</strong> ${data.itemName}</p>
                            <p><strong>Category:</strong> ${data.category}</p>
                            <p><strong>Price:</strong> ${data.price}</p>
                            <p><strong>Quantity:</strong> ${data.quantity}</p>
                            <p><strong>LowStockThreshold:</strong> ${data.LowStockThreshold}</p>
                            <hr>
                        </div>
                    `;
                });
            } else {
                document.getElementById("viewCategorymessage").innerText = "Could not fetch data, try again later!";
            }
        } catch (error) {
            console.error("Error occurred while fetching data:", error);
            document.getElementById("viewCategorymessage").innerText = "Server Error, try again later!";
        }
    });

    const priceSection = document.getElementById("getPriceSection");
    const formForPrice = document.getElementById("viewPrice");

    formForPrice.addEventListener("submit", async (e) => {
        e.preventDefault();
        const itemId = document.getElementById("itemId").value.trim();
        const itemName = document.getElementById("itemName").value.trim();
        priceSection.innerHTML = "<p>Loading...</p>";

        try {
            const res = await fetch(`/InventorySystem/inventory?action=getPricebyItemID&itemId=${encodeURIComponent(itemId)}&itemName=${encodeURIComponent(itemName)}`, {
                method: "GET"
            });

            if (res.ok) {
                const price = await res.json();
                priceSection.innerHTML = `
                    <h3>Price</h3>
                    <p><strong>Price:</strong> ${price}</p>
                `;
            } else {
                document.getElementById("PriceMessage").innerText = "Failed to retrieve price, try again later!";
            }
        } catch (error) {
            console.error("Error occurred when retrieving price:", error);
            document.getElementById("PriceMessage").innerText = "Server Error, try again later!";
        }
    });

    const BtntoDashboard = document.getElementById("gotoDashboard");
    if(BtntoDashboard){
        BtntoDashboard.addEventListener("click", ()=> {
            const role = localStorage.getItem("role");
            if(role === "customer"){
                window.location.href = "../dashboard/CustomerDashboard.html";
            }

            else if(role === "admin" || role === "staff"){
                window.location.href = "../dashboard/AdminAndStaffDashboard.html";
            }

            else{
                alert("role not found, redirecting to login!");
                setTimeout(() => {
                    window.location.href = "../User/login.html";
                }, 5000)
            }
        })
    }
});
