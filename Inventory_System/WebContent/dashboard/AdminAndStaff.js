// dashboard.js
console.log("🟢 JS is loading");
document.addEventListener("DOMContentLoaded", ()=>{
  console.log("🟢 DOM is loading");
  const role = localStorage.getItem("role");

if (!role) {
  alert("No user role found, redirecting to login!");
  window.location.href = "../User/login.html";
  return;
}

const deleteUserBtn = document.getElementById("deleteUserBtn");
const deleteOrderBtn = document.getElementById("deleteOrderBtn")
const deleteInventoryBtn = document.getElementById("deleteInventoryBtn");
const updateInventoryBtn = document.getElementById("updateInventoryBtn");

if (role === "staff") {
  if (deleteUserBtn) deleteUserBtn.style.display = "none";
  if (deleteOrderBtn) deleteOrderBtn.style.display = "none";
  if (deleteInventoryBtn) deleteInventoryBtn.style.display = "none";
  if (updateInventoryBtn) updateInventoryBtn.style.display = "none";
}

document.getElementById("logoutBtn").addEventListener("click", () => {
  localStorage.clear();
  window.location.href = "../User/logout.html";
});

})