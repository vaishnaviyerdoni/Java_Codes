//Dashboard.html
const role = localStorage.getItem('role');
if(!role){
    alert('No user found. redirecting to login page');
    window.location.href='../User/login.html';
}

document.getElementById('logoutBtn').addEventListener('click', ()=>{
    localStorage.removeItem('role');
    window.location.href = '../User/login.html';
})
