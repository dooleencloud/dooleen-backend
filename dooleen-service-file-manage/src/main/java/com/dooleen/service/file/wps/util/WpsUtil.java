package com.dooleen.service.file.wps.util;


import com.dooleen.service.file.wps.propertie.RedirectProperties;
import com.dooleen.service.file.wps.propertie.WpsProperties;
import com.dooleen.service.file.wps.util.file.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WpsUtil {

    private final WpsProperties wpsProperties;
    private final RedirectProperties redirect;

    @Autowired
    public WpsUtil(WpsProperties wpsProperties,RedirectProperties redirect) {
        this.wpsProperties = wpsProperties;
        this.redirect = redirect;
    }

    public String getWpsUrl(Map<String, String> values, String fileType, String fileId,String tenantId) {
        String keyValueStr = SignatureUtil.getKeyValueStr(values);
        String signature = SignatureUtil.getSignature(values, wpsProperties.getAppsecret(tenantId));
        String fileTypeCode = FileUtil.getFileTypeCode(fileType);

        return wpsProperties.getDomain(tenantId) + fileTypeCode + "/" + fileId + "?"
                + keyValueStr + "_w_signature=" + signature;
    }

}
