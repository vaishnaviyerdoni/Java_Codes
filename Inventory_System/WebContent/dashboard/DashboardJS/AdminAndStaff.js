// dashboard.js
const role = localStorage.getItem('role');

if (!role) {
  alert('No user role found, redirecting to login.');
  window.location.href = '../User/login.html';
}

const deleteUserBtn = document.getElementById('deleteUserBtn');
const deleteOrderBtn = document.querySelector(
  "#orderManagementSection button[onclick*='DeleteOrders.html']"
);
const deleteInventoryBtn = document.querySelector(
  "#inventoryManagementSection button[onclick*='UpdateNDelete.html']"
);

if (role === 'staff') {
  deleteUserBtn.style.display = 'none';
  deleteOrderBtn.style.display = 'none';
  deleteInventoryBtn.style.display = 'none';
}

document.getElementById('logoutBtn').addEventListener('click', () => {
  localStorage.removeItem('role');
  window.location.href = '../login.html';
});
