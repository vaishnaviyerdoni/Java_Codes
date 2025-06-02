document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("deleteUserForm");

    form.addEventListener("submit", async(e) => {
        e.preventDefault();
        const adminID = document.getElementById("AdminID").value.trim();
        const deleteUserID = document.getElementById("DeleteUserID").value.trim();

        try{
            const response = await fetch("/user", {
                method : "DELETE",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({
                    action : "AdminDeletesUser",
                    adminID,
                    deleteUserID
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