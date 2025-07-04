console.log("🟢 JS is loading");

document.addEventListener("DOMContentLoaded", () => {
    console.log("🟢 DOM ready");

    const form = document.getElementById("ViewUsers");
    form.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("userID").value.trim();
        if (!userId) {
            document.getElementById("viewMessage").innerText = "Please enter a User ID!";
        return;
        }
        try{
            const response = await fetch(`/InventorySystem/user?action=viewUserInfo&userId=${encodeURIComponent(userId)}`, {
                method : "GET"
            });

            if(response.ok){
                const userInfo = await response.json();
                console.log(userInfo);

                if(userInfo.length === 0){
                    document.getElementById("viewAllUsersTable") = "<p>User Information Not available!what </p>";
                }
                else{
                    let Table = `
                        <table border="1" cellpadding="8" cellspacing="0">
                          <thead>
                            <tr>
                              <th>UserID</th>
                              <th>User Name</th>
                              <th>Email ID</th>
                              <th>PassCode</th>
                              <th>Role</th>
                            </tr>
                    `;

                    userInfo.forEach(data => {
                        Table += `
                            <tr>
                              <td>${data.userId}</td>
                              <td>${data.userName}</td>
                              <td>${data.email}</td>
                              <td>${data.passcode}</td>
                              <td>${data.role}</td>
                            </tr>
                        `;
                    });

                    Table += "</tbody></table>";
                    document.getElementById("viewAllUsersTable").innerHTML = Table;
                }
            }
            else{
                const errorMessage = await response.json();
                console.log("Server response:", errorMessage);
                document.getElementById("viewMessage").innerText = errorMessage.error;
            }
        }
        catch(error){
            console.error("Error:", error);
            document.getElementById("viewMessage").innerText = "Server Error, Try again later!";
        }
    });

    const BtntoDashboard = document.getElementById("gotoDashboard");
    if(BtntoDashboard){
        BtntoDashboard.addEventListener("click", ()=> {
            const role = localStorage.getItem("role");
            if(role === "customer"){
                window.location.href = "../dashboard/CustomerDashboard.html";
            }

            else if(role === "admin" || role === "staff"){
                window.location.href = "../dashboard/AdminAndStaffDashboard.html";
            }

            else{
                alert("role not found, redirecting to login!");
                setTimeout(() => {
                    window.location.href = "../User/login.html";
                }, 5000)
            }
        })
    }
});
