<#include "../include/imports.ftl">
<#if document??>
    <@hst.link var="link" hippobean=document/>
<div class="col-md-9 col-sm-9">
    <h2>${document.title?html}</h2>
    <p>${document.introduction?html}</p>
    <@hst.html hippohtml=document.content/>
</div>
<#elseif editMode>
<img src="<@hst.link path="/images/essentials/catalog-component-icons/simple-content.png" />"> Click to edit Simple Content
</#if>
<#-- @ftlvariable name="document" type="org.example.beans.ContentDocument" -->
<#--
<#if document??>
  <article class="has-edit-button">
    <@hst.cmseditlink hippobean=document/>
    <h3>${document.title?html}</h3>
    <#if document.publicationDate??>
      <p>
        <@fmt.formatDate value=document.publicationDate.time type="both" dateStyle="medium" timeStyle="short"/>
      </p>
    </#if>
    <#if document.introduction??>
      <p>
        ${document.introduction?html}
      </p>
    </#if>
    <@hst.html hippohtml=document.content/>
  </article>
&lt;#&ndash; @ftlvariable name="editMode" type="java.lang.Boolean"&ndash;&gt;
<#elseif editMode>
  <div>
    <img src="<@hst.link path="/images/essentials/catalog-component-icons/simple-content.png" />"> Click to edit Simple Content
  </div>
</#if>-->
