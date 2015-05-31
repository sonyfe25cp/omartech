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

        <div class="company">
            <p><strong class="mr10">国家电网</strong>职员</p>

            <p><strong>6,600</strong> 平均工资</p>

            <p>最低￥7600 最高￥17600 <a class="openDetail" href="javascript:;">详情 <i class="i_open"></i></a></p>
        </div>
        <div class="co-chart-wrap">
            <p class="tt f_16"><span class="orange">国家电网</span>公司 <span class="orange">职员</span>工资分布
            </p>

            <div class="co-chart"></div>
        </div>
        <div class="ml10 mr10"><a ka="ooshare" class="btn-orange wx-share-btn" href="javascript:;">分享到朋友圈</a></div>

        <ul id="job_list" class="v_menu salary_detail">
            <li>
                <p>软件工程师 <span class="grey ml15">共378条工资</span></p>

                <p class="grey">
					<span class="fright">
						平均<span class="orange">￥5,182</span>
					</span>
                    最低￥3,130
					 <span class="ml15">
					最高￥12,300
					</span>
                </p>
            </li>
            <li>
                <p>基本工资 <span class="grey ml15">共127条工资</span></p>

                <p class="grey">
					<span class="fright">
						平均<span class="orange">￥5,182</span>
					</span>
                    最低￥2,300
					 <span class="ml15">
					最高￥6,000
					</span>
                </p>
            </li>
            <li>
                <p>奖金 <span class="grey ml15">共100条工资</span></p>

                <p class="grey">
					<span class="fright">
						平均<span class="orange">￥5,182</span>
					</span>
                    最低￥3,300
					 <span class="ml15">
					最高￥13,600
					</span>
                </p>
            </li>
            <li>
                <p>补助 <span class="grey ml15">共96条工资</span></p>

                <p class="grey">
					<span class="fright">
						平均<span class="orange">￥5,182</span>
					</span>
                    最低￥2,900
					 <span class="ml15">
					最高￥9,500
					</span>
                </p>
            </li>
            <li>
                <p>销售提成 <span class="grey ml15">共95条工资</span></p>

                <p class="grey">
					<span class="fright">
						平均<span class="orange">￥5,182</span>
					</span>
                    最低￥4,500
					 <span class="ml15">
					最高￥18,000
					</span>
                </p>
            </li>
            <li>
                <p>其他收入 <span class="grey ml15">共95条工资</span></p>

                <p class="grey">
					<span class="fright">
						平均<span class="orange">￥5,182</span>
					</span>
                    最低￥4,500
					 <span class="ml15">
					最高￥18,000
					</span>
                </p>
            </li>
        </ul>
        <div class="praise user_behavior">
            <a class="js_useful" href="javascript:;" rel="follow" data-url="salary/useful.json" data-sid="21672"
               data-type="salary"><i class="i i_mark_h"></i>&nbsp;(<span>0</span>)</a>
            <a href="javascript:;"><em>分享给朋友</em></a>
        </div>

        <ul class="v_menu">
            <li><a href="#">查看“易宝支付”所有工资 <i class="arrow_g"></i></a></li>
            <li><a href="#">查看“易宝支付”招聘 <i class="arrow_g"></i></a></li>
        </ul>

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
    <em>我在这里工作过-我要点评</em>
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