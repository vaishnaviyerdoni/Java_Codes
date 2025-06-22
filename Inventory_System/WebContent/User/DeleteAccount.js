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
            if(response.ok && result.includes("Account deleted successfully!")){
                alert("Account deleted!");
            }
            else{
                document.getElementById("DeleteAccountMessage").innerText = "Account could not be deleted, try again later!";
            }
        }
        catch(error){
            console.error("Error while deleting account:", error);
            document.getElementById("DeleteAccountMessage").innerText = "Server Error, Try again later!";
        }
    })
})