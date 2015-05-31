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
    <title>评公司-1</title>
    <link rel="stylesheet" href="/css/wap/base.css"/>
    <link rel="stylesheet" href="/css/wap/ugc_page.css">
</head>
<body>

<div class="wrapper">
    <form name="commentForm" action="/createArticle" method="post">
        <div class="js_comment_step1 content">
        <#include "subHeader.ftl" >
            <h2 class="comment_title">
                <i class="i i_o_trangle">匿名</i>

                <p>分享一条自己的故事，为其他心情不佳的人带去欢乐！</p>
            </h2>
            <section class="comment_content">
                <div class="js_allScoreDetail all_scoreDetail" style="display:none">
                    <p class="up_trange"><i class="i"></i></p>
                    <a class="js_close close">
                        <i class="i i_thin_no"></i>
                    </a>
                </div>

                <div class="divTextarea_1 bdefault mt10 clear">
                    <textarea name="title" placeholder="详细的讲讲你的故事吧" rows="10" maxlength="64"></textarea>
                    <em class="js_error error" style="display:none">少侠，你这故事太短..重讲！</em>
                </div>


            </section>
            <div class="ml10 mr10 mt10 mb10">
                <button class="js_btNext btn_submit_1" type="button">提交</button>
            </div>
        </div>
    </form>
</div>
<#include "footer.ftl">
<script src="/js/wap/jquery-2.1.1.min.js"></script>
<script src="/js/wap/m-kanzhun.js"></script>
</body>
</html>


