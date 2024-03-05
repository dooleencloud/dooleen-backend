package com.dooleen.common.core.wechat.vo;

import lombok.Data;

/**
 * @Author lishuli
 * @Date 2020/8/7 6:45 下午
 * @Version 1.0
 */
@Data
public class WxSendToUserMsg {

    private String toUserName;
    private String msgType;
    private String msgID;
}
