<#include "../include/imports.ftl">
<#if document??>
    <@hst.link var="link" hippobean=document/>
<div class="blog-post">
    <@hst.cmseditlink hippobean=document/>
    <div class="blog-post-type">
        <i class="icon-news"> </i>
    </div>
    <div class="blog-span">
        <#if document.image?? && document.image.large??>
            <@hst.link var="img" hippobean=document.image.large/>
            <div class="blog-post-featured-img">
                <img src="${img}" alt="${document.title?html}" />
            </div>
        </#if>
        <h2>${document.title}</h2>
        <div class="blog-post-body">
            <p>${document.introduction?html}</p>
            <@hst.html hippohtml=document.content/>
        </div>
        <div class="blog-post-details">
            <div class="blog-post-details-item blog-post-details-item-left icon-calendar">
                <#if document.date??>
                    <span class="date">
                        <@fmt.formatDate value=document.date.time type="both" dateStyle="medium" timeStyle="short"/>
            </span>
                </#if>
            </div>
        </div>
    </div>
</div>
</#if>
<#-- @ftlvariable name="document" type="org.example.beans.NewsDocument" -->
<#--
<#if document??>
  <@hst.link var="link" hippobean=document/>
  <article class="has-edit-button">
    <@hst.cmseditlink hippobean=document/>
    <h3><a href="${link}">${document.title?html}</a></h3>
    <#if document.date??>
      <p><@fmt.formatDate value=document.date.time type="both" dateStyle="medium" timeStyle="short"/></p>
    </#if>
    <#if document.endDate??>
      <p><@fmt.formatDate value=document.endDate.time type="both" dateStyle="medium" timeStyle="short"/></p>
    </#if>
    <#if document.author??>
      <p>${document.author?html}</p>
    </#if>
    <#if document.source??>
      <p>${document.source?html}</p>
    </#if>
    <#if document.location??>
      <p>${document.location?html}</p>
    </#if>
    <#if document.introduction??>
      <p>${document.introduction?html}</p>
    </#if>
    <#if document.image?? && document.image.original??>
      <@hst.link var="img" hippobean=document.image.original/>
      <figure>
        <img src="${img}" title="${document.image.fileName?html}" alt="${document.image.fileName?html}"/>
        <#if document.image.description??>
          <figcaption>${document.image.description?html}</figcaption>
        </#if>
      </figure>
    </#if>
    <@hst.html hippohtml=document.content/>
  </article>
</#if>-->
