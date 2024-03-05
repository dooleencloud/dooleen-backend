package com.dooleen.common.core.common.entity;

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
public class SendMsgUserInfoEntity {
	private String id;
	private String userName;
	private String realName;
	private String wxOpenId;
	private String clientId;
	private String dingtalkUserId;
	private String email;
	private String mobileNo;
	private String belongDeptId;
	private String belongDeptName;
	private String headImgUrl;
}
