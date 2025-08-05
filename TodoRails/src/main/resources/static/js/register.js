function validateForm(){
    const errors = [];
    try{

        //Get form fields
        const username = document.getElementById("username").value.trim();
        const email = document.getElementById("email").value.trim();
        const password = document.getElementById("password").value.trim();
        const confirmPassword = document.getElementById("confirmPassword").value.trim();
        const hobbies = document.querySelectorAll('input[name="hobbies"]:checked');


    //Check if the username is empty or not
    if(!username){
        errors.push("Username cannot be blank.");
    }

    //Check if the username is between 3 and 20 characters or not
    if(!(username.length >= 3 && username.length <= 20)){
        errors.push("Username must be between 3 and 20 character");
    }

    //Check if the email is empty or not first, then check format
    if(!email){
        errors.push("Email cannot be blank.");

    }
    else if(!email.includes("@") || !email.includes(".") || email.indexOf("@") > email.lastIndexOf(".")){
        errors.push("Please enter a valid email address.");

    }

    //check if password is empty or not
    if(!password){
        errors.push("Password cannot be blank.");
    }
    //check if password is atleast 6 characters or not
    if(!(password.length >= 6)){
        errors.push("Password must be min 6 characters.");
    }

    //check if password matches confirm password or not
    if(password && password !== confirmPassword){
        errors.push("Password and Confirm Password must be same");
    }

    //check if at least one hobby is selected
    if(hobbies.length === 0){
        errors.push("Please select at least one hobby.");
    }
    }


    catch(error){
        errors.push("Error handling fields!");
        console.error("Validation error : ",error);
        return false;
    }
        //Displays errors if any exist
        if(errors.length > 0){
            displayClientErrors(errors);
            //prevent form submission
            return false;
        }
    return true;
}

//This function displays client-side error messages in the error-messages-client box
function displayClientErrors(messages){
    const errorList = document.getElementById("error-list"); //The list hold each error message
    const errorMessagesDiv = document.getElementById("error-messages-client"); //The error message container

    //Clear any previous error messages
    errorList.innerHTML = "";

    // Loop through messages array and create list items for each error
    messages.forEach(message =>{
        const li = document.createElement("li"); // Create a list item
        li.textContent = message; // Set the text of the list item to the error message
        errorList.appendChild(li); // Add the list item to the error list
    });
    errorMessagesDiv.style.display = "block";
}