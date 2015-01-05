<table>
    <tr>
        <th>ID</th>
        <th>content</th>
        <th>操作</th>
    </tr>
<#list replyMessages as replyMessage>
<tr>
    <td>${replyMessage.id}</td>
    <td>${replyMessage.content}</td>
    <td>edit</td>
</#list>
</tr>
</table>