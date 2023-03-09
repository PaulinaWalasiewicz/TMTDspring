const container = document.querySelector(".container"),
      pwShowHide = document.querySelectorAll(".showHidePw"),
      pwFields = document.querySelectorAll(".password"),
      signUp = document.querySelector(".signup-link"),
      login = document.querySelector(".login-link"),
      buttonLogIn = document.querySelector(".button input");

    // Email Validtion
    function checkEmail() {
        const emaiPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;
        if (!emailInput.value.match(emaiPattern)) {
            return emailField.classList.add("invalid"); //adding invalid class if email value do not mathced with email pattern
        }
        emailField.classList.remove("invalid"); //removing invalid class if email value matched with emaiPattern
    }
  

    //   js code to show/hide password and change icon
    pwShowHide.forEach(eyeIcon =>{
        eyeIcon.addEventListener("click", ()=>{
            pwFields.forEach(pwField =>{
                if(pwField.type ==="password"){
                    pwField.type = "text";

                    pwShowHide.forEach(icon =>{
                        icon.classList.replace("uil-eye-slash", "uil-eye");
                    })
                }else{
                    pwField.type = "password";

                    pwShowHide.forEach(icon =>{
                        icon.classList.replace("uil-eye", "uil-eye-slash");
                    })
                }
            }) 
        })
    })
    // Password Validation
    function createPass() {
        const passPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        if (!passInput.value.match(passPattern)) {
            return passField.classList.add("invalid"); //adding invalid class if password input value do not match with passPattern
        }
        passField.classList.remove("invalid"); //removing invalid class if password input value matched with passPattern
    }
  
    // Confirm Password Validtion
    function confirmPass() {
        if (passInput.value !== cPassInput.value || cPassInput.value === "") {
            return cPassField.classList.add("invalid");
        }
        cPassField.classList.remove("invalid");
    }
  
    // js code to appear signup and login form
    signUp.addEventListener("click", ( )=>{
        container.classList.add("active");
    });
    login.addEventListener("click", ( )=>{
        container.classList.remove("active");
    });

    clickLogin = (e) => {
        e.preventDefault();
        fetch("API Address", {
            method: "POST",
            body: JSON.stringify({
                email: this.state.idValue,
                password: this.state.pwField
            })
        })
        .then((response) => response.json())
        .then((result) => {
            if(result.message = "SUCCESS"){
                alert("You are logged in!");
                this.gotoMain();
            } else {
                alert("Please check your login information");
            }
        })
    }

    // Calling Funtion on Form Sumbit
    form.addEventListener("submit", (e) => {
    e.preventDefault(); //preventing form submitting
    checkEmail();
    createPass();
    confirmPass();
  
    //calling function on key up
    emailInput.addEventListener("keyup", checkEmail);
    passInput.addEventListener("keyup", createPass);
    cPassInput.addEventListener("keyup", confirmPass);
  
    if (
      !emailField.classList.contains("invalid") &&
      !passField.classList.contains("invalid") &&
      !cPassField.classList.contains("invalid")
    ) {
      location.href = form.getAttribute("action");
    }
  });
