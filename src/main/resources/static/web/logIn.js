function Authentification() {



    this.init = function() {

        this.logIn();
        this.signUp();

    };


    this.logIn = function() {
    var login = document.querySelector("#login");
    var notification = document.querySelector("#notification")
    login.addEventListener("click", function(e) {



    var formData = new FormData();

    formData.append('username', document.getElementById('username').value);
    formData.append('password', document.getElementById('password').value);

    fetch('/api/login', {
      method: 'POST',
      body: formData
    })
    .then(function(response) {
        if(response.ok) {
        notification.textContent = "Logged In"
        location.href = "games.html"
        } else {
        notification.textContent = "Please state valid Username and Password"
        }
    })

    .catch(error => console.log("inkebinke"))


    })
}

    this.signUp = function() {
    var signUpLogin = document.querySelector("#sign-up-login");
    var signUpNotification = document.querySelector("#sign-up-notification")
    signUpLogin.addEventListener("click", function(e) {

    var formData = new FormData();



    formData.append('username', document.getElementById('sign-up-username').value);
    formData.append('password', document.getElementById('sign-up-password').value);

    fetch('/api/players', {
      method: 'POST',
      body: formData
    })
    .then(function(response) {
        if(response.ok) {
        signUpNotification.textContent = "Created!"
        } else {
        signUpNotification.textContent = "Incorrect!"
        }
    })

    .catch(error => console.log("ink"))


    })
}
}


var logIn = new Authentification();
logIn.init();





