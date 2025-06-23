console.log("🟢 JS is loading");
document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM ready");
    const placeOrder = document.getElementById("placeOrderForm");

    placeOrder.addEventListener("submit", async(e) => {
    	e.preventDefault();
    	const userId = document.getElementById("PlaceUserID").value.trim();
    	const orderDate = document.getElementById("orderDate").value.trim();
    	const customerName = document.getElementById("customerName").value.trim();

    	try{
            const res = await fetch("/InventorySystem/order", {
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
        }catch(error){
            console.error("Error occurred when placing order:", error);
            document.getElementById("placeOrderMessage").innerText = "Server Error try again later!";
           }
        })
    
      //This function reused to populate dropdowns
      async function populateDropdown(select){
         try{
            //request to backend to gel all items
            const res = await fetch("/InventorySystem/inventory?action=viewAll");
            const items = await res.json();

            //clear the existing options and add dafault 1st option
            select.innerHTML = '<option value="">--Select value--</option>';

            items.forEach(item => {
                //create new option element
                const option = document.createElement("option");
                option.value = item.itemName;  //set value to item name
                option.text = item.itemName;   //set visible text
                select.appendChild(option);    //add append option to select
            });
        }
        catch(error){
            console.error("Error ocurred when populating dropdown:", error);
        }
    }
    
     //This will run when page is loaded
     //It loops through all the existing dopdowns and populates them
     const selects = document.getElementsByClassName("select-item");
     //get all dropdowns
     for(const select of selects){
         populateDropdown(select);
     }

     //To handle another Item
     document.getElementById("addItem").addEventListener("click", async() => {

    //create new div to hold new item
    const newItemRow = document.createElement("div");

    //add class for styling and for future reference
    newItemRow.classList.add("item-Row")

    newItemRow.innerHTML = `
        <label> Item: </label>
        <select name="itemName" class="select-item" required>
            <option value="">--Select Item--</option>
        </select><br><br>
        
        <label> Quantity: </label>
        <input type="number" name="quantity" required min="1"><br><br>

        <label> Subtotal: </label>
        <input type="number" readonly><br><br>

        <button type="button" class="removeItem"></button>
    `;

    document.getElementById("itemsList").appendChild(newItemRow);

    const newSelect = newItemRow.querySelector(".select-item");
    await populateDropdown(newSelect);
    updateTotalPrice(); // Optional immediate refresh
   })

    //remove item from the form
    document.getElementById("itemsList").addEventListener("click", async(e) => {
    if(e.target.classList.contains("removeItem")){
        const row = e.target.closest(".item-Row");
        if(document.querySelectorAll(".item-Row").length){
            row.remove();
            updateTotalPrice(); // Refresh total
            }
        }
    })
    
    //to calculate subtotal
    document.getElementById("itemsList").addEventListener("change", async(e) => {
        const row = e.target.closest(".item-Row");
        const select = row.querySelector(".select-item");
        const quantityInput = row.querySelector('input[name="quantity"]');
        const subtotalInput = row.querySelector("input[readonly]");

        const itemName = select.value;
        const quantity = parseInt(quantityInput.value);

        if(itemName && quantity > 0){
            try{
                const res = await fetch(`/InventorySystem/inventory?action=getPriceByItemName&itemName=${encodeURIComponent(itemName)}`);
                const price = await res.json();
                subtotalInput.value = (price * quantity).toFixed(2);
                updateTotalPrice();
            }
            catch(error){
                console.error("Failed to update subtotal:", error);
            }
        }
    })

    //to calculate total
    function updateTotalPrice() {
        const subtotals = document.querySelectorAll(".item-Row input[readonly]");
        let total = 0;

        subtotals.forEach(input => {
            const subtotal = parseFloat(input.value);
            if (!isNaN(subtotal)) {
            total += subtotal;
            }
        });

        document.getElementById("totalPrice").value = total.toFixed(2);
    }

    //to submit all items
    document.getElementById("addItemsToOrderForm").addEventListener("submit", async (e) => {
        e.preventDefault();

        const orderId = document.getElementById("itemsOrderID").value.trim();
        const userId = document.getElementById("ordersUserID").value.trim();
        const itemRows = document.querySelectorAll(".item-Row");

        let allSuccess = true;

        for (const row of itemRows) {
            const itemName = row.querySelector(".select-item").value;
            const quantity = row.querySelector("input[name='quantity']").value;
            const subtotal = row.querySelector("input[readonly]").value;

            const params = new URLSearchParams({
                action: "addItems",
                orderId,
                userId,
                itemName,
                quantity,
                subtotal
            });

            try {
                const res = await fetch("/InventorySystem/order", {
                   method: "POST",
                   headers: { "Content-Type": "application/x-www-form-urlencoded" },
                   body: params
                });

                const result = await res.text();

                if (!res.ok || !result.includes("Order Placed")) {
                    allSuccess = false;
                    console.error("Error from server:", result);
                }
            } catch (error) {
                allSuccess = false;
                console.error("Error adding item:", error);
            }
        }

        if (allSuccess) {

            const total = parseFloat(document.getElementById("totalPrice").value);

            try{
                const updatePriceResult = await fetch("/InventorySystem/order",{
                    method : "PUT",
                    headers : {"Content-Type" : "application/json"},
                    body : JSON.stringify({
                        action : "updateTotalPrice",
                        price : total.toFixed(2),
                        orderId,
                        userId
                    }) 
                });
                
                const result = await updatePriceResult.text();
                if(updatePriceResult.ok && result.includes("total price updated")){
                    console.log("total Price updated!");
                }
                else{
                    console.log("Failed to update the total price of an order!");
                }
            }
            catch(error){
                console.error("Price was not updated:", error);
            }

            alert("Order placed successfully with all items!");
            document.getElementById("AddItemMessage").innerText = "";
        } else {
            document.getElementById("AddItemMessage").innerText = "Some items failed to add. Please check.";
        }
    });


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