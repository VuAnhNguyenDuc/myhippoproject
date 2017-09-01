package org.example.components;

import com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;
import org.example.beans.User;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.component.support.forms.FormField;
import org.hippoecm.hst.component.support.forms.FormMap;
import org.hippoecm.hst.component.support.forms.FormUtils;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.ObjectBeanPersistenceException;
import org.hippoecm.hst.content.beans.manager.workflow.BaseWorkflowCallbackHandler;
import org.hippoecm.hst.content.beans.manager.workflow.WorkflowPersistenceManager;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.FilterException;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.hippoecm.hst.content.beans.standard.HippoFolder;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.repository.documentworkflow.DocumentWorkflow;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

public class UserRegisterForm extends BaseHstComponent{

    @Override
    public void doAction(HstRequest request, HstResponse response) throws HstComponentException {
        FormMap formMap = new FormMap(request, new String[]{"username","password","email"});

        boolean valid = true;
        for(String fieldName : formMap.getFieldNames()){
            FormField field = formMap.getField(fieldName);
            if(StringUtils.isEmpty(field.getValue()) && (fieldName.equals("username") || fieldName.equals("password") )){
                field.addMessage("Field is required");
                valid = false;
            }
            else if(fieldName.equals("username") && field.getValue().length() > 32){
                field.addMessage("Username must not be longer than 32 characters");
            }
            else if(fieldName.equals("password") && field.getValue().length() > 32){
                field.addMessage("Password must not be longer than 32 characters");
            }
        }

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

            if(checkUsername(request,response,formMap.getField("username").getValue())){
                request.setAttribute("username-exist","This username is already taken");
                response.setRenderParameter("failed","failed");
            } else{
                String documentPath = wpm.createAndReturn(folderPath, "myhippoproject:user",documentName,true);

                User user = (User) wpm.getObject(documentPath);

                if(user == null){
                    throw new HstComponentException("Failed to add User");
                }

                user.setUsername(formMap.getField("username").getValue());
                user.setPassword(formMap.getField("password").getValue());
                user.setEmail(formMap.getField("email").getValue());

                wpm.update(user);
                response.setRenderParameter("success","success");
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        } catch (ObjectBeanPersistenceException e) {
            e.printStackTrace();
        } catch (ObjectBeanManagerException e) {
            e.printStackTrace();
        }

    }

    private boolean checkUsername(HstRequest request, HstResponse response, String username){
        HstRequestContext context = request.getRequestContext();
        HippoBean scope = context.getSiteContentBaseBean();

        if(scope == null){
            throw new HstComponentException("Scope is not allowed to be null");
        }
        String sortField = "myhippoproject:username";

        Class filterClass = getObjectConverter().getAnnotatedClassFor("myhippoproject:user");
        try {
            HstQuery hstQuery = context.getQueryManager().createQuery(scope,filterClass,true);
            Filter filter = hstQuery.createFilter();
            filter.addAndFilter(createQueryFilter(request,hstQuery,sortField,username));

            hstQuery.setFilter(filter);

            HstQueryResult result = hstQuery.execute();
            if(result.getTotalSize() > 0){
                return true;
            }
        } catch (QueryException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected Filter createQueryFilter(final HstRequest request, final HstQuery query, String searchField, String queryParam) throws FilterException {
        Filter queryFilter = null;

        // check if we have query parameter

        if (!Strings.isNullOrEmpty(queryParam)) {

            queryFilter = query.createFilter();
            queryFilter.addEqualTo(searchField, queryParam);
        }
        return queryFilter;
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);

        FormMap formMap = new FormMap();
        FormUtils.populate(request,formMap);

        formMap.addMessage("username-exist", "This username existed");

        request.setAttribute("errors", formMap.getMessage());

    }
}
