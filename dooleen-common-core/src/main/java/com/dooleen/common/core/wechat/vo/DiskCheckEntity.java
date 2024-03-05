package com.dooleen.common.core.wechat.vo;

import com.dooleen.common.core.common.entity.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Copy Right Information : 独领科技版权所有
 * @Project : 独领教育平台
 * @Project No : dooleen
 * @Version : 1.0.0
 * @CreateDate : 2019-07-11 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description :短信接口
 * @Maintainer:liqiuhong
 * @Update:
 */
@Data
public class DiskCheckEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "URL链接不能为空")
	private String httpUrl;
	private String getCode;
	private String respCode;
}
