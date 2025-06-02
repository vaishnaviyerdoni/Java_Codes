document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("UpdatePassCodeForm");
    form.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userId = document.getElementById("userID").value.trim();
        const nPasscode = document.getElementById("newPassCode").value.trim();
        const userName = document.getElementById("userName").value.trim();
        
        try{
            const response = await fetch("/user", {
                method : "PUT",
                headers : {"Content-Type" : "application/json"},
                body : JSON.stringify({
                    action : "UpdatePassCode",
                    userId,
                    nPasscode,
                    userName
                })
            })

            const result = await response.text();
            if(response.ok && result.includes("PassCode updated Successfully!")){
                alert("PassCode Updated Successfully!");
            }
            else{
                document.getElementById("UpdatePasscodeMessage").innerText = "Failed to update PassCode, try again later!";
            }
        }
        catch(error){
            console.error("Error while updating passcode: ", error);
            document.getElementById("UpdatePasscodeMessage").innerText = "Server error, try again later";
        }
    })
})