package com.dooleen.common.core.wechat.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentV3Entity implements Serializable {
    private String appid;//小程序ID
    private String mch_id;//商户号
    private String description;//商品描述
    private String out_trade_no;//商户订单号
    private String time_expire;//交易结束时间
    private String attach;//附加数据
    private String notify_url;//通知地址
    private String goods_tag;//商品标记
    private JSONObject amount;//总金额
    private JSONObject detail;//商品详情
    private JSONObject scene_info;//货币类型
}