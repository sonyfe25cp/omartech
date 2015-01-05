<#include "head.ftl" >
<#include "front-nav.ftl">
<div class="container">
    <ol class="breadcrumb">
        <li><a href="/">主页</a></li>
        <li><a href="#">比惨</a></li>
    <#--<li class="active">笑话</li>-->
    </ol>

<#if article??>
    <div class="jumbotron">
        <#if article.title??>
            <h1>${article.title}</h1>
        </#if>
        <#if article.content??>
            <p>${article.content}</p>
        </#if>
    </div>
</#if>
</div>
<#include "bottom.ftl" >
