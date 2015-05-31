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
                        <a href="/daleitai">打擂台</a>
                        <a href="/kanxiaohua" class="current">看笑话</a>
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
                <a class="js_useful" href="javascript:;" rel="follow" data-url="/momoArticle" data-sid="${article.id}"
                   data-type="salary">
                    <i class="i i_mark_h"></i>&nbsp;有意思(<span>${article.hot}</span>)</a>
                <a href="javascript:;"><em>分享给朋友</em></a>
            </div>
        </#list>

        </ul>
    <#--<div class="praise user_behavior">-->
    <#--<a class="js_useful" href="javascript:;" rel="follow" data-url="salary/useful.json" data-sid="21672"-->
    <#--data-type="salary"><i class="i i_mark_h"></i>&nbsp;(<span>0</span>)</a>-->
    <#--<a href="javascript:;"><em>分享给朋友</em></a>-->
    <#--</div>-->

    </div>
</div>


<div class="set">
    <div><a class="bt_orange" href="/kanxiaohua">上一页</a></div>
    <div><a class="bt_orange green" href="/kanxiaohua">下一页</a></div>
</div>
<p style="height:50px;"></p>
<a class="js_floatFooter floatFooter" href="/createArticle?commentType=Xiaohua">
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
<#include "../common/ba.ftl">
</body>
</html>