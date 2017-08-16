package org.example.components.info;

import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;
public interface EssentialsCustomComponentInfo {

    @Parameter(name = "document", required = true, displayName = "Select Folder")
    @JcrPath(
            isRelative = true,
            pickerSelectableNodeTypes = {"hippostd:folder",
            "hippostd:directory"}
            )
    String getDocument();
}
