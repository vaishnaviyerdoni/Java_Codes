document.addEventListener("DOMContentLoaded", () => {
    const userInfoSection = document.getElementById("userInfoSection");
    const form = document.getElementById("viewUsers");
    form.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("userId").value.trim();
        
        try{
            const response = await fetch("/user", {
                method : "GET",
                headers : {"Content-Type" : "application/x-www-form-urlencoded"},
                body : new URLSearchParams({
                    action : "viewUserInfo",
                    userId
                })
            });

            const userInfo = await response.json();
            if(response.ok){
                userInfoSection.innerHTML = "";

                for (let i = 0; i < userInfo.length; i++){
                    const data = userInfo.get(i);

                    const jsonData = `
                        <h3>User Information</h3>
                        <p><strong>UserID:</strong> ${data.i.userId}</p>
                        <p><strong>UserName:</strong> ${data.i.userName}</p>
                        <p><strong>Email ID:</strong> ${data.i.email}</p>
                        <p><strong>PassCode</strong> ${data.i.passCode}</p>
                        <p><strong>Role:</strong> ${data.i.role}</p>
                        <h5>-----------------------------</h5>
                    `
                    userInfoSection.innerHTML = jsonData;
                }
            }
        }
        catch(error){
            console.error("Error:", error);
            userInfoSection,innerHTML = "<p>Failed to retrieve data!</p>"
        }
    });
});
