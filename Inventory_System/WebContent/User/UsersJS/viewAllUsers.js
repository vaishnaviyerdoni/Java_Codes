document.addEventListener("DOMContentLoaded", () => {
    const userInfoSection = document.getElementById("userInfoSection");
    const form = document.getElementById("ViewUsers");
    form.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("userID").value.trim();
        
        try{
            const response = await fetch(`/user?action=viewUserInfo&userId=${encodeURIComponent(userId)}`, {
                method : "GET"
            });

            const userInfo = await response.json();
            if(response.ok){
                userInfoSection.innerHTML = "<h3>User Information</h3>";

                for (let i = 0; i < userInfo.length; i++){
                    const data = userInfo[i];

                    const jsonData = `
                        <p><strong>UserID:</strong> ${data.userId}</p>
                        <p><strong>UserName:</strong> ${data.userName}</p>
                        <p><strong>Email ID:</strong> ${data.email}</p>
                        <p><strong>PassCode</strong> ${data.passCode}</p>
                        <p><strong>Role:</strong> ${data.role}</p>
                        <hr>
                    `
                    userInfoSection.innerHTML += jsonData;
                }
            }
        }
        catch(error){
            console.error("Error:", error);
            document.getElementById("viewMessage").innerText = "Server Error, Try again later!";
        }
    });
});
