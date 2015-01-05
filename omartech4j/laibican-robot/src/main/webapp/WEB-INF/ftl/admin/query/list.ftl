<table class="table">
    <tr>
        <th>ID</th>
        <th>UID</th>
        <th>Query</th>
        <th>Source</th>
        <th>操作</th>
    </tr>
<#if queryLogs??>
    <#list queryLogs as queryLog>
        <tr>
            <td>${queryLog.id}</td>
            <td>${queryLog.uid}</td>
            <td>${queryLog.query}</td>
            <td>${queryLog.source}</td>
            <td></td>
        </tr>
    </#list>
</#if>
</table>