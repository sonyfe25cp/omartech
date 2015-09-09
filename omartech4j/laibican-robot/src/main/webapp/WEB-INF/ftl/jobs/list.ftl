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
    <title>校招实习小助手</title>
    <link rel="stylesheet" href="/css/wap/base.css"/>
    <link rel="stylesheet" href="/css/wap/main.css"/>
    <link rel="stylesheet" href="/css/wap/salary.css"/>
</head>

<body>
<div class="wrapper detail">
    <div class="content">
    <#include "../wap/subHeader.ftl" >
        <section class="menu">
            <div class="menu_wrap">
                <div class="menu_con" id="companyMenu">
                    <p>
                        <a href="/story">讲悲剧</a>
                        <a href="/daleitai">打擂台</a>
                        <a href="/kanxiaohua">看笑话</a>
                        <a href="/jobs/today?intern=${intern?string}" class="current">找工作</a>
                    </p>
                </div>
            </div>
            <a class="moving" href="javascript:;"><i class="arrow_green"></i></a>
        </section>

        <form action="/jobs/today">
            <table>
                <tr>
                    <td>
                        <select name="area">
                            <option value="">不限</option>
                            <option value="北京">北京</option>
                            <option value="天津">天津</option>
                            <option value="湖南">湖南</option>
                            <option value="广东">广东</option>
                            <option value="广西">广西</option>
                            <option value="海南">海南</option>
                            <option value="重庆">重庆</option>
                            <option value="四川">四川</option>
                            <option value="贵州">贵州</option>
                            <option value="云南">云南</option>
                            <option value="西藏">西藏</option>
                            <option value="陕西">陕西</option>
                            <option value="甘肃">甘肃</option>
                            <option value="青海">青海</option>
                            <option value="宁夏">宁夏</option>
                            <option value="湖北">湖北</option>
                            <option value="河南">河南</option>
                            <option value="山东">山东</option>
                            <option value="河北">河北</option>
                            <option value="山西">山西</option>
                            <option value="内蒙古">内蒙古</option>
                            <option value="辽宁">辽宁</option>
                            <option value="吉林">吉林</option>
                            <option value="黑龙江">黑龙江</option>
                            <option value="上海">上海</option>
                            <option value="江苏">江苏</option>
                            <option value="浙江">浙江</option>
                            <option value="安徽">安徽</option>
                            <option value="福建">福建</option>
                            <option value="江西">江西</option>
                            <option value="新疆">新疆</option>
                        </select>
                        <input name="intern" value="${intern?string}" type="hidden">
                    </td>
                    <td><input type="submit" value="地区筛选"></td>
                </tr>
            </table>
        </form>

        <ul class="v_menu hot_jobs">
        <#if jobs?? && jobs?size != 0>
            <#list jobs as job>
                <li>
                    <a href="${job.url}">
                        <p>
                            <span class="blue">${job.title}</span>
                            <#if job.company??>
                                - ${job.company}
                            </#if>
                        </p>
                    </a>
                </li>
            </#list>
        <#else>
            <li>
                <a href="/story">
                    <p>
                        <span>今天暂时还没有相关职位，要不先看个故事等一会？</span>
                    </p>
                </a>
            </li>
        </#if>
        </ul>
    </div>
</div>


<div class="set">
<#if pageNo &gt; 1>
    <div><a class="bt_orange" href="/jobs/today?pageNo=${pageNo-1}&intern=${intern?string}&area=${area}">上一页</a></div>
</#if>
<#if jobs?size &gt; 0 >
    <div><a class="bt_orange green" href="/jobs/today?pageNo=${pageNo+1}&intern=${intern?string}&area=${area}">下一页</a>
    </div>
</#if>
</div>
<p style="height:50px;"></p>

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