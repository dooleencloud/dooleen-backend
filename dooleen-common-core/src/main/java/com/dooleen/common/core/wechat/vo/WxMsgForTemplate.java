package com.dooleen.common.core.wechat.vo;

import lombok.Data;

/**
 * 用于接收微信模板消息的xml
 * 原因：微信其他返回的xml都是msgId  模板消息是：msgID 所以需要分开处理
 * @Author lishuli
 * @Date 2020/8/10 10:27 上午
 * @Version 1.0
 */
@Data
public class WxMsgForTemplate{

    private String msgID;
    private String status;
    private String toUserName;
    private String fromUserName;
    private String createTime;
    private String msgType;
    private String event;

}
