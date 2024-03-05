package com.dooleen.common.core.wechat.vo;

import lombok.Data;

/**
 * @Copy Right Information : 独领科技版权所有
 * @Project : 独领教育平台
 * @Project No : dooleen
 * @Version : 1.0.0
 * @CreateDate : 2019-07-11 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description :用户及用户关系信息
 * @Maintainer:liqiuhong
 * @Update:
 */
@Data
public class OpenIdEntity {
	private String role; 
	private String jsCode; 
	private String openId;
	private String channelId;
	private String sessionKey;
}
