package org.example.listeners;

import com.google.common.eventbus.Subscribe;
import org.hippoecm.hst.core.container.ComponentManager;
import org.hippoecm.hst.core.container.ComponentManagerAware;
import org.hippoecm.hst.pagecomposer.jaxrs.api.ChannelEvent;

import javax.jcr.RepositoryException;

public class MyChannelEventListener implements ComponentManagerAware {

    private ComponentManager componentManager;

    @Override
    public void setComponentManager(ComponentManager componentManager) {
        this.componentManager = componentManager;
    }

    public void init(){
        componentManager.registerEventSubscriber(this);
    }

    public void destroy(){
        componentManager.unregisterEventSubscriber(this);
    }

    @Subscribe
    public void onEvent(ChannelEvent event){
        if(event.getException() != null){
            return;
        }

        final ChannelEvent.ChannelEventType type = event.getChannelEventType();

        if(type != ChannelEvent.ChannelEventType.DISCARD && type != ChannelEvent.ChannelEventType.PUBLISH){
            return;
        } else if (type == ChannelEvent.ChannelEventType.PUBLISH){
            String a = event.getEditingPreviewSite().getName();
            ComponentManager mng = this.componentManager;
            try {
                String username = event.getRequestContext().getSession().getUserID();
                String host = getHost(event.toString());
                String channel = event.getEditingPreviewSite().getName();
                String evt = event.getChannelEventType().toString();

            } catch (RepositoryException e) {
                e.printStackTrace();
            }

        }
    }

    public String getHost(String channel){
        int request = channel.indexOf("request");
        int sub = channel.indexOf("host",request+1);
        String host = channel.substring(sub);
        int from = host.indexOf("'");
        int to = host.indexOf("'",from+1);
        return host.substring(from,to);
    }
}
