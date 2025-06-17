console.log("🟢 JS is loading");

document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM ready");

    const userInfoSection = document.getElementById("userInfoSection");
    const form = document.getElementById("ViewUsers");
    form.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("userID").value.trim();
        
        try{
            const response = await fetch(`/InventorySystem/user?action=viewUserInfo&userId=${encodeURIComponent(userId)}`, {
                method : "GET"
            });

            if(response.ok){
                const userInfo = await response.json();
                userInfoSection.innerHTML = "<h3>User Information</h3>";

                for (let i = 0; i < userInfo.length; i++){
                    const data = userInfo[i];

                    const jsonData = `
                        <p><strong>UserID:</strong> ${data.userId}</p>
                        <p><strong>UserName:</strong> ${data.userName}</p>
                        <p><strong>Email ID:</strong> ${data.email}</p>
                        <p><strong>PassCode</strong> ${data.passcode}</p>
                        <p><strong>Role:</strong> ${data.role}</p>
                        <hr>
                    `
                    userInfoSection.innerHTML += jsonData;
                }
            }
            else{
                const errorText = await response.text();
                console.log("Server response:", errorText);
                document.getElementById("viewMessage").innerText = "Customer cannot access the data!";
            }
        }
        catch(error){
            console.error("Error:", error);
            document.getElementById("viewMessage").innerText = "Server Error, Try again later!";
        }
    });
});
