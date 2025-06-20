//Dashboard.html
const role = localStorage.getItem("role");
if (!role || role !== "customer") {
    alert("Unauthorized access. Redirecting to login page.");
    window.location.href = "../User/login.html";
}

document.getElementById("logoutBtn").addEventListener("click", () => {
  localStorage.clear();
  window.location.href = "../User/logout.html";
});