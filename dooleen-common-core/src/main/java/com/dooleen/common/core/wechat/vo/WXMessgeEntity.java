package com.dooleen.common.core.wechat.vo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class WXMessgeEntity {
    private String FromUserName;
    private String ToUserName;
    private long CreateTime;
    private String MsgType;
    private String Event;
    private String EventKey;
    private String Content;
    private String Url;
    private String PicUrl;
    private String MediaId;
    private String Format;
    private String Recognition;
    private String ThumbMediaId;
    //地理位置
    private float Location_X;
    private float Location_Y;
    private float Scale;

    private long MsgId;
}