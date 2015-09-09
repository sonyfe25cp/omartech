<#include "../common/head.ftl" >
<h3>今日
${area}
<#if intern>
    实习
<#else>
    全职
</#if>：${today}, 第${pageNo}页</h3>
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
<#if jobs??>
<table class="table table-striped">
    <thead>
    <tr>
        <th>职位名</th>
        <th>公司名</th>
    </tr>
    </thead>
    <tbody>
        <#list jobs as job>
        <tr>
            <td>
                <a href="${job.url}" target="_blank">${job.title}</a></td>
            <td>
                <#if job.company??>
                        ${job.company}
                    </#if>
            </td>
        </tr>
        </#list>
    </tbody>
</table>
<nav>
    <ul class="pagination">
        <#if pageNo &gt; 1>
            <li>
                <a href="/jobs/today?pageNo=${pageNo-1}&intern=${intern?string}&area=${area}" aria-label="Previous">
                    <span aria-hidden="true">上一页</span>
                </a>
            </li>
        </#if>
    <#--<li><a href="#">1</a></li>-->
    <#--<li><a href="#">2</a></li>-->
        <#if jobs?size &gt; 0 >
            <li>
                <a href="/jobs/today?pageNo=${pageNo+1}&intern=${intern?string}&area=${area}" aria-label="Next">
                    <span aria-hidden="true">下一页</span>
                </a>
            </li>
        </#if>
    </ul>
</nav>
<#else>
木有啦
</#if>
<#include "../common/bottom.ftl" >