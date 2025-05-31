document.addEventListener("DOMContentLoaded", () =>{
    const form = document.getElementById("RegisterForm");
    form.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userName = document.getElementById("userName").value.trim();
        const passCode = document.getElementById("passCode").value.trim();
        const email = document.getElementById("email").value.trim();
        const role = document.getElementById("role").value.trim();

        try{

            const res = await fetch("/user", {
                method : "POST", 
                headers : {"Content-Type" : "application/x-www-form-urlencoded"},
                body : new URLSearchParams({
                    action : "RegisterUser",
                    userName,
                    passCode,
                    email,
                    roleUser : role
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