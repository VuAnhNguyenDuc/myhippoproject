package org.example;

import org.hippoecm.repository.HippoStdNodeType;
import org.hippoecm.repository.api.HippoNode;
import org.hippoecm.repository.util.JcrUtils;
import org.hippoecm.repository.util.NodeIterable;
import org.onehippo.cms7.services.HippoServiceRegistry;
import org.onehippo.cms7.services.eventbus.HippoEventBus;
import org.onehippo.cms7.services.eventbus.Subscribe;
import org.onehippo.repository.events.HippoWorkflowEvent;
import org.onehippo.repository.modules.DaemonModule;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

public class PublicationPostProcessingModule implements DaemonModule {

    public static final String PUBLICATION_INTERACTION = "default:handle:publish";
    public static final String TITLE_PROPERTY = "myhippoproject:title";

    private Session session;

    @Override
    public void initialize(Session session) throws RepositoryException {
        this.session = session;
        HippoServiceRegistry.registerService(this, HippoEventBus.class);
    }

    @Override
    public void shutdown() {
        HippoServiceRegistry.unregisterService(this, HippoEventBus.class);
    }

    @Subscribe
    public void handleEvent(final HippoWorkflowEvent event){
        if(event.success() && PUBLICATION_INTERACTION.equals(event.interaction())){
            postPublish(event);
        }
    }

    private void postPublish(final HippoWorkflowEvent workflowEvent){
        String title = null;
        try {
            final HippoNode handle = (HippoNode) session.getNodeByIdentifier(workflowEvent.subjectId());

            String user = workflowEvent.user();
            String documentType = workflowEvent.documentType();
            String subjectPath = workflowEvent.subjectPath();
            String interaction = workflowEvent.interaction();

            final Node published = getPublishedVariant(handle);
            if(published != null){
                title = JcrUtils.getStringProperty(published, TITLE_PROPERTY, handle.getDisplayName());
            } else{
                title = handle.getDisplayName();
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        System.out.println(title);
    }

    private static Node getPublishedVariant(Node handle) throws RepositoryException{
        for(Node variant: new NodeIterable(handle.getNodes(handle.getName()))){
            final String state = JcrUtils.getStringProperty(variant, HippoStdNodeType.HIPPOSTD_STATE, null);
            if(HippoStdNodeType.PUBLISHED.equals(state)){
                return variant;
            }
        }
        return null;
    }
}
