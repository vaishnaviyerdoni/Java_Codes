console.log("🟢 The page is getting loaded");
document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM is loading");

    const viewItemsForm = document.getElementById("viewItemsForm");

    viewItemsForm.addEventListener("submit", async(e) => {
        e.preventDefault();
        const userId = document.getElementById("ViewItemsUserID").value.trim();

        try{
            const res = await fetch(`/InventorySystem/order?action=viewItems&userId=${encodeURIComponent(userId)}`, {
                method : "GET"
            })

            if(res.ok){
                const ItemsInOrderInfo = await res.json();
                console.log(ItemsInOrderInfo);

                if(ItemsInOrderInfo.length === 0){
                    document.getElementById("viewAllItemsTable").innerHTML = "<p>The information is not available</p>"
                }
                else{
                    let Table = `
                        <table border="1" cellspacing="0" cellpadding="8">
                          <thead>
                            <tr>
                              <th>ItemsID</th>
                              <th>OrderID</th>
                              <th>InventoryID</th>
                              <th>Quantity</th>
                              <th>Subtotal</th>
                              <th>UserID</th>
                    `;

                    ItemsInOrderInfo.forEach(data => {
                        Table += `
                            <tr>
                                <td>${data.itemsId}</td>
                                <td>${data.OrderId.orderId}</td>
                                <td>${data.inventoryId.itemId}</td>
                                <td>${data.quantity}</td>
                                <td>${data.subtotal}</td>
                                <td>${data.userid.userId}</td>
                        `;
                    });

                    Table += "</tbody></table>";
                    document.getElementById("viewAllItemsTable").innerHTML = Table;
                }
            }
            else{
                const errorText = await res.json();
                console.log("Server response:", errorText);
                document.getElementById("viewItemsMessage").innerText = errorText.error;
            }
        }
        catch(error){
            console.log("Error occurred while fetching data:", error);
            document.getElementById("viewItemsMessage").innerText = "Server Error, try again later!";
        }
    })

    const viewItemsByOrderID = document.getElementById("viewItemsbyOrderIDForm");
    
    viewItemsByOrderID.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("viewUserID").value.trim();
        const orderId = document.getElementById("ViewOrderID").value.trim();

        try{
            const res = await fetch(`/InventorySystem/order?action=ItemsbyOrderId&userId=${encodeURIComponent(userId)}&orderId=${encodeURIComponent(orderId)}`,{
                method : "GET"
            })

            if(res.ok){
                const orderInfobyOrderId = await res.json();
                console.log(orderInfobyOrderId);

                if(orderInfobyOrderId.length === 0){
                    document.getElementById("viewItemsbyIDTable") = "<p>This information is not available</p>";
                }
                else{
                    let Table = `
                        <table border="1" cellspacing="0" cellpadding="8">
                          <thead>
                            <tr>
                              <th>ItemsID</th>
                              <th>OrderID</th>
                              <th>InventoryID</th>
                              <th>Quantity</th>
                              <th>Subtotal</th>
                              <th>UserID</th>
                    `;

                    orderInfobyOrderId.forEach(data => {
                        Table += `
                            <tr>
                                <td>${data.itemsId}</td>
                                <td>${data.OrderId.orderId}</td>
                                <td>${data.inventoryId.itemId}</td>
                                <td>${data.quantity}</td>
                                <td>${data.subtotal}</td>
                                <td>${data.userid.userId}</td>
                        `;
                    });

                    Table += "</tbody></table>";
                    document.getElementById("viewItemsbyIDTable").innerHTML = Table;
                }
                
            }
            else{
                const errorText = await res.json();
                console.log("Server response:", errorText);
                document.getElementById("viewByOrderIDMessage").innerText = errorText.error;
            }
        }
        catch(error){
            console.log("Error occurred while fetching the data:", error);
            document.getElementById("viewByOrderIDMessage").innerText = "Server Error, try again later!";
        }
    })

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
})
 