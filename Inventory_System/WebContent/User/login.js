console.log("🟢 login.js loaded");

document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM ready");

    const form = document.getElementById("loginform");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const userId = document.getElementById("userId").value.trim();
        const userName = document.getElementById("userName").value.trim();
        const passCode = document.getElementById("passCode").value.trim();
        const role = document.getElementById("roleUser").value.trim();

        console.log("Submitting login for:", userId, userName, role);

        try {
            const res = await fetch("/InventorySystem/user", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: new URLSearchParams({
                    action: "isValidUser",
                    userId,
                    userName,
                    passCode,
                    roleUser: role
                })
            });

            const result = await res.text();
            console.log("Server response:", result);

            if (res.ok && result.includes("Logged In")) {
                localStorage.setItem("userId", userId);
                localStorage.setItem("userName", userName);
                localStorage.setItem("passCode", passCode);
                localStorage.setItem("role", role);

                if (role === "admin" || role === "staff") {
                    window.location.href = "/InventorySystem/dashboard/AdminAndStaffDashboard.html";
                } else if (role === "customer") {
                    window.location.href = "/InventorySystem/dashboard/CustomerDashboard.html";
                }
            } else {
                const error = await res.text();
                console.log("Error while login :", error);
                document.getElementById("LoginMessage").innerText = error, "Login failed. Check your credentials.";
            }
        } catch (err) {
            console.error("Login error:", err);
            document.getElementById("LoginMessage").innerText = "Server error. Try again.";
        }
    });
});
