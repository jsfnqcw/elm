var $ = layui.$;
var shoppingBag = [];
var addressArray = [];

//////////////
//////////////动态添加div
var stringa = "<br><div class='layui-card card'><div class='layui-card-header'>";
//////菜品名称
var stringb = "<button class='layui-btn layui-btn-sm' onclick='up(this)'><i class='layui-icon'>&#xe619;</i></button><b>0</b><button class='layui-btn layui-btn-sm' onclick='down(this)'><i class='layui-icon'>&#xe61a;</i></button></div><div class='layui-card-body'>";
//////定价：26.0元<br>
//////预计销售量：200件<br>
//////2018.10.20-2020.10.20
var stringc = "</div><p style='display:none'>";
//////1235132431
var stringd = "</p></div>";


String.prototype.temp = function(obj) {
  return this.replace(/\$\w+\$/gi, function(matchs) {
    var returns = obj[matchs.replace(/\$/g, "")];
    return (returns + "") == "undefined" ? "" : returns;
  });
};


function bodyload() {
  var url = "http://" + window.location.host + "/user/getGoods"
  var message = {
    "resID": window.localStorage.enterResID
  }
  $.post(url, message, function(result) {
    var obj = JSON.parse(result);
    $.each(obj, function(i, n) {
      var bodyContent = "定价：" + n.price + "<br>剩余销售量：" + n.allowance + "件<br>" + n.introduction;
      var string = stringa + n.name + stringb + bodyContent + stringc + n.iD + stringd;
      $("#con").prepend(string);
      shoppingBag.push({
        "goodsID": n.iD,
        "goodsName": n.name,
        "num": 0
      });
    });
  });

  var url = "http://" + window.location.host + "/user/getAddress"
  $.post(url, function(result) {
    Object.assign(addressArray , JSON.parse(result));
    $.each(addressArray, function(i, n) {
      var string = "<option value="+ i +">" + n.addressString + "</option>";
      $("#selectAddress").append(string);
      layui.form.render();

    });
  });

  var url = "http://" + window.location.host + "/user/getDiscounts"
  var message = {
    "resID": window.localStorage.enterResID
  }
  $.post(url, message, function(result) {
    var htmlList = '',
      htmlTemp = $("textarea").val();
    var obj = JSON.parse(result);
    obj.forEach(function(object) {
      htmlList += htmlTemp.temp(object);
    });
    $("#discount").html(htmlList);
  });
}


///////////////////
function buy() {
  var id = $("#selectAddress").find("option:selected").val();
  if(id == ""){
    ale("您还未选择地址！");
  }else if(shoppingBag === undefined || shoppingBag.length == 0){
    ale("不买东西还想下单？？？");
  }else{
    var url = "http://" + window.location.host + "/user/newOrder"
    var message = {
      "resID": window.localStorage.enterResID,
      "shoppingBag":JSON.stringify(shoppingBag),
      "addressXY":addressArray[id].addressX+","+addressArray[id].addressY,
      "addressString":addressArray[id].addressString
    }
    $.post(url, message, function(result) {
      if(result=="Done"){
        ale("下单成功，请尽快支付。");
      }else{
        ale(result);
      }
    });
  }
};


function up(obj) {
  var id = $(obj).parent().next().next().text();
  var number = -1;
  $.each(shoppingBag, function(i, n) {
      if (n.goodsID == id) {
        n.num = n.num + 1;
        number = n.num;
        return;
    }
  });
  $(obj).next().text(number);
};

function down(obj) {
  var id = $(obj).parent().next().next().text();
  var number = 0;
    $.each(shoppingBag, function(i, n) {
      if (n.goodsID == id && n.num >= 1) {
        n.num = n.num - 1;
        number = n.num;
        return;
      }
    });
  $(obj).prev().text(number);
};



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
