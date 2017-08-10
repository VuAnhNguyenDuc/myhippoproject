package org.example.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.example.beans.Imageset;
import java.util.List;

@HippoEssentialsGenerated(internalName = "myhippoproject:product")
@Node(jcrType = "myhippoproject:product")
public class Product extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "myhippoproject:title")
    public String getTitle() {
        return getProperty("myhippoproject:title");
    }

    @HippoEssentialsGenerated(internalName = "myhippoproject:price")
    public Double getPrice() {
        return getProperty("myhippoproject:price");
    }

    @HippoEssentialsGenerated(internalName = "myhippoproject:introduction")
    public String getIntroduction() {
        return getProperty("myhippoproject:introduction");
    }

    @HippoEssentialsGenerated(internalName = "myhippoproject:Description")
    public HippoHtml getDescription() {
        return getHippoHtml("myhippoproject:Description");
    }

    @HippoEssentialsGenerated(internalName = "myhippoproject:images")
    public List<Imageset> getImages() {
        return getLinkedBeans("myhippoproject:images", Imageset.class);
    }
}
