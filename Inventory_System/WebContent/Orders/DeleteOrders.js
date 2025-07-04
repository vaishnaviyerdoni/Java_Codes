console.log("🟢 Delete page is loading");

document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM ready");

    const deleteOrderForm = document.getElementById("DeleteOrderForm");
    deleteOrderForm.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("OrderUserID").value.trim();
        const orderId = document.getElementById("orderID").value.trim();

        try{
            const res = await fetch(`/InventorySystem/order?action=AdminDeletesOrder&userId=${userId}&orderId=${orderId}`, {
                method : "DELETE"
            })

            const result = await res.text();
            if(res.ok && result.includes("order Deleted")){
                alert("Order deleted Successfully!");
            }
            else{
                const errorMessage = await res.json();
                console.log("Server response: ", errorMessage);
                document.getElementById("DeleteOrderMessage").innerText = errorMessage.error;
            }
        }
        catch(error){
            console.log("Error occurred when deleting Order:", error);
            document.getElementById("DeleteOrderMessage").innerText = "Server Error, try again later!";
        }
    })

    const deleteItemsForm = document.getElementById("DeleteItemsForm");
    deleteItemsForm.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("adminID").value.trim();
        const itemsId = document.getElementById("ItemsID").value.trim();

        try{
            const res = await fetch(`/InventorySystem/order?action=AdminDeletesItem&userId=${userId}&itemsId=${itemsId}`, {
                method : "DELETE"
            })

            const result = await res.text();
            if(res.ok && result.includes("Item Deleted")){
                alert("Item deleted from Order!");
            }
            else{
                const errorMessage = await res.json();
                console.log("Server response: ", errorMessage);
                document.getElementById("DeleteItemMessage").innerText = errorMessage.error;
            }
        }
        catch(error){
            console.log("Server response: ", result);
            console.error("Error occurred when deleting error", error);
            document.getElementById("DeleteItemMessage").innerText = "Server Error, try again later!";
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