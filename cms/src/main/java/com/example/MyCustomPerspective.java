package com.example;


import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.commons.webdav.QueryUtil;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.frontend.plugins.standards.panelperspective.PanelPluginPerspective;
import org.hippoecm.frontend.session.UserSession;
import org.hippoecm.hst.servlet.utils.SessionUtils;
import org.hippoecm.repository.api.HippoSession;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;

public class MyCustomPerspective extends PanelPluginPerspective {
    private FileUploadField field;

    private static final ResourceReference PERSPECTIVE_CSS = new CssResourceReference(MyCustomPerspective.class, "MyCustomPerspective.css");
    @SuppressWarnings("unchecked")
    public MyCustomPerspective(IPluginContext context, IPluginConfig config){
        super(context,config);
        setOutputMarkupId(true);
        try {
            HippoSession session = (HippoSession) UserSession.get().getJcrSession();
            Node node = session.getNode("/hst:hst/hst:configurations/myhippoproject/hst:sitemap/about");
            System.out.println(node.getProperty("jcr:primaryType").getString());
            System.out.println(node.getProperty("jcr:uuid").getString());
            System.out.println(node.getProperty("hst:componentconfigurationid").getString());
            System.out.println(node.getProperty("hst:relativecontentpath").getString());

            Query q = session.getWorkspace().getQueryManager().createQuery("select * from nt:base where jcr:uuid = '19a03281-62fb-4f41-ac4f-25f3911e17cc'", Query.SQL);

            QueryResult r = q.execute();
            if(r.getNodes().getSize() > 0){
                // get node path
                String path = r.getNodes().nextNode().getPath();

                for (NodeIterator i = r.getNodes(); i.hasNext(); ) {

                    System.err.println("document " + i.nextNode().getPath() + " matches");
                }
            }


            //QueryResult queryResult =
            /*if(JcrUtils.getNodes()){

            }*/

            /*Node root = session.getNode("/hst:hst/hst:configurations/myhippoproject/hst:sitemap");
            Node newNode = root.addNode("test-new-node","hst:sitemapitem");
            //newNode.setProperty("jcr:primaryType","hst:sitemapitem");
            //newNode.setProperty("jcr:uuid","19a03281-62fb-4f41-ac4f-25f3911e17tt");
            newNode.setProperty("hst:componentconfigurationid","hst:pages/contentpage");
            newNode.setProperty("hst:relativecontentpath","content/test-new-node");
            session.save();*/

        } catch (RepositoryException e) {
            e.printStackTrace();
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
