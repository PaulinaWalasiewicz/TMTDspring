const container = document.querySelector(".container"),
      pwShowHide = document.querySelectorAll(".showHidePw"),
      pwFields = document.querySelectorAll(".password"),
      signUp = document.querySelector(".signup-link"),
      login = document.querySelector(".login-link"),
      buttonLogIn = document.querySelector(".button input");

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
