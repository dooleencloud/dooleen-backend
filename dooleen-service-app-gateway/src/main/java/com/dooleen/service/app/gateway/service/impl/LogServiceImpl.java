package com.dooleen.service.app.gateway.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.context.RequestContext;
import com.dooleen.common.core.app.system.log.entity.SysLogEntity;
import com.dooleen.common.core.constant.MqQueueConstant;
import com.dooleen.common.core.enums.LogTypeEnum;
import com.dooleen.common.core.mq.vo.LogVO;
import com.dooleen.common.core.utils.BodyReaderRequestWrapper;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.aes.AESUtil;
import com.dooleen.service.app.gateway.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dcit
 * @date 2017/11/16
 * 消息发往消息队列工具类
 */
@Slf4j
@Component
public class LogServiceImpl implements LogService {
    private static final String SERVICE_ID = "serviceId";
    private Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    private UserService userService;

    /**
     * 1. 获取 requestContext 中的请求信息
     * 2. 如果返回状态不是OK，则获取返回信息中的错误信息
     * 3. 发送到MQ
     *
     * @param requestContext 上下文对象
     */
    @Override
    public void send(RequestContext requestContext,String responseBody,boolean encode) {
        HttpServletRequest request = requestContext.getRequest();
        SysLogEntity log = new SysLogEntity();

        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        JSONObject json = new JSONObject(map);
        log.setRequestHeader(json.toJSONString());
        logger.info("json={}",json.toString());
        //获取请求body
        String body = null;
        try {
            BodyReaderRequestWrapper requestWrapper = new BodyReaderRequestWrapper(request);
            body = requestWrapper.getBody();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if(body!=null) {
            String contentType = request.getContentType();
            if(contentType!=null&&contentType.contains("multipart")) {//上传文件的请求
                Collection<Part> parts = null;
                try {
                    parts = request.getParts();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                StringBuffer sb = new StringBuffer();
                //获取分隔符
                int index1 = body.indexOf("\r\n");
                String split = body.substring(0,index1);
                //拆分body
                String[] strarr = body.split(split);
                for(int i=0;i<strarr.length-1;i++) {
                    if("".equals(strarr[i])) {
                        continue;
                    }
                    //获取字段名
                    String colName = strarr[i].split("\"")[1];
                    String[] colValues = strarr[i].split("\r\n");
                    sb.append(colName).append(":");
                    if(i<strarr.length-2) {
                        //获取非文件的字段值
                        String colValue = colValues[colValues.length-1];
                        sb.append(colValue).append(";");
                    }
                }
                for(Part part : parts) {
//        			String name = part.getName();
//        			sb.append(name).append(":");
                    //获取所有文件名
                    String fileName = part.getSubmittedFileName();
                    if(fileName!=null&&!"null".equals(fileName)) {
                        sb.append(fileName).append(";");
                    }
                }
                String allFileName = sb.toString();
                log.setRequestBody(allFileName);
                log.setResponseBody(responseBody);
            }else {
                log.setRequestBody(body);
                log.setResponseBody(responseBody);
            }
        }
        if (AESUtil.encode() && encode) {
            try {
                log.setRequestBody(AESUtil.decrypt(log.getRequestBody().trim()).trim());
                if(!StringUtil.isEmpty(responseBody)) {
                    try {
                        log.setResponseBody(AESUtil.decrypt(responseBody.trim()).trim());
                    } catch (Exception e) {
                        logger.info("解密错误，跳过解密！");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else{
            log.setRequestBody(log.getRequestBody());
            log.setResponseBody(responseBody);
        }
        String requestUri = request.getRequestURI();
        String method = request.getMethod();

        log.setLogType(LogTypeEnum.NORMAL.getValue());
//        log.setRemoteAddr(HttpUtil.getClientIP(request));
        // 获取token、刷新token从url上的参数获取租户号
        if("/dooleenApi/oauth/token".equals(requestUri)){
            Map<String, String[]> params = request.getParameterMap();
            if(null != params){
                log.setTenantId(params.get("tenantId")[0]);
            }
        }
        else if(requestUri.indexOf("/dooleenReport/ureport") >=0){
            Map<String, String[]> params = request.getParameterMap();
            if(params.get("tenantId") != null) {
                byte[] tenantIdByte = Base64.decodeBase64(params.get("tenantId")[0]);
                String tenantId = new String(tenantIdByte);
                log.setTenantId(tenantId);
            }
            else{
                log.setTenantId(request.getHeader("userId").substring(0, 8));
            }
        }
        else{
            if(StringUtil.isEmpty(request.getHeader("userId"))){
                Map<String, String[]> params = request.getParameterMap();
                if(params.containsKey("tenantId")) {
                    log.setTenantId(params.get("tenantId")[0]);
                }
                else{
                    log.setTenantId(map.get("tenantid").toString());
                }
            }
            else{
                log.setTenantId(request.getHeader("userId").substring(0, 8));
            }
        }
        log.setHandleAddress(getRequestIp(request));
        log.setRequestAddress(URLUtil.getPath(requestUri));
        log.setRequestWay(method);
        log.setUserAgent(request.getHeader("user-agent"));
        log.setRequestParam(HttpUtil.toParams(request.getParameterMap()));
        log.setResponseCode(requestContext.getResponseStatusCode()+"");
        Long startTime = (Long) requestContext.get("startTime");
        log.setExecDuration(String.valueOf(System.currentTimeMillis() - startTime));
        if (requestContext.get(SERVICE_ID) != null) {
            log.setServiceName(requestContext.get(SERVICE_ID).toString());
        }

        //正常发送服务异常解析
        if (requestContext.getResponseStatusCode() != HttpStatus.SC_OK
                && requestContext.getResponseDataStream() != null) {
            InputStream inputStream = requestContext.getResponseDataStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream stream1 = null;
            InputStream stream2;
            byte[] buffer = IoUtil.readBytes(inputStream);
            try {
                baos.write(buffer);
                baos.flush();
                stream1 = new ByteArrayInputStream(baos.toByteArray());
                stream2 = new ByteArrayInputStream(baos.toByteArray());
                String resp = IoUtil.read(stream1, "UTF-8");
                log.setLogType(LogTypeEnum.EXCEPTION.getValue());
                log.setExceptionContent(resp);
                requestContext.setResponseDataStream(stream2);
            } catch (IOException e) {
                logger.error("响应流解析异常：", e);
                throw new RuntimeException(e);
            } finally {
                IoUtil.close(stream1);
                IoUtil.close(baos);
                IoUtil.close(inputStream);
            }

        }

        //网关内部异常
        Throwable throwable = requestContext.getThrowable();
        if (throwable != null) {
            logger.error("网关异常", throwable);
            log.setExceptionContent(throwable.getMessage());
        }

        //保存发往MQ（只保存授权）
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && StrUtil.isNotBlank(authentication.getName())) {
            LogVO logVo = new LogVO();
            String username = authentication.getName();
//            log.setCreateUserName(username);
            //获取机构号
            if(!"anonymousUser".equals(username)) {
//            	log.setCorporationNo(userService.findUserByUsername(username).getCorporationNo());
            }
            logVo.setSysLog(log);
            logVo.setUsername(username);
            rabbitTemplate.convertAndSend(MqQueueConstant.LOG_QUEUE, logVo);
        }
    }

    public String getRequestIp(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        log.info("client IP address is:{}", XFor);
        return XFor;
    }

    public void req(RequestContext requestContext) throws IOException, ServletException {

        HttpServletRequest request = requestContext.getRequest();
        log.info("Protocol: {}",  request.getProtocol());
        log.info("Scheme: {}", request.getScheme());
        log.info("Server Name: {}", request.getServerName());
        log.info("Server Port: {}", request.getServerPort());
        log.info("Protocol: {}", request.getProtocol());
        log.info("Remote Addr: {}", request.getRemoteAddr());
        log.info("Remote Host: {}", request.getRemoteHost());
        log.info("Character Encoding: {}", request.getCharacterEncoding());
        log.info("Content Length: {}", request.getContentLength());
        log.info("Content Type: {}", request.getContentType());
        log.info("Auth Type: {}", request.getAuthType());
        log.info("HTTP Method: {}", request.getMethod());
        log.info("Path Info: {}", request.getPathInfo());
        log.info("Path Trans: {}", request.getPathTranslated());
        log.info("Query String: {}", request.getQueryString());
        log.info("Remote User: {}", request.getRemoteUser());
        log.info("Session Id: {}", request.getRequestedSessionId());
        log.info("Request URI: {}", request.getRequestURI());
        log.info("Servlet Path: {}", request.getServletPath());
        log.info("Accept: {}", request.getHeader("Accept"));
        log.info("Host: {}", request.getHeader("Host"));
        Collection<Part> parts = request.getParts();
        for(Part part : parts) {
            String fileName = part.getSubmittedFileName();
            log.info("part: {}", part);
        }
//        log.info("parts: {}", request.getParts());
        log.info("Referer : {}", request.getHeader("Referer"));
        log.info("Accept-Language : {}", request.getHeader("Accept-Language"));
        log.info("Accept-Encoding : {}", request.getHeader("Accept-Encoding"));
        log.info("User-Agent : {}", request.getHeader("User-Agent"));
        log.info("Connection : {}", request.getHeader("Connection"));
        log.info("Cookie : {}", request.getHeader("Cookie"));
        log.info("Created : {}", request.getSession().getCreationTime());
        log.info("LastAccessed : {}", request.getSession().getLastAccessedTime());
    }
}
