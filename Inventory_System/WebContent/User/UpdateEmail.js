console.log("🟢 Js is loading");
document.addEventListener("DOMContentLoaded", () => { 
    console.log("🟢 DOM ready");

    const form = document.getElementById("UpdateEmail");
    form.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("userID").value.trim();
        const nEmail = document.getElementById("newEmail").value.trim();
        const userName = document.getElementById("userName").value.trim();

        try{
            const response = await fetch("/InventorySystem/user", {
                method : "PUT",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({
                    action : "UpdateEmail",
                    userId,
                    nEmail,
                    userName
                })
            })

            const result = await response.text();
            if(response.ok && result.includes("Email Updated")){
                alert("Email Updated!");
            }
            else{
                document.getElementById("UpdateEmailMessage").innerText = "Failed to update Email!";
            }
        }
        catch(error){
            console.error("Error while updating email:", error);
            document.getElementById("UpdateEmailMessage").innerText = "Server Error, Try again later!";
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