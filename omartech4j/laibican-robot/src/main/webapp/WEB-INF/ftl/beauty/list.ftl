<table class="table">
    <tr>
        <th></th>
    </tr>
<#if beauties??>
    <#list beauties as beauty>
        <tr>
            <td><a href="#"><img src="${beauty.thumbLargeUrl}"></img></a></td>
        </tr>
    </#list>
</#if>
</table>