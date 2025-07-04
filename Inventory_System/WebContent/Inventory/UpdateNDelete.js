console.log("🟢 JS is loading");

document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM ready");

    const form = document.getElementById("UpdateItemPriceForm");
    form.addEventListener("submit", async(e) => {
        e.preventDefault();

        const itemId = document.getElementById("priceItemID").value.trim();
        const userId = document.getElementById("priceUserID").value.trim();
        const newPrice = document.getElementById("newPrice").value.trim();

        try{
            const response = await fetch("/InventorySystem/inventory", {
                method : "PUT",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({
                    action : "updatePrice",
                    itemId,
                    userId,
                    newPrice
                })
            })

            const result = await response.text();
            if(response.ok && result.includes("Price Updated")){
                alert("Price is updated!");
            }
            else{
                console.log(result)
                document.getElementById("PriceMessage").innerText = result;
            }
        }
        catch(error){
            console.error("Failed to insert item:", error);
            document.getElementById("PriceMessage").innerText = "Server Error, try again later!";
        }
    })

    const formforDeleteItem = document.getElementById("DeleteItemForm");
    formforDeleteItem.addEventListener("submit", async(e) => {
        e.preventDefault();
        
        const itemId = document.getElementById("DeleteItemID").value.trim();
        const userId = document.getElementById("DeleteUserID").value.trim();

        try{
            const response = await fetch(`/InventorySystem/inventory?action=deleteItem&itemId=${encodeURIComponent(itemId)}&userId=${encodeURIComponent(userId)}`, {
                method : "DELETE"
            })

            const result = await response.text();
            if(response.ok && result.includes("Deleted Successfully")){
                alert("Item was deleted successfully!");
            }
            else{
                const errorMessage = await response.json();
                console.log("Server response:", errorMessage);
                document.getElementById("DeleteMessage").innerText = errorMessage.error;
            }
        }
        catch(error){
            console.error("Item not deleted due to error:", error);
            document.getElementById("DeleteMessage").innerText = "Server Error, try again later!";
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