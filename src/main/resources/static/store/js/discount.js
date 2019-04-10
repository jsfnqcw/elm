var $ = layui.$;
var form = layui.form;;


var stringa = "<blockquote class='layui-elem-quote'><b>满";
var stringb = "</b></blockquote>";


function bodyload(){

  form.on('submit(discount)', function(data){
      var discount = data.field;
      var url = "http://" + window.location.host + "/store/addDiscount"
      $.post(url,discount,function(result) {
        aleAndF5(result);
      });

      return false;
  });

  var url = "http://" + window.location.host + "/store/getDiscount"
  $.post(url,function(result) {
    var obj = JSON.parse(result);
    $.each(obj,function(i,n){
        var bodyContent = n.price + "减" + n.dis;
        var string = stringa + bodyContent+ stringb;
        $("#main").append(string);
    });
  });
};



function deleteDiscount(obj){
  var url = "http://" + window.location.host + "/store/delDiscount";
  $.post(url,function(result){aleAndF5(result)});
};

function aleAndF5(message) {
  layer.open({
    content: message,
    yes: function(index, layero) {
      location.reload();
      layer.close(index); //如果设定了yes回调，需进行手工关闭
    }
  });
};

function ale(message) {
  layer.open({
    content: message,
    yes: function(index, layero) {
      layer.close(index); //如果设定了yes回调，需进行手工关闭
    }
  });
};
