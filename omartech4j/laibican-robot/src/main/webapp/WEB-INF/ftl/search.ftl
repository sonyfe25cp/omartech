<#include "common/head.ftl" >
<#include "common/front-nav.ftl">
<div class="container">
    <form action="/search" method="get">
        <table class="table">
            <tr>
                <td>
                    <input name="query">
                </td>
                <td>
                    <select name="appName">
                        <option value="Bican">比惨</option>
                        <option value="Xiaohua">笑话</option>
                        <option value="Other">其他</option>
                    </select>
                </td>
                <td>
                    <input type="submit" class="btn btn-info" value="搜索">
                </td>
            </tr>
        </table>
    </form>
<#if articles??>
    <table>
        <tr>
            <th>ID</th>
            <th>Content</th>
        </tr>
        <#list articles as article>
            <tr>
                <td>${article.id}</td>
                <td>${article.content}</td>
            </tr>
        </#list>
    </table>
</#if>
</div>
<#include "common/bottom.ftl">