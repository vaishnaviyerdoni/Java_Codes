document.addEventListener("DOMContentLoaded", () => {
    const orderSection = document.getElementById("getAllSection");
    const formToGetAllOrders = document.getElementById("getAllOrdersForm");
    formToGetAllOrders.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("GetAllUserID").value.trim();

        try{
            const res = await fetch(`/order?action=getAllOrders&userId=${encodeURIComponent(userId)}`, {
                method : "GET"
            })

            const OrderInfo = await res.json();
            if(res.ok){
                orderSection.innerHTML = "<h3>Order Info</h3>";
                for(let i=0; i<OrderInfo.length; i++){
                    const data = OrderInfo[i];
                    const jsonData = `
                        <p><strong>OrderID:</strong>${data.orderId}</p>
                        <p><strong>UserID:</strong>${data.userId}</p>
                        <p><strong>OrderDate:</strong>${data.orderDate}</p>
                        <p><strong>CustomerName:</strong>${data.customerName}</p>
                        <p><strong>Status:</strong>${data.orderStatus}</p>
                        <p><strong>Total Price</strong>${data.total_Price}</p>
                        <hr>
                    `
                    orderSection.innerHTML += jsonData;
                }
            }
            else{
                document.getElementById("getAllOrdersMessage").innerText = "Failed to fetch data, try again later!";
            }
        }
        catch(error){
            console.error("Error while fetching data:", error);
            document.getElementById("getAllOrdersMessage").innerText = "Server Error, try again later!";
        }
    })

    const viewSection = document.getElementById("viewbyOrderIDSection");
    const viewbyOrderIDForm = document.getElementById("viewOrderbyOrderIDForm");
    viewbyOrderIDForm.addEventListener("submit", async(e) => {
        e.preventDefault();

        const orderId = document.getElementById("orderID").value.trim();
        const userId = document.getElementById("viewUserID").value.trim();

        try{
            const res = await fetch(`/order?action=viewByOrderId&orderId=${encodeURIComponent(orderId)}&userId=${encodeURIComponent(userId)}`, {
                method : "GET"
            })

            if(res.ok){
                viewSection.innerHTML = "<h3>Order Information </h3>";
                for(let i=0; i<OrderInfo.length; i++){
                    const data = OrderInfo[i];
                    const jsonDatabyOrderID = `
                        <p><strong>OrderID:</strong>${data.orderId}</p>
                        <p><strong>UserID:</strong>${data.userId}</p>
                        <p><strong>OrderDate:</strong>${data.orderDate}</p>
                        <p><strong>CustomerName:</strong>${data.customerName}</p>
                        <p><strong>Status:</strong>${data.orderStatus}</p>
                        <p><strong>Total Price</strong>${data.total_Price}</p>
                        <hr>
                    `
                    viewSectionSection.innerHTML += jsonDatabyOrderID;
                }
            }
            else{
                document.getElementById("viewbyIDMessage").innerText = "Failed to fetch data, try again later!";
            }
        }
        catch(error){
            console.error("Error while fetching data:", error);
            document.getElementById("viewbyIDMessage").innerText = "Server Error, try again later!";
        }
    })

    const ViewByUserIDForm = document.getElementById("viewbyUserIDForm");
    const viewbyuserIDsection = document.getElementById("getbyUserIDSection");

    ViewByUserIDForm.addEventListener("submit", async(e) => {
        e.preventDefault();
        const userId = document.getElementById("viewPriceID").value.trim();
        try{
            const res = await fetch(`/order?action=viewByUserId&userId=${encodeURIComponent(userId)}`, {
                method : "GET"
            })

            const orderbyUserID = await res.json();

            if(res.ok){
                viewbyuserIDsection.innerHTML = "<h3>Order Information</h3>";
                for(let i=0; i<orderbyUserID.length; i++){
                    const data = orderbyUserID[i];
                    const jsonDatabyUserID = `
                        <p><strong>OrderID:</strong>${data.orderId}</p>
                        <p><strong>UserID:</strong>${data.userId}</p>
                        <p><strong>OrderDate:</strong>${data.orderDate}</p>
                        <p><strong>CustomerName:</strong>${data.customerName}</p>
                        <p><strong>Status:</strong>${data.orderStatus}</p>
                        <p><strong>Total Price</strong>${data.total_Price}</p>
                        <hr>
                    `
                    viewbyuserIDsection.innerHTML += jsonDatabyUserID;
                }
            }
            else{
                document.getElementById("viewByUserIDMessage").innerText = "Failed to fetch data, try again later!";
            }
        }
        catch(error){
            console.error("Error while fetching data:", error);
            document.getElementById("viewByUserIDMessage").innerText = "Server Error, try again later!";
        }
    })

    const getPriceSection = document.getElementById("getPriceSection");
    const getPriceForm = document.getElementById("getPriceForm");
    
    getPriceForm.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("viewPriceID").value.trim();

        try{
            const res = await fetch(`/order?action=getPrice&userId=${encodeURIComponent(userId)}`, {
            method : "GET"
            })

            const price = await res.json();

            if(res.ok){
                getPriceSection.innerHTML = "<h3>Price</h3>";
                const jsonPrice = `<p><strong>Price:</strong>${price}</p>`;
                getPriceSection.innerHTML += jsonPrice;
            }
            else{
                document.getElementById("getPriceMessage").innerText = "";
            }   
        }
        catch(error){
            console.error("Error while fetching data:", error);
            document.getElementById("getPriceMessage").innerText = "Server Error, try again later!";
        }
    })
})
