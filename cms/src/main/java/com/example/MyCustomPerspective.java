package com.example;


import org.apache.commons.io.FilenameUtils;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.lang.Bytes;
import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.frontend.plugins.standards.panelperspective.PanelPluginPerspective;
import org.hippoecm.frontend.session.UserSession;
import org.hippoecm.repository.api.HippoSession;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import java.io.File;
import java.io.IOException;


public class MyCustomPerspective extends PanelPluginPerspective {
    private FileUploadField field;
    private TextField path;

    private static final ResourceReference PERSPECTIVE_CSS = new CssResourceReference(MyCustomPerspective.class, "MyCustomPerspective.css");
    @SuppressWarnings("unchecked")
    public MyCustomPerspective(IPluginContext context, IPluginConfig config){
        super(context,config);
        setOutputMarkupId(true);
        try {
            final TextField<String> path = new TextField<String>("path", Model.of(""));
            path.setRequired(true);

            Form<?> form = new Form<Void>("form"){
                private static final long serialVersionUID = 1L;
                @Override
                protected void onSubmit(){
                    String pathValue = path.getModelObject();
                    final FileUpload uploadFile = field.getFileUpload();

                    PageParameters pageParameters = new PageParameters();

                    if(uploadFile != null){
                        String extension = FilenameUtils.getExtension(uploadFile.getClientFileName());
                        if(!extension.equals("xml")){
                            pageParameters.add("error","Only XML file is accepted");
                        } else{
                            HippoSession session = (HippoSession) UserSession.get().getJcrSession();
                            Node node = null;
                            Boolean nodeExist = false;
                            try {
                                //"/hst:hst/hst:configurations/myhippoproject/hst:sitemap/about"
                                File xmlFile = new File(uploadFile.getClientFileName());
                                if(xmlFile.exists()){
                                    xmlFile.delete();
                                }
                                xmlFile.createNewFile();
                                uploadFile.writeTo(xmlFile);

                                ReadXML readXML = new ReadXML();
                                NodeList nodeList = readXML.readXML(xmlFile);
                                String nodeName = readXML.getRootName(xmlFile);

                                if(CheckNodeExistByPath(session,pathValue+"/"+nodeName)!=null){
                                    nodeExist = true;
                                }

                                Node root = session.getNode(pathValue);
                                if(nodeExist){
                                    addOrUpdateNode(root,nodeList,session.getNode(pathValue+"/"+nodeName),nodeName);
                                }else{
                                    addOrUpdateNode(root,nodeList,null,nodeName);
                                }

                                session.save();
                            } catch (RepositoryException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    pageParameters.add("path",pathValue);
                    System.out.println(pathValue);
                }
            };

            form.setMultiPart(true);
            form.setMaxSize(Bytes.megabytes(10));
            form.add(path);
            form.add(field = new FileUploadField("fileUpload"));
            add(form);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Node CheckNodeExistByPath(HippoSession session,String path){
        try {
            Node temp = session.getNode(path);
            return temp;
        } catch (RepositoryException e) {
            return null;//e.printStackTrace();
        }
    }

    private Node CheckNodeExistByUUID(HippoSession session,String UUID) throws RepositoryException {
        Query q = session.getWorkspace().getQueryManager().createQuery("select * from nt:base where jcr:uuid = '"+UUID+"'", Query.SQL);

        QueryResult r = q.execute();
        if(r.getNodes().getSize() > 0){
            // get node path
            //String path = r.getNodes().nextNode().getPath();
            return r.getNodes().nextNode();
        }
        return null;
    }

    private String getUUID(NodeList nodeList){
        for(int i = 0; i < nodeList.getLength(); i++){
            if(nodeList.item(i).getAttributes().getNamedItem("sv:name").getNodeValue().equals("jcr:uuid")){
                Element element = (Element) nodeList.item(i);
                return element.getElementsByTagName("sv:value").item(0).getTextContent();
            }
        }
        return "";
    }

    private void addOrUpdateNode(Node root, NodeList nodeList,Node oldNode,String nodeName) throws RepositoryException {
        Node newNode = null;
        if(oldNode != null){
            newNode = oldNode;
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            String property = nodeList.item(i).getAttributes().getNamedItem("sv:name").getNodeValue();
            Element element = (Element) nodeList.item(i);
            String value = "";
            if(element.getElementsByTagName("sv:value").getLength() > 0){
                value = element.getElementsByTagName("sv:value").item(0).getTextContent();
            } else{
                value = "";
            }

            if(property.equals("jcr:primaryType") && oldNode == null){
                newNode = root.addNode(nodeName,value);
            } else if(!property.equals("jcr:uuid") && !value.equals("")){
                newNode.setProperty(property,value);
            }
        }
    }

    @Override
    public String getPanelServiceId() {
        return null;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(CssHeaderItem.forReference(PERSPECTIVE_CSS));
    }


}
