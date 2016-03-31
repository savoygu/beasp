! function(a) {
    var b = 0,
        c = {
            init: function(a) {},
            pop: function(b) {
                function c() {
                    null !== i.popFunc && i.popFunc();
                    var b = "<div id='blacklayer'></div>";
                    a("body").append(b);
                    var c = a(document).height();
                    a("#blacklayer").css({
                        zIndex: i.zIndex - 10,
                        background: "#000",
                        opacity: i.opacity,
                        position: "absolute",
                        left: 0,
                        top: 0,
                        width: "100%",
                        height: c
                    }), a(".popclose").bind("click", function() {
                        h = 0, a("#blacklayer").remove(), null === i.popHtml ? a(i.popId).hide() : a(i.popId).remove(), null != i.closePop && i.closePop()
                    })
                }

                function d(b, c, e) {
                    return 0 === h ? !1 : void(_jumpfunc = window.setTimeout(function() {
                        if (b--, b > 0) {
                            if (1 == g) return !1;
                            a(c).html(b + "秒"), d(b, c, e)
                        } else null == e ? (a("#blacklayer").remove(), a(i.popId).remove()) : document.location = e
                    }, 1e3))
                }
                var e = a(window).height(),
                    f = a(window).width(),
                    g = 0,
                    h = 1,
                    i = {
                        width: 100,
                        height: 100,
                        zIndex: 9999,
                        poparent: "body",
                        opacity: .5,
                        popId: null,
                        popHtml: null,
                        popFunc: null,
                        time: null,
                        timeId: null,
                        url: null,
                        closePop: null
                    };
                b && a.extend(i, b);
                var j, k = (f - i.width) / 2;
                return null === i.popHtml ? this.each(function() {
                    var b = a(this);
                    "auto" == i.height && (i.height = b.height()), j = (e - i.height) / 2, b.show(), b.css({
                        width: i.width,
                        height: i.height,
                        zIndex: i.zIndex,
                        top: j,
                        left: k,
                        position: "fixed"
                    }), c(), null !== i.time && d(i.time, i.timeId, i.url)
                }) : (null !== i.popHtml && (a(i.poparent).append(i.popHtml), "auto" == i.height && (i.height = a(i.popId).height()), j = (e - i.height) / 2, a(i.popId).css({
                    width: i.width,
                    height: i.height,
                    zIndex: i.zIndex,
                    top: j,
                    left: k,
                    position: "fixed"
                }), c()), void(null !== i.time && d(i.time, i.timeId, i.url)))
            },
            tag: function(b) {
                var c = {
                    type: "click",
                    selected: "on",
                    contentClass: ".content",
                    func: null
                };
                return b && a.extend(c, b), "click" != c.type && "mouseover" != c.type ? !1 : (a(this).eq(0).addClass(c.selected), this.each(function() {
                    function b() {
                        var b = d.index();
                        d.siblings().removeClass(c.selected), d.addClass(c.selected), a(c.contentClass).hide(), a(c.contentClass).eq(b).show(), null != c.func && c.func()
                    }
                    var d = a(this);
                    a(c.contentClass).hide(), a(c.contentClass).eq(0).show(), d.bind(c.type, b)
                }))
            },
            imgmove: function(c) {
                function d() {
                    var c = a(e.oneEle).size(),
                        d = c * e.oneWidth,
                        f = d / e.boxWidth;
                    f % e.boxWidth > 0;
                    var g = a(e.oneEle).parent();
                    if (1 == e.loop) {
                        a(e.oneEle).parent().width(d).css("left", -e.oneWidth);
                        var g = a(e.oneEle).parent(),
                            h = a(e.oneEle).last();
                        g.prepend(h.clone()), h.remove()
                    } else a(e.oneEle).parent().width(d);
                    a(e.prev).click(function() {
                        if (1 == e.loop) {
                            var c = a(e.oneEle).last();
                            TweenMax.to(g, 1, {
                                left: 0,
                                onComplete: function() {
                                    g.prepend(c.clone()), g.css("left", -e.oneWidth), a(e.oneEle).last().remove()
                                },
                                ease: Quart.easeOut
                            })
                        } else b > 0 && (b--, TweenMax.to(g, 1, {
                            left: -e.boxWidth * b,
                            ease: Quart.easeOut
                        }))
                    }), a(e.next).click(function() {
                        1 == e.loop ? TweenMax.to(g, .4, {
                            left: -e.oneWidth,
                            onComplete: function() {
                                g.css("left", "0px");
                                var b = a(e.oneEle).slice(0, 1);
                                g.append(b.clone()), a(e.oneEle).slice(0, 1).remove(), console.log("ok")
                            },
                            ease: Quart.easeOut
                        }) : f - 1 > b && (b++, TweenMax.to(g, 1, {
                            left: -e.boxWidth * b,
                            ease: Quart.easeOut
                        }))
                    })
                }
                var e = {
                    oneEle: null,
                    oneWidth: null,
                    loop: !1,
                    boxWidth: null,
                    prev: null,
                    next: null
                };
                c && a.extend(e, c), d()
            }
        };
    a.fn.tooltip = function(b) {
        return c[b] ? c[b].apply(this, Array.prototype.slice.call(arguments, 1)) : "object" != typeof b && b ? void a.error("Method " + b + " does not exist on jQuery.tooltip") : c.init.apply(this, arguments)
    }
}(jQuery), $(function() {
    $("#subCourseBtn").click(function(a) {
    	if (a.preventDefault(), window.OP_CONFIG.isLogin === !1) return tips(1, "请先登录"), setTimeout(function() {
//            window.location.href = window.WWW_URL + "account/signin?fromurl=" + encodeURIComponent(window.CURRENT_URL + "#apply")
            window.location.href = window.WWW_URL + "account/signin?fromurl=" + encodeURIComponent(base64encode(window.CURRENT_URL));
        }, 2e3), !1;
       /*
		 * if (window.OP_CONFIG.is_vip === !1) return tips(1, "只有会员才能提交课程需求"),
		 * window.location.hash = "", !1;
		 */
        var b = "",
            c = "dia_topic_subcourse";
        b += '<div class="dialog" id="' + c + '">', 
        b += '<div class="dialog-box">', 
        b += '<div class="dialog-tit"><span class="poptit">提交书籍需求</span><span class="popclose"></span></div>', 
	        b += '<div class="dialog-body">', 
		        b += '<div class="jkxy-form">', 
			        b += "<form>", 
				        b += '<input type="hidden" id="temp" value="" />', 
				        b += '<div class="form-c">', 
					        b += '<div class="form-dom">', 
						        b += '<div class="form-tit"><i>*</i>书籍名称：</div>', 
						        b += '<div class="form-input">', 
							        b += '<input type="text" class="jkxy-input" name="bookName" placeholder="控制在 20 个文字之内" maxlength="20" id="topic_name" />', 
							        b += '<div class="form-tip">&nbsp;</div>', 
						        b += "</div>", 
					        b += "</div>", 
					        b += '<div class="form-dom">', 
						        b += '<div class="form-tit"><i>*</i>书籍作者：</div>', 
						        b += '<div class="form-input">', 
							        b += '<input type="text" class="jkxy-input" name="bookAuthor" placeholder="控制在 20 个文字之内" maxlength="20" id="topic_author" />', 
							        b += '<div class="form-tip">&nbsp;</div>', 
						        b += "</div>", 
					        b += "</div>", 
					        b += '<div class="form-dom">', 
						        b += '<div class="form-tit"><i>*</i>书籍版本：</div>', 
						        b += '<div class="form-select">', 
							        b += '<select id="topic_version" name="bookVersion" class="jkxy-select">',
								        b += '<option value="1">1</option>',
								        b += '<option value="2">2</option>',
								        b += '<option value="3">3</option>',
								        b += '<option value="4">4</option>',
								        b += '<option value="5">5</option>',
								        b += '<option value="6">6</option>',
								        b += '<option value="7">7</option>',
								        b += '<option value="8">8</option>',
								        b += '<option value="9">9</option>',
							        b += '</select>',
							        b += '<div class="form-tip">&nbsp;</div>', 
						        b += "</div>", 
					        b += "</div>", 
					        b += '<div class="form-dom">', 
						        b += '<div class="form-tit"><i>*</i>交换OR分享：</div>', 
						        b += '<div class="form-input">', 
							        b += '<input type="radio" class="jkxy-radio" name="shareExchange" value="1" checked="checked" />分享', 
							        b += '<input type="radio" class="jkxy-radio" name="shareExchange" value="2"  />交换', 
							        b += '<div class="form-tip">&nbsp;</div>', 
						        b += "</div>", 
					        b += "</div>", 
					        b += '<div class="form-dom">', 
						        b += '<div class="form-tit"><i>*</i>书籍描述：</div>', 
						        b += '<div class="form-input">', 
							        b += '<textarea name="description" id="topic_content" class="jkxy-textarea topic-textarea" placeholder="控制在 500 个文字之内" maxlength="500"></textarea>', 
							        b += '<div class="form-tip">&nbsp;</div>', 
						        b += "</div>", 
					        b += "</div>", 
					        b += '<div class="form-dom">', 
						        b += '<div class="form-tit">&nbsp;</div>', 
						        b += '<div class="form-input"><a href="javascript:;" class="greenbtn" id="diaSubCourse">提交需求</a></div>', 
					        b += "</div>", 
				        b += "</div>",
			        b += "</form>", 
		        b += "</div>", 
	        b += "</div>", 
        b += "</div>", 
        b += "</div>", $().tooltip("pop", {
            width: 650,
            height: 560,
            popId: "#" + c,
            opacity: .8,
            popHtml: b,
            popFunc: function() {
                var a = $("#" + c),
                    b = a.find("#diaSubCourse");
                b.click(function() {
                    var a = $("#topic_name").val(),
                        c = $("#topic_author").val(),
                        version = $("#topic_version").val();
                        shareExchange = $('input:radio[name="shareExchange"]:checked').val(),
                        description = $("#topic_content").val();
                    if (1 == b.attr("stop")) return !1;
                    if (!a) return tips(1, "请填写书籍名称"), !1;
                    if (a.length > 20) return tips(1, "书籍名称请控制在20个文字之内"), !1;
                    
                    if (!c) return tips(1, "请填写书籍作者"), !1;
                    if (c.length > 20) return tips(1, "书籍作者请控制在20个文字之内"), !1;
                    
                    if(!version) return tips(1, "请填写书籍版本"), !1;
                    if(version.length > 2) return tips(1, "书籍版本请控制在2个文字之内"), !1;
                    
                    if(!description) return tips(1, "请填写书籍描述"), !1;
                    if (description.length > 500) return tips(1, "书籍描述请控制在500个文字之内"), !1;
                    
                    if (!a || !c) return tips(1, "请补充完所有信息"), !1;
                    b.attr("stop", 1);
                    var d = {
                        name: a,
                        author: c,
                        version : version,
                        description : description,
                        shareExchange : shareExchange,
                    };
                    $.ajax({
                        url: window.WWW_URL + "topic/create",
                        data: d,
                        dataType: "json",
                        type: "POST",
                        async: !1,
                        success: function(a) {
                            if (0 === a.result) {
                                $(".popclose").click(), tips(0, a.msg);
                                /*a.data.topic.id;*/
                                setTimeout(function(a) {
                                    window.location.href = window.WWW_URL + "topic"
                                }, 2e3)
                            } else tips(1, a.msg)
                        },
                        error: function() {
                            tips(1, "系统错误")
                        }
                    }), b.removeAttr("stop")
                })
            },
            closePop: function() {}
        })
    })
    var a = document.location.toString();
    if (a.match("#")) {
        var b = a.split("#")[1];
        "apply" == b && $("#subCourseBtn").click(), b.match("^topic_") && $("#" + b).addClass("active")
    }
});
var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";  
var base64DecodeChars = new Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);  
function base64encode(str){  
    var out, i, len;  
    var c1, c2, c3;  
    len = str.length;  
    i = 0;  
    out = "";  
    while (i < len) {  
        c1 = str.charCodeAt(i++) & 0xff;  
        if (i == len) {  
            out += base64EncodeChars.charAt(c1 >> 2);  
            out += base64EncodeChars.charAt((c1 & 0x3) << 4);  
            out += "==";  
            break;  
        }  
        c2 = str.charCodeAt(i++);  
        if (i == len) {  
            out += base64EncodeChars.charAt(c1 >> 2);  
            out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));  
            out += base64EncodeChars.charAt((c2 & 0xF) << 2);  
            out += "=";  
            break;  
        }  
        c3 = str.charCodeAt(i++);  
        out += base64EncodeChars.charAt(c1 >> 2);  
        out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));  
        out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));  
        out += base64EncodeChars.charAt(c3 & 0x3F);  
    }  
    return out;  
}