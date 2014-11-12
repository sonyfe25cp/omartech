/*
 * 全站公共脚本,基于jquery-1.9.1脚本库
 */
$(function() {
	function isIos()  
	{  
       var userAgentInfo = navigator.userAgent;  
       var Agents = new Array("iPhone");  
       var flag = false;  
       for (var v = 0; v < Agents.length; v++) {  
           if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = true; break; }  
       }  
       return flag;  
	}
	var fullHeight = document.documentElement.clientHeight;
	if ( fullHeight >= 504 && fullHeight < 570 && isIos() == true )
	{
		fullHeight = 504;
	}
	if ( fullHeight < 500 && isIos() == true )
	{
		fullHeight = 416;
	}
	$("html,body,#loading,#enter,#mySwiper").height(fullHeight);
	//微信分享到朋友圈
        var imgUrl = "http://www.locuser.cn/case/youku/wangyou/images/share.jpg";
        var lineLink = "http://www.locuser.cn/case/youku/wangyou/";
        var descContent = "优酷放大招！谜团即将揭晓，到底猜对了没有，11月15日见分晓。";
        var shareTitle = "11月15日放大招！";
        var appid = '';
        function shareFriend() {
            WeixinJSBridge.invoke('sendAppMessage',{
                "appid": appid,
                "img_url": imgUrl,
                "img_width": "200",
                "img_height": "200",
                "link": lineLink,
                "desc": descContent,
                "title": shareTitle
            }, function(res) {
                //_report('send_msg', res.err_msg);
            })
        }
        function shareTimeline() {
            WeixinJSBridge.invoke('shareTimeline',{
                "img_url": imgUrl,
                "img_width": "200",
                "img_height": "200",
                "link": lineLink,
                "desc": descContent,
                "title": descContent,
            }, function(res) {
                   //_report('timeline', res.err_msg);
            });
        }
        function shareWeibo() {
            WeixinJSBridge.invoke('shareWeibo',{
                "content": descContent,
                "url": lineLink,
            }, function(res) {
                //_report('weibo', res.err_msg);
            });
        }
        // 当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
        document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
            // 发送给好友
            WeixinJSBridge.on('menu:share:appmessage', function(argv){
                shareFriend();
            });
            // 分享到朋友圈
            WeixinJSBridge.on('menu:share:timeline', function(argv){
                shareTimeline();
            });
            // 分享到微博
            WeixinJSBridge.on('menu:share:weibo', function(argv){
                shareWeibo();
            });
        }, false);
	//初始加载分屏动画
		var page = 0;
		mySwiper = $("#mySwiper").swiper({
			mode:"vertical",
			loop: false,
			noSwiping:true,
			noSwipingNext:true,
			noSwipingPrev:true,
			queueEndCallbacks:true,
			progress:true,
			autoResize:false,
		    onSlideChangeEnd : function(){
		    	if ( page != mySwiper.activeIndex )
		    	{
			    	remove($("#mySwiper>.swiper-wrapper>.swiper-slide:eq("+mySwiper.previousIndex+")"));
			    	page = mySwiper.activeIndex;
					animates($(mySwiper.activeSlide()));
		    	}
		    },
			onProgressChange: function(swiper) {
			    for (var i = 0; i < swiper.slides.length; i++) {
			        var slide = swiper.slides[i];
			        var progress = slide.progress;
			        var rotate = -90 * progress;
			        if (rotate < -90) rotate = -90;
			        if (rotate > 90) rotate = 90;
			        var translate = progress * swiper.width / 2;
			        var opacity = 1 - Math.min(Math.abs(progress), 1);
			        slide.style.opacity = opacity;
			        swiper.setTransform(slide, 'rotateX(' + rotate + 'deg) translate3d(0,' + translate + 'px,0)');
			    }
			},
			onTouchStart: function(swiper) {
			    for (var i = 0; i < swiper.slides.length; i++) {
			        swiper.setTransition(swiper.slides[i], 0);
			    }
			},
			onSetWrapperTransition: function(swiper) {
			    for (var i = 0; i < swiper.slides.length; i++) {
			        swiper.setTransition(swiper.slides[i], swiper.params.speed);
			    }
			}
		})
	var html = new Array();
	//移除动画后的效果
		function remove(slide)
		{
			slide.html(html[mySwiper.previousIndex]);
		}
	//判断动画类型
		function animates(slide)
		{
			$(".pages").hide();
			if ( slide.attr("data-page") == "wu" )
			{
				$(".pages").show();
			}
			var type = slide.attr("data-page");
			switch ( type )
			{
				case "du":
				case "chui":
				case "bo":
				case "han":
				case "wu":
					man(slide);
					break;
				case "share":
					share(slide);
					break;
				case "map":
					map(slide);
					break;
				 
			}
		}
	//人物
		function man(slide)
		{
			if ( typeof(print) != "undefined" )
			{
				clearInterval(print);
			}
			if ( typeof(prints) != "undefined" )
			{
				clearInterval(prints);
			}
			var saosing = slide.find(".saosing");
			var head = slide.find(".head-big");
			slide.find(".animate:first").css({y:-56,opacity:1}).transition({y:0,complete:function(){
				$(this).next().css({scale:0,opacity:1}).transition({scale:1,complete:function(){
					head.css({x:-head.width(),opacity:1}).transition({x:0,complete:function(){
						saosing.addClass("saosing-animate");
						setTimeout(function(){
							head.next().next().next().css({x:200}).transition({x:0,opacity:1,complete:function(){
								var talk = $(this).next();
								talk.css({scale:[0,1]}).transition({scale:1,opacity:1,complete:function(){
									if ( talk.length )
									{
										clearInterval(print);
									}
									var inter = 100;
									if ( talk.closest(".chui").length )
									{
										inter = 50;
									}
									talk.next().find(".animate:first").transition({opacity:1},2000);
									var text = talk.next().find(".animate:eq(1)");
									var texts = text.children();
									var lengths = texts.length;
									var orders = 0;
									text.css({opacity:1}).html("");
									var htmls = "";
									var prints = setInterval(function(){
										htmls+=texts.eq(orders).prop("outerHTML");
										orders ++;
										if ( orders == lengths )
										{
											if ( text.length )
											{
												clearInterval(prints);
											}
											text.html(htmls);
											text.parent().parent().next().css({y:50}).transition({y:0,opacity:1,complete:function(){
												$(this).find(".msg-head").addClass("msg-head-animate");
											}},800);
										}
										else
										{
											text.html(htmls+" _");
										}
									},inter)
								}},300);
							}},500,"linear");
						},500);
					}},200,"linear");
				}},400);
			}},400);
		}
	//分享
		function share(slide)
		{
			var title = slide.find(".animate:first");
			title.transition({opacity:1},6000);
			if ( title.next().find(".share-rotate").length )
			{
				title.next().find(".share-rotate").addClass("rotate-1");
			}
			else
			{
				title.next().css({y:50}).transition({y:0,opacity:1},500);
			}
		}
	//地图
		function map(slide)
		{
			if ( slide.find(".share-rotate").length )
			{
				slide.find(".share-rotate").addClass("rotate-1");
			}
			slide.find(".animate:first").css({scale:[0,1],opacity:1}).transition({scale:1,complete:function(){
				var maps = $(this).next();
				var mapLine = maps.find(">div:first").css({opacity:1}).children();
				var delay = 0;
				var order = 0;
				for ( var i=0;i<mapLine.length;i++)
				{
					switch ( i )
					{
						case 0:
						case 5:
						case 8:
							var x = -50;
							var y = 0;
							break;
						case 1:
						case 4:
						case 10:
							var x = 50;
							var y = 0;
							break;
						case 2:
						case 6:
						case 11:
							var x = 0;
							var y = -50;
							break;
						case 3:
						case 7:
						case 9:
							var x = 0;
							var y = 50;
							break;
					}
					mapLine.eq(i).css({x:x,y:y}).transition({x:0,y:0,opacity:1,delay:delay,complete:function(){
						order ++;
						if (  order == mapLine.length)
						{
							maps.find(".map-name").transition({opacity:1},2000);
							maps.find(".map-here").transition({opacity:1},2000);
							maps.find(".map-block").css({opacity:1});
							san(maps.find(".map-block"));
						}
					}},400);
					delay+=200;
				}
			}},400);
		}
		function san(tag)
		{
			setInterval(function(){
				if ( tag.is(":visible")==false )
				{
					tag.show();
				}
				else
				{
					tag.hide();
				}
			},200)
		}
	//loading
		function loading()
		{
			var loadings = $("#loading");
			var luo = loadings.find(".luo");
			var fire = loadings.find(".fire");
			luo.css({y:-(fire.height()+fire.offset().top-80),opacity:1}).transition({y:0,complete:function(){
				$(this).find(".light").css({opacity:1}).addClass("light-animate");
				setTimeout(function(){
					loadings.find(".pan").transition({opacity:1},3000);
					loadings.find(".text").transition({opacity:1},5000);
				},1000)
			}},4000);
		}
	//enter
		var isenter = false;
		function enter()
		{
			var enters = $("#enter");
			var star = enters.find(".shadow");
			star.transition({opacity:1,complete:function(){
				var delay = 0;
				for ( var i=1;i<=star.children().length;i++)
				{
					star.children().eq(i).css({scale:0}).transition({opacity:1,scale:1,delay:delay},1000);
					delay+=600;
				}
			}},1000);
			var small = enters.find(".rotate-small");
			var normal = enters.find(".rotate-normal");
			var big = enters.find(".rotate-big");
//			small.css({rotate:-90});
//			normal.css({rotate:90});
//			big.css({rotate:-90});
			small.transition({rotate:0,opacity:1,complete:function(){
				normal.transition({rotate:0,opacity:1,complete:function(){
					big.transition({rotate:0,opacity:1,complete:function(){
						enters.find(".saoing").addClass("saoing-animate");
						isenter = true;
						$(document).on("tap","#enter .saomiao-div",function(){
							music.pause();
							if ( isenter )
							{
								music3.play();
								enters.find(".saoing").removeClass("saoing-animate");
								small.transition({rotate:-90,complete:function(){
									normal.transition({rotate:90,complete:function(){
										big.transition({rotate:-90,complete:function(){
											startSwiper();
										}},600,"linear");
									}},600,"linear");
								}},600,"linear");
							}
						})
						$(document).on("press","#enter .saomiao-div",function(event){
							event.preventDefault();
							if ( isenter )
							{
								music3.play();
								enters.find(".saoing").removeClass("saoing-animate");
								small.transition({rotate:-90,complete:function(){
									normal.transition({rotate:90,complete:function(){
										big.transition({rotate:-90,complete:function(){
											startSwiper();
										}},600,"linear");
									}},600,"linear");
								}},600,"linear");
							}
						})
					}},0,"linear");
				}},0,"linear");
			}},0,"linear");
		}
	function startSwiper()
	{
		$("#enter").transition({y:"-="+document.documentElement.clientHeight,complete:function(){
			$(this).hide();
		}},800);
		$("#mySwiper").transition({y:-document.documentElement.clientHeight,complete:function(){
			$(this).css({y:0,"top":0});
			animates($("#mySwiper .swiper-slide:first"));
			for ( var num=0;num<$("#mySwiper>.swiper-wrapper>.swiper-slide").length;num++ )
			{
				html[num] = $("#mySwiper>.swiper-wrapper>.swiper-slide").eq(num).html();
			}
			$("#music").transition({y:-35,complete:function(){
				playmusic();
			}},200)
		}},800)
	}
	var music = new Audio();
	music.src = "http://dn-locuser.qbox.me/case/youku/wangyou/js/1.mp3";
	
	var music2 = new Audio();
	music2.src = "http://dn-locuser.qbox.me/case/youku/wangyou/js/2.mp3";
	
	var music3 = new Audio();
	music3.src = "http://dn-locuser.qbox.me/case/youku/wangyou/js/3.mp3";
	music3.load();
	$(".music").on("tap",function(){
		if ( $(this).hasClass("play") )
		{
			$(this).removeClass("play");
			$("#music>span").addClass("zshow").html("关闭");
			setTimeout(function(){$("#music>span").removeClass("zshow")},1000);
			music2.pause();
		}
		else
		{
			$(this).addClass("play");
			$("#music>span").addClass("zshow").html("开启");
			setTimeout(function(){$("#music>span").removeClass("zshow")},1000);
			music2.play();
		}
	})
	function playmusic()
	{
		$(".music").addClass("play");
		music2.play();
		$("#mySwiper").one("touchstart",function(){
			music2.play();
		})
	}
	window.onload = function()
	{
		loading();
		var mouseDown = true;
		if ( mouseDown )
		{
			music.play();
		}
		$("body").one("touchstart",function(){
			if ( mouseDown )
			{
				music.play();
			}
		})
		$(this).addClass("ing");
		var img = $("#mySwiper,#enter").find("img");
		var length = img.length;
		var loadingPercent = $("#loading .percent");
		var downImg = 0;
		var percent = 0;
		var number = 1;
		startTime = setInterval(function(){
			number++;
		},1000)
		for ( var i=0;i<length;i++ )
		{
			img.eq(i).attr("src",img.eq(i).attr("loadsrc")).load(function(){
				$(this).removeAttr("loadsrc");
				downImg ++;
				percent = parseInt(100*downImg/length);
				loadingPercent.html((percent-1)+"%");
				if ( percent == 100 )
				{
					downloadOver();
				}
			})
		}
		function downloadOver()
		{
			isOver = setInterval(function(){
				if ( number >= 7 )
				{
					clearInterval(startTime);
					clearInterval(isOver);
					loadingPercent.html(percent+"%");
					setTimeout(function(){
						
						mouseDown = false;
						$("#loading").transition({y:"-="+document.documentElement.clientHeight,complete:function(){
							$(this).hide();
						}},800);
						$("#enter").transition({y:"-="+document.documentElement.clientHeight,complete:function(){
							enter();
						}},800);
					},800)
				}
			},1000)
		}
	}
	$(document).on("touchstart touchmove flick",function(event){
		event.preventDefault();
	})
	$(document).on("touchstart","#enter .saomiao-div",function(event){
		event.preventDefault();
	})
	$(document).on("touchstart",".share-button",function(){
		$(".shares").show();
	})
	$(document).on("touchstart",".shares",function(){
		$(this).hide();
	})
	$(document).on("tap",".msg",function(){
		setTimeout(function(){
			mySwiper.swipeNext();
		},100)
	})
	$(document).on("tap",".pages",function(){
		setTimeout(function(){
			mySwiper.swipeNext();
		},100)
	})
	$(document).on("tap",".view",function(){
		setTimeout(function(){
			mySwiper.swipeNext();
		},100)
	})		
})