var $ = layui.$;
var element = layui.element;

/////////组件功能绑定
$('#buy').click(function (){
  element.tabChange('tab', 'buy');
});

$('#search').click(function (){
  element.tabChange('tab', 'search');
});

$('#user').click(function (){
  element.tabChange('tab', 'user');
});



/////////////页面初始信息载入
//$("#user").text(window.localStorage.account);

var url = "http://" + window.location.host + "/user/info"
$.post(url,function(result) {

  var obj = JSON.parse(result);
  $("#user").text(obj.name);

});

//$("#user").text("790200244");
