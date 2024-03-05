package com.dooleen.service.file.wps.propertie;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wps.convert")
public class ConvertProperties {

    private String convert; // 转换地址
    private String query; // 文件查询地址
    private String appid;
    private String appsecret;

}
