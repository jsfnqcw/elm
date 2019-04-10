var $ = layui.$;
var allOrder = [];

String.prototype.temp = function(obj) {
  return this.replace(/\$\w+\$/gi, function(matchs) {
    var returns = obj[matchs.replace(/\$/g, "")];
    return (returns + "") == "undefined" ? "" : returns;
  });
};

function bodyload() {
  var url = "http://" + window.location.host + "/user/getOrders"
  $.post(url, function(result) {
    if (result == null) {
      $("#con").html("<p>暂时没有新订单</p>");
      return;
    }
    allOrder = JSON.parse(result);
    fill(allOrder);
  });


  var url = "http://" + window.location.host + "/user/getOrderResTypes"
  $.post(url, function(result) {
    if (result == null) {return;}
    var ResTypes = JSON.parse(result);

    var htmlSel = "<option value='0' selected=''>筛选餐厅类型</option>";
    ResTypes.forEach(function(object) {
      var tmp = "<option value='"+ object +"'>"+ object +"</option>";
      htmlSel += tmp;
    });
    $("#quiz3").html(htmlSel);
  });
  layui.form.render();
};




function select(){
  var sel1 = $(quiz1).val();
  var sel2 = $(quiz2).val();
  var sel3 = $(quiz3).val();

  if(sel1 == 0 && sel2 == 0 && sel3 == 0){
    console.log("fuck!");
    fill(allOrder);
    return;
  }


  var url = "http://" + window.location.host + "/user/getSelectedOrders"
  var message = {
    "time" : sel1,
    "price" : sel2,
    "type" : sel3
  }
  $.post(url, message , function(result) {
    if (result == null) {
      $("#con").html("<p>暂时没有新订单</p>");
      return;
    }
    fill(JSON.parse(result));
  });
}

function fill(data){
  var htmlList = '',htmlTemp = $("textarea").val();
  var nums = 0, sucNums = 0 ,sumPrice = 0;

  data.forEach(function(object) {
    htmlList += htmlTemp.temp(object);
    nums +=1;
    if(object.state == "arrival"){
      sucNums +=1;
      sumPrice += object.price;
    }
  });
  $("#con").html(htmlList);

  $("#numberOrders").html(nums);
  $("#numberSucOrders").html(sucNums);
  $("#numberPrice").html(sumPrice);
}


function card(obj) {
  var orderID = $(obj).find("#orderID").text();

  var url = "http://" + window.location.host + "/order/getOrderGood"
  var message = {"orderID": orderID};

  $.post(url, message, function(result) {
    var htmlList = '',htmlTemp = "$goodsName$:$num$件<br>";

    var objb = JSON.parse(result);
    objb.forEach(function(object) {
      htmlList += htmlTemp.temp(object);
    });

    $(obj).find("#goods").slideToggle("fast");
    $(obj).find("#goods").html("详细物品内容:" + htmlList);
  });
}

function cancel(obj) {
  var orderID = $(obj).parent().next().next().text();
  var url = "http://" + window.location.host + "/order/cancel"
  var message = {
    "orderID": orderID
  };
  $.post(url, message, function(result) {
    aleAndF5(result);
  });
}
function pay(obj) {
  var orderID = $(obj).parent().next().next().text();
  var url = "http://" + window.location.host + "/order/pay"
  var message = {
    "orderID": orderID
  };
  $.post(url, message, function(result) {
    aleAndF5(result);
  });
}
function confirm(obj) {
  var orderID = $(obj).parent().next().next().text();
  var url = "http://" + window.location.host + "/order/confirm"
  var message = {
    "orderID": orderID
  };
  $.post(url, message, function(result) {
    ale(result);
  });
}
function ale(message) {
  layer.open({
    content: message,
    yes: function(index, layero) {
      layer.close(index);
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
