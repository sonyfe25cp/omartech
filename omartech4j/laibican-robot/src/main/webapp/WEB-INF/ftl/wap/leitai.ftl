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

        <h2 class="tt"><span>擂主的悲剧</span></h2>
        <fieldset>
            <p>1、负责用户端产品规划、设计以及持续改进，不断创新、挖掘、设计新的产品或功能；</p>
            <p>2、负责产品需求收集、整理，撰写，进行用户需求分析，了解竞品动态与行业情况； </p>
            <p>3、负责与seo部门对接，优化招聘的seo和sem； </p>
            <p>4、进行持续的数据监控及分析，把握用户需求，不断对产品优化迭代。</p>
        </fieldset>
        <div class="praise user_behavior">
            <a class="js_useful" href="javascript:;" rel="follow" data-url="salary/useful.json" data-sid="21672"
               data-type="salary"><i class="i i_mark_h"></i>&nbsp;(<span>0</span>)</a>
            <a href="javascript:;"><em>分享给朋友</em></a>
        </div>

        <h2 class="tt"><span>自以为是的悲剧</span></h2>
        <fieldset>
            <p>1、本科或以上学历，2年以上互联网产品工作经历；</p>
            <p>2、擅长产品数据分析，能够从数据中挖掘内在的隐性规律，并找到改进方向；</p>
            <p>3、熟悉各种需求分析工具， 熟练掌握axure等界面原型设计工具；</p>
            <p>4、有良好的团队合作精神，较强的责任心、学习能力及抗压能力；</p>
            <p>5、招聘类项目背景的经验优先。</p>
        </fieldset>

        <div class="praise user_behavior">
            <a class="js_useful" href="javascript:;" rel="follow" data-url="salary/useful.json" data-sid="21672"
               data-type="salary"><i class="i i_mark_h"></i>&nbsp;(<span>0</span>)</a>
            <a href="javascript:;"><em>分享给朋友</em></a>
        </div>

    </div>
</div>

<footer class="page_ft">
    <p>看准网移动版 m.kanzhun.com</p>

    <p>电脑版 | 移动版</p>
</footer>
<div class="set">
    <div><a class="bt_orange" href="javascript:;">关注该公司</a></div>
    <div><a class="bt_orange green" href="javascript:;">告诉朋友们</a></div>
</div>
<p style="height:50px;"></p>
<a class="js_floatFooter floatFooter" href="#">
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