document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("deleteUserForm");

    form.addEventListener("submit", async(e) => {
        e.preventDefault();
        const AdminUserId = document.getElementById("AdminID").value.trim();
        const UserId = document.getElementById("DeleteUserID").value.trim();

        try{
            const response = await fetch("/user", {
                method : "POST",
                headers : {"Content-Type" : "application/x-www-urlencoded"},
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
                document.getElementById("DeleteMessage").innerText = "Failed to delete User, try again later";
            }
        }
        catch(error){
            document.getElementById("DeleteMessage").innerText = "Server Error, try again later!";
        }
    })
})