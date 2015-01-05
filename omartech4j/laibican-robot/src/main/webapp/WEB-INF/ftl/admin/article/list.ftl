<a href="./add">Add an article</a>
<table class="table">
    <tr>
        <th>ID</th>
        <th>内容</th>
        <th>操作</th>
    </tr>
<#--<#if articles??>-->
    <#list articles as article>
        <tr>
            <td>${article.id}</td>
            <td>${article.content}</td>
            <td><a href="/admin/article/show/${article.id}">查看</a></td>
        </tr>
    </#list>
<#--</#if>-->
</table>