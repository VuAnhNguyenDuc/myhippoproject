package org.example.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSet;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageBean;

@HippoEssentialsGenerated(internalName = "myhippoproject:imageset")
@Node(jcrType = "myhippoproject:imageset")
public class Imageset extends HippoGalleryImageSet {
    @HippoEssentialsGenerated(internalName = "myhippoproject:small")
    public HippoGalleryImageBean getSmall() {
        return getBean("myhippoproject:small", HippoGalleryImageBean.class);
    }

    @HippoEssentialsGenerated(internalName = "myhippoproject:large")
    public HippoGalleryImageBean getLarge() {
        return getBean("myhippoproject:large", HippoGalleryImageBean.class);
    }

    @HippoEssentialsGenerated(internalName = "myhippoproject:smallsquare")
    public HippoGalleryImageBean getSmallsquare() {
        return getBean("myhippoproject:smallsquare",
                HippoGalleryImageBean.class);
    }

    @HippoEssentialsGenerated(internalName = "myhippoproject:mediumsquare")
    public HippoGalleryImageBean getMediumsquare() {
        return getBean("myhippoproject:mediumsquare",
                HippoGalleryImageBean.class);
    }

    @HippoEssentialsGenerated(internalName = "myhippoproject:largesquare")
    public HippoGalleryImageBean getLargesquare() {
        return getBean("myhippoproject:largesquare",
                HippoGalleryImageBean.class);
    }

    @HippoEssentialsGenerated(internalName = "myhippoproject:banner")
    public HippoGalleryImageBean getBanner() {
        return getBean("myhippoproject:banner", HippoGalleryImageBean.class);
    }
}
