package com.dooleen.common.core.app.general.flow.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.general.common.service.impl.GetBizParamsService;
import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowExtInfoEntity;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessRecordEntity;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowProcessRecordMapper;
import com.dooleen.common.core.app.general.flow.service.GeneralFlowProcessService;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.common.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-28 18:24:13
 * @Description : 流程处理主程序
 * @Author : apple
 * @Update: 2020-06-28 18:24:13
 */
@Slf4j
@Service
public class GeneralFlowProcessUtil {
	@Autowired
	private  GeneralFlowProcessRecordMapper generalFlowProcessRecordMapper;

	@Autowired
	private  GeneralFlowProcessService  generalFlowProcessService;

	@Autowired
	private  GetBizParamsService getBizParamsService;

	/**
	 * 初始化流程处理数据
	 */
	public  <T,S> void queryExtData(CommonMsg<T, S> commonMsg) {
		if(!StringUtil.isEmpty(commonMsg.getBody().getFlowArea().getIsFlow()) && commonMsg.getBody().getFlowArea().getIsFlow().equals("true")) {
			GeneralFlowProcessRecordEntity  generalFlowProcessRecordEntity = generalFlowProcessRecordMapper.selectById(commonMsg.getBody().getFlowArea().getProcessRecordId());
			if(generalFlowProcessRecordEntity != null) {
				commonMsg.getBody().getFlowArea().setProcessRecordId(generalFlowProcessRecordEntity.getId());
				commonMsg.getBody().getFlowArea().setFlowExtData((JSONObject) JSON.parse(generalFlowProcessRecordEntity.getDataArea()));
				commonMsg.getBody().getFlowArea().setFormExtData((JSONObject) JSON.parse(generalFlowProcessRecordEntity.getFormArea()));
				commonMsg.getBody().getFlowArea().setFlowEndFlag(generalFlowProcessRecordEntity.getFlowEndFlag());
			}
		}
	}

	/**
	 * 处理流程
	 */
	public  <T,S> void handleProccess(CommonMsg<T, S> commonMsg, GeneralFlowExtInfoEntity generalFlowExtInfoEntity) {
		// 判断是否需要处理流程
		//自动启动流程 flowBeginWay 0-手动启动，1-自动启动
		if(!StringUtil.isEmpty(commonMsg.getBody().getFlowArea().getIsFlow()) && commonMsg.getBody().getFlowArea().getIsFlow().equals("true")) {
			FlowProcessDataEntity flowProcessDataEntity = commonMsg.getBody().getFlowArea();
			// 流程操作标志 flowOptFlag 1-开始启动 2-处理流程 3-撤回流程
			if (flowProcessDataEntity.getFlowOptFlag().equals("1")) {
				GeneralFlowProcessRecordEntity generalFlowProcessRecordEntity = new GeneralFlowProcessRecordEntity();
				// 在业务字典定义： 业务流程ID列表（字典名：flowIdList； 字典值：示例：bizAppArchFlowId）
				String flowId = generalFlowExtInfoEntity.getFlowId();
				if(StringUtil.isEmpty(flowId)) {
					flowId = getBizParamsService.getBizDictValue(getBizParamsService.getBizDict(commonMsg, "flowIdList"), generalFlowExtInfoEntity.getBizCode());
				}
				generalFlowProcessRecordEntity.setFlowId(flowId);
				generalFlowProcessRecordEntity.setFormId(generalFlowExtInfoEntity.getFormId());
				// 请将业务类型改为实际的
				generalFlowProcessRecordEntity.setBizType(generalFlowExtInfoEntity.getBizType());
				generalFlowProcessRecordEntity.setBizId(generalFlowExtInfoEntity.getBizId());
				generalFlowProcessRecordEntity.setBizCode(generalFlowExtInfoEntity.getBizCode());
				//getDemandName() 此处需要修改成实际的业务名称
				generalFlowProcessRecordEntity.setBizName(generalFlowExtInfoEntity.getBizName());
				generalFlowProcessRecordEntity.setTenantId(commonMsg.getHead().getTenantId());
				generalFlowProcessRecordEntity.setBizDataEntity(commonMsg.getBody().getSingleBody());
				CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> flowCommonMsg = CreateCommonMsg.getCommonMsg(generalFlowProcessRecordEntity, new NullEntity());
				flowCommonMsg.setHead(commonMsg.getHead());
				flowCommonMsg = generalFlowProcessService.startFlow(flowCommonMsg);
				commonMsg.setHead(flowCommonMsg.getHead());
			} else if (flowProcessDataEntity.getFlowOptFlag().equals("2")) {
				GeneralFlowProcessRecordEntity generalFlowProcessRecordEntity = new GeneralFlowProcessRecordEntity();
				generalFlowProcessRecordEntity.setId(commonMsg.getBody().getFlowArea().getProcessRecordId());
				generalFlowProcessRecordEntity.setFormId(generalFlowExtInfoEntity.getFormId());
				generalFlowProcessRecordEntity.setTenantId(commonMsg.getHead().getTenantId());
				generalFlowProcessRecordEntity.setBizType(generalFlowExtInfoEntity.getBizType());
				generalFlowProcessRecordEntity.setBizId(generalFlowExtInfoEntity.getBizId());
				//getDemandName() 此处需要修改成实际的业务名称
				generalFlowProcessRecordEntity.setBizName(generalFlowExtInfoEntity.getBizName());
				generalFlowProcessRecordEntity.setDataArea(commonMsg.getBody().getFlowArea().getFlowExtData().toString());
				generalFlowProcessRecordEntity.setBizDataEntity(commonMsg.getBody().getSingleBody());

				CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> flowCommonMsg = CreateCommonMsg.getCommonMsg(generalFlowProcessRecordEntity, new NullEntity());
				flowCommonMsg.setHead(commonMsg.getHead());
				flowCommonMsg = generalFlowProcessService.processFlow(flowCommonMsg);
				commonMsg.setHead(flowCommonMsg.getHead());
			} else if (flowProcessDataEntity.getFlowOptFlag().equals("3")) {
				GeneralFlowProcessRecordEntity generalFlowProcessRecordEntity = new GeneralFlowProcessRecordEntity();
				generalFlowProcessRecordEntity.setId(commonMsg.getBody().getFlowArea().getProcessRecordId());
				generalFlowProcessRecordEntity.setTenantId(commonMsg.getHead().getTenantId());
				generalFlowProcessRecordEntity.setBizType(generalFlowExtInfoEntity.getBizType());
				generalFlowProcessRecordEntity.setBizId(generalFlowExtInfoEntity.getBizId());
				//getDemandName() 此处需要修改成实际的业务名称
				generalFlowProcessRecordEntity.setBizName(generalFlowExtInfoEntity.getBizName());
				generalFlowProcessRecordEntity.setDataArea(commonMsg.getBody().getFlowArea().getFlowExtData().toString());
				generalFlowProcessRecordEntity.setBizDataEntity(commonMsg.getBody().getSingleBody());

				CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> flowCommonMsg = CreateCommonMsg.getCommonMsg(generalFlowProcessRecordEntity, new NullEntity());
				flowCommonMsg.setHead(commonMsg.getHead());
				flowCommonMsg = generalFlowProcessService.processCallBack(flowCommonMsg);
				commonMsg.setHead(flowCommonMsg.getHead());
			} else if (flowProcessDataEntity.getFlowOptFlag().equals("4")) {
				GeneralFlowProcessRecordEntity generalFlowProcessRecordEntity = new GeneralFlowProcessRecordEntity();
				generalFlowProcessRecordEntity.setId(commonMsg.getBody().getFlowArea().getProcessRecordId());
				generalFlowProcessRecordEntity.setTenantId(commonMsg.getHead().getTenantId());
				generalFlowProcessRecordEntity.setBizType(generalFlowExtInfoEntity.getBizType());
				generalFlowProcessRecordEntity.setBizId(generalFlowExtInfoEntity.getBizId());
				//getDemandName() 此处需要修改成实际的业务名称
				generalFlowProcessRecordEntity.setBizName(generalFlowExtInfoEntity.getBizName());
				generalFlowProcessRecordEntity.setDataArea(commonMsg.getBody().getFlowArea().getFlowExtData().toString());
				generalFlowProcessRecordEntity.setBizDataEntity(commonMsg.getBody().getSingleBody());

				CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> flowCommonMsg = CreateCommonMsg.getCommonMsg(generalFlowProcessRecordEntity, new NullEntity());
				flowCommonMsg.setHead(commonMsg.getHead());
				flowCommonMsg = generalFlowProcessService.processReject(flowCommonMsg);
				commonMsg.setHead(flowCommonMsg.getHead());
			}
		}
	}
}
