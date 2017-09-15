package org.example.components;

import org.example.components.info.DocumentComponentInfo;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.cms7.essentials.components.CommonComponent;

public class DocumentComponent extends CommonComponent {
    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);
        final DocumentComponentInfo paramInfo = getComponentParametersInfo(request);
        final String documentPath = paramInfo.getDocument();
        setContentBeanForPath(documentPath,request,response);
    }
}
