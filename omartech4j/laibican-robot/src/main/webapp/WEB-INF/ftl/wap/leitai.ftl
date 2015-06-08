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
                        <a href="/story">讲悲剧</a>
                        <a href="/daleitai" class="current">打擂台</a>
                        <a href="/kanxiaohua">看笑话</a>
                        <a href="/choumeinv">瞅美女</a>
                    </p>
                </div>
            </div>
            <a class="moving" href="javascript:;"><i class="arrow_green"></i></a>
        </section>

    <#assign num = 1>
    <#list articles as article>
        <h2 class="tt">
            <#if num == 1>
                <span>擂主的悲剧</span>
                <#assign num = 2>
            <#else>
                <span>挑战者的悲剧</span>
            </#if>
        </h2>
        <fieldset>
            <p>${article.content}</p>
        </fieldset>
        <div class="praise user_behavior">
            <a class="js_useful" href="javascript:;" rel="follow" data-url="/momoArticle" data-sid="${article.id}"
               data-type="salary"><i class="i i_mark_h"></i>&nbsp;这个比较惨(<span>${article.hot}</span>)</a>
            <a href="javascript:;"><em>分享给朋友</em></a>
        </div>
    </#list>
    </div>

    <div class="set">
        <div><a class="bt_orange" href="/daleitai">上一组</a></div>
        <div><a class="bt_orange green" href="/daleitai">下一组</a></div>
    </div>
</div>
<#include "footer.ftl" >

<a class="js_floatFooter floatFooter" href="/createArticle?commentType=Bican">
    <em>我来讲个悲剧！</em>
</a>

<div class="mask" id="wxMask">
    <div class="transmitDialog" id="transmitDialog">
        <p class="t-center"><i class="share_font"></i></p>
        <i class="arrow_down"></i>
    </div>
</div>
<script src="/js/wap/zepto.js"></script>
<script src="/js/wap/iscroll-lite.js"></script>
<script src="/js/wap/m-laibican.js"></script>
<#include "../common/ba.ftl">
</body>
</html>