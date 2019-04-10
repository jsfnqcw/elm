var $ = layui.$;

function upCal(obj) {
  var per = $(obj).prev().find("#percent").val();
  if(per == "" || per == 0){
    ale("您还未选择价格！");
  }else{
    var url = "http://" + window.location.host + "/admin/changePer"
    var message = {"per": per };
    $.post(url, message, function(result) {
      ale(result);
    });
  }
}

function ale(message) {
  layer.open({
    content: message,
    yes: function(index, layero) {
      layer.close(index); //如果设定了yes回调，需进行手工关闭
    }
  });
}
