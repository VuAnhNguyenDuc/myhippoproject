package org.example.components.info;

import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

public interface DocumentComponentInfo {
    @Parameter(name = "document", required = true, displayName = "Document")
    @JcrPath(
            isRelative = true,
            pickerConfiguration = "cms-pickers/documents",
            pickerSelectableNodeTypes = {"hippo:document"}
    )
    String getDocument();
}
