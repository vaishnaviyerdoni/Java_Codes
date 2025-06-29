console.log("🟢 JS is loading");

document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM is loading");

    const formforStock = document.getElementById("UpdateStockForm");
    formforStock.addEventListener("submit", async(e) => {
        e.preventDefault();

        const itemId = document.getElementById("StockItemID").value.trim();
        const userId = document.getElementById("StockUserID").value.trim();
        const newQuantity = document.getElementById("newQuantity").value.trim();

        try{
            const response = await fetch("/InventorySystem/inventory", {
                method : "PUT",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({

                    action : "addtoQuantity",
                    itemId,
                    userId,
                    newQuantity
                })
            })

            const result = await response.text();
            if(response.ok && result.includes("Quantity Updated")){
                alert("Stock Updated succesfully!");
                formforStock.reset();
            }
            else{
                console.log("Server response: ", result)
                document.getElementById("StockMessage").innerText = result;
            }
        }
        catch(error){
            console.error("Error while updating stock:", error);
            document.getElementById("StockMessage").innerText = "Server Error, Try again later!";
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