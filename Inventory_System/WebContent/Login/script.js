// WebContent/js/login.js
document.getElementById("loginForm").addEventListener("submit", function(e) {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const passcode = document.getElementById("passcode").value;
    const role = document.getElementById("role").value;

    fetch(`/user?action=login&username=${username}&passcode=${passcode}&role=${role}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Login failed");
            }
            return response.json();
        })
        .then(user => {
            // Store user info if needed (e.g., in localStorage)
            localStorage.setItem("userId", user.userId);
            localStorage.setItem("username", user.username);
            localStorage.setItem("role", user.role);

            // Redirect to dashboard
            window.location.href = "dashboard.html";
        })
        .catch(error => {
            document.getElementById("message").textContent = "Invalid credentials or role.";
            console.error("Error:", error);
        });
});
