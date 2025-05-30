document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("loginform");
    form.addEventListener("submit",  async(e) => {
        e.preventDefault();

        const userId = document.getElementById("userId").value.trim();
        const userName = document.getElementById("userName").value.trim();
        const passCode = document.getElementById("passCode").value.trim();
        const role = document.getElementById("role").value.trim();
        try{
            const res = await fetch("/user", {
                method : "POST",
                headers : {"Content-Type" : "application/x-www-form-urlencoded"},
                body : new URLSearchParams({
                    action : "isValidUser",
                    userId,
                    userName,
                    passCode,
                    roleUser : role
                })
            });

            const result = await res.text();
            if(res.ok && result.status.includes("Logged In")){

                //save all parameters to localStorage
                localStorage.setItem("userId", userId);
                localStorage.setItem("userName", userName);
                localStorage.setItem("passCode", passCode);
                localStorage.setItem("role", role);

                if(role === "admin" || role === "staff"){
                    window.location.href = "../dashboard/AdminAndStaffDashboard.html";
                }
                else if(role === "customer"){
                    window.location.href = "../dashboard/CustomerDashboard.html";
                }
                else{
                    document.getElementById("loginMessage").innerText = "Login Failed, Check the credentials!";
                }
            }
        }
        catch(error){
            console.error("Login error", error);
            document.getElementById("LoginMessage").innerText = "Server Error, Try again later!";
        }
    });
});