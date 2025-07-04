console.log("🟢 The page is getting loaded");

document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM ready");
    const formToGetAllOrders = document.getElementById("getAllOrdersForm");
    formToGetAllOrders.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("GetAllUserID").value.trim();

        try{
            const res = await fetch(`/InventorySystem/order?action=getAllOrders&userId=${encodeURIComponent(userId)}`, {
                method : "GET"
            })

            if(res.ok){
                const OrderInfo = await res.json();
                console.log(OrderInfo);

                if(OrderInfo.length === 0){
                    document.getElementById("orderInfoTable").innerHTML = "<p>Order Information not available</p>";
                }
                else{
                    let Table = `
                        <table border="1" cellpadding="8" cellspacing="0">
                          <thead>
                            <tr>
                              <th>OrderID</th>
                              <th>UserID</th>
                              <th>OrderDate</th>
                              <th>Customer Name</th>
                              <th>Status </th>
                              <th>Total Price</th>
                    `;

                    OrderInfo.forEach(data => {
                        Table += `
                            <tr>
                                <td>${data.orderId}</td>
                                <td>${data.userId.userId}</td>
                                <td>${data.orderDate}</td>
                                <td>${data.customerName}</td>
                                <td>${data.status}</td>
                                <td>${data.total_Price}</td>
                            <tr>
                        `;
                    });

                    Table += "</tbody></table>";
                    document.getElementById("orderInfoTable").innerHTML = Table;
                }
            }
            else{
                const errorMessage = await res.json();
                console.log(errorMessage);
                document.getElementById("getAllOrdersMessage").innerText = errorMessage.error;
            }
        }
        catch(error){
            console.error("Error while fetching data:", error);
            document.getElementById("getAllOrdersMessage").innerText = "Server Error, try again later!";
        }
    })

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
                console.log(OrderInfobyOrderID);

                if(OrderInfobyOrderID.length === 0){
                    document.getElementById("orderbyIDTable").innerHTML = "<p>Order Information not available</p>";
                }
                else{
                    let Table = `
                        <table border="1" callpadding="8" cellspacing="0">
                          <thead>
                            <tr>
                              <th>OrderID</th>
                              <th>UserID</th>
                              <th>OrderDate</th>
                              <th>Customer Name</th>
                              <th>Status </th>
                              <th>Total Price</th>
                    `;

                    OrderInfobyOrderID.forEach(data => {
                        Table += `
                            <tr>
                                <td>${data.orderId}</td>
                                <td>${data.userId.userId}</td>
                                <td>${data.orderDate}</td>
                                <td>${data.customerName}</td>
                                <td>${data.status}</td>
                                <td>${data.total_Price}</td>
                            <tr>
                        `;
                    });

                    Table += "</tbody></table>";
                    document.getElementById("orderbyIDTable").innerHTML = Table;
                }
            }
            else{
                const errorMessage = await res.json();
                console.log(" Server error:", errorMessage);
                document.getElementById("viewbyIDMessage").innerText = errorMessage.error;
            }
        }
        catch(error){
            console.error("Error while fetching data:", error);
            document.getElementById("viewbyIDMessage").innerText = "Server Error, try again later!";
        }
    })

    const ViewByUserIDForm = document.getElementById("viewbyUserIDForm");
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
                console.log(orderbyUserID);

                if(orderbyUserID.length === 0){
                    document.getElementById("orderbyUserIDTable").innerHTML = "<p>Order Information not available</p>";
                }
                else{
                    let Table = `
                        <table border="1" callpadding="8" cellspacing="0">
                          <thead>
                            <tr>
                              <th>OrderID</th>
                              <th>UserID</th>
                              <th>OrderDate</th>
                              <th>Customer Name</th>
                              <th>Status </th>
                              <th>Total Price</th>
                    `;

                    orderbyUserID.forEach(data => {
                        Table += `
                            <tr>
                                <td>${data.orderId}</td>
                                <td>${data.userId.userId}</td>
                                <td>${data.orderDate}</td>
                                <td>${data.customerName}</td>
                                <td>${data.status}</td>
                                <td>${data.total_Price}</td>
                            <tr>
                        `;
                    });

                    Table += "</tbody></table>";
                    document.getElementById("orderbyUserIDTable").innerHTML = Table; 
                }
            }
            else{
                const errorMessage = await res.json();
                console.log("Error while fetching the order information:", errorMessage)
                document.getElementById("viewByUserIDMessage").innerText = errorMessage.error;
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
