// login.js - 适配Thymeleaf
function refreshCaptcha() {
    var img = document.getElementById('captchaImg');
    if (!img) return;

    // 加时间戳防止缓存
    var timestamp = new Date().getTime();
    img.src = '/captcha?t=' + timestamp;
}

// 前端简单校验
function validateForm() {
    var user = document.getElementById('username')?.value.trim();
    var pwd = document.getElementById('password')?.value.trim();
    var cap = document.getElementById('captcha')?.value.trim();

    if (!user || !pwd) {
        alert('请输入用户名和密码');
        return false;
    }
    if (!cap || cap.length < 1) {
        alert('请输入验证码');
        return false;
    }
    return true;
}

// 页面加载完成后初始化
document.addEventListener('DOMContentLoaded', function() {
    // 确保验证码图片可以点击刷新
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