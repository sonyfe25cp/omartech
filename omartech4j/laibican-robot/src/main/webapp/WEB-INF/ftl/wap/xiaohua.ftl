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
        <section class="menu">
            <div class="menu_wrap">
                <div class="menu_con" id="companyMenu">
                    <p>
                        <a href="/story" >讲悲剧</a>
                        <a href="/daleitai">打擂台</a>
                        <a href="/kanxiaohua"  class="current">看笑话</a>
                        <a href="/choumeinv">瞅美女</a>
                    </p>
                </div>
            </div>
            <a class="moving" href="javascript:;"><i class="arrow_green"></i></a>
        </section>
        <ul id="job_list" class="v_menu salary_detail">
            <#list articles as article>
            <#--<li>-->
                <fieldset>
                <p>${article.content}</p>
                </fieldset>
                <div class="praise user_behavior">
                    <a class="js_useful" href="javascript:;" rel="follow" data-url="salary/useful.json" data-sid="21672"
                       data-type="salary">
                        <i class="i i_mark_h"></i>&nbsp;无聊(<span>0</span>)</a>
                    <a href="javascript:;"><em>真逗</em></a>
                </div>
                <#--<p>软件工程师 <span class="grey ml15">共378条工资</span></p>-->

                <#--<p class="grey">-->
					<#--<span class="fright">-->
						<#--平均<span class="orange">￥5,182</span>-->
					<#--</span>-->
                    <#--最低￥3,130-->
					 <#--<span class="ml15">-->
					<#--最高￥12,300-->
					<#--</span>-->
                <#--</p>-->
            <#--</li>-->
            </#list>
            <#--<li>-->
                <#--<p>基本工资 <span class="grey ml15">共127条工资</span></p>-->

                <#--<p class="grey">-->
					<#--<span class="fright">-->
						<#--平均<span class="orange">￥5,182</span>-->
					<#--</span>-->
                    <#--最低￥2,300-->
					 <#--<span class="ml15">-->
					<#--最高￥6,000-->
					<#--</span>-->
                <#--</p>-->
            <#--</li>-->
            <#--<li>-->
                <#--<p>奖金 <span class="grey ml15">共100条工资</span></p>-->

                <#--<p class="grey">-->
					<#--<span class="fright">-->
						<#--平均<span class="orange">￥5,182</span>-->
					<#--</span>-->
                    <#--最低￥3,300-->
					 <#--<span class="ml15">-->
					<#--最高￥13,600-->
					<#--</span>-->
                <#--</p>-->
            <#--</li>-->
            <#--<li>-->
                <#--<p>补助 <span class="grey ml15">共96条工资</span></p>-->

                <#--<p class="grey">-->
					<#--<span class="fright">-->
						<#--平均<span class="orange">￥5,182</span>-->
					<#--</span>-->
                    <#--最低￥2,900-->
					 <#--<span class="ml15">-->
					<#--最高￥9,500-->
					<#--</span>-->
                <#--</p>-->
            <#--</li>-->
            <#--<li>-->
                <#--<p>销售提成 <span class="grey ml15">共95条工资</span></p>-->

                <#--<p class="grey">-->
					<#--<span class="fright">-->
						<#--平均<span class="orange">￥5,182</span>-->
					<#--</span>-->
                    <#--最低￥4,500-->
					 <#--<span class="ml15">-->
					<#--最高￥18,000-->
					<#--</span>-->
                <#--</p>-->
            <#--</li>-->
            <#--<li>-->
                <#--<p>其他收入 <span class="grey ml15">共95条工资</span></p>-->

                <#--<p class="grey">-->
					<#--<span class="fright">-->
						<#--平均<span class="orange">￥5,182</span>-->
					<#--</span>-->
                    <#--最低￥4,500-->
					 <#--<span class="ml15">-->
					<#--最高￥18,000-->
					<#--</span>-->
                <#--</p>-->
            <#--</li>-->
            <#--<li>-->
                <#--<p>其他收入 <span class="grey ml15">共95条工资</span></p>-->

                <#--<p class="grey">-->
					<#--<span class="fright">-->
						<#--平均<span class="orange">￥5,182</span>-->
					<#--</span>-->
                    <#--最低￥4,500-->
					 <#--<span class="ml15">-->
					<#--最高￥18,000-->
					<#--</span>-->
                <#--</p>-->
            <#--</li>-->
            <#--<li>-->
                <#--<p>其他收入 <span class="grey ml15">共95条工资</span></p>-->

                <#--<p class="grey">-->
					<#--<span class="fright">-->
						<#--平均<span class="orange">￥5,182</span>-->
					<#--</span>-->
                    <#--最低￥4,500-->
					 <#--<span class="ml15">-->
					<#--最高￥18,000-->
					<#--</span>-->
                <#--</p>-->
            <#--</li>-->
            <#--<li>-->
                <#--<p>其他收入 <span class="grey ml15">共95条工资</span></p>-->

                <#--<p class="grey">-->
					<#--<span class="fright">-->
						<#--平均<span class="orange">￥5,182</span>-->
					<#--</span>-->
                    <#--最低￥4,500-->
					 <#--<span class="ml15">-->
					<#--最高￥18,000-->
					<#--</span>-->
                <#--</p>-->
            <#--</li>-->
            <#--<li>-->
                <#--<p>其他收入 <span class="grey ml15">共95条工资</span></p>-->

                <#--<p class="grey">-->
					<#--<span class="fright">-->
						<#--平均<span class="orange">￥5,182</span>-->
					<#--</span>-->
                    <#--最低￥4,500-->
					 <#--<span class="ml15">-->
					<#--最高￥18,000-->
					<#--</span>-->
                <#--</p>-->
            <#--</li>-->
            <#--<li>-->
                <#--<p>其他收入 <span class="grey ml15">共95条工资</span></p>-->

                <#--<p class="grey">-->
					<#--<span class="fright">-->
						<#--平均<span class="orange">￥5,182</span>-->
					<#--</span>-->
                    <#--最低￥4,500-->
					 <#--<span class="ml15">-->
					<#--最高￥18,000-->
					<#--</span>-->
                <#--</p>-->
            <#--</li>-->
            <#--<li>-->
                <#--<p>其他收入 <span class="grey ml15">共95条工资</span></p>-->

                <#--<p class="grey">-->
					<#--<span class="fright">-->
						<#--平均<span class="orange">￥5,182</span>-->
					<#--</span>-->
                    <#--最低￥4,500-->
					 <#--<span class="ml15">-->
					<#--最高￥18,000-->
					<#--</span>-->
                <#--</p>-->
            <#--</li>-->
            <#--<li>-->
                <#--<p>其他收入 <span class="grey ml15">共95条工资</span></p>-->

                <#--<p class="grey">-->
					<#--<span class="fright">-->
						<#--平均<span class="orange">￥5,182</span>-->
					<#--</span>-->
                    <#--最低￥4,500-->
					 <#--<span class="ml15">-->
					<#--最高￥18,000-->
					<#--</span>-->
                <#--</p>-->
            <#--</li>-->
            <#--<li>-->
                <#--<p>其他收入 <span class="grey ml15">共95条工资</span></p>-->

                <#--<p class="grey">-->
					<#--<span class="fright">-->
						<#--平均<span class="orange">￥5,182</span>-->
					<#--</span>-->
                    <#--最低￥4,500-->
					 <#--<span class="ml15">-->
					<#--最高￥18,000-->
					<#--</span>-->
                <#--</p>-->
            <#--</li>-->
        </ul>
        <#--<div class="praise user_behavior">-->
            <#--<a class="js_useful" href="javascript:;" rel="follow" data-url="salary/useful.json" data-sid="21672"-->
               <#--data-type="salary"><i class="i i_mark_h"></i>&nbsp;(<span>0</span>)</a>-->
            <#--<a href="javascript:;"><em>分享给朋友</em></a>-->
        <#--</div>-->

    </div>
</div>


<div class="set">
    <div><a class="bt_orange" href="/kanxiaohua?pageNo=${pageNo-1}">上一页</a></div>
    <div><a class="bt_orange green" href="/kanxiaohua?pageNo=${pageNo+1}">下一页</a></div>
</div>
<p style="height:50px;"></p>
<a class="js_floatFooter floatFooter" href="#">
    <em>我来讲个笑话！</em>
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