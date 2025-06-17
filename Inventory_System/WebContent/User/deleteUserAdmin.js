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
            } else {
                console.log("Failed to delete user");
                console.log("Server response:", result);
            }
        }
        catch(error){
            document.getElementById("DeleteMessage").innerText = "Server Error, try again later!";
        }
    })
})