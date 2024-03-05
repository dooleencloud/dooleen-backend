package com.dooleen.service.app.gateway.service;

import com.netflix.zuul.context.RequestContext;

public interface LogService {

    /**
     * 往消息通道发消息
     *
     * @param requestContext requestContext
     */
    void send(RequestContext requestContext,String responseBody,boolean encode);
    
}
