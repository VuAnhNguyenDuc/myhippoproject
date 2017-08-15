package org.example.components.info;

import org.hippoecm.hst.core.parameters.Parameter;

public interface CustomDocumentListComponentInfo extends EssentialsDocumentListComponentInfo {
    @Parameter(name = "keyword", required = false, displayName = "Keyword")
    String getKeyword();

    @Parameter(name = "isDate", required = true, displayName = "Is Date Value(M-dd-yyyy hh:mm)")
    Boolean isDate();

    @Parameter(name = "from", required = false, displayName = "From")
    String getFrom();

    @Parameter(name = "to", required = false, displayName = "To")
    String getTo();

    @Parameter(name = "lower", required = false, displayName = "Lower than")
    String getLower();

    @Parameter(name = "higher", required = false, displayName = "Higher than")
    String getHigher();
}
