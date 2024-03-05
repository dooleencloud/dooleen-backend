package com.dooleen.service.app.gateway.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Configuration
@ConditionalOnExpression("!'${notchecktoken}'.isEmpty()")
@ConfigurationProperties(prefix = "notchecktoken")
public class NotCheckTokenUrlsPropertiesConfig {
    private List<String> anon = new ArrayList<>();

    public List<String> getAnon() {
        return anon;
    }

    public void setAnon(List<String> anon) {
        this.anon = anon;
    }
}
