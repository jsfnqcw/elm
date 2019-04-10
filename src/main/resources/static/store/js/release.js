var laydate = layui.laydate;
var $ = layui.$;
var form = layui.form;;

//////////////
//////////////动态添加div
var stringa = "<br><div class='layui-card card'><div class='layui-card-header'>";
//////菜品名称
var stringb = "<button class='layui-btn layui-btn-sm' onclick='del(this)'><i class='layui-icon'>&#xe640;</i></div><div class='layui-card-body'>";
//////定价：26.0元<br>
//////预计销售量：200件<br>
//////2018.10.20-2020.10.20
var stringc = "</div><p style='display:none'>";
//////1235132431
var stringd = "</p></div>";




function bodyload(){
  ////////////////////属性绑定
  laydate.render({
    elem: '#date1' //指定元素
  });
  laydate.render({
    elem: '#date2' //指定元素
  });

  form.on('submit(demo1)', function(data){
      var url = "http://" + window.location.host + "/store/releaseGoods"
      var postInfo = data.field;
      $.post(url,postInfo,function(result) {
        aleAndF5(result);
      });
      return false;
  });

  var url = "http://" + window.location.host + "/store/getGoods"
  $.post(url,function(result) {
    var obj = JSON.parse(result);
    $.each(obj,function(i,n){
        var bodyContent = "定价："+ n.price +"元<br>剩余销售量："+ n.allowance + "件<br>" + n.startTime +"-"+n.endTime;
        var string = stringa + n.name + stringb +bodyContent+ stringc+ n.iD + stringd;
        $("#con").prepend(string);
    });
  });
}





///////////////////
function del(obj) {
  var name = $(obj).parent().text();
  var id = $(obj).parent().next().next().text();
  var message = "确认要删除 "+ name +"吗？";
  delConfirm(id,message);
};

function delConfirm(id,message) {
  layer.open({
    content: message,
    yes: function(index,layero) {

      var url = "http://" + window.location.host + "/store/deleteGoods"
      var postInfo = {
        "goodsId" : id
      }

      $.post(url,postInfo,function(result) {
        layer.close(index); //如果设定了yes回调，需进行手工关闭
        aleAndF5(result);
      });
    }
  });
}

function aleAndF5(message) {
  layer.open({
    content: message,
    yes: function(index, layero) {
      location.reload();
      layer.close(index); //如果设定了yes回调，需进行手工关闭
    }
  });
}
