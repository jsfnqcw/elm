var $ = layui.$;

//////////////
//////////////动态添加div
var stringa = "<br><div class='layui-card card'><div class='layui-card-header'>";
//////菜品名称
var stringb = "<button class='layui-btn layui-btn-sm' onclick='enter(this)'><i class='layui-icon'>&#xe623;</i></div><div class='layui-card-body'>";
//////定价：26.0元<br>
//////预计销售量：200件<br>
//////2018.10.20-2020.10.20
var stringc = "</div><p style='display:none'>";
//////1235132431
var stringd = "</p></div>";


function bodyload(){
  var url = "http://" + window.location.host + "/store/stores"
  $.post(url,function(result) {
    var obj = JSON.parse(result);
    $.each(obj,function(i,n){
        var bodyContent = "类型："+ n.type +"<br>地址："+ n.address;
        var string = stringa + n.name + stringb +bodyContent+ stringc+ n.resID + stringd;
        $("#con").prepend(string);
    });
  });
}


///////////////////
function enter(obj) {
  var id = $(obj).parent().next().next().text();
  window.localStorage.enterResID = id;
  window.location.href = './res.html';
};
