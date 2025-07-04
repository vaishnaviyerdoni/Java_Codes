console.log("🟢 JS is loading");

document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM ready");

    const formToViewAll = document.getElementById("ViewAllItemsForm");

    formToViewAll.addEventListener("submit", async (e) => {
        e.preventDefault();
        
        try {
            const response = await fetch("/InventorySystem/inventory?action=viewAll", {
                method: "GET"
            });

            if (response.ok) {
                const items = await response.json();
                if(items.length === 0){
                    document.getElementById("AllItemsTable").innerHTML = "<p> No Items found </p>";
                }
                else{
                    let Table = `
                        <table border="1" cellpadding="8" cellspacing="0">
                          <thead>
                            <tr>
                              <th>Item ID</th>
                              <th>Item Name</th>
                              <th>Category</th>
                              <th>Price</th>
                              <th>Quantity</th>
                              <th>Low Stock Threshold</th>
                            </tr>
                          </thead>
                        <tbody>
                    `;

                    items.forEach(data => {
                    Table += `
                        <tr>
                          <td>${data.itemId}</td>
                          <td>${data.itemName}</td>
                          <td>${data.category}</td>
                          <td>${data.price}</td>
                          <td>${data.quantity}</td>
                          <td>${data.LowStockThreshold}</td>
                        </tr>
                    `;
                });

                    Table += "</tbody></table>";
                    console.log("AllItemsTable exists?", document.getElementById("AllItemsTable"));
                    document.getElementById("AllItemsTable").innerHTML = Table;
                }
            } 
            else {
                const errorMessage = await response.json();
                document.getElementById("viewItemMessage").innerText = errorMessage.error;
                return;
            }
        } catch (error) {
            console.error("Error occurred when obtaining data: ", error);
            document.getElementById("viewItemMessage").innerText = "Server Error, try again later!";
        }
    });

    const formToViewByCategory = document.getElementById("viewByCategoryForm");

    formToViewByCategory.addEventListener("submit", async (e) => {
        e.preventDefault();
        const category = document.getElementById("ItemsByCategory").value.trim();
       
        try {
            const res = await fetch(`/InventorySystem/inventory?action=viewByCategory&category=${encodeURIComponent(category)}`, {
                method: "GET"
            });

            if (res.ok) {
                const items = await res.json();
                if (items.length === 0){
                    document.getElementById("viewCategorymessage").innerHTML = "<p>No information found!</p>";
                }
                else{
                    let Table = `
                        <table border="1" cellpadding="8" cellspacing="0">
                          <thead>
                            <tr>
                              <th>Item ID</th>
                              <th>Item Name</th>
                              <th>Category</th>
                              <th>Price</th>
                              <th>Quantity</th>
                              <th>Low Stock Threshold</th>
                            </tr>
                          </thead>
                        <tbody>
                    `;

                    items.forEach(data => {
                    Table += `
                        <tr>
                          <td>${data.itemId}</td>
                          <td>${data.itemName}</td>
                          <td>${data.category}</td>
                          <td>${data.price}</td>
                          <td>${data.quantity}</td>
                          <td>${data.LowStockThreshold}</td>
                        </tr>
                    `;
                    });

                    Table += "</tbody></table>";
                    console.log("AllItemsTable exists?", document.getElementById("viewCategorymessage"));
                    document.getElementById("viewCategorymessage").innerHTML = Table;
                }
            } else {
                const errorMessage = await response.json();
                document.getElementById("viewCategorymessage").innerText = errorMessage.error;
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
                const errorMessage = await response.json();
                document.getElementById("PriceMessage").innerText = errorMessage.error;
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
