console.log("🟢 JavaScript is loading");
document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM ready");

    const form = document.getElementById("deleteForm");
    form.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("userID").value.trim();
        const userName = document.getElementById("userName").value.trim();

        try{
            const response = await fetch(`/InventorySystem/user?action=UserDeletesTheirAccount&userId=${encodeURIComponent(userId)}&userName=${encodeURIComponent(userName)}`, {
                method : "DELETE"
            });

            const result = await response.text();
            if(response.ok && result.toLowerCase().includes("account deleted successfully")){
                alert("Account deleted Successfully, redirecting you to User registration!");

                setTimeout(() => {
                    window.location.href = "../User/register.html";
                }, 5000)

            }
            else{
                const errorMessage = await response.json();
                console.log(errorMessage);
                document.getElementById("DeleteAccountMessage").innerText = errorMessage.error;
            }
        }
        catch(error){
            console.error("Error while deleting account:", error);
            document.getElementById("DeleteAccountMessage").innerText = "Server Error, Try again later!";
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