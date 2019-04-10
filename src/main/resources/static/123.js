<script type="text/javascript">
(function(){
    var $ = function(id){return document.getElementById(id)};
    var on = function(el, event, callback){el.addEventListener(event, callback, false)};
    var getCookie = function(name) {
         var r = new RegExp("(?:^|;+|\\s+)\s*" + name + "=([^;]*)"), m = document.cookie.match(r);
                return !m ? "" : decodeURIComponent(m[1]);
    }
    var setCookie = function(name, value, domain, path, hour){
        if (hour) {
            var expire = new Date;
            expire.setTime(expire.getTime() + 36E5 * hour)
        }
        document.cookie = name + "=" + value+ "; " + (hour ? "expires=" + expire.toGMTString() + "; " : "") + (path ? "path=" + path + "; " : "path=/; ") + (domain ? "domain=" + domain + ";" : "domain=" + domainPrefix + ";");
        return true
    }
    var pv = function(domain, path){
        var refer = document.referrer.match(/http:\/\/([^/]+)\/([^\?#]+)/);
        var param = [
            'dm+=' + escape(domain),
            'url+=' + escape(path),
            'rdm+=' + escape(refer?refer[1]:''),
            'rurl+=' + escape(refer?refer[2]:''),
            'pgv_pvid+=' + getId(),
            'sds+=' + Math.random()
        ];
        img = new Image();
        img.src = "http://pingfore.qq.com/pingd?cc=-&ct=-&java=1&lang=-&pf=-&scl=-&scr=-&tt=-&tz=-8&vs=3.3&flash=&" +  param.join("&")
    }
    var getId = function () {
        var t, d, h, f;
        t = document.cookie.match(/(?:^|;+|\s+)pgv_pvid=([^;]*)/i);
        if (t && t.length && t.length > 1) {
            d = t[1];
        } else {
            d = (Math.round(Math.random() * 2147483647) * (new Date().getUTCMilliseconds())) % 10000000000;
            document.cookie = "pgv_pvid=" + d+ "; path=/; domain=qq.com; expires=Sun,18 Jan 2038 00:00:00 GMT;";
        }
        h = document.cookie.match(/(?:^|;+|\s+)pgv_info=([^;]*)/i);
        if (!h) {
            f = (Math.round(Math.random() * 2147483647) * (new Date().getUTCMilliseconds())) % 10000000000;
            document.cookie = "pgv_info=ssid=s" + f + "; path=/; domain=qq.com;";
        }
        return d;
    }
    /*layer switch*/
    var hasShown = getCookie('guide2');
    var refer = document.referrer || '';
    var url = location.href;
    /*弹出逻辑：手动输入网址：refer空或等于http://m.qzone.com*/
  //  if(refer && refer != 'http://m.qzone.com/' && refer != 'http://m.qzone.com'){
  //      hasShown = true;
  //  }
    //活动页干掉
    if(refer && refer.indexOf('qzs.qq.com')>0){
        hasShown = true;
    }
    //微信,qq干掉
    if(url.indexOf('5758')>0 || url.indexOf('5757')>0){
        hasShown = true;
    }else if(url.indexOf('6456')>0 || url.indexOf('17636')>0 || url.indexOf('17615')>0 || url.indexOf('22578')>0 || url.indexOf('22174')>0){
        hasShown = true;
    }
    //MSIE也不展示
    var ua = navigator.userAgent;
    if(ua.match(/MSIE/)){
        hasShown = true;
    }
    if(ua.indexOf('MicroMessenger')>0){
        hasShown = true;
    }
    if(!hasShown){
        $('guide').style.display = '';
        if(navigator.connection && navigator.connection.type == '2'){
            $('guideBG').setAttribute('class','wifi');
        }
        var close = function(){
            setCookie('guide2', '1', ".ui.ptlogin2.qq.com", "/", 7*24);
            $('guide').style.display = 'none';
        }
        on($('guideSkip'),'click',function(){
            close();
            pv('m.qzone.com','/guide_toWeb');
        });
        on($('guideJump'),'click',function(){

        });
        pv('m.qzone.com','/guide_show');
    }
})();
</script>
