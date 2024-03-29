var T, baidu = T = baidu || {
  version: "1.5.2.2"
};
baidu.guid = "$BAIDU$";
baidu.$$ = window[baidu.guid] = window[baidu.guid] || {
  global: {}
};
baidu.browser = baidu.browser || {};
(function() {
  var a = navigator.userAgent;
  baidu.browser.safari = /(\d+\.\d)?(?:\.\d)?\s+safari\/?(\d+\.\d+)?/i.test(a) && !/chrome/i.test(a) ? +(RegExp["\x241"] || RegExp["\x242"]) : undefined
})();
baidu.browser.ie = baidu.ie = /msie (\d+\.\d+)/i.test(navigator.userAgent) ? (document.documentMode || +RegExp["\x241"]) : undefined;
baidu.browser.opera = /opera(\/| )(\d+(\.\d+)?)(.+?(version\/(\d+(\.\d+)?)))?/i.test(navigator.userAgent) ? +(RegExp["\x246"] || RegExp["\x242"]) : undefined;
baidu.dom = baidu.dom || {};
(function() {
  var a = baidu.dom.ready = function() {
    var g = false,
      f = [],
      c;
    if (document.addEventListener) {
      c = function() {
        document.removeEventListener("DOMContentLoaded", c, false);
        d()
      }
    } else {
      if (document.attachEvent) {
        c = function() {
          if (document.readyState === "complete") {
            document.detachEvent("onreadystatechange", c);
            d()
          }
        }
      }
    }

    function d() {
      if (!d.isReady) {
        d.isReady = true;
        for (var k = 0, h = f.length; k < h; k++) {
          f[k]()
        }
      }
    }

    function b() {
      try {
        document.documentElement.doScroll("left")
      } catch (h) {
        setTimeout(b, 1);
        return
      }
      d()
    }

    function e() {
      if (g) {
        return
      }
      g = true;
      if (document.readyState === "complete") {
        d.isReady = true
      } else {
        if (document.addEventListener) {
          document.addEventListener("DOMContentLoaded", c, false);
          window.addEventListener("load", d, false)
        } else {
          if (document.attachEvent) {
            document.attachEvent("onreadystatechange", c);
            window.attachEvent("onload", d);
            var h = false;
            try {
              h = window.frameElement == null
            } catch (i) {}
            if (document.documentElement.doScroll && h) {
              b()
            }
          }
        }
      }
    }
    e();
    return function(h) {
      d.isReady ? h() : f.push(h)
    }
  }();
  a.isReady = false
})();
baidu.lang = baidu.lang || {};
baidu.lang.isString = function(a) {
  return "[object String]" == Object.prototype.toString.call(a)
};
baidu.isString = baidu.lang.isString;
baidu.dom._g = function(a) {
  if (baidu.lang.isString(a)) {
    return document.getElementById(a)
  }
  return a
};
baidu._g = baidu.dom._g;
baidu.dom.getParent = function(a) {
  a = baidu.dom._g(a);
  return a.parentElement || a.parentNode || null
};
T.undope = true;
