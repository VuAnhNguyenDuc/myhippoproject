package org.example.components;

import com.google.common.base.Strings;
import org.example.components.info.GetDocumentsComponentInfo;
import org.hippoecm.hst.content.beans.standard.HippoDocumentBean;
import org.hippoecm.hst.content.beans.standard.HippoFolder;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.CommonComponent;
import java.util.List;

@ParametersInfo(type = GetDocumentsComponentInfo.class)
public class GetDocumentsComponent extends CommonComponent{
    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);
        final GetDocumentsComponentInfo paramInfo = getComponentParametersInfo(request);
        final String documentPath = paramInfo.getDocumentPath();
        final String documentType = paramInfo.getType();
        if(!Strings.isNullOrEmpty(documentPath) && !Strings.isNullOrEmpty(documentType)){
            final HippoFolder folder = getHippoBeanForPath(documentPath,HippoFolder.class);
            final List<HippoDocumentBean> documents = folder.getDocuments();
            request.setAttribute("documents",documents);
            request.setAttribute("type",documentType);
        }else{
            request.setAttribute("error","Document path and type cannot be empty");
        }
        request.setAttribute(REQUEST_ATTR_PARAM_INFO, paramInfo);
    }
}
