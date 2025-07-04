console.log("🟢 Js is loading");
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("deleteUserForm");
    console.log("🟢 DOM ready");

    form.addEventListener("submit", async(e) => {
        e.preventDefault();
        const AdminUserId = document.getElementById("AdminID").value.trim();
        const UserId = document.getElementById("DeleteUserID").value.trim();

        try{
            const response = await fetch(`/InventorySystem/user?action=AdminDeletesUser&AdminUserId=${encodeURIComponent(AdminUserId)}&UserId=${encodeURIComponent(UserId)}`, {
                method : "DELETE"
            });
        

            const result = await response.text();
            if (response.ok && result.toLowerCase().includes("deleted")) {
                console.log("User deleted successfully!");
                document.getElementById("DeleteMessage").innerText = result;

            } else {
                const errorMessage = await response.json();
                console.log("Failed to delete user:", errorMessage);
                document.getElementById("DeleteMessage").innerText = errorMessage.error;
            }
        }
        catch(error){
            document.getElementById("DeleteMessage").innerText = "Server Error, try again later!";
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