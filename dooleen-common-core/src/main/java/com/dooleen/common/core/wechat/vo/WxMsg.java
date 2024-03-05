package com.dooleen.common.core.wechat.vo;

import lombok.Data;

/**
 * 微信中大部分需要使用的字段
 * @Author lishuli
 * @Date 2020/8/6 3:30 下午
 * @Version 1.0
 */
@Data
public class WxMsg {
    private String status;
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;
    private String URL;
    private String Event;
    private String EventKey;
    private String MenuId;
    private String Content;
    private String PicUrl;
    private String MediaId;
    private String Format;
    private String Recognition;
    private String ThumbMediaId;
    private String Location_X;
    private String Location_Y;
    private String Scale;
    private String Label;
    private String Title;
    private String Description;
    private String Url;
    private String Ticket;
    private String Latitude;
    private String Longitude;
    private String Precision;
    private String MsgID;
}
