<#include "../include/imports.ftl">
<#--<#if pageable??>-->
<div>
    <div class="row">
        <div class="col-md-12 col-sm-12">
            <h2 class="h2-section-title">Custom Products Bitch</h2>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <h2 class="h2-section-title">Cách 1 : GetHippoBeanForPath</h2>
            <#list products as item>
                <@hst.link var="link" hippobean=item/>
                <div class="col-md-4 col-sm-4">
                    <div class="feature product-category">
                        <div class="feature-image">
                            <a href="${link}">
                                <#if item.images?? && item.images[0]??>
                                    <@hst.link var="img" hippobean=item.images[0].largesquare/>
                                    <img src="${img}" alt="${item.title?html}" />
                                </#if>
                            </a>
                            <div class="feature-content">
                                <h3 class="h3-body-title">
                                    <a href="${link}">${item.title?html}</a>
                                </h3>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>

            <h2 class="h2-section-title">Cách 2 : GetScopeBean</h2>
            <#list productBeans as product>
                <@hst.link var="link" hippobean=product/>
                <div class="col-md-4 col-sm-4">
                    <div class="feature product-category">
                        <div class="feature-image">
                            <a href="${link}">
                                <#if product.images?? && product.images[0]??>
                                    <@hst.link var="img" hippobean=product.images[0].largesquare/>
                                    <img src="${img}" alt="${product.title?html}" />
                                </#if>
                            </a>
                            <div class="feature-content">
                                <h3 class="h3-body-title">
                                    <a href="${link}">${product.title?html}</a>
                                </h3>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </div>
</div>
<#--
</#if>-->
