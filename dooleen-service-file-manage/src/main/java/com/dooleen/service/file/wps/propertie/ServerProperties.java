package com.dooleen.service.file.wps.propertie;

import com.alibaba.fastjson.JSONObject;
import com.dooleen.service.file.wps.commonservice.CommonService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
public class ServerProperties {

    @Autowired
    private CommonService commonService;

    /**
     * 获取文件地址
     * @param tenantId
     * @return
     */
    public String getDomain(String tenantId){
        JSONObject jsonObj = commonService.getThirdParam(tenantId,"金山WPS");
        return jsonObj.getString("fileDomin");
    }

    public int getPort(String tenantId){
        JSONObject jsonObj = commonService.getThirdParam(tenantId,"金山WPS");
        return 80;
    }
}
