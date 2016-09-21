function isTouch() {
    return navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i) ? !0 : !1
}
!function() {
    for (var t = document.cookie ? document.cookie.split("; ") : [], i = t.length, l = "", d = 0; i > d; d++) {
        var s = t[d].split("=")
          , r = decodeURIComponent(s.shift());
        if ("nick" === r) {
            l = decodeURIComponent(s.join("="));
            break
        }
    }
    var c = document.getElementById("menuUserName");
    c.innerHTML = l
}(),
isTouch() || (window.onscroll = function() {
    var e = document.getElementById("menuList")
      , n = 0;
    window.scrollY ? n = window.scrollY : document.documentElement.scrollTop && (n = document.documentElement.scrollTop);
    var o = document.getElementById("finMenuHeader")
      , t = document.getElementById("userHead");
    n >= 354 ? (e.className = "fin-menu-list fin-menu-list-fixed",
    o.style.display = "none",
    t.style.display = "none") : (e.className = "fin-menu-list",
    o.style.display = "block",
    t.style.display = "block")
}
);