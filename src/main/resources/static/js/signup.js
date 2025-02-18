document.addEventListener('DOMContentLoaded', function () {
    const signupForm = document.getElementById('signupForm');

    signupForm.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent default form submission

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('passwordcon').value;
        const email = document.getElementById('email').value;

        // Check if passwords match
        if (password !== confirmPassword) {
            alert("Passwords do not match!");
            return;
        }

        // Check if username is valid (only letters and spaces, no special characters)
        const usernameRegex = /^[a-zA-Z\s]+$/;
        if (!usernameRegex.test(username)) {
            alert("Username can only contain letters and spaces.");
            return;
        }

        // Prepare data for the request
        const data = {
            username,
            email,
            password
        };

        // Send the request to the server
        fetch('/req/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (response.ok) {
                window.location.href = "/req/login"; // Redirect to login page on success
            } else {
                response.json().then(data => {
                    alert("Signup failed: " + (data.message || "Unknown error")); // Show error message
                });
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("An error occurred. Please try again."); // Handle network errors
        });
    });
});
