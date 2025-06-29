console.log("🟢 InsertItem page loading");

document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM ready");

    const userIDfromStorage =  localStorage.getItem("userId");
    if(userID){
        document.getElementById("userID").value = userIDfromStorage;
    }

    const form = document.getElementById("InsertItemsForm");
    form.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("userID").value.trim();
        const itemName = document.getElementById("itemName").value.trim();
        const category = document.getElementById("Category").value.trim();
        const price = document.getElementById("price").value.trim();
        const quantity = document.getElementById("quantity").value.trim();
        const LowStockThreshold = document.getElementById("LowStockThreshold").value.trim();

        try{
            const response = await fetch("/InventorySystem/inventory", {
                method : "POST",
                headers : {"Content-Type" : "application/x-www-form-urlencoded"},
                body : new URLSearchParams({
                    action : "AddItemToInventory",
                    userId,
                    itemName,
                    category,
                    price,
                    quantity,
                    LowStockThreshold
                })
            })

            const result = await response.text();
            if(response.ok && result.includes("Item Added successfully")){
                alert("Item Added to the Inventory!");
            }
            else{
                console.log(result)
                document.getElementById("InsertMessage").innerText = result;
            }
        }
        catch(error){
            console.error("Failed to insert item:", error);
            document.getElementById("InsertMessage").innerText = "Server Error, try again later!";
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