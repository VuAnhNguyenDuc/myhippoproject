package org.example.components.info;

import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;
//org.example.components.EssentialsCustomComponent
//org.onehippo.cms7.essentials.components.EssentialsListComponent
public interface EssentialsCustomComponentInfo {

    String DEFAULT_PICKER_PATH = "/content/products";
    String HIPPO_DOCUMENT = "hippo:imageset";
    String CMS_PICKERS_DOCUMENTS = "cms-picker/images";

    @Parameter(name = "document", required = true, displayName = "Select Folder")
    @JcrPath(
            isRelative = true,
            pickerSelectableNodeTypes = {"hippostd:folder",
            "hippostd:directory"}
            )
    String getDocument();

    /*@Parameter(name = "document", required = true, displayName = "Image")
    @JcrPath(
            isRelative = true,
            pickerConfiguration = "cms-pickers/images",
            pickerSelectableNodeTypes = {"hippogallery:imageset"}
    )
    String getDocument();*/
}
