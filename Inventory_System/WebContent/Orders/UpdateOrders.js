document.addEventListener("DOMContentLoaded", ()=>{
    const UpdateForm = document.getElementById("updatePrice");
    UpdateForm.addEventListener("submit", async(e) => {
        e.preventDefault();
        const price = document.getElementById("TotalPrice").value.trim();
        const orderId = document.getElementById("orderID").value.trim();
        const userId = document.getElementById("userID").value.trim();

        try{
            const res = await fetch("/InventorySystem/order", {
                method : "PUT",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({
                    action : "updateTotalPrice",
                    price,
                    orderId,
                    userId
                })
            })

            const result = await res.text();
            if(res.ok && result.includes("total price updated")){
                alert("The total price of an order is updated!");
            }
            else{
                const errorMessage = await res.json();
                console.log(errorMessage);
                document.getElementById("updatePriceMessage").innerText = errorMessage.error;
            }
        }
        catch(error){
            console.log("Error occurred when updating error:", error);
            document.getElementById("updatePriceMessage").innerText = "Server error, try again later!";
        }
    })

    const statusChangeForm = document.getElementById("changeStatus");
    statusChangeForm.addEventListener("submit", async(e) => {
        e.preventDefault();
        const userId = document.getElementById("AdminORStaffID").value.trim();
        const customerId = document.getElementById("customerOrderID").value.trim();
        const status = document.getElementById("OrderStatus").value.trim();

        try{
            const res = await fetch("/InventorySystem/order", {
                method : "PUT",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({
                    action : "changeStatus",
                    userId,
                    customerId,
                    status
                })
            })

            const result = await res.text();
            if(res.ok && result.includes("Status updated!")){
                alert("Status updated successfully!");
            }
            else{
                document.getElementById("changeStatusMessage").innerText = "Failed to update status, try again later!";
            }
        }
        catch(error){
            console.error("Error occurred when updating status:", error);
            document.getElementById("changeStatusMessage").innerText = "Server Error, try again later!";
        }
    })

    const updateQuantityForm = document.getElementById("UpdateQuantityForm");
    updateQuantityForm.addEventListener("submit", async(e) => {
        e.preventDefault();
        const itemsId = document.getElementById("itemsID").value.trim();
        const userId = document.getElementById("UpdateQuantityUserID").value.trim();
        const nQuantity = document.getElementById("updatedQuantity").value.trim();
        const inventoryId = document.getElementById("inventoryID").value.trim();

        try{
            const res = await fetch("/InventorySystem/order", {
                method : "PUT",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({
                    action : "updateQuantity",
                    itemsId,
                    userId,
                    nQuantity,
                    inventoryId
                })
            })

            const result = await res.text();
            if(res.ok && result.includes("quantity and subtotal updated!")){
                alert("Quantity and subtotal updated!");
            }
            else{
                document.getElementById("updateQuantityMessage").innerText = "Failed to update quantity!";            
            }
        }
        catch(error){
            console.error("Error occurred when updating quantity!");
            document.getElementById("updateQuantityMessage").innerText = "Server Error, try again later!"; 
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