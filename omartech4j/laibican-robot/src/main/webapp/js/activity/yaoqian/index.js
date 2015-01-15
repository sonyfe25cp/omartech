//new Image().src="decode.png";
//new Image().src="234.png";
var start, showDecode, jumpToDecode, lastTime, lastAcc, isStarted = false;

start = function() {
	isStarted = true;
	$('.decode').hide();
	$('.result').show();
	setTimeout(showDecode, 3000);
}

showDecode = function(){
	$('.result').hide();
	$('.decode').show();
	setTimeout(jumpToDecode, 3000);
}

jumpToDecode = function(){
	var urls = [
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659917&idx=1&sn=9bb2a7374eb7e2058a50500ecc990445#rd",
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659903&idx=1&sn=50d200870483618fe54ff3aae4f481c8#rd",
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659893&idx=1&sn=10111250836b43158989fde477f0b095#rd",
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659880&idx=1&sn=aa7ccfe3163c3719c60692d738ea02c1#rd",
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659862&idx=1&sn=d8669191a0adf67f6f03922af988a78c#rd",
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659853&idx=1&sn=ce4d171571adcea7becea0df5673590b#rd",
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659839&idx=1&sn=a5d2ca50bbcc9c7ba3d07545677f47f7#rd",
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659831&idx=1&sn=645d4f9afc4bc6046bf7d6e52b56820f#rd",
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659821&idx=1&sn=81d7c5572a91ee103d7a4f1c0d24fefb#rd",
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659807&idx=1&sn=8bfe913e025f335391d398a4753a3eae#rd",
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659795&idx=1&sn=3ce3d2c286cfc3482347ad0ef8bf1326#rd",
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659779&idx=1&sn=7fbf2aa7e344c61e638c1448660a7ea5#rd",
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659765&idx=1&sn=9ff28ef13c4aba8c0750ea5213345944#rd",
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659754&idx=1&sn=3d5044629ff18b9decbb9048edcdd5b9#rd",
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659744&idx=1&sn=1a07e29c04485dfff9642f242340af33#rd",
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659732&idx=1&sn=2048bc87438332153fa314dd716128e2#rd",
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659718&idx=1&sn=8220159ff1d8f217cb11eccf15b79d1e#rd",
	"http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202659691&idx=1&sn=afedc9e15cd9ff5326cd5943a5c20bd8#rd"
	];
	var jumpTo = urls[parseInt(Math.random() * urls.length)];
	window.location = jumpTo;
}

$('.do').click(start);

//摇一摇
$(window).on('deviceorientation', function(e) {
	if (isStarted) {
		return true;
	}
	if (!lastAcc) {
		lastAcc = e;
		return true;
	}
	var speed = e.alpha + e.beta + e.gamma - lastAcc.alpha - lastAcc.beta - lastAcc.gamma;
	if (Math.abs(speed) > 50) {
		start();
	}
	lastAcc = e;
});

//微信分享

var shareMeta = {
	img_url: "http://www.laibican.com/images/activity/yaoqian/thumbnail.gif",
	image_width: 100,
	image_height: 100,
	link: 'http://www.laibican.com/sactivity/yaoqian.html',
	title: "2015乙未羊，为自己摇枚新年签！",
	desc: "这是对过去的感悟和对新年的祈望，希望它能为你带来好运...",
	appid: ''
};
document.addEventListener('WeixinJSBridgeReady', function () {
	WeixinJSBridge.on('menu:share:appmessage', function(){
		WeixinJSBridge.invoke('sendAppMessage', shareMeta);
	});
	WeixinJSBridge.on('menu:share:timeline', function(){
		WeixinJSBridge.invoke('shareTimeline', shareMeta);
	});
	WeixinJSBridge.on('menu:share:weibo', function(){
		WeixinJSBridge.invoke('shareWeibo', {
			content: shareMeta.title + shareMeta.desc,
			url: shareMeta.link
		});
	});
});