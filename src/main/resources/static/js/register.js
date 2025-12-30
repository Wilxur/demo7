// register.js - 适配Thymeleaf
function refreshCaptcha() {
    var img = document.getElementById('captchaImg');
    if (!img) return;

    var timestamp = new Date().getTime();
    img.src = '/captcha?t=' + timestamp;
}

function validateRegisterForm() {
    var user = document.getElementById('username')?.value.trim();
    var pwd = document.getElementById('password')?.value.trim();
    var confirmPwd = document.getElementById('confirmPassword')?.value.trim();
    var email = document.getElementById('email')?.value.trim();
    var cap = document.getElementById('captcha')?.value.trim();

    // 用户名验证
    var usernameRegex = /^[a-zA-Z0-9_]{3,20}$/;
    if (!usernameRegex.test(user)) {
        alert('用户名长度3-20位，只能包含字母、数字、下划线');
        return false;
    }

    // 密码验证
    if (pwd.length < 6 || pwd.length > 20) {
        alert('密码长度6-20位');
        return false;
    }

    // 确认密码
    if (pwd !== confirmPwd) {
        alert('两次输入的密码不一致');
        return false;
    }

    // 邮箱验证
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert('请输入有效的邮箱地址');
        return false;
    }

    // 验证码验证
    if (!cap || cap.length < 1) {
        alert('请输入验证码');
        return false;
    }

    return true;
}

// 页面加载完成后初始化
document.addEventListener('DOMContentLoaded', function() {
    var img = document.getElementById('captchaImg');
    if (img) {
        img.onclick = refreshCaptcha;
    }

    // 自动聚焦到用户名输入框
    var usernameInput = document.getElementById('username');
    if (usernameInput) {
        usernameInput.focus();
    }
});