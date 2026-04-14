function showSuccess(message) {
    const alertBox = document.getElementById("alertBox");
    alertBox.innerText = message;
    alertBox.classList.add("show");

    setTimeout(() => {
        alertBox.classList.remove("show");
    }, 3000);
}

// Register simulation
function registerUser(event) {
    event.preventDefault();

    showSuccess("Registration successful!");

    setTimeout(() => {
        window.location.href = "/login";
    }, 1500);
}

// Login simulation
function loginUser(event) {
    event.preventDefault();

    showSuccess("Login successful!");

    setTimeout(() => {
        window.location.href = "/dashboard";
    }, 1500);
}