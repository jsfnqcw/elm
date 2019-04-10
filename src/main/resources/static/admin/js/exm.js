var $ = layui.$;


String.prototype.temp = function(obj) {
  return this.replace(/\$\w+\$/gi, function(matchs) {
    var returns = obj[matchs.replace(/\$/g, "")];
    return (returns + "") == "undefined" ? "" : returns;
  });
};


function bodyload() {
  var url = "http://" + window.location.host + "/admin/getResExm"

  $.post(url,function(result) {
    var htmlList = '',
      htmlTemp = $("textarea").val();
    var obj = JSON.parse(result);
    obj.forEach(function(object) {
      htmlList += htmlTemp.temp(object);
    });
    $("#con").html(htmlList);
  });
}




function confirm(obj){
  var resID = $(obj).parent().next().next().text();
  var url = "http://" + window.location.host + "/admin/confirmExm"
  var message = {"resID": resID}
  $.post(url, message, function(result) {aleAndF5(result);});
}

function deny(obj){
  var resID = $(obj).parent().next().next().text();
  var url = "http://" + window.location.host + "/admin/denyExm"
  var message = {"resID": resID}
  $.post(url, message, function(result) {aleAndF5(result);});
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

function ale(message) {
  layer.open({
    content: message,
    yes: function(index, layero) {
      layer.close(index); //如果设定了yes回调，需进行手工关闭
    }
  });
}
