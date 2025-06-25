console.log("🟢 The page is getting loaded");

document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM ready");
    const orderSection = document.getElementById("getAllSection");
    const formToGetAllOrders = document.getElementById("getAllOrdersForm");
    formToGetAllOrders.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("GetAllUserID").value.trim();

        try{
            const res = await fetch(`/InventorySystem/order?action=getAllOrders&userId=${encodeURIComponent(userId)}`, {
                method : "GET"
            })

            const OrderInfo = await res.json();
            if(res.ok){
                orderSection.innerHTML = "<h3>Order Info</h3>";
                for(let i=0; i<OrderInfo.length; i++){
                    const data = OrderInfo[i];
                    console.log("userId field:", data.userId);

                    const jsonData = `
                        <p><strong>OrderID:</strong>${data.orderId}</p>
                        <p><strong>UserID:</strong>${data.userId.userId}</p>
                        <p><strong>OrderDate:</strong>${data.orderDate}</p>
                        <p><strong>CustomerName:</strong>${data.customerName}</p>
                        <p><strong>Status:</strong>${data.status}</p>
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
        const userId = document.getElementById("ViewUserIDforOrders").value.trim();
        
        if (!orderId || !userId) {
        document.getElementById("viewbyIDMessage").innerText = "Order ID and User ID are required.";
        return;
        }

        console.log(" Fetching OrderID:", orderId, "| UserID:", userId);

        try{
            const res = await fetch(`/InventorySystem/order?action=viewByOrderId&orderId=${encodeURIComponent(orderId)}&userId=${encodeURIComponent(userId)}`, {
                method : "GET"
            })

            if(res.ok){
                const OrderInfobyOrderID = await res.json();
                viewSection.innerHTML = "<h3>Order Information </h3>";
                for(let i=0; i<OrderInfobyOrderID.length; i++){
                    const data = OrderInfobyOrderID[i];
                    const jsonDatabyOrderID = `
                        <p><strong>OrderID:</strong>${data.orderId}</p>
                        <p><strong>UserID:</strong>${data.userId.userId}</p>
                        <p><strong>OrderDate:</strong>${data.orderDate}</p>
                        <p><strong>CustomerName:</strong>${data.customerName}</p>
                        <p><strong>Status:</strong>${data.status}</p>
                        <p><strong>Total Price:</strong>${data.total_Price}</p>
                        <hr>
                    `
                    viewSection.innerHTML += jsonDatabyOrderID;
                }
            }
            else{
                const error = await res.text();
                console.warn(" Server error:", error);
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
        const userId = document.getElementById("viewUserID").value.trim();
        
        if (!userId) {
                document.getElementById("viewByUserIDMessage").innerText = "User ID is required.";
                return;
            }

        try{
            const res = await fetch(`/InventorySystem/order?action=viewByUserId&userId=${encodeURIComponent(userId)}`, {
                method : "GET"
            })
            
        console.log("UserID:", userId);


            if(res.ok){
                const orderbyUserID = await res.json();

                viewbyuserIDsection.innerHTML = "<h3>Order Information</h3>";
                for(let i=0; i<orderbyUserID.length; i++){
                    const data = orderbyUserID[i];
                    const jsonDatabyUserID = `
                        <p><strong>OrderID:</strong>${data.orderId}</p>
                        <p><strong>UserID:</strong>${data.userId.userId}</p>
                        <p><strong>OrderDate:</strong>${data.orderDate}</p>
                        <p><strong>CustomerName:</strong>${data.customerName}</p>
                        <p><strong>Status:</strong>${data.status}</p>
                        <p><strong>Total Price:</strong>${data.total_Price}</p>
                        <hr>
                    `
                    viewbyuserIDsection.innerHTML += jsonDatabyUserID;
                }
            }
            else{
                const error = await res.text();
                console.warn(" Server error:", error);
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

        const orderId = document.getElementById("viewPriceID").value.trim();

        try{
            const res = await fetch(`/InventorySystem/order?action=getPrice&orderId=${encodeURIComponent(orderId)}`, {
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
