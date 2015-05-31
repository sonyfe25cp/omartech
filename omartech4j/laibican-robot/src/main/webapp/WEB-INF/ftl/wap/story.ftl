<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
    <meta name="apple-touch-fullscreen" content="yes"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <meta name="format-detection" content="telephone=no"/>
    <title>来比惨网</title>
    <link rel="stylesheet" href="/css/wap/base.css"/>
    <link rel="stylesheet" href="/css/wap/main.css"/>
    <link rel="stylesheet" href="/css/wap/salary.css"/>
</head>

<body>
<div class="wrapper detail">
    <div class="content">
    <#include "subHeader.ftl" >

        <h2 class="tt"><span>一位网友的真实经历</span></h2>
        <fieldset>
            <p>“我经历过的最难的面试，不好忽悠啊”</p>

            <p class="f12 mt10">面试易宝支付产品经理并通过面试</p>

            <p class="grey f12">发布于 2月2日 北京</p>

            <h3>面试经历</h3>

            <p>
                我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面试经历，我是面。</p>

            <h3>面试官提出的问题</h3>

        </fieldset>


        <div class="praise user_behavior">
            <a class="js_useful" href="javascript:;" rel="follow" data-url="salary/useful.json" data-sid="21672"
               data-type="salary"><i class="i i_mark_h"></i>&nbsp;摸摸头(<span>0</span>)</a>
            <a href="javascript:;"><em>分享给朋友</em></a>
        </div>
    </div>
</div>
<div class="set">
    <div><a class="bt_orange" href="javascript:;">上一个</a></div>
    <div><a class="bt_orange green" href="javascript:;">下一个</a></div>
</div>
<p style="height:50px;"></p>
<a class="js_floatFooter floatFooter" href="#">
    <em>我也要讲个悲剧！</em>
</a>

<div class="mask" id="wxMask">
    <div class="transmitDialog" id="transmitDialog">
        <p class="t-center"><i class="share_font"></i></p>
        <i class="arrow_down"></i>
    </div>
</div>
<script src="/js/wap/zepto.js"></script>
<script src="/js/wap/iscroll-lite.js"></script>
<script src="/js/wap/m-kanzhun.js"></script>
<script type="text/javascript">
    document.querySelector('span.more').addEventListener('click', function (e) {
        var details = this.parentNode.querySelector('span.show_details');

        if (details) {
            if (details.classList.contains('hidden')) {
                this.innerHTML = '<i class="ml10">收起</i>';
                details.classList.remove('hidden');
            } else {
                this.innerHTML = '... <i>展开</i>';
                details.classList.add('hidden');
            }
        }
    }, false);
    $.setCompanyMenu(2);
</script>
</body>
</html>