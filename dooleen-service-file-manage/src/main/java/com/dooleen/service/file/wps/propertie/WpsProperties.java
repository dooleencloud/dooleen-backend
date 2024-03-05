package com.dooleen.service.file.wps.propertie;

import com.alibaba.fastjson.JSONObject;
import com.dooleen.service.file.wps.commonservice.CommonService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 属性文件
 * @author zm
 */
@Data
@Component
public class WpsProperties {
	@Autowired
	private CommonService commonService;

	public String getDomain(String tenantId){
		JSONObject jsonObj = commonService.getThirdParam(tenantId,"金山WPS");
		return jsonObj.getString("wpsDomain");
	}

	public String getAppid(String tenantId){
		JSONObject jsonObj = commonService.getThirdParam(tenantId,"金山WPS");
		return jsonObj.getString("appId");
	}

	public String getAppsecret(String tenantId){
		JSONObject jsonObj = commonService.getThirdParam(tenantId,"金山WPS");
		return jsonObj.getString("appsecret");
	}
}
