// ================= ALERT FUNCTION =================
function showAlert(message, type = "success") {
    const alertBox = document.getElementById("alertBox");

    if (!alertBox) return;

    alertBox.innerText = message;
    alertBox.className = "alert " + type + " show";

    setTimeout(() => {
        alertBox.classList.remove("show");
    }, 3000);
}

// ================= REGISTER =================


// ================= DELETE TASK =================
function deleteTask(id) {
    if (confirm("Are you sure you want to delete this task?")) {
        window.location.href = "/tasks/delete/" + id;
    }
}

// ================= FILTER TASK =================
function filterTasks(status) {
    window.location.href = "/tasks/filter?status=" + status;
}

// ================= FORM VALIDATION =================
function validateForm() {
    const inputs = document.querySelectorAll("input[required]");

    for (let input of inputs) {
        if (input.value.trim() === "") {
            showAlert("Please fill all fields!", "error");
            return false;
        }
    }
    return true;
}

// ================= AUTO HIDE ALERT =================
window.onload = function () {
    const alertBox = document.getElementById("alertBox");

    if (alertBox && alertBox.innerText.trim() !== "") {
        alertBox.classList.add("show");

        setTimeout(() => {
            alertBox.classList.remove("show");
        }, 3000);
    }
};