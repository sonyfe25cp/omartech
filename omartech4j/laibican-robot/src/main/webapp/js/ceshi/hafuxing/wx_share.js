//weixin share lib
(function(){
	var onBridgeReady=function(){
		WeixinJSBridge.on('menu:share:appmessage', function(argv){
			WeixinJSBridge.invoke('sendAppMessage',{
				"appid":_shareData.appId,
				"img_url":_shareData.weixin_icon,
				"img_width":"180",
				"img_height":"180",
				"link":_shareData.weixin_url,
				"desc":_shareData.description,
				"title":_shareData.title
			}, function(res){(_shareData.callback)();});});
		WeixinJSBridge.on('menu:share:timeline', function(argv){
			(_shareData.callback)();
			WeixinJSBridge.invoke('shareTimeline',{
				"img_url":_shareData.weixin_tl_icon,
				"img_width":"120",
				"img_height":"120",
				"link":_shareData.weixin_url,
				"desc":_shareData.description,
				"title":_shareData.title+_shareData.description//_shareData.title
			}, function(res){});});
		WeixinJSBridge.on('menu:share:weibo', function(argv){
			WeixinJSBridge.invoke('shareWeibo',{
				"content":_shareData.title+_shareData.description,//_shareData.title,
				"url":_shareData.url
			}, function(res){(_shareData.callback)();});});
		WeixinJSBridge.on('menu:share:facebook', function(argv){
			(_shareData.callback)();
			WeixinJSBridge.invoke('shareFB',{
				"img_url":_shareData.weibo_icon,
				"img_width":"180",
				"img_height":"180",
				"link":_shareData.url,
				"desc":_shareData.title+_shareData.description,//_shareData.description,
				"title":_shareData.title+_shareData.description//_shareData.title
			}, function(res){});});
		WeixinJSBridge.on("menu:general:share", function(s) {
		    var _img_url_s,_img_width_s,_img_height_s,_link_s;
		    switch(s.shareTo){
		        case "friend":
		            _img_url_s=_shareData.weixin_icon;
		            _img_width_s="180";
		            _img_height_s="180";
		            _link_s=_shareData.weixin_url;
					_desc_s=_shareData.description;
					_title_s=_shareData.title;
		        break;
		        case "timeline":
		            _img_url_s=_shareData.weixin_tl_icon;
		            _img_width_s="120";
		            _img_height_s="120";
		            _link_s=_shareData.weixin_url;	
					_desc_s=_shareData.title+_shareData.description;//_shareData.description;
					_title_s=_shareData.title+_shareData.description;//_shareData.title;
		        break;
		        default:
		            _img_url_s=_shareData.weibo_icon;
		            _img_width_s="180";
		            _img_height_s="180";
		            _link_s=_shareData.url;	  
					_desc_s=_shareData.title+_shareData.description;//_shareData.description;
					_title_s=_shareData.title+_shareData.description;//_shareData.title;
		        break;
		    }
		    s.generalShare({
		        appid: _shareData.appId,
		        img_url: _img_url_s,
		        img_width: _img_width_s,
		        img_height: _img_height_s,
		        link: _link_s,
		        desc: _desc_s,
		        title: _title_s
		    },function(e){(_shareData.callback)();});
		});
	};
	if (typeof WeixinJSBridge == "undefined"){
	    if(document.addEventListener){
		    document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	    }else if(document.attachEvent){
		    document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
		    document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	    }
	}else{
	    onBridgeReady();
	}
})();