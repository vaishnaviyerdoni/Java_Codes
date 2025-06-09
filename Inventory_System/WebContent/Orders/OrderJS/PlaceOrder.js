const placeOrder = document.getElementById("placeOrderForm");
placeOrder.addEventListener("submit", async(e) => {
    e.preventDefault();
    const userId = document.getElementById("PlaceUserID").value.trim();
    const orderDate = document.getElementById("orderDate").value.trim();
    const customerName = document.getElementById("customerName").value.trim();

    try{
        const res = await fetch("/order", {
            method : "POST",
            headers: {"Content-Type" : "application/x-www-form-urlencoded"},
            body: new URLSearchParams({
                action : "addOrder",
                userId,
                orderDate,
                customerName,
                totalPrice : "0.0"
            })
        });

        const result = await res.json();
        if(res.ok && result.OrderID){
            document.getElementById("placeOrderMessage").innerText = result.Message;
            document.getElementById("itemsOrderID").value = result.OrderID;
        }
        else{
            document.getElementById("placeOrderMessage").innerText = result.Message || "Could not place the Order, try again later!";
        }
    }
    catch(error){
        console.error("Error occurred when placing order:", error);
        document.getElementById("placeOrderMessage").innerText = "Server Error try again later!";
    }
})