package com.leonardo.monalisa.monalisa

import com.leonardo.monalisa.contentmanagement.content.proto.ContentProto

class ContentHelper {
    public static String getCaption(ContentProto contentProto){
        return contentProto.getContentMetadata().getMetadataMapMap().get("CAPTION").getLocaleMetadataMapMap().get("en");
    }
}
