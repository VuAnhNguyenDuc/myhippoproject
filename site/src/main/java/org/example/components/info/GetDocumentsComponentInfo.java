package org.example.components.info;

import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

public interface GetDocumentsComponentInfo {
    @Parameter(name = "document", required = true, displayName = "Select Folder")
    @JcrPath(
            isRelative = true,
            pickerSelectableNodeTypes = {"hippostd:folder","hippostd:directory"}
    )
    String getDocumentPath();

    @Parameter(name = "type", required = true,displayName = "Select Type")
    String getType();
}
