package com.dooleen.service.file.wps.propertie;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "redirect")
public class RedirectProperties {

    private String key;
    private String value;

}
