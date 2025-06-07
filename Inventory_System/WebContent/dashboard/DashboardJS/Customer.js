//Dashboard.html
const role = localStorage.getItem("role");
if (!role || role !== "customer") {
    alert("Unauthorized access. Redirecting to login page.");
    window.location.href = "../../User/UserHTML/login.html";
}

document.getElementById("logoutBtn").addEventListener("click", ()=>{
    localStorage.removeItem("role");
    window.location.href = "../../User/UserHTML/logout.html";
})
