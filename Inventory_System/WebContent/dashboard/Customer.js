//Dashboard.html
console.log("🟢 JS is loading");
document.addEventListener("DOMContentLoaded", () => {
  console.log("🟢 DOM is loading");

  const role = localStorage.getItem("role");
  if (!role || role !== "customer") {
    alert("Unauthorized access. Redirecting to login page.");
    window.location.href = "../User/login.html";
 }

  document.getElementById("logoutBtn").addEventListener("click", () => {
  localStorage.clear();
  window.location.href = "../User/logout.html";
 });
})
