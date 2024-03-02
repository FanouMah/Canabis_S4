var passwordToggle = document.getElementById('passwordToggle');
var confirmPasswordToggle = document.getElementById('confirmPasswordToggle');
var passwordField = document.getElementById('password');
var confirmPasswordField = document.getElementById('confirmPassword');

if(passwordToggle != null && passwordField != null){
    passwordToggle.addEventListener('click', function() {
        if (passwordField.type === 'password') {
            passwordField.type = 'text';
            passwordToggle.querySelector('i').classList.remove('bi-eye');
            passwordToggle.querySelector('i').classList.add('bi-eye-slash');
        } else {
            passwordField.type = 'password';
            passwordToggle.querySelector('i').classList.remove('bi-eye-slash');
            passwordToggle.querySelector('i').classList.add('bi-eye');
        }
    });
}

if(confirmPasswordField !=null && confirmPasswordToggle != null) {
    confirmPasswordToggle.addEventListener('click', function() {
        if (confirmPasswordField.type === 'password') {
            confirmPasswordField.type = 'text';
            confirmPasswordToggle.querySelector('i').classList.remove('bi-eye');
            confirmPasswordToggle.querySelector('i').classList.add('bi-eye-slash');
        } else {
            confirmPasswordField.type = 'password';
            confirmPasswordToggle.querySelector('i').classList.remove('bi-eye-slash');
            confirmPasswordToggle.querySelector('i').classList.add('bi-eye');
        }
    });
}