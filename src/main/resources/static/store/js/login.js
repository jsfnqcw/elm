
$("#login").click(function functionName() {
  var account = $("#account").val();
  var password = $("#password").val();


  var url = "http://" + window.location.host + "/store/login"
  var message = {
    "account": account,
    "password":password
  };

  $.post(url,message,function(result) {
    if (result == "Done") {
      console.log("登陆成功！");
      window.location.href = './main.html';
    } else {alert("Failed!");}
  });
})





$(".note").click(function(){
  jump();
})

function jump() {
  $(".card").animate({
    left: '-400px'
  }, "1s", function() {
    window.location.href = "./register.html";
  });
}
