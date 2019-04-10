$(function() {
  window.history.replaceState(null, null, "/");
  $("#account").val(window.localStorage.adminAccount);
})

$("#login").click(function() {
  var account = $("#account").val();
  var password = $("#password").val();

  $.post("/admin/login", {
      "account": account,
      "password": password
    },
    function(result) {
      if (result == "Done") {
        window.localStorage.adminAccount = account;
        console.log("登陆成功！");
        window.location.href = 'admin/main.html';
      } else {
        alert("Failed!");
      }
    });
})
