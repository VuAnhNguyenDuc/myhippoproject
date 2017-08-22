<#include "../include/imports.ftl">
<@hst.actionURL var="actionLink"/>
<form action="${actionLink}" method="post">
    Username : <br>
    <input type="text" name="username"/><br>
    <#if errors['username']??>
        <span style="color: red">${errors['username']}</span>
    </#if>
    <br>
    <br>
    Password : <br>
    <input type="password" name="password"/><br>
    <#if errors['password']??>
        <span style="color: red">${errors['password']}</span>
    </#if>
    <br>
    <br>
    Email : <br>
    <input type="email" name="email"/><br>
    <#if errors['email']??>
        <span style="color: red">${errors['email']}</span>
    </#if>
    <br>
    <br>
    <#if errors['username-exist']??>
        <span style="color: red">${errors['username-exist']}</span>
        <br><br>
    </#if>
    <input type="submit" value="Register"/>
</form>
