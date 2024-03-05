package com.dooleen.common.core.common.entity;

import java.util.Map;
/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-04-21 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description : Controller类
 * @Maintainer:liqiuhong
 * @Update:
 */
public class ValidationResult {

	// 校验结果是否有错  
    private boolean hasErrors;  
  
    // 校验错误信息  
    private Map<String, String> errorMsg;

	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public Map<String, String> getErrorMsg() {
		return errorMsg;
	}

	public void setRespMsg(Map<String, String> errorMsg) {
		this.errorMsg = errorMsg;
	}
    
}
