var $ = layui.$;
var table = layui.table;
var layer = layui.layer;

var stringa = "<div class='layui-colla-item addressTable'><h2 class='layui-colla-title'><p id='add'>";
var stringb = "</p></h2><div class='layui-colla-content'><a onclick='deleteAdd(this)' class='layui-icon layui-icon-delete'></a></div></div>";
/////////////////基础信息读取
//////////////////////////////////



function bodyload(){

  /////////////用户信息读取
  var url = "http://" + window.location.host + "/user/info"
  $.post(url,function(result) {
    var obj = JSON.parse(result);
    $("#account").text(window.localStorage.account);
    $("#name").val(obj.name);
    $("#phone").val(obj.phoneNumber);
    $("#lvl").text("等级：" +obj.level+ " ,经验:"+obj.exp);
  });

  /////////////地址信息读取
  url = "http://" + window.location.host + "/user/getAddress"
  $.post(url,function(result) {
    var obj = JSON.parse(result);
    $.each(obj,function(i,n){
        var string = stringa + n.addressString + stringb;
        $("#addTable").prepend(string);
    });
    layui.element.render();
  });

}


//////////////////事件绑定
//////////////////////////////////
$('#phone1').click(function() {
  $("#phone").attr("disabled", false);
});

$('#phone2').click(function() {
  //上传修改信息
  var name = $('#name').val();
  var phone = $('#phone').val();

  var url = "http://" + window.location.host + "/user/modify"
  var message = {
    "name": name,
    "phoneNumber":phone
  };

  $.post(url,message,function(result) {ale(result);});
});


$('#name1').click(function() {
  $("#name").attr("disabled", false);
});

$('#name2').click(function() {
  //上传修改信息
  var name = $('#name').val();
  var phone = $('#phone').val();

  var url = "http://" + window.location.host + "/user/modify"
  var message = {
    "name": name,
    "phoneNumber":phone
  };
  $.post(url,message,function(result) {ale(result);});

});

$('#money1').click(function() {
  var url = "http://" + window.location.host + "/money/new"
  $.post(url,function(result) {ale(result);});
});



$('#cancelBu').click(function() {
  layer.open({
    content: "我确认要注销账号。",
    yes: function(index, layero) {
      cancel();
      layer.close(index); //如果设定了yes回调，需进行手工关闭
    }
  });
});

$('#ifmBut').click(function(){
  var addressString = $("#ifmMap").contents().find('#localvalue').val();
  var addressXY = $("#ifmMap").contents().find('#pointInput').val();

  if((addressString == "")||(addressXY == "")){
    ale("地址信息不能为空噢！");
    return
  }

  var message = {
    "addressString":addressString,
    "addressXY":addressXY
  }

  var url = "http://" + window.location.host + "/user/addAddress"
  $.post(url,message,function(result) {alert(result);});
});
/////////////////自定义函数
//////////////////////////////////


/////////////注销账户
function cancel(){
  var url = "http://" + window.location.host + "/user/cancel"
  var message = {
    "email": $("#account").text()
  };
  $.post(url,message,function(result) {ale(result);});
}

////////////页面提醒
function ale(message) {
  layer.open({
    content: message,
    yes: function(index, layero) {
      location.reload();
      layer.close(index); //如果设定了yes回调，需进行手工关闭
    }
  });
}

function deleteAdd(obj){
  var addressString = $(obj).parent().prev().children("#add").text();

  var url = "http://" + window.location.host + "/user/delAddress"
  var message = {
    "addressString": addressString
  };

  $.post(url,message,function(result) {ale(result);});
}
