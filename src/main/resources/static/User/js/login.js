$(function() {
  window.localStorage.sendLock = 0;
  window.history.replaceState(null, null, "/");
  $("#account").val(window.localStorage.account);
})

$("#login").click(function functionName() {
  var account = $("#account").val();
  var password = $("#password").val();


  $.post("/user/login", {
      "email": account,
      "code": password
    },
    function(result) {
      if (result == "Done") {
        window.localStorage.account = account;
        console.log("登陆成功！");
        window.location.href = './user/main.html';
      } else {
        alert("Failed!");
      }
    });
})

$("#send").click(function () {
  if(window.localStorage.sendLock == 1){return;}
  window.localStorage.sendLock = 1;
  var account = $("#account").val();
  var message = {"email": account};
  $.post("/user/send",message,

    function(result) {
      if (result == "Done") {
        alert("验证码已发送!");
      }else if (result == "Error") {
        alert("系统错误!");
      }else {}
      window.localStorage.sendLock = 0;
    });
})


$("#store").click(function () {
  window.location.href = './store/login.html';
})
$("#admin").click(function () {
  window.location.href = './admin/login.html';
})
