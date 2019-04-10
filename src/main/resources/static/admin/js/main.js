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
