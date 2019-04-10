var $ = layui.$;


String.prototype.temp = function(obj) {
  return this.replace(/\$\w+\$/gi, function(matchs) {
    var returns = obj[matchs.replace(/\$/g, "")];
    return (returns + "") == "undefined" ? "" : returns;
  });
};


function bodyload() {
  var url = "http://" + window.location.host + "/admin/getNumUsers"
  $.post(url,function (result){
    $("#numUser").text(result);
  });

  var url = "http://" + window.location.host + "/admin/getNumRes"
  $.post(url,function (result){
    $("#numRes").text(result);
  });


  var url = "http://" + window.location.host + "/admin/finance"
  $.post(url,function (result){
    data = JSON.parse(result);
    var htmlList = '',htmlTemp = $("textarea").val();
    data.forEach(function(object) {
      htmlList += htmlTemp.temp(object);
    });

    $("#con").html(htmlList);
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

function ale(message) {
  layer.open({
    content: message,
    yes: function(index, layero) {
      layer.close(index); //如果设定了yes回调，需进行手工关闭
    }
  });
}
