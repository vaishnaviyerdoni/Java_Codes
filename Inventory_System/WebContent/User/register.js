console.log("🟢 register page is loading");
document.addEventListener("DOMContentLoaded", () =>{
    console.log("🟢 DOM ready");
    const form = document.getElementById("RegisterForm");
    form.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userName = document.getElementById("userName").value.trim();
        const passCode = document.getElementById("passCode").value.trim();
        const email = document.getElementById("email").value.trim();
        const roleUser = document.getElementById("roleUser").value.trim();

        try{

            const res = await fetch("/InventorySystem/user", {
                method : "POST", 
                headers : {"Content-Type" : "application/x-www-form-urlencoded"},
                body : new URLSearchParams({
                    action : "RegisterUser",
                    userName,
                    passCode,
                    email,
                    roleUser
                })
            });

            const result = await res.text();
            if(res.ok && result.includes("User Registered Successfully")){
                window.location.href = "../User/login.html";
            }
            else{
                    document.getElementById("RegisterMessage").innerText = "Registration Failed, Check the credentials!";
                }
        }
        catch(error){
            console.error("Login error", error);
            document.getElementById("RegisterMessage").innerText = "Server Error, Try again later!";
        }
    })
})