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

//This function can be reused to populate the dropdown
async function populateDropdown(select){
    try{
        //request to backend to gel all items
        const res = await fetch("/inventory?action=viewAll");
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
window.addEventListener("DOMContentLoaded", async() => {
    const selects = document.getElementsByClassName("select-item");

    //get all dropdowns
    for(const select of selects){
        await populateDropdown(select);
    }
})

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

    document.getElementById("itemsList").appendList(newItemRow);

    const newSelect = newItemRow.querySelector(".select-item");
    await populateDropdown(newSelect);
})

