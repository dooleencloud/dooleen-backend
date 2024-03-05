package com.dooleen.common.core.wechat.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author lishuli
 * @Date 2020/8/6 5:45 下午
 * @Version 1.0
 */
@Data
public class WxUserInfo {

   private int  subscribe;
   private String  openid;
   private String  nickname;
   private String  sex;
   private String  language;
   private String  city;
   private String  province;
   private String  country;
   private String  headimgurl;
   private String  unionid;
   private String  remark;
   private String  groupid;
   private List<String> tagid_list;
   private String  subscribe_scene;
   private String  qr_scene;
   private String  qr_scene_str;

}
