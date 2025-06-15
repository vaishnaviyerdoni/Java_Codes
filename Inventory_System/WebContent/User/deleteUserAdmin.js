console.log("🟢 Js is loading");
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("deleteUserForm");
    console.log("🟢 DOM ready");

    form.addEventListener("submit", async(e) => {
        e.preventDefault();
        const AdminUserId = document.getElementById("AdminID").value.trim();
        const UserId = document.getElementById("DeleteUserID").value.trim();

        try{
            const response = await fetch("/InventorySystem/user", {
                method : "POST",
                headers : {"Content-Type" : "application/x-www-form-urlencoded"},
                body : new URLSearchParams({
                    action : "AdminDeletesUser",
                    AdminUserId,
                    UserId
                })
            });

            const result = await response.text();
            if(response.ok && result.includes("User was deleted successfully!")){
                alert("The user is deleted!");
            }
            else{
                console.log(result);
                document.getElementById("DeleteMessage").innerText = "Failed to delete User, try again later";
            }
        }
        catch(error){
            document.getElementById("DeleteMessage").innerText = "Server Error, try again later!";
        }
    })
})