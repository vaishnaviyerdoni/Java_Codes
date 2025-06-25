console.log("🟢 The page is getting loaded");
document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM is loading");

    const viewItemsForm = document.getElementById("viewItemsForm");
    const viewSection = document.getElementById("viewItemsSection");

    viewItemsForm.addEventListener("submit", async(e) => {
        e.preventDefault();
        const userId = document.getElementById("ViewItemsUserID").value.trim();

        try{
            const res = await fetch(`/InventorySystem/order?action=viewItems&userId=${encodeURIComponent(userId)}`, {
                method : "GET"
            })

            if(res.ok){
                const ItemsInOrderInfo = await res.json();
                viewSection.innerHTML = "<h3>Items Information</h3>";
                for (let i = 0; i < ItemsInOrderInfo.length; i++){
                    const data = ItemsInOrderInfo[i];
                    const jsonData = `
                        <p><strong>ItemsID:</strong>${data.itemsId}</p>
                        <p><strong>OrderID:</strong>${data.OrderId.orderId}</p>
                        <p><strong>InventoryID:</strong>${data.inventoryId.itemId}</p>
                        <p><strong>Quantity:</strong>${data.quantity}</p>
                        <p><strong>Subtotal:</strong>${data.subtotal}</p>
                        <p><strong>userID:</strong>${data.userid.userId}</p>
                        <hr>
                    `
                    viewSection.innerHTML += jsonData;
                }
            }
            else{
                const errorText = await res.text();
                console.warn("Server response:", errorText);
                if (errorText.includes("Customer cannot view this information")){
                    document.getElementById("viewItemsMessage").innerText = "Access Denied: Customers are not allowed to view the data!";
                }
                else{
                    document.getElementById("viewItemsMessage").innerText = "Failed to fetch data, try again later!";
                }
                    
            }
        }
        catch(error){
            console.error("Error occurred while fetching data:", error);
            document.getElementById("viewItemsMessage").innerText = "Server Error, try again later!";
        }
    })

    const viewItemsByOrderID = document.getElementById("viewItemsbyOrderIDForm");
    const viewItemsSection = document.getElementById("viewByOrderIDSection");

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
                viewItemsSection.innerHTML = "<h3>Order Information</h3>";
                for (let i = 0; i < orderInfobyOrderId.length; i++){
                    
                    const data = orderInfobyOrderId[i];
                    console.log("Raw data:", data);

                    const jsonData = `
                        <p><strong>ItemsID:</strong>${data.itemsId}</p>
                        <p><strong>OrderID:</strong>${data.OrderId.orderId}</p>
                        <p><strong>InventoryID:</strong>${data.inventoryId.itemId}</p>
                        <p><strong>Quantity:</strong>${data.quantity}</p>
                        <p><strong>Subtotal:</strong>${data.subtotal}</p>
                        <p><strong>userID:</strong>${data.userid.userId}</p>
                        <hr>
                    `
                    viewItemsSection.innerHTML += jsonData;
                } 
            }
            else{
                const errorText = await res.text();
                console.warn("Server response:", errorText);
                document.getElementById("viewByOrderIDMessage").innerText = "Failed to fetch the data, try again later!";
            }
        }
        catch(error){
            console.error("Error occurred while fetching the data:", error);
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
 