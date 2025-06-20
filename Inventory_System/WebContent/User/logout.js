console.log("🟢 logout page is loading");

localStorage.removeItem("role");
localStorage.removeItem("userId");
localStorage.removeItem("userName"); 

localStorage.clear();


setTimeout(() => {
    window.location.href = "../User/login.html";
}, 5000);
