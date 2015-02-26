<#include "/common/head.ftl" >
<h3>今日
    <#if intern>
        实习
    <#else>
        全职
    </#if>：${today}, 第${pageNo}页</h3>
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
                <a href="/jobs/today?pageNo=${pageNo-1}&intern=${intern?string}" aria-label="Previous">
                    <span aria-hidden="true">上一页</span>
                </a>
            </li>
        </#if>
    <#--<li><a href="#">1</a></li>-->
    <#--<li><a href="#">2</a></li>-->
        <#if jobs?size &gt; 0 >
            <li>
                <a href="/jobs/today?pageNo=${pageNo+1}&intern=${intern?string}" aria-label="Next">
                    <span aria-hidden="true">下一页</span>
                </a>
            </li>
        </#if>
    </ul>
</nav>
<#else>
木有啦
</#if>
<#include "/common/bottom.ftl" >