package com.dooleen.service.system.utils.workbenchMsg;

import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowInfoEntity;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessRecordEntity;
import org.springframework.stereotype.Component;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020年6月6日 下午8:13:50
 * @Description : 自定义消息转化工具
 * @Author : apple
 * @Update: 2020年6月6日 下午8:13:50
 */
@Component
public class BuildActiveFormWorkbenchMsgData {
	public String getWorkbenchMsgData(GeneralFlowProcessRecordEntity generalFlowProcessRecordEntity){
		JSONObject json = (JSONObject) generalFlowProcessRecordEntity.getBizDataEntity();
		String returnValue = "{ \"first\": \""+generalFlowProcessRecordEntity.getFlowName()+"\", \"label1\":\"请假类型\", \"keyword1\": \""+generalFlowProcessRecordEntity.getBizName()+"\", \"label2\":\"开始时间\", \"keyword2\": \""+json.getString("planBeginDate")+"\", \"label3\":\"结束时间\", \"keyword3\": \""+json.getString("planEndDate")+"\", \"label4\":\"处理意见\", \"keyword4\": \"\"}";
		return returnValue;
	}
}
