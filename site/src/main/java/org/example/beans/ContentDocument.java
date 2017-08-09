package org.example.beans;

import java.util.Calendar;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.standard.HippoBean;

@HippoEssentialsGenerated(internalName = "myhippoproject:contentdocument")
@Node(jcrType = "myhippoproject:contentdocument")
public class ContentDocument extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "myhippoproject:introduction")
    public String getIntroduction() {
        return getProperty("myhippoproject:introduction");
    }

    @HippoEssentialsGenerated(internalName = "myhippoproject:title")
    public String getTitle() {
        return getProperty("myhippoproject:title");
    }

    @HippoEssentialsGenerated(internalName = "myhippoproject:content")
    public HippoHtml getContent() {
        return getHippoHtml("myhippoproject:content");
    }

    @HippoEssentialsGenerated(internalName = "myhippoproject:publicationdate")
    public Calendar getPublicationDate() {
        return getProperty("myhippoproject:publicationdate");
    }

    @HippoEssentialsGenerated(internalName = "myhippoproject:hippo_mirror")
    public HippoBean getHippo_mirror() {
        return getLinkedBean("myhippoproject:hippo_mirror", HippoBean.class);
    }
}
