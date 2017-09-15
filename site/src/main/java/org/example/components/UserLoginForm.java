package org.example.components;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.frontend.session.LoginException;
import org.hippoecm.frontend.session.UserSession;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.component.support.forms.FormField;
import org.hippoecm.hst.component.support.forms.FormMap;
import org.hippoecm.hst.component.support.forms.FormUtils;
import org.hippoecm.hst.content.beans.manager.workflow.BaseWorkflowCallbackHandler;
import org.hippoecm.hst.content.beans.manager.workflow.WorkflowPersistenceManager;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.repository.documentworkflow.DocumentWorkflow;

import javax.jcr.RepositoryException;
import javax.jcr.Session;


public class UserLoginForm extends BaseHstComponent{

    @Override
    public void doAction(HstRequest request, HstResponse response) throws HstComponentException {
        FormMap formMap = new FormMap(request, new String[]{"username","password","email"});

        boolean valid = true;

        if(!valid){
            FormUtils.persistFormMap(request,response,formMap,null);
            return;
        }

        String siteContentBasePath = request.getRequestContext().getSiteContentBasePath();

        String folderPath = "/" + siteContentBasePath + "/users";

        String documentName = "User " + formMap.getField("username").getValue();

        WorkflowPersistenceManager wpm = null;

        try {
            Session session = request.getRequestContext().getSession();
            wpm = getWorkflowPersistenceManager(session);

            wpm.setWorkflowCallbackHandler(new BaseWorkflowCallbackHandler<DocumentWorkflow>() {
                @Override
                public void processWorkflow(DocumentWorkflow workflow) throws Exception {
                    workflow.requestPublication();
                }
            });

            UserSession session1 = UserSession.get();
            session1.login("admin","admin");
            System.out.println(session);
        } catch (RepositoryException e) {
            e.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        //UserCredentials credentials = new UserCredentials("admin", "admin");
        //this.login(credentials);
    }
}
