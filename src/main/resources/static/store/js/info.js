var $ = layui.$;
var table = layui.table;
var layer = layui.layer;

/////////////////基础信息读取
//////////////////////////////////
function bodyload(){
  /////////////信息读取
  var url = "http://" + window.location.host + "/store/info"
  $.post(url,function(result) {
    var obj = JSON.parse(result);
    $("#account").text(obj.resID);
    $("#name").val(obj.name);
    $("#type").val(obj.type);
    $("#add").text(obj.address);
  });
}


//////////////////事件绑定
//////////////////////////////////
$('#name1').click(function() {
  $("#name").attr("disabled", false);
  $("#type").attr("disabled", false);
});

$('#name2').click(function() {
  //上传修改信息
  var name = $('#name').val();
  var type = $('#type').val();
  var address = $("#ifmMap").contents().find('#localvalue').val();
  var addressXY = $("#ifmMap").contents().find('#pointInput').val();

  if((address == "")||(addressXY == "")){
    ale("地址信息不能为空噢！");
    return
  }
  if(name == ""){
    ale("餐厅名称不能为空噢！");
    return
  }
  if(type == ""){
    ale("餐厅类型不能为空噢！");
    return
  }

  var url = "http://" + window.location.host + "/store/modifyInfo"
  var message = {
    "name": name,
    "type": type,
    "address":address,
    "addressXY":addressXY
  };
  $.post(url,message,function(result) {aleAndF5(result);});
});
/////////////////自定义函数
//////////////////////////


////////////页面提醒
function aleAndF5(message) {
  layer.open({
    content: message,
    yes: function(index, layero) {
      location.reload();
      layer.close(index); //如果设定了yes回调，需进行手工关闭
    }
  });
}

function ale(message) {
  layer.open({
    content: message,
    yes: function(index, layero) {
      layer.close(index); //如果设定了yes回调，需进行手工关闭
    }
  });
}
