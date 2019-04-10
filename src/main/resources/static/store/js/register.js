$(function() {
  window.localStorage.sendLock = 0;
})

$("#register").click(function () {
  var email = $("#email").val();
  var password = $("#password").val();
  var code = $("#code").val();

  if (password.length <= 6){
    alert("密码太短！");
    return;
  }

  var url = "http://" + window.location.host + "/store/register"
  var message = {
    "email": email,
    "password":password,
    "code":code
  };

  $.post(url,message,function(result) {
    if (result != null) {
      alert("您的餐厅辨识码为"+result+",该号码用于登陆，请妥善保存。");
      window.localStorage.account = result;
    } else {alert("Failed!");}
  });
})

$("#send").click(function () {
  if(window.localStorage.sendLock == 1){return;}
  window.localStorage.sendLock = 1;
  var email = $("#email").val();

  if (email == ""){
    alert("请输入邮箱");
    return;
  }

  var url = "http://" + window.location.host + "/store/send"
  var message = {
    "email": email,
  };

  $.post(url,message,function(result) {
    if (result == "Done") {
      alert("验证码已发送!");
    } else {alert("Failed!");}
    window.localStorage.sendLock = 0;
  });
})


$(".note").click(function(){
  jump();
})

function jump() {
  $(".card").animate({
    left: '-400px'
  }, "1s", function() {
    window.location.href = "./login.html";
  });
}
