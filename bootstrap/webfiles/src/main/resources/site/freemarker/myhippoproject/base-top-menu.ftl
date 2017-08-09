<#include "../include/imports.ftl">
<#if menu??>
  <#if menu.siteMenuItems??>
  <nav>
      <ul class="navigation" id="main-navigation">
        <#list menu.siteMenuItems as item>
          <#if  item.selected || item.expanded>
              <li><a href="<@hst.link link=item.hstLink/>" class="activelink"><span class="label-nav">${item.name?html}</span> </a></li>
          <#else>
              <li><a href="<@hst.link link=item.hstLink/>"><span class="label-nav">${item.name?html}</span></a></li>
          </#if>
        </#list>
      </ul>
  </nav>
  </#if>
  <@hst.cmseditmenu menu=menu/>
</#if>




<#--@ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu"
@ftlvariable name="editMode" type="java.lang.Boolean"-->
<#--
<#if menu??>
<div class="has-edit-button">
  <#if menu.siteMenuItems??>
    <ul class="nav nav-pills">
      <#list menu.siteMenuItems as item>
        <#if !item.hstLink?? && !item.externalLink??>
          <#if item.selected || item.expanded>
            <li class="active"><div style="padding: 10px 15px;">${item.name?html}</div></li>
          <#else>
            <li><div style="padding: 10px 15px;">${item.name?html}</div></li>
          </#if>
        <#else>
          <#if item.hstLink??>
            <#assign href><@hst.link link=item.hstLink/></#assign>
          <#elseif item.externalLink??>
            <#assign href>${item.externalLink?replace("\"", "")}</#assign>
          </#if>
          <#if  item.selected || item.expanded>
            <li class="active"><a href="${href}">${item.name?html}</a></li>
          <#else>
            <li><a href="${href}">${item.name?html}</a></li>
          </#if>
        </#if>
      </#list>
    </ul>
  </#if>
  <@hst.cmseditmenu menu=menu/>
</div>
</#if>-->
