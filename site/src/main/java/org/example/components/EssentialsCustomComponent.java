package org.example.components;

import com.google.common.base.Strings;
import org.example.beans.Product;
import org.example.components.info.EssentialsCustomComponentInfo;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.standard.*;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.CommonComponent;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@ParametersInfo(type = EssentialsCustomComponentInfo.class)
public class EssentialsCustomComponent extends CommonComponent {
    private static final Logger log = LoggerFactory.getLogger(EssentialsCustomComponent.class);

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);
        final EssentialsCustomComponentInfo paramInfo = getComponentParametersInfo(request);
        final String documentPath = paramInfo.getDocument();
        if (!Strings.isNullOrEmpty(documentPath)) {
            try {
                final HippoFolder folder = getHippoBeanForPath(documentPath,HippoFolder.class);
                final HippoBean beanFolder = getScopeBean(documentPath);
                List<HippoDocumentBean> documentBeans = new ArrayList<>();
                List<Product> productList = new ArrayList<Product>();
                if(beanFolder.isHippoFolderBean()){
                    documentBeans = beanFolder.getChildBeans(HippoDocumentBean.class);
                    productList = beanFolder.getChildBeans(Product.class);
                }
                final List<HippoDocumentBean> documents = folder.getDocuments();

                request.setAttribute("productList",productList);
                request.setAttribute("productBeans",documentBeans);
                request.setAttribute("products", documents);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        request.setAttribute(REQUEST_ATTR_PARAM_INFO, paramInfo);
    }
}
