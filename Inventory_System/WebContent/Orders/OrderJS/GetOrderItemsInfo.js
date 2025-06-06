document.addEventListener("DOMContentLoaded", () => {
    const viewItemsForm = document.getElementById("viewItemsForm");
    const viewSection = document.getElementById("viewItemsSection");

    viewItemsForm.addEventListener("submit", async(e) => {
        e.preventDefault();
        const userId = document.getElementById("ViewItemsUserID").value.trim();

        try{
            const res = await fetch(`/inventory?action=viewItems&userId=${encodeURIComponent(userId)}`, {
                method : "GET"
            })

            const ItemsInOrderInfo = await res.json();
            if(res.ok){
                viewSection.innerHTML = "<h3>Items Information</h3>";
                for (let i = 0; i < ItemsInOrderInfo.length; i++){
                    const data = ItemsInOrderInfo[i];
                    const jsonData = `
                        <p><strong>ItemsID:</strong>${data.itemsId}</p>
                        <p><strong>OrderID:</strong>${data.orderId}</p>
                        <p><strong>InventoryID:</strong>${data.inventoryId}</p>
                        <p><strong>Quantity:</strong>${data.quantity}</p>
                        <p><strong>Subtotal:</strong>${data.subtotal}</p>
                        <p><strong>userID:</strong>${data.userId}</p>
                        <hr>
                    `
                    viewSection.innerHTML += jsonData;
                }
            }
            else{
                document.getElementById("viewItemsMessage").innerText = "Failed to fetch the data, try again later!";
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
            const res = await fetch(`/inventory?action=ItemsbyOrderId&userId=${encodeURIComponent(userId)}&orderId=${encodeURIComponent(orderId)}`,{
                method : "GET"
            })

            const orderInfobyOrderId = await res.json();
            if(res.ok){
                viewItemsSection.innerHTML = "<h3>Order Information</h3>";
                for (let i = 0; i < orderInfobyOrderId.length; i++){
                    const data = orderInfobyOrderId[i];
                    const jsonData = `
                        <p><strong>ItemsID:</strong>${data.itemsId}</p>
                        <p><strong>OrderID:</strong>${data.orderId}</p>
                        <p><strong>InventoryID:</strong>${data.inventoryId}</p>
                        <p><strong>Quantity:</strong>${data.quantity}</p>
                        <p><strong>Subtotal:</strong>${data.subtotal}</p>
                        <p><strong>userID:</strong>${data.userId}</p>
                        <hr>
                    `
                    viewItemsSection.innerHTML += jsonData;
                } 
            }
            else{
                document.getElementById("viewByOrderIDMessage").innerText = "Failed to fetch the data, try again later!";
            }
        }
        catch(error){
            console.error("Error occurred while fetching the data:", error);
            document.getElementById("viewByOrderIDMessage").innerText = "Server Error, try again later!";
        }
    })
})
 