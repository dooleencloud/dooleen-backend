package com.dooleen.common.core.app.general.flow.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dooleen.common.core.app.general.org.info.entity.GeneralOrgInfoEntity;
import com.dooleen.common.core.app.general.org.info.mapper.GeneralOrgInfoMapper;
import com.dooleen.common.core.app.general.org.staff.entity.GeneralOrgStaffRelationEntity;
import com.dooleen.common.core.app.general.org.staff.mapper.GeneralOrgStaffRelationMapper;
import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.mq.utils.MsgSendUtil;
import com.rits.cloning.Cloner;
import com.dooleen.common.core.app.general.flow.entity.*;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowInfoMapper;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowNodeConfigMapper;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowProcessCcRecordMapper;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowProcessRecordMapper;
import com.dooleen.common.core.app.general.flow.service.GeneralFlowProcessService;
import com.dooleen.common.core.app.system.form.entity.SysDynamicFormEntity;
import com.dooleen.common.core.app.system.form.mapper.SysDynamicFormMapper;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.enums.ServiceErrCode;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.global.exception.BaseServiceException;
import com.dooleen.common.core.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

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
@Transactional(rollbackFor = Exception.class)
public class GeneralFlowProcessServiceImpl extends ServiceImpl<GeneralFlowProcessRecordMapper, GeneralFlowProcessRecordEntity> implements GeneralFlowProcessService {
	/**
	 * 状态定义
	 * ProgressStatus：待处理，已完成，已转派，已委派，已回退，自动回退，已暂停（到子流程后暂停）
	 */
	public static final String CHECK_CONTENT = "checkContent";
	public static final String DELETE = "delete";
	private static final Cloner cloner = new Cloner();
	@Autowired
	private  GeneralFlowProcessRecordMapper generalFlowProcessRecordMapper;

	@Autowired
	private  GeneralFlowProcessCcRecordMapper generalFlowProcessCcRecordMapper;

	@Autowired
	private  GeneralFlowInfoMapper generalFlowInfoMapper;

	@Autowired
	private  GeneralFlowNodeConfigMapper generalFlowNodeConfigMapper;

	@Autowired
	private GeneralOrgInfoMapper generalOrgInfoMapper;

	@Autowired
	private  SysDynamicFormMapper sysDynamicFormMapper;

	@Autowired
	private GeneralOrgStaffRelationMapper generalOrgStaffRelationMapper;

	private  static String seqPrefix= TableEntityORMEnum.GENERAL_FLOW_PROCESS_RECORD.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.GENERAL_FLOW_PROCESS_RECORD.getEntityName();

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Autowired
	private MsgSendUtil msgSendUtil;

	private GeneralFlowInfoEntity generalFlowInfoEntity = null;
	private GeneralFlowNodeConfigEntity generalFlowNodeConfigEntity = null;
	private String handlerUserName;
	private String handlerRealName;
	private String handlerHeadImgUrl;
	private boolean inHandle = false;
	/**
	 * 开始一个流程
	 *
	 */
	@Override
	public CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> startFlow(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}

		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		commonMsg.getBody().setListBody(nullEntityList);
		//新流程开始根据流程ID获取流程及节点信息
		GeneralFlowProcessRecordEntity  generalFlowProcessRecordEntity = commonMsg.getBody().getSingleBody();
		handlerUserName =  commonMsg.getHead().getUserName();
		handlerRealName =  commonMsg.getHead().getRealName();
		handlerHeadImgUrl = commonMsg.getHead().getHeadImgUrl();
		generalFlowInfoEntity = generalFlowInfoMapper.selectById(generalFlowProcessRecordEntity.getFlowId());
		BizResponseEnum.FLOW_DATA_NOT_EXIST.assertNotNull(generalFlowInfoEntity,commonMsg);

		String  tenantId = generalFlowInfoEntity.getTenantId();
		// 获取序号为1的流程节点信息
		QueryWrapper<GeneralFlowNodeConfigEntity> nodeQueryWrapper = new QueryWrapper<GeneralFlowNodeConfigEntity>();
		nodeQueryWrapper.lambda().eq(GeneralFlowNodeConfigEntity::getTenantId, generalFlowProcessRecordEntity.getTenantId());
		nodeQueryWrapper.lambda().eq(GeneralFlowNodeConfigEntity::getFlowId, generalFlowProcessRecordEntity.getFlowId());
		nodeQueryWrapper.lambda().eq(GeneralFlowNodeConfigEntity::getOrderSeq, 1);
		generalFlowNodeConfigEntity = generalFlowNodeConfigMapper.selectOne(nodeQueryWrapper);
		BizResponseEnum.FLOW_NODE_DATA_NOT_EXIST.assertNotNull(generalFlowNodeConfigEntity,commonMsg);

		//保存一条启动流程记录
		generalFlowProcessRecordEntity.setFlowName(generalFlowInfoEntity.getFlowName());
		generalFlowProcessRecordEntity.setNodeType("开始节点");  //开始节点
		generalFlowProcessRecordEntity.setNodeId(generalFlowNodeConfigEntity.getId());
		generalFlowProcessRecordEntity.setNodeName(generalFlowNodeConfigEntity.getNodeName());
		generalFlowProcessRecordEntity.setNodeOrderSeq(generalFlowNodeConfigEntity.getOrderSeq());
		if(StringUtil.isEmpty(generalFlowProcessRecordEntity.getMainNodeId())){
			generalFlowProcessRecordEntity.setProcessStatus("流程已启动");
			generalFlowProcessRecordEntity.setProcessResult("流程启动成功");
		}
		else{
			generalFlowProcessRecordEntity.setProcessStatus("子流程已启动");
			generalFlowProcessRecordEntity.setProcessResult("子流程启动成功");
		}
		//保证处理时间一致
		String nowDate =  DateUtils.getLongDateStr();
		generalFlowProcessRecordEntity.setNodeStageStatus("");
		generalFlowProcessRecordEntity.setIsDelegateFlag("0");
		generalFlowProcessRecordEntity.setIsTransferFlag("0");
		generalFlowProcessRecordEntity.setFlowEndFlag("1");
		generalFlowProcessRecordEntity.setDelegateUserName("");
		generalFlowProcessRecordEntity.setTransferUserName("");
		generalFlowProcessRecordEntity.setProcessBeginDatetime(nowDate);
		generalFlowProcessRecordEntity.setProcessFinishDatetime(DateUtils.initialDatatime());
		generalFlowProcessRecordEntity.setProcessUserName(commonMsg.getHead().getUserName());
		generalFlowProcessRecordEntity.setProcessRealName(commonMsg.getHead().getRealName());
		generalFlowProcessRecordEntity.setPrevNodeUserName(FormatUserName.getAllName(commonMsg.getHead().getRealName(), commonMsg.getHead().getUserName()));
		generalFlowProcessRecordEntity.setLaunchUserName(commonMsg.getHead().getUserName());
		generalFlowProcessRecordEntity.setLaunchRealName(commonMsg.getHead().getRealName());
		generalFlowProcessRecordEntity.setLaunchDatetime(nowDate);
		generalFlowProcessRecordEntity.setProcessOpinion("发起流程");
		generalFlowProcessRecordEntity.setCcFlag("0");
		generalFlowProcessRecordEntity.setAttList("[]");
		generalFlowProcessRecordEntity.setPrevNodeUserName(FormatUserName.getAllName(generalFlowProcessRecordEntity.getProcessRealName(),generalFlowProcessRecordEntity.getProcessUserName()));
		generalFlowProcessRecordEntity.setPrevNodeOpinion("发起流程");
		generalFlowProcessRecordEntity.setProcessProgressStatus("已启动");
		generalFlowProcessRecordEntity.setValidFlag("1");
		//如果是动态表单，获取表单的配置
			if(StringUtils.isNotEmpty(generalFlowInfoEntity.getFormType()) &&  generalFlowInfoEntity.getFormType().equals("2")) {
			String formId = generalFlowProcessRecordEntity.getFormId();
			if(StringUtils.isNotEmpty(generalFlowNodeConfigEntity.getFormId())) {
				formId = generalFlowNodeConfigEntity.getFormId();
			}
			generalFlowProcessRecordEntity.setFormId(formId);
			SysDynamicFormEntity sysDynamicFormEntity = sysDynamicFormMapper.selectById(formId);
			if(sysDynamicFormEntity == null) {
				throw new BaseServiceException("流程启动失败,未找到表单配置记录！", ServiceErrCode.NOTFOUND_RESULT_ERR);
			}
			else {
				generalFlowProcessRecordEntity.setFormArea(sysDynamicFormEntity.getFormJson());
				generalFlowProcessRecordEntity.setBizCode(sysDynamicFormEntity.getBizCode());
			}
		}
		//组装扩展表单信息 是否会签、是否转派、是否委托（第二层）
		Map<String,String> nodeNameMap = new HashMap<String, String>();
		List<JSONObject> nullList = new ArrayList<JSONObject>();
		JSONObject startFlowExtInfo = getFlowExtInfo(generalFlowNodeConfigEntity,nodeNameMap);
		startFlowExtInfo.put("nodeName",generalFlowNodeConfigEntity.getNodeName());
		startFlowExtInfo.put("flowEndFlag","0");
		startFlowExtInfo.put("optUserList",nullList);
		startFlowExtInfo.put("optUser",nullList);
		startFlowExtInfo.put("isSubFlow","");
		startFlowExtInfo.put("easyModeFlag",generalFlowNodeConfigEntity.getEasyModeFlag());
		generalFlowProcessRecordEntity.setDataArea(startFlowExtInfo.toString());

		// 处理下个节点用户待处理记录
		// 获取下个节点信息（第二层）
		JSONArray statusArray = JSONObject.parseArray(generalFlowNodeConfigEntity.getExecStatusList());
		JSONObject statusObj = (JSONObject) statusArray.get(0);
		List<Object> idArray = JSONObject.parseArray(statusObj.getString("nextProccessId"));
		String flowNodeStatus = statusObj.getString("nodeStatus");
		String nodeStageStatus = statusObj.getString("nodeStageStatus");
		generalFlowProcessRecordEntity.setNodeStatus(generalFlowNodeConfigEntity.getNodeName());
		generalFlowProcessRecordEntity.setNodeStageStatus("开始");
		log.debug(">>> 启动流程时，启动节点的执行状态列表应有1个，实际有 {} 个,value= {}", statusArray.size(), statusArray.toString());

		//处理工作台内容
		generalFlowProcessRecordEntity.setWorkbenchData(getWorkbenchMsgData(generalFlowProcessRecordEntity,generalFlowNodeConfigEntity));

		// 写启动流程记录
		generalFlowProcessRecordEntity.setDataSign(DooleenMD5Util.md5(generalFlowProcessRecordEntity.toString(),  ConstantValue.md5Key));

		//保存记录
		super.save(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalFlowProcessRecordEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate));

		// 构建下个用户处理记录
		generalFlowProcessRecordEntity.setDataArea("");
		generalFlowProcessRecordEntity.setPrevNodeOpinion(generalFlowProcessRecordEntity.getProcessRealName()+"("+generalFlowProcessRecordEntity.getProcessUserName()+")提交了流程");
		for(int i = 0; i < idArray.size(); i++){
			buildNextUserInfo(commonMsg, generalFlowInfoEntity,generalFlowProcessRecordEntity,flowNodeStatus,nodeStageStatus,idArray.get(i).toString(),idArray.size(),nowDate);

			//更新所有记录状态为当前节点状态
			generalFlowProcessRecordEntity.setProcessStageStatus(flowNodeStatus);
			generalFlowProcessRecordMapper.batchUpdateProcessRecord(generalFlowProcessRecordEntity);

			//更新抄送人员列表 所有记录状态为当前节点状态
			generalFlowProcessCcRecordMapper.batchUpdateProcessRecord(generalFlowProcessRecordEntity);
		}

		//设置返回码 工作台
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	/**
	 * 流程处理主逻辑
	 *
	 */
	@Override
	public CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> processFlow(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}

		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		commonMsg.getBody().setListBody(nullEntityList);
		String tenantId = "";
		//根据处理记录ID获取当前处理记录
		GeneralFlowProcessRecordEntity  generalFlowProcessRecordEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
		BizResponseEnum.FLOW_NODE_DATA_NOT_EXIST.assertNotNull(generalFlowProcessRecordEntity,commonMsg);
		generalFlowProcessRecordEntity.setFormId(commonMsg.getBody().getSingleBody().getFormId());
		generalFlowProcessRecordEntity.setBizDataEntity(commonMsg.getBody().getSingleBody().getBizDataEntity());

		generalFlowProcessRecordEntity.setDataArea(commonMsg.getBody().getSingleBody().getDataArea());
		JSONObject dataAreaObj = JSONObject.parseObject(generalFlowProcessRecordEntity.getDataArea());
		//如果不是结束节点 检查输入数据（转给人、委派人、转派人）,不通过直接返回
		if(!generalFlowProcessRecordEntity.getFlowEndFlag().equals("2") && !checkProcessData(commonMsg,dataAreaObj)) {
			return commonMsg;
		}
		handlerUserName =  commonMsg.getHead().getUserName();
		handlerRealName =  commonMsg.getHead().getRealName();
		handlerHeadImgUrl = commonMsg.getHead().getHeadImgUrl();
		//根据流程ID获取流程及节点信息
		generalFlowInfoEntity = generalFlowInfoMapper.selectById(generalFlowProcessRecordEntity.getFlowId());

		tenantId = generalFlowInfoEntity.getTenantId();
		// 获取当前处理节点配置信息
		generalFlowNodeConfigEntity = generalFlowNodeConfigMapper.selectById(generalFlowProcessRecordEntity.getNodeId());

		//保存当前处理结果记录
		generalFlowProcessRecordEntity.setProcessOpinion(dataAreaObj.getString("processOpinion"));
		generalFlowProcessRecordEntity.setAttName("");
		generalFlowProcessRecordEntity.setAttList(dataAreaObj.getString("fileList"));
		if(generalFlowProcessRecordEntity.getFlowEndFlag().equals("2")) {
			generalFlowProcessRecordEntity.setProcessStatus("流程结束");
		}
		else {
			generalFlowProcessRecordEntity.setProcessStatus(dataAreaObj.getString("nodeStatus"));
		}
		BizResponseEnum.FLOW_HAS_BEEN_DONE.assertNotEmpty(generalFlowProcessRecordEntity.getProcessStatus(),commonMsg);

		if(!StringUtil.isEmpty(dataAreaObj.getString("delegateUser"))) {
			generalFlowProcessRecordEntity.setIsDelegateFlag("1");
			generalFlowProcessRecordEntity.setDelegateUserName(dataAreaObj.getString("delegateUser"));
			generalFlowProcessRecordEntity.setProcessProgressStatus("已完成");
			generalFlowProcessRecordEntity.setProcessStatus("已委托");
			generalFlowProcessRecordEntity.setProcessResult("已委托给："+dataAreaObj.getString("delegateUser"));
			generalFlowProcessRecordEntity.setProcessOpinion(generalFlowProcessRecordEntity.getProcessOpinion() + "; 已委托给："+dataAreaObj.getString("transferUser"));
			generalFlowProcessRecordEntity.setDataSign(DooleenMD5Util.md5(generalFlowProcessRecordEntity.toString(),  ConstantValue.md5Key));
			//保存记录
			super.updateById(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalFlowProcessRecordEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate));

			//写委托人开始处理记录
			GeneralFlowProcessRecordEntity delegateProcessRecordEntity = generalFlowProcessRecordEntity;
			delegateProcessRecordEntity.setId(null);
			delegateProcessRecordEntity.setTransferDelegatePrevId(delegateProcessRecordEntity.getId());
			delegateProcessRecordEntity.setProcessStatus("");
			delegateProcessRecordEntity.setIsDelegateFlag("0");
			delegateProcessRecordEntity.setIsTransferFlag("0");
			String[] user =  FormatUserName.getUser(dataAreaObj.getString("delegateUser"));
			//已转派的不可再次转派和
			dataAreaObj.put("isTransferFlag", false);
			dataAreaObj.put("isDelegateFlag", false);
			dataAreaObj.put("processOpinion","");
			dataAreaObj.put("transferUser","");
			dataAreaObj.put("delegateUser","");
			delegateProcessRecordEntity.setDataArea(dataAreaObj.toString());

			delegateProcessRecordEntity.setProcessUserName(user[1]);
			delegateProcessRecordEntity.setProcessRealName(user[0]);
			delegateProcessRecordEntity.setProcessBeginDatetime(DateUtils.getLongDateStr());
			delegateProcessRecordEntity.setPrevNodeUserName(FormatUserName.getAllName(generalFlowProcessRecordEntity.getProcessRealName(),generalFlowProcessRecordEntity.getProcessUserName()));
			delegateProcessRecordEntity.setPrevNodeOpinion(generalFlowProcessRecordEntity.getProcessOpinion());
			delegateProcessRecordEntity.setPrevNodeIdList(generalFlowProcessRecordEntity.getNodeId());
			delegateProcessRecordEntity.setPrevNodeNameList(generalFlowProcessRecordEntity.getNodeName());
			delegateProcessRecordEntity.setPrevNodeUserName(FormatUserName.getAllName(generalFlowProcessRecordEntity.getProcessRealName(), generalFlowProcessRecordEntity.getProcessUserName()));
			delegateProcessRecordEntity.setProcessOpinion("");
			delegateProcessRecordEntity.setProcessResult("");
			delegateProcessRecordEntity.setDelegateUserName("");

			//处理工作台记录
			delegateProcessRecordEntity.setWorkbenchData(getWorkbenchMsgData(delegateProcessRecordEntity,generalFlowNodeConfigEntity));

			delegateProcessRecordEntity.setDataSign(DooleenMD5Util.md5(delegateProcessRecordEntity.toString(),  ConstantValue.md5Key));
			super.save(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(delegateProcessRecordEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate));

			// 写消息 01-委托
			sendMsg("01",delegateProcessRecordEntity,null,null);
			// 返回成功
			CommonUtil.successReturn(commonMsg);
			return commonMsg;
		}
		else if(!StringUtil.isEmpty(dataAreaObj.getString("transferUser"))) {
			generalFlowProcessRecordEntity.setIsTransferFlag("1");
			generalFlowProcessRecordEntity.setTransferUserName(dataAreaObj.getString("transferUser"));
			generalFlowProcessRecordEntity.setProcessProgressStatus("已完成");
			generalFlowProcessRecordEntity.setProcessStatus("已转派");
			generalFlowProcessRecordEntity.setProcessResult("已转派给："+dataAreaObj.getString("transferUser"));
			generalFlowProcessRecordEntity.setProcessFinishDatetime(DateUtils.getLongDateStr());
			generalFlowProcessRecordEntity.setProcessOpinion(generalFlowProcessRecordEntity.getProcessOpinion() + "; 已转派给："+dataAreaObj.getString("transferUser"));
			generalFlowProcessRecordEntity.setDataSign(DooleenMD5Util.md5(generalFlowProcessRecordEntity.toString(),  ConstantValue.md5Key));
			super.updateById(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalFlowProcessRecordEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate));

			// 写转派人开始处理记录
			GeneralFlowProcessRecordEntity delegateProcessRecordEntity = generalFlowProcessRecordEntity;
			delegateProcessRecordEntity.setId(null);
			delegateProcessRecordEntity.setTransferDelegatePrevId(delegateProcessRecordEntity.getId());
			delegateProcessRecordEntity.setProcessStatus("");
			delegateProcessRecordEntity.setIsDelegateFlag("0");
			delegateProcessRecordEntity.setIsTransferFlag("0");
			String[] user =  FormatUserName.getUser(dataAreaObj.getString("transferUser"));

			//已转派的不可再次转派和
			dataAreaObj.put("isTransferFlag", false);
			dataAreaObj.put("isDelegateFlag", false);
			dataAreaObj.put("processOpinion","");
			dataAreaObj.put("transferUser","");
			dataAreaObj.put("delegateUser","");
			delegateProcessRecordEntity.setDataArea(dataAreaObj.toString());

			delegateProcessRecordEntity.setProcessUserName(user[1]);
			delegateProcessRecordEntity.setProcessRealName(user[0]);
			delegateProcessRecordEntity.setProcessBeginDatetime(DateUtils.getLongDateStr());
			delegateProcessRecordEntity.setPrevNodeUserName(FormatUserName.getAllName(generalFlowProcessRecordEntity.getProcessRealName(),generalFlowProcessRecordEntity.getProcessUserName()));
			delegateProcessRecordEntity.setPrevNodeOpinion(generalFlowProcessRecordEntity.getProcessOpinion());
			delegateProcessRecordEntity.setPrevNodeIdList(generalFlowProcessRecordEntity.getNodeId());
			delegateProcessRecordEntity.setPrevNodeNameList(generalFlowProcessRecordEntity.getNodeName());
			delegateProcessRecordEntity.setPrevNodeUserName(FormatUserName.getAllName(generalFlowProcessRecordEntity.getProcessRealName(), generalFlowProcessRecordEntity.getProcessUserName()));
			delegateProcessRecordEntity.setProcessOpinion("");
			delegateProcessRecordEntity.setProcessResult("");
			delegateProcessRecordEntity.setTransferUserName("");

			//处理工作台记录
			delegateProcessRecordEntity.setWorkbenchData(getWorkbenchMsgData(delegateProcessRecordEntity,generalFlowNodeConfigEntity));

			delegateProcessRecordEntity.setDataSign(DooleenMD5Util.md5(delegateProcessRecordEntity.toString(),  ConstantValue.md5Key));
			super.save(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(delegateProcessRecordEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
			// 写消息 01-委托，02-转派
			sendMsg("02",delegateProcessRecordEntity,null,null);
			// 返回成功
			CommonUtil.successReturn(commonMsg);
			return commonMsg;
		}

		/**
		 *  判断是或签还是会签
		 *  0-或签 直接处理下个节点
		 *  1-会签 判断会签比例再处理
		 */
		//获取当前节点的处理状态 在会签时 是否通过标志joinPassFlag  1-通过 0-不通过
		JSONArray currentStatusArray = JSONObject.parseArray(generalFlowNodeConfigEntity.getExecStatusList());
		Map<String,String> joinFlagMap =  new HashMap<String, String>();
		Map<String,Integer> countStatusMap =  new HashMap<String, Integer>();
		Map<String,String> nodeStageStatusMap =  new HashMap<String, String>();
		Map<String,String> countStatusNextNodeMap =  new HashMap<String, String>();
		for (Iterator<Object> currentIterator = currentStatusArray.iterator(); currentIterator.hasNext(); ) {
			JSONObject nextObj = (JSONObject) currentIterator.next();
			joinFlagMap.put(nextObj.getString("nodeStatus"),nextObj.getString("joinPassFlag"));
			nodeStageStatusMap.put(nextObj.getString("nodeStatus"),nextObj.getString("nodeStageStatus"));
			countStatusMap.put(nextObj.getString("nodeStatus"),0);
			countStatusNextNodeMap.put(nextObj.getString("nodeStatus"),nextObj.getString("nextProccessId"));
		}
		//会签
		boolean joinPassFlag = false;
		boolean orPassFlag = false;
		String nextNodeId = dataAreaObj.getString("nextNode");
		String passNodeStatus = dataAreaObj.getString("nodeStatus");
		String nodeStatus = dataAreaObj.getString("nodeStatus");
		//非子流程，跳转类型为会签，并且执行状态不能为空 ，getGotoType 0-或签 1-会签
		if(!dataAreaObj.getString("isSubFlow").equals("1") && generalFlowNodeConfigEntity.getGotoType().equals("1") && !countStatusMap.isEmpty()) {
			//获取当前节点下所有处理记录的状态（已转派、已委托除外）
			QueryWrapper<GeneralFlowProcessRecordEntity> recordQueryWrapper = new QueryWrapper<GeneralFlowProcessRecordEntity>();
			recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getTenantId, tenantId);
			recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getBizId, generalFlowProcessRecordEntity.getBizId());
			recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getNodeId, generalFlowProcessRecordEntity.getNodeId());
			recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getValidFlag, "1");
			recordQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getIsDelegateFlag, "1");
			recordQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getIsTransferFlag, "1");
			recordQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getProcessStatus, "");
			List<GeneralFlowProcessRecordEntity> generalFlowProcessRecordEntityList = generalFlowProcessRecordMapper.selectList(recordQueryWrapper);
			//统计每种状态的通过数目
			for(int i=0; i<generalFlowProcessRecordEntityList.size(); i++) {
				if(!StringUtil.isEmpty(generalFlowProcessRecordEntityList.get(i).getProcessStatus())) {
					if(countStatusMap.containsKey(generalFlowProcessRecordEntityList.get(i).getProcessStatus())) {
						countStatusMap.put(generalFlowProcessRecordEntityList.get(i).getProcessStatus(), countStatusMap.get(generalFlowProcessRecordEntityList.get(i).getProcessStatus())+1);
					}
					else {
						countStatusMap.put(generalFlowProcessRecordEntityList.get(i).getProcessStatus(), 1);
					}
				}
			}
			//当前处理状态未写入数据库，单独+1；
			countStatusMap.put(generalFlowProcessRecordEntity.getProcessStatus(), countStatusMap.get(generalFlowProcessRecordEntity.getProcessStatus())+1);

			//获取当前节点处理人数
			JSONArray userJsonArray = JSONObject.parseArray(generalFlowNodeConfigEntity.getOptUserList());
			Map<String,String> userList = getAllUserList(userJsonArray,tenantId, generalFlowProcessRecordEntity.getLaunchUserName(),"");
			int totUserCount = userList.size();

			//计算通过比例是否可以往下走
			for (Map.Entry<String, Integer> entry : countStatusMap.entrySet()) {
				double realRate = new BigDecimal((float)entry.getValue() / totUserCount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				double setRate = new BigDecimal((float)Float.parseFloat(generalFlowNodeConfigEntity.getPassRate())/100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if(realRate >= setRate) {
					joinPassFlag = true;
					nextNodeId = countStatusNextNodeMap.get(entry.getKey());
					passNodeStatus = entry.getKey();
				}
			}
			//会签不通过(处理人数 == 实际人数，并且joinPassFlag = false)，直接回到上节点， +1为当前用户状态没更新
			if(!joinPassFlag && (generalFlowProcessRecordEntityList.size()+1 == totUserCount)) {
				List<GeneralFlowProcessRecordEntity> rollbackGeneralFlowProcessRecordEntitList = new ArrayList<GeneralFlowProcessRecordEntity>();
				//循环处理上个处理节点（存在多个上一节点的情况）
				String[] nodeIdList = generalFlowProcessRecordEntity.getPrevNodeIdList().split(",");
				for(int j = 0; j < nodeIdList.length; j++) {
					QueryWrapper<GeneralFlowProcessRecordEntity> prevRecordQueryWrapper = new QueryWrapper<GeneralFlowProcessRecordEntity>();
					prevRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getTenantId, tenantId);
					prevRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getBizId, generalFlowProcessRecordEntity.getBizId());
					prevRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getNodeId, nodeIdList[j]);
					prevRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getValidFlag, "1");
					prevRecordQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getIsDelegateFlag, "1");
					prevRecordQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getIsTransferFlag, "1");
					List<GeneralFlowProcessRecordEntity> prevGeneralFlowProcessRecordEntityList = generalFlowProcessRecordMapper.selectList(prevRecordQueryWrapper);
					//整理上节点处理用户记录，重新写待处理记录。转派的仍然回到转派，委托的仍然回到委托
					for(int i=0; i<prevGeneralFlowProcessRecordEntityList.size(); i++) {
						if(!prevGeneralFlowProcessRecordEntityList.get(i).getIsDelegateFlag().equals("1") && !prevGeneralFlowProcessRecordEntityList.get(i).getIsTransferFlag().equals("1")) {
							prevGeneralFlowProcessRecordEntityList.get(i).setId(null);
							prevGeneralFlowProcessRecordEntityList.get(i).setProcessStatus("");
							prevGeneralFlowProcessRecordEntityList.get(i).setProcessResult("会签不通过，自动退回");
							prevGeneralFlowProcessRecordEntityList.get(i).setProcessOpinion("自动回退");
							prevGeneralFlowProcessRecordEntityList.get(i).setIsDelegateFlag("0");
							prevGeneralFlowProcessRecordEntityList.get(i).setIsTransferFlag("0");
							prevGeneralFlowProcessRecordEntityList.get(i).setProcessFinishDatetime("");
							prevGeneralFlowProcessRecordEntityList.get(i).setProcessBeginDatetime(DateUtils.initialDatatime());
							prevGeneralFlowProcessRecordEntityList.get(i).setProcessFinishDatetime(DateUtils.initialDatatime());
							prevGeneralFlowProcessRecordEntityList.get(i).setPrevNodeUserName(FormatUserName.getAllName(generalFlowProcessRecordEntity.getProcessRealName(),generalFlowProcessRecordEntity.getProcessUserName()));
							prevGeneralFlowProcessRecordEntityList.get(i).setPrevNodeOpinion("会签不通过，自动退回");
							prevGeneralFlowProcessRecordEntityList.get(i).setProcessProgressStatus("自动回退");
							prevGeneralFlowProcessRecordEntityList.get(i).setValidFlag("1");
							prevGeneralFlowProcessRecordEntityList.get(i).setPrevNodeNameList(generalFlowProcessRecordEntity.getNodeName());
							prevGeneralFlowProcessRecordEntityList.get(i).setDataSign(DooleenMD5Util.md5(prevGeneralFlowProcessRecordEntityList.get(i).toString(),  ConstantValue.md5Key));
							prevGeneralFlowProcessRecordEntityList.set(i, EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(prevGeneralFlowProcessRecordEntityList.get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
							rollbackGeneralFlowProcessRecordEntitList.add(prevGeneralFlowProcessRecordEntityList.get(i));
						}
					}
				}
				// 批量写回退到上节点记录
				super.saveBatch(rollbackGeneralFlowProcessRecordEntitList);

				// 写消息 01-委托，02-转派 ，03-自动回退到上节点
				sendMsg("03",null, rollbackGeneralFlowProcessRecordEntitList,null);

				// 更新当前节点的所有未处理记录为自动回退
				//获取当前节点下所有未处理记录的状态（已转派、已委托除外）
				QueryWrapper<GeneralFlowProcessRecordEntity> nodoQueryWrapper = new QueryWrapper<GeneralFlowProcessRecordEntity>();
				nodoQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getTenantId, tenantId);
				nodoQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getBizId, generalFlowProcessRecordEntity.getBizId());
				nodoQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getNodeId, generalFlowProcessRecordEntity.getNodeId());
				nodoQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getValidFlag, "1");
				nodoQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getProcessStatus, "");
				nodoQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getIsDelegateFlag, "1");
				nodoQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getIsTransferFlag, "1");
				List<GeneralFlowProcessRecordEntity> nodeGeneralFlowProcessRecordEntityList = generalFlowProcessRecordMapper.selectList(nodoQueryWrapper);
				for(int i=0; i<nodeGeneralFlowProcessRecordEntityList.size(); i++) {
					if(!nodeGeneralFlowProcessRecordEntityList.get(i).getId().equals(generalFlowProcessRecordEntity.getId())) {
						nodeGeneralFlowProcessRecordEntityList.get(i).setProcessStatus("自动回退");
						nodeGeneralFlowProcessRecordEntityList.get(i).setProcessProgressStatus("已回退");
						nodeGeneralFlowProcessRecordEntityList.get(i).setValidFlag("0");
						nodeGeneralFlowProcessRecordEntityList.get(i).setProcessResult("会签不通过——已回退至上节点");
						nodeGeneralFlowProcessRecordEntityList.get(i).setDataSign(DooleenMD5Util.md5(nodeGeneralFlowProcessRecordEntityList.get(i).toString(),  ConstantValue.md5Key));
						nodeGeneralFlowProcessRecordEntityList.set(i, EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(nodeGeneralFlowProcessRecordEntityList.get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
					}
				}
				//更新当前处理记录
				generalFlowProcessRecordEntity.setProcessProgressStatus("已回退");
				generalFlowProcessRecordEntity.setProcessResult("会签不通过");
				generalFlowProcessRecordEntity.setProcessFinishDatetime(DateUtils.getLongDateStr());
				generalFlowProcessRecordEntity.setDataSign(DooleenMD5Util.md5(generalFlowProcessRecordEntity.toString(),  ConstantValue.md5Key));
				generalFlowProcessRecordEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalFlowProcessRecordEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate);
				nodeGeneralFlowProcessRecordEntityList.add(generalFlowProcessRecordEntity);
				super.updateBatchById(nodeGeneralFlowProcessRecordEntityList);

				// 写消息  01-委托，02-转派 ，03-自动回退到上节点，04-已被回退
				sendMsg("04",null, nodeGeneralFlowProcessRecordEntityList,null);
			}
		}
		//会签通过走通过状态的下个节点，并把当前业务节点的所有未完成记录的处理结果置为 会签通过 TODO
		if(joinPassFlag) {
			QueryWrapper<GeneralFlowProcessRecordEntity> otherRecordQueryWrapper = new QueryWrapper<GeneralFlowProcessRecordEntity>();
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getTenantId, tenantId);
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getBizId, generalFlowProcessRecordEntity.getBizId());
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getNodeId, generalFlowProcessRecordEntity.getNodeId());
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getProcessStatus, "");
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getValidFlag, "1");
			List<GeneralFlowProcessRecordEntity> otherGeneralFlowProcessRecordEntityList = generalFlowProcessRecordMapper.selectList(otherRecordQueryWrapper);
			//剩下未处理的记录更新为通过状态
			List<GeneralFlowProcessRecordEntity> otherGeneralFlowProcessRecordList = new ArrayList<GeneralFlowProcessRecordEntity>();
			for(int i=0; i<otherGeneralFlowProcessRecordEntityList.size(); i++) {
				if(!generalFlowProcessRecordEntity.getId().equals(otherGeneralFlowProcessRecordEntityList.get(i).getId())) {
					otherGeneralFlowProcessRecordEntityList.get(i).setProcessStatus(passNodeStatus);
					otherGeneralFlowProcessRecordEntityList.get(i).setProcessResult("会签比例通过");
					otherGeneralFlowProcessRecordEntityList.get(i).setProcessProgressStatus("已完成");
					otherGeneralFlowProcessRecordEntityList.get(i).setProcessFinishDatetime(DateUtils.getLongDateStr());
					otherGeneralFlowProcessRecordEntityList.get(i).setDataSign(DooleenMD5Util.md5(otherGeneralFlowProcessRecordEntityList.get(i).toString(),  ConstantValue.md5Key));
					otherGeneralFlowProcessRecordEntityList.set(i, EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(otherGeneralFlowProcessRecordEntityList.get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
					otherGeneralFlowProcessRecordList.add(otherGeneralFlowProcessRecordEntityList.get(i));
				}
			}
			log.info("*********+++OK"+otherGeneralFlowProcessRecordEntityList.toString());
			// 批量更新记录
			super.updateBatchById(otherGeneralFlowProcessRecordList);
		}
		//处理或签，或签时将其他节点更新为当前状态
		//非子流程，跳转类型为或签，并且执行状态不能为空 ，getGotoType 0-或签 1-会签
		if(!dataAreaObj.getString("isSubFlow").equals("1") && generalFlowNodeConfigEntity.getGotoType().equals("0") && !countStatusMap.isEmpty()) {
			QueryWrapper<GeneralFlowProcessRecordEntity> otherRecordQueryWrapper = new QueryWrapper<GeneralFlowProcessRecordEntity>();
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getTenantId, tenantId);
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getBizId, generalFlowProcessRecordEntity.getBizId());
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getNodeId, generalFlowProcessRecordEntity.getNodeId());
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getProcessStatus, "");
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getValidFlag, "1");
			List<GeneralFlowProcessRecordEntity> otherGeneralFlowProcessRecordEntityList = generalFlowProcessRecordMapper.selectList(otherRecordQueryWrapper);
			//剩下未处理的记录更新为通过状态
			List<GeneralFlowProcessRecordEntity> otherGeneralFlowProcessRecordList = new ArrayList<GeneralFlowProcessRecordEntity>();
			for(int i=0; i<otherGeneralFlowProcessRecordEntityList.size(); i++) {
				if(!generalFlowProcessRecordEntity.getId().equals(otherGeneralFlowProcessRecordEntityList.get(i).getId())) {
					otherGeneralFlowProcessRecordEntityList.get(i).setProcessStatus(passNodeStatus);
					otherGeneralFlowProcessRecordEntityList.get(i).setProcessResult("或签自动通过");
					otherGeneralFlowProcessRecordEntityList.get(i).setProcessProgressStatus("已完成");
					otherGeneralFlowProcessRecordEntityList.get(i).setProcessFinishDatetime(DateUtils.getLongDateStr());
					otherGeneralFlowProcessRecordEntityList.get(i).setDataSign(DooleenMD5Util.md5(otherGeneralFlowProcessRecordEntityList.get(i).toString(),  ConstantValue.md5Key));
					otherGeneralFlowProcessRecordEntityList.set(i, EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(otherGeneralFlowProcessRecordEntityList.get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
					otherGeneralFlowProcessRecordList.add(otherGeneralFlowProcessRecordEntityList.get(i));
				}
			}
			orPassFlag = true;
			// 批量更新记录
			super.updateBatchById(otherGeneralFlowProcessRecordList);
		}
		//如果下节点是子流程，则启动子流程
		if(dataAreaObj.getString("isSubFlow").equals("1")) {
			log.debug(">>>开始启动子流程="+dataAreaObj.toString());
			//启动子流程
			List<Object> idArray = JSONObject.parseArray(nextNodeId);
			for(int i = 0; i < idArray.size(); i++){
				if(startSubFlow(generalFlowProcessRecordEntity,commonMsg,tenantId,idArray.get(i).toString()).getHead().getRespCode() != "S0000"){
					return errorReturn(commonMsg);
				}
			}
			// 更新当前记录为暂停状态
			generalFlowProcessRecordEntity.setProcessProgressStatus("已完成");
			generalFlowProcessRecordEntity.setProcessResult("进入子流程");
			generalFlowProcessRecordEntity.setProcessFinishDatetime(DateUtils.getLongDateStr());
			generalFlowProcessRecordEntity.setDataSign(DooleenMD5Util.md5(generalFlowProcessRecordEntity.toString(),  ConstantValue.md5Key));
			super.updateById(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalFlowProcessRecordEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		}
		else {
			//更新当前处理记录 flowEndFlag 0-开始，1-过程中 2-结束 ，8-暂停
			if(generalFlowProcessRecordEntity.getFlowEndFlag().equals("2") || dataAreaObj.getString("flowEndFlag").equals("2") ) {
				log.debug(">>>流程处理id="+generalFlowProcessRecordEntity.getId()+"结束！");
				generalFlowProcessRecordEntity.setProcessResult("流程结束");
				generalFlowProcessRecordEntity.setFlowEndFlag("2");
				//如果是子流程结束，则更新主流程状态，让主流程继续执行。
				if(!StringUtil.isEmpty(generalFlowProcessRecordEntity.getMainNodeId())) {
					//获取主流程处理记录：
					//根据处理记录ID获取当前处理记录 MainNodeId() 处理ID
					GeneralFlowProcessRecordEntity  mainGeneralFlowProcessRecordEntity = super.getById(generalFlowProcessRecordEntity.getMainNodeId());

					//获取当前
					//更新主节点处理记录
					//将子流程节点从状态列表中移除
					JSONObject mainDataArea = JSONObject.parseObject(mainGeneralFlowProcessRecordEntity.getDataArea());
					JSONObject subObj = new JSONObject();
					subObj.put("label", mainGeneralFlowProcessRecordEntity.getProcessStatus());
					subObj.put("value", mainGeneralFlowProcessRecordEntity.getProcessStatus());
					mainDataArea.getJSONArray("nodeStatusList").remove(subObj);
					mainGeneralFlowProcessRecordEntity.setId("");
					mainGeneralFlowProcessRecordEntity.setDataArea(mainDataArea.toString());
					mainGeneralFlowProcessRecordEntity.setProcessStatus("");
					mainGeneralFlowProcessRecordEntity.setProcessProgressStatus("待处理");
					mainGeneralFlowProcessRecordEntity.setProcessResult("子流程结束回到主流程节点");
					mainGeneralFlowProcessRecordEntity.setProcessBeginDatetime(DateUtils.getLongDateStr());
					mainGeneralFlowProcessRecordEntity.setProcessFinishDatetime(DateUtils.initialDatatime());
					mainGeneralFlowProcessRecordEntity.setCreateDatetime("");
					mainGeneralFlowProcessRecordEntity.setCreateUserName("");
					mainGeneralFlowProcessRecordEntity.setCreateRealName("");
					mainGeneralFlowProcessRecordEntity.setUpdateDatetime("");
					mainGeneralFlowProcessRecordEntity.setUpdateRealName("");
					mainGeneralFlowProcessRecordEntity.setUpdateUserName("");

					//处理工作台记录
					mainGeneralFlowProcessRecordEntity.setWorkbenchData(getWorkbenchMsgData(mainGeneralFlowProcessRecordEntity,generalFlowNodeConfigEntity));

					mainGeneralFlowProcessRecordEntity.setDataSign(DooleenMD5Util.md5(mainGeneralFlowProcessRecordEntity.toString(),  ConstantValue.md5Key));
					super.save(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(mainGeneralFlowProcessRecordEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
					// 写消息 01-委托，02-转派 ，03-自动回退到上节点，04-已被回退，05-子流程结束
					sendMsg("05",mainGeneralFlowProcessRecordEntity, null,null);
				}
			}
			else {
				generalFlowProcessRecordEntity.setProcessResult("进入下一节点");
			}
			generalFlowProcessRecordEntity.setProcessProgressStatus("已完成");
			generalFlowProcessRecordEntity.setProcessFinishDatetime(DateUtils.getLongDateStr());
			generalFlowProcessRecordEntity.setDataSign(DooleenMD5Util.md5(generalFlowProcessRecordEntity.toString(),  ConstantValue.md5Key));
			super.updateById(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalFlowProcessRecordEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate));

			//如果有抄送，给抄送人发送消息
			JSONObject flowExtData = JSON.parseObject(generalFlowProcessRecordEntity.getDataArea());
			List<String> ccUserList = new ArrayList<String>();
			if(!StringUtil.isEmpty(flowExtData) && !StringUtil.isEmpty(flowExtData.getString("ccUser"))) {
				JSONArray userJsonArray = JSONObject.parseArray(flowExtData.getString("ccUser"));
				for(int i = 0; i< userJsonArray.size();i++) {
					ccUserList.add(userJsonArray.getString(i));
				}
				writeCcUserRecord(generalFlowProcessRecordEntity,ccUserList,commonMsg);
			}
			//会签通过并且流程未结束，处理下一节点用户待处理记录（第二层）flowEndFlag 0-开始 ，1-过程中 2-结束 ，8-暂停
			if(!dataAreaObj.getString("flowEndFlag").equals("2") && (joinPassFlag || orPassFlag || generalFlowNodeConfigEntity.getGotoType().equals("") ) && !generalFlowProcessRecordEntity.getFlowEndFlag().equals("2")) {
				List<Object> idArray = JSONObject.parseArray(nextNodeId);
				//保证处理时间一致
				String nowDate =  DateUtils.getLongDateStr();
				for(int i = 0; i < idArray.size(); i++){
					buildNextUserInfo(commonMsg, generalFlowInfoEntity,generalFlowProcessRecordEntity,nodeStatus,nodeStageStatusMap.get(nodeStatus),idArray.get(i).toString(),idArray.size(),nowDate);
				}
			}
			else {
				//将启动记录置为完成
				generalFlowProcessRecordMapper.updateFirstProcessRecord(generalFlowProcessRecordEntity);

				log.info(">>>业务ID="+generalFlowProcessRecordEntity.getBizId()+"，流程ID="+generalFlowProcessRecordEntity.getFlowId()+",节点ID="+generalFlowProcessRecordEntity.getNodeId()+", 已结束");
			}

			//更新所有记录状态为当前节点状态
			generalFlowProcessRecordEntity.setProcessStageStatus(nodeStatus);
			generalFlowProcessRecordMapper.batchUpdateProcessRecord(generalFlowProcessRecordEntity);
			//更新抄送人员列表
			generalFlowProcessCcRecordMapper.batchUpdateProcessRecord(generalFlowProcessRecordEntity);
		}
		// 返回成功
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	/**
	 * 流程处理主逻辑
	 *
	 */
	@Override
	public CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> processReject(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}

		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		commonMsg.getBody().setListBody(nullEntityList);
		String tenantId = "";
		//根据处理记录ID获取当前处理记录
		GeneralFlowProcessRecordEntity  generalFlowProcessRecordEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
		BizResponseEnum.FLOW_NODE_DATA_NOT_EXIST.assertNotNull(generalFlowProcessRecordEntity,commonMsg);
		generalFlowProcessRecordEntity.setFormId(commonMsg.getBody().getSingleBody().getFormId());
		generalFlowProcessRecordEntity.setBizDataEntity(commonMsg.getBody().getSingleBody().getBizDataEntity());

		generalFlowProcessRecordEntity.setDataArea(commonMsg.getBody().getSingleBody().getDataArea());
		JSONObject dataAreaObj = JSONObject.parseObject(generalFlowProcessRecordEntity.getDataArea());
		//如果不是结束节点 检查输入数据（转给人、委派人、转派人）,不通过直接返回
		if(!generalFlowProcessRecordEntity.getFlowEndFlag().equals("2") && !checkProcessData(commonMsg,dataAreaObj)) {
			return commonMsg;
		}
		handlerUserName =  commonMsg.getHead().getUserName();
		handlerRealName =  commonMsg.getHead().getRealName();
		handlerHeadImgUrl = commonMsg.getHead().getHeadImgUrl();
		//根据流程ID获取流程及节点信息
		generalFlowInfoEntity = generalFlowInfoMapper.selectById(generalFlowProcessRecordEntity.getFlowId());

		tenantId = generalFlowInfoEntity.getTenantId();
		// 获取当前处理节点配置信息
		generalFlowNodeConfigEntity = generalFlowNodeConfigMapper.selectById(generalFlowProcessRecordEntity.getNodeId());

		//保存当前处理结果记录
		generalFlowProcessRecordEntity.setProcessOpinion(dataAreaObj.getString("processOpinion"));
		generalFlowProcessRecordEntity.setAttName("");
		generalFlowProcessRecordEntity.setAttList(dataAreaObj.getString("fileList"));

		String nextNodeId = dataAreaObj.getString("nextNode");
		String passNodeStatus = dataAreaObj.getString("nodeStatus");
		String nodeStatus = dataAreaObj.getString("nodeStatus");
		List<Object> idArray = JSONObject.parseArray(nextNodeId);

		//如果下节点已开始，不能驳回
		for(int i = 0; i < idArray.size(); i++) {
			QueryWrapper<GeneralFlowProcessRecordEntity> nextRecordQueryWrapper = new QueryWrapper<GeneralFlowProcessRecordEntity>();
			nextRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getTenantId, tenantId);
			nextRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getBizId, generalFlowProcessRecordEntity.getBizId());
			nextRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getNodeId, idArray.get(0));
			nextRecordQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getIsDelegateFlag, "1");
			nextRecordQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getIsTransferFlag, "1");
			GeneralFlowProcessRecordEntity flowProcessRecordEntity = generalFlowProcessRecordMapper.selectOne(nextRecordQueryWrapper);
			BizResponseEnum.NEXT_NODE_HAS_BEGIN_CANNOT_REJECT.assertIsNull(flowProcessRecordEntity,commonMsg);
		}
		//如果流程已终止，则直接改为完成
		if(generalFlowProcessRecordEntity.getFlowEndFlag().equals("2")) {
			generalFlowProcessRecordEntity.setProcessStatus("已完成");
			generalFlowProcessRecordEntity.setProcessFinishDatetime(DateUtils.getLongDateStr());
			super.updateById(generalFlowProcessRecordEntity);
			// 返回成功
			CommonUtil.successReturn(commonMsg);
			return commonMsg;
		}
		else {
			generalFlowProcessRecordEntity.setProcessStatus(dataAreaObj.getString("nodeStatus"));
		}
		BizResponseEnum.FLOW_HAS_BEEN_DONE.assertNotEmpty(generalFlowProcessRecordEntity.getProcessStatus(),commonMsg);

		/**
		 *  判断是或签还是会签
		 *  0-或签 直接处理下个节点
		 *  1-会签 判断会签比例再处理
		 */
		//获取当前节点的处理状态 在会签时 是否通过标志joinPassFlag  1-通过 0-不通过
		JSONArray currentStatusArray = JSONObject.parseArray(generalFlowNodeConfigEntity.getExecStatusList());
		Map<String,String> joinFlagMap =  new HashMap<String, String>();
		Map<String,Integer> countStatusMap =  new HashMap<String, Integer>();
		Map<String,String> nodeStageStatusMap =  new HashMap<String, String>();
		Map<String,String> countStatusNextNodeMap =  new HashMap<String, String>();
		for (Iterator<Object> currentIterator = currentStatusArray.iterator(); currentIterator.hasNext(); ) {
			JSONObject nextObj = (JSONObject) currentIterator.next();
			joinFlagMap.put(nextObj.getString("nodeStatus"),nextObj.getString("joinPassFlag"));
			nodeStageStatusMap.put(nextObj.getString("nodeStatus"),nextObj.getString("nodeStageStatus"));
			countStatusMap.put(nextObj.getString("nodeStatus"),0);
			countStatusNextNodeMap.put(nextObj.getString("nodeStatus"),nextObj.getString("nextProccessId"));
		}
		//会签
		boolean joinPassFlag = false;
		boolean orPassFlag = false;

		//非子流程，跳转类型为会签，并且执行状态不能为空 ，getGotoType 0-或签 1-会签
		if(!dataAreaObj.getString("isSubFlow").equals("1") && generalFlowNodeConfigEntity.getGotoType().equals("1") && !countStatusMap.isEmpty()) {
			//获取当前节点下所有处理记录的状态（已转派、已委托除外）
			QueryWrapper<GeneralFlowProcessRecordEntity> recordQueryWrapper = new QueryWrapper<GeneralFlowProcessRecordEntity>();
			recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getTenantId, tenantId);
			recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getBizId, generalFlowProcessRecordEntity.getBizId());
			recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getNodeId, generalFlowProcessRecordEntity.getNodeId());
			recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getValidFlag, "1");
			recordQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getIsDelegateFlag, "1");
			recordQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getIsTransferFlag, "1");
			recordQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getProcessStatus, "");
			List<GeneralFlowProcessRecordEntity> generalFlowProcessRecordEntityList = generalFlowProcessRecordMapper.selectList(recordQueryWrapper);
			//统计每种状态的通过数目
			for(int i=0; i<generalFlowProcessRecordEntityList.size(); i++) {
				if(!StringUtil.isEmpty(generalFlowProcessRecordEntityList.get(i).getProcessStatus())) {
					if(countStatusMap.containsKey(generalFlowProcessRecordEntityList.get(i).getProcessStatus())) {
						countStatusMap.put(generalFlowProcessRecordEntityList.get(i).getProcessStatus(), countStatusMap.get(generalFlowProcessRecordEntityList.get(i).getProcessStatus())+1);
					}
					else {
						countStatusMap.put(generalFlowProcessRecordEntityList.get(i).getProcessStatus(), 1);
					}
				}
			}
			//当前处理状态未写入数据库，单独+1；
			//countStatusMap.put(generalFlowProcessRecordEntity.getProcessStatus(), countStatusMap.get(generalFlowProcessRecordEntity.getProcessStatus())+1);

			//获取当前节点处理人数
			JSONArray userJsonArray = JSONObject.parseArray(generalFlowNodeConfigEntity.getOptUserList());
			Map<String,String> userList = getAllUserList(userJsonArray,tenantId, generalFlowProcessRecordEntity.getLaunchUserName(),"");
			int totUserCount = userList.size();

			//计算通过比例是否可以往下走
			for (Map.Entry<String, Integer> entry : countStatusMap.entrySet()) {
				double realRate = new BigDecimal((float)entry.getValue() / totUserCount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				double setRate = new BigDecimal((float)Float.parseFloat(generalFlowNodeConfigEntity.getPassRate())/100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if(realRate >= setRate) {
					joinPassFlag = true;
					nextNodeId = countStatusNextNodeMap.get(entry.getKey());
					passNodeStatus = entry.getKey();
				}
			}
			//会签不通过(处理人数 == 实际人数，并且joinPassFlag = false)，直接回到上节点， +1为当前用户状态没更新
			if(!joinPassFlag && (generalFlowProcessRecordEntityList.size()+1 == totUserCount)) {
				// 更新当前节点的所有未处理记录为自动回退
				//获取当前节点下所有未处理记录的状态（已转派、已委托除外）
				QueryWrapper<GeneralFlowProcessRecordEntity> nodoQueryWrapper = new QueryWrapper<GeneralFlowProcessRecordEntity>();
				nodoQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getTenantId, tenantId);
				nodoQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getBizId, generalFlowProcessRecordEntity.getBizId());
				nodoQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getNodeId, generalFlowProcessRecordEntity.getNodeId());
				nodoQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getValidFlag, "1");
				nodoQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getProcessStatus, "");
				nodoQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getIsDelegateFlag, "1");
				nodoQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getIsTransferFlag, "1");
				List<GeneralFlowProcessRecordEntity> nodeGeneralFlowProcessRecordEntityList = generalFlowProcessRecordMapper.selectList(nodoQueryWrapper);
				for(int i=0; i<nodeGeneralFlowProcessRecordEntityList.size(); i++) {
					if(!nodeGeneralFlowProcessRecordEntityList.get(i).getId().equals(generalFlowProcessRecordEntity.getId())) {
						nodeGeneralFlowProcessRecordEntityList.get(i).setProcessStatus("自动回退");
						nodeGeneralFlowProcessRecordEntityList.get(i).setProcessProgressStatus("已回退");
						nodeGeneralFlowProcessRecordEntityList.get(i).setValidFlag("0");
						nodeGeneralFlowProcessRecordEntityList.get(i).setProcessResult("会签被拒绝——已回退至上节点");
						nodeGeneralFlowProcessRecordEntityList.get(i).setDataSign(DooleenMD5Util.md5(nodeGeneralFlowProcessRecordEntityList.get(i).toString(),  ConstantValue.md5Key));
						nodeGeneralFlowProcessRecordEntityList.set(i, EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(nodeGeneralFlowProcessRecordEntityList.get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
					}
				}
				//更新当前处理记录
				generalFlowProcessRecordEntity.setProcessProgressStatus("已完成");
				generalFlowProcessRecordEntity.setProcessResult("会签被拒绝——已回退至上节点(主)");
				generalFlowProcessRecordEntity.setProcessFinishDatetime(DateUtils.getLongDateStr());
				generalFlowProcessRecordEntity.setDataSign(DooleenMD5Util.md5(generalFlowProcessRecordEntity.toString(),  ConstantValue.md5Key));
				generalFlowProcessRecordEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalFlowProcessRecordEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate);
				nodeGeneralFlowProcessRecordEntityList.add(generalFlowProcessRecordEntity);
				super.updateBatchById(nodeGeneralFlowProcessRecordEntityList);

				// 写消息  01-委托，02-转派 ，03-自动回退到上节点，04-已被回退
				sendMsg("09",null, nodeGeneralFlowProcessRecordEntityList,null);
			}
		}
		//拒绝时会签不可能通过，跳过通过处理

		//处理或签，或签时将其他节点更新为当前状态
		//非子流程，跳转类型为或签，并且执行状态不能为空 ，getGotoType 0-或签 1-会签
		else if(!dataAreaObj.getString("isSubFlow").equals("1") && generalFlowNodeConfigEntity.getGotoType().equals("0") && !countStatusMap.isEmpty()) {
			QueryWrapper<GeneralFlowProcessRecordEntity> otherRecordQueryWrapper = new QueryWrapper<GeneralFlowProcessRecordEntity>();
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getTenantId, tenantId);
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getBizId, generalFlowProcessRecordEntity.getBizId());
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getNodeId, generalFlowProcessRecordEntity.getNodeId());
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getProcessStatus, "");
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getValidFlag, "1");
			List<GeneralFlowProcessRecordEntity> otherGeneralFlowProcessRecordEntityList = generalFlowProcessRecordMapper.selectList(otherRecordQueryWrapper);
			//剩下未处理的记录更新为拒绝状态
			List<GeneralFlowProcessRecordEntity> otherGeneralFlowProcessRecordList = new ArrayList<GeneralFlowProcessRecordEntity>();
			for(int i=0; i<otherGeneralFlowProcessRecordEntityList.size(); i++) {
				if(!generalFlowProcessRecordEntity.getId().equals(otherGeneralFlowProcessRecordEntityList.get(i).getId())) {
					otherGeneralFlowProcessRecordEntityList.get(i).setProcessStatus(passNodeStatus);
					otherGeneralFlowProcessRecordEntityList.get(i).setProcessResult("或签拒绝");
					otherGeneralFlowProcessRecordEntityList.get(i).setProcessProgressStatus("已完成");
//					if(!otherGeneralFlowProcessRecordEntityList.get(i).getProcessUserName().equals(generalFlowProcessRecordEntity.getProcessUserName())) {
//						otherGeneralFlowProcessRecordEntityList.get(i).setValidFlag("0");
//					}
					otherGeneralFlowProcessRecordEntityList.get(i).setProcessFinishDatetime(DateUtils.getLongDateStr());
					otherGeneralFlowProcessRecordEntityList.get(i).setDataSign(DooleenMD5Util.md5(otherGeneralFlowProcessRecordEntityList.get(i).toString(),  ConstantValue.md5Key));
					otherGeneralFlowProcessRecordEntityList.set(i, EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(otherGeneralFlowProcessRecordEntityList.get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
					otherGeneralFlowProcessRecordList.add(otherGeneralFlowProcessRecordEntityList.get(i));
				}
			}
			orPassFlag = true;
			// 批量更新记录
			super.updateBatchById(otherGeneralFlowProcessRecordList);

			// 写消息  01-委托，02-转派 ，03-自动回退到上节点，04-已被回退 09-驳回
			List<GeneralFlowProcessRecordEntity> generalFlowProcessRecordEntityList = new ArrayList<>();
			generalFlowProcessRecordEntityList.add(generalFlowProcessRecordEntity);
			sendMsg("09", null, generalFlowProcessRecordEntityList, null);
		}
		else if(generalFlowNodeConfigEntity.getGotoType().equals("")) {
			//更新当前处理记录
			generalFlowProcessRecordEntity.setProcessProgressStatus("已完成");
			generalFlowProcessRecordEntity.setProcessResult("已驳回");
			generalFlowProcessRecordEntity.setProcessFinishDatetime(DateUtils.getLongDateStr());
			generalFlowProcessRecordEntity.setDataSign(DooleenMD5Util.md5(generalFlowProcessRecordEntity.toString(), ConstantValue.md5Key));
			generalFlowProcessRecordEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalFlowProcessRecordEntity, commonMsg.getHead(), tableName, seqPrefix, redisTemplate);
			super.updateById(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalFlowProcessRecordEntity, commonMsg.getHead(), tableName, seqPrefix, redisTemplate));

			// 写消息  01-委托，02-转派 ，03-自动回退到上节点，04-已被回退 09-驳回
			List<GeneralFlowProcessRecordEntity> generalFlowProcessRecordEntityList = new ArrayList<>();
			generalFlowProcessRecordEntityList.add(generalFlowProcessRecordEntity);
			sendMsg("09", null, generalFlowProcessRecordEntityList, null);
		}

		//更新当前处理记录
		generalFlowProcessRecordEntity.setProcessProgressStatus("已完成");
		generalFlowProcessRecordEntity.setProcessResult("已驳回");
		generalFlowProcessRecordEntity.setProcessFinishDatetime(DateUtils.getLongDateStr());
		generalFlowProcessRecordEntity.setDataSign(DooleenMD5Util.md5(generalFlowProcessRecordEntity.toString(),  ConstantValue.md5Key));
		super.updateById(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalFlowProcessRecordEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate));

		//开始处理拒绝回退
		if((!joinPassFlag && generalFlowNodeConfigEntity.getGotoType().equals("1")) || orPassFlag || generalFlowNodeConfigEntity.getGotoType().equals("")) {
			List<GeneralFlowProcessRecordEntity> rollbackGeneralFlowProcessRecordEntitList = new ArrayList<GeneralFlowProcessRecordEntity>();
			//循环处理上个处理节点（存在多个上一节点的情况）
			String[] nodeIdList = generalFlowProcessRecordEntity.getPrevNodeIdList().split(",");
			for(int j = 0; j < nodeIdList.length; j++) {
				QueryWrapper<GeneralFlowProcessRecordEntity> prevRecordQueryWrapper = new QueryWrapper<GeneralFlowProcessRecordEntity>();
				prevRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getTenantId, tenantId);
				prevRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getBizId, generalFlowProcessRecordEntity.getBizId());
				prevRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getNodeId, nodeIdList[j]);
				prevRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getValidFlag, "1");
				prevRecordQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getIsDelegateFlag, "1");
				prevRecordQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getIsTransferFlag, "1");
				List<GeneralFlowProcessRecordEntity> prevGeneralFlowProcessRecordEntityList = generalFlowProcessRecordMapper.selectList(prevRecordQueryWrapper);
				//整理上节点处理用户记录，重新写待处理记录。转派的仍然回到转派，委托的仍然回到委托
				for(int i=0; i<prevGeneralFlowProcessRecordEntityList.size(); i++) {
					if(!prevGeneralFlowProcessRecordEntityList.get(i).getIsDelegateFlag().equals("1") && !prevGeneralFlowProcessRecordEntityList.get(i).getIsTransferFlag().equals("1")) {
						prevGeneralFlowProcessRecordEntityList.get(i).setProcessResult("被驳回");
						prevGeneralFlowProcessRecordEntityList.get(i).setProcessOpinion(generalFlowProcessRecordEntity.getProcessOpinion());
						prevGeneralFlowProcessRecordEntityList.get(i).setIsDelegateFlag("0");
						prevGeneralFlowProcessRecordEntityList.get(i).setIsTransferFlag("0");
						if(prevGeneralFlowProcessRecordEntityList.get(i).getNodeOrderSeq() == 1) {
							prevGeneralFlowProcessRecordEntityList.get(i).setProcessFinishDatetime(DateUtils.getLongDateStr());
						}
						else{
							prevGeneralFlowProcessRecordEntityList.get(i).setProcessStatus("");
							prevGeneralFlowProcessRecordEntityList.get(i).setProcessProgressStatus("待处理");
							prevGeneralFlowProcessRecordEntityList.get(i).setProcessFinishDatetime(DateUtils.initialDatatime());
						}
						prevGeneralFlowProcessRecordEntityList.get(i).setValidFlag("1");
						generalFlowProcessRecordEntity = prevGeneralFlowProcessRecordEntityList.get(i);

						prevGeneralFlowProcessRecordEntityList.get(i).setDataSign(DooleenMD5Util.md5(prevGeneralFlowProcessRecordEntityList.get(i).toString(),  ConstantValue.md5Key));
						prevGeneralFlowProcessRecordEntityList.set(i, EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(prevGeneralFlowProcessRecordEntityList.get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));

						prevGeneralFlowProcessRecordEntityList.get(i).setId(null);
						rollbackGeneralFlowProcessRecordEntitList.add(prevGeneralFlowProcessRecordEntityList.get(i));
					}
				}
			}
			// 批量写回退到上节点记录（开始节点只能一个）
			if(generalFlowProcessRecordEntity.getNodeOrderSeq() == 1){
				//结束流程
				generalFlowProcessRecordEntity.setFlowEndFlag("2");
				generalFlowProcessRecordEntity.setProcessStageStatus("流程已终止");
				//更新所有记录状态为当前节点状态
				generalFlowProcessRecordEntity.setProcessFinishDatetime(DateUtils.getLongDateStr());
				super.updateById(generalFlowProcessRecordEntity);
			}
			else {
				super.saveBatch(rollbackGeneralFlowProcessRecordEntitList);
				//更新所有记录状态为当前节点状态
				generalFlowProcessRecordEntity.setProcessFinishDatetime(DateUtils.initialDatatime());
				generalFlowProcessRecordEntity.setProcessStageStatus(generalFlowProcessRecordEntity.getNodeStatus());
			}
			// 写消息  01-委托，02-转派 ，03-自动回退到上节点，04-已被回退
			sendMsg("03",null, rollbackGeneralFlowProcessRecordEntitList,null);

			generalFlowProcessRecordMapper.batchUpdateProcessRecord(generalFlowProcessRecordEntity);
			//更新抄送人员列表
			generalFlowProcessCcRecordMapper.batchUpdateProcessRecord(generalFlowProcessRecordEntity);
		}

		// 返回成功
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	/**
	 * 对下个节点未处理的信息进行审批追回处理
	 */
	@Override
	public CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> processCallBack(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}

		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		commonMsg.getBody().setListBody(nullEntityList);
		String tenantId = "";
		//根据处理记录ID获取当前处理记录
		GeneralFlowProcessRecordEntity  generalFlowProcessRecordEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
		BizResponseEnum.FLOW_NODE_DATA_NOT_EXIST.assertNotNull(generalFlowProcessRecordEntity,commonMsg);

		generalFlowProcessRecordEntity.setDataArea(commonMsg.getBody().getSingleBody().getDataArea());
		JSONObject dataAreaObj = JSONObject.parseObject(generalFlowProcessRecordEntity.getDataArea());
		//处理意见为空直接返回错误
		BizResponseEnum.PROCESS_OPITION_CANNOT_EMPTY.assertNotEmpty(dataAreaObj.getString("processOpinion").trim(),commonMsg);

		// 如果下个处理节点为子流程，则不能撤回
		if("1".equals(dataAreaObj.getString("isSubFlow"))){
			BizResponseEnum.NEXT_NODE_IS_SUB_FLOW.assertIsTrue(false,commonMsg);
		}

		//根据流程ID获取流程及节点信息
		generalFlowInfoEntity = generalFlowInfoMapper.selectById(generalFlowProcessRecordEntity.getFlowId());
		handlerUserName =  commonMsg.getHead().getUserName();
		handlerRealName =  commonMsg.getHead().getRealName();
		handlerHeadImgUrl = commonMsg.getHead().getHeadImgUrl();

		tenantId = generalFlowInfoEntity.getTenantId();

		// 获取当前处理节点配置信息
		generalFlowNodeConfigEntity = generalFlowNodeConfigMapper.selectById(generalFlowProcessRecordEntity.getNodeId());
		BizResponseEnum.CURRENT_NODE_CANNOT_REBACK.assertIsFalse(generalFlowNodeConfigEntity.getIsRollbackFlag().equals("0"),commonMsg);

		// 会签不允许撤回 0-或签 1-会签
		BizResponseEnum.ALL_SIGN_CANNOT_REBACK.assertIsFalse(generalFlowNodeConfigEntity.getGotoType().equals("1"),commonMsg);

		//读取该节点后续的所有处理记录
		QueryWrapper<GeneralFlowProcessRecordEntity> nextRecordQueryWrapper = new QueryWrapper<GeneralFlowProcessRecordEntity>();
		nextRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getTenantId, tenantId);
		nextRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getBizId, generalFlowProcessRecordEntity.getBizId());
		nextRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getPrevNodeProcessId, generalFlowProcessRecordEntity.getId());
		nextRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getValidFlag, "1");
		List<GeneralFlowProcessRecordEntity> nextGeneralFlowProcessRecordEntityList = generalFlowProcessRecordMapper.selectList(nextRecordQueryWrapper);
		//判断是否已处理
		boolean isHandleFlag = false;
		for(int i=0; i < nextGeneralFlowProcessRecordEntityList.size(); i++) {
			if(!StringUtil.isEmpty(nextGeneralFlowProcessRecordEntityList.get(i).getProcessStatus())) {
				isHandleFlag = true;
			}
			// 预处理所有记录为撤回，
			nextGeneralFlowProcessRecordEntityList.get(i).setProcessStatus("人工撤回");
			nextGeneralFlowProcessRecordEntityList.get(i).setProcessResult("撤回"+"-"+dataAreaObj.getString("processOpinion").trim());
			nextGeneralFlowProcessRecordEntityList.get(i).setProcessProgressStatus("已撤回");
			nextGeneralFlowProcessRecordEntityList.get(i).setValidFlag("0");
		}

		//如果已处理返回错误提示
		BizResponseEnum.NEXT_NODE_HAS_BEGIN.assertIsFalse(isHandleFlag,commonMsg);

		// 开始更新被撤回的所有记录
		super.updateBatchById(nextGeneralFlowProcessRecordEntityList);
		// 写消息 01-委托，02-转派 ，03-自动回退到上节点，04-已被回退，07-流程被撤回
		sendMsg("07",null, nextGeneralFlowProcessRecordEntityList,null);

		// 更新当前记录为处理状态
		// 如果是开始节点撤回，将validFlag 置为 0-不可用
		if(generalFlowProcessRecordEntity.getNodeOrderSeq() == 1){
			generalFlowProcessRecordEntity.setValidFlag("0");
		}
		generalFlowProcessRecordEntity.setProcessStatus("");
		generalFlowProcessRecordEntity.setProcessResult("");
		generalFlowProcessRecordEntity.setProcessProgressStatus("待处理");
		if(generalFlowProcessRecordEntity.getNodeOrderSeq() == 1){
			generalFlowProcessRecordEntity.setProcessStatus("撤回作废");
			generalFlowProcessRecordEntity.setProcessResult("撤回作废");
			generalFlowProcessRecordEntity.setProcessProgressStatus("撤回");
		}
		else{
			generalFlowProcessRecordEntity.setFlowEndFlag("1");
		}
		generalFlowProcessRecordEntity.setProcessFinishDatetime(DateUtils.initialDatatime());
		generalFlowProcessRecordEntity.setDataSign(DooleenMD5Util.md5(generalFlowProcessRecordEntity.toString(),  ConstantValue.md5Key));
		super.updateById(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalFlowProcessRecordEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate));

		//更新流程开始结束时间
		generalFlowProcessRecordMapper.updateFirstProcessRecord(generalFlowProcessRecordEntity);

		//更新所有记录状态为当前节点状态
		generalFlowProcessRecordEntity.setProcessStageStatus(generalFlowProcessRecordEntity.getNodeStatus());
		generalFlowProcessRecordMapper.batchUpdateProcessRecord(generalFlowProcessRecordEntity);

		//更新抄送人员列表 所有记录状态为当前节点状态
		generalFlowProcessCcRecordMapper.batchUpdateProcessRecord(generalFlowProcessRecordEntity);

		// 返回成功
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	/**
	 * 启动子流程
	 */
	public CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> startSubFlow(GeneralFlowProcessRecordEntity flowProcessRecordEntity, CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg, String tenantId,String nodeId) {
		//读选择节点的配置表
		GeneralFlowNodeConfigEntity nextGeneralFlowNodeConfigEntity = getNodeConfigByNodeId(tenantId,nodeId);
		if(nextGeneralFlowNodeConfigEntity == null || !nextGeneralFlowNodeConfigEntity.getIsSubFlowFlag().equals("1")) {
			return errorReturn(commonMsg);
		}
		GeneralFlowProcessRecordEntity  generalFlowProcessRecordEntity = new GeneralFlowProcessRecordEntity();
		generalFlowProcessRecordEntity.setFormId(flowProcessRecordEntity.getFormId());
		generalFlowProcessRecordEntity.setFlowId(nextGeneralFlowNodeConfigEntity.getSubFlowId());
		generalFlowProcessRecordEntity.setFormType(flowProcessRecordEntity.getFormType());
		generalFlowProcessRecordEntity.setBizType(flowProcessRecordEntity.getBizType());
		generalFlowProcessRecordEntity.setBizId(commonMsg.getBody().getSingleBody().getBizId());
		generalFlowProcessRecordEntity.setBizName(commonMsg.getBody().getSingleBody().getBizName());
		generalFlowProcessRecordEntity.setTenantId(commonMsg.getHead().getTenantId());
		generalFlowProcessRecordEntity.setMainFlowId(flowProcessRecordEntity.getFlowId());
		generalFlowProcessRecordEntity.setMainNodeId(flowProcessRecordEntity.getId());
		generalFlowProcessRecordEntity.setDataArea("");
		commonMsg.getBody().setSingleBody(generalFlowProcessRecordEntity);
		startFlow(commonMsg);
		return commonMsg;
	}

	/**
	 * 返回失败
	 */
	public CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> errorReturn(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg) {
		//设置返回码
		Map<String, String> map = new HashMap<String, String>(2);
		commonMsg.getHead().setRespCode(RespStatus.succCode);
		map.put("E8008", "流程配置错误");
		commonMsg.getHead().setRespMsg(map);
		log.info("====GeneralFlowProcess service end == " + RespStatus.json.getString("S0000"));
		return commonMsg;
	}
	/**
	 * 处理数据校验检查
	 */
	public boolean checkProcessData(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg,JSONObject dataAreaObj) {
		Map<String, String> map = new HashMap<String, String>(2);
		//如果不是结束节点，转给人、委托人、委派人不能同时为空
		if(!dataAreaObj.getString("flowEndFlag").equals("2") && StringUtil.isEmpty(dataAreaObj.getJSONArray("optUser")) && StringUtil.isEmpty(dataAreaObj.getString("delegateUser")) && StringUtil.isEmpty(dataAreaObj.getString("transferUser"))) {
			// 设置失败返回信息
			BizResponseEnum.TRANS_DE_USER_CANNOT_ALL_EMPTY.assertIsFalse(true,commonMsg);
		}
		else if(StringUtil.isEmpty(dataAreaObj.getString("processOpinion").trim())) {
			// 设置失败返回信息
			BizResponseEnum.PROCESS_OPITION_CANNOT_EMPTY.assertIsFalse(true,commonMsg);
		}
		return true;
	}
	/**
	 * 继续组装下节点用户需要看到的信息
	 */
	public String buildNextUserInfo(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg,GeneralFlowInfoEntity generalFlowInfoEntity,GeneralFlowProcessRecordEntity generalFlowProcessRecordEntity, String nodeStatus,String nodeStageStatusMap, String nodeId, int nodeSize,String nowDate) {
		log.debug(">>>节点ID= {} 开始组装节点后续处理记录，当前处理数据：{}", nodeId, ReflectionToStringBuilder.toString(generalFlowProcessRecordEntity,ToStringStyle.MULTI_LINE_STYLE));
		//获取节点配置信息
		String flowEndFlag = "1";
		String nodeType = "过程节点";
		String tenantId = generalFlowInfoEntity.getTenantId();
		//需要发送处理信息的用户列表（第二层）
		List<String> joinUserList = new ArrayList<String>();

		//如果是审批树，获取当前处理用户
		//此处handlerUserName为全局的当前处理人
		// 获取节点处理人列表, 用于推送消息（第二层）
		String currentNodeUserName = "";
		JSONObject flowExtData = JSON.parseObject(generalFlowProcessRecordEntity.getDataArea());

		if(!StringUtil.isEmpty(flowExtData) && !StringUtil.isEmpty(flowExtData.getString("optUser")) && nodeSize == 1) {
			JSONArray userJsonArray = JSONObject.parseArray(flowExtData.getString("optUser"));
			for(int i = 0; i< userJsonArray.size();i++) {
				joinUserList.add(userJsonArray.getString(i));
			}
		}
		else  if(!StringUtil.isEmpty(generalFlowInfoEntity.getApproveTreeNo().trim())) {
			Map<String, String> userMap = getTreeUserList(handlerUserName);
			for (Map.Entry<String, String> entry : userMap.entrySet()) {
				joinUserList.add(entry.getValue() + "(" + entry.getKey() + ")");
				currentNodeUserName = entry.getKey();
			}
		}

		//获取第二层的配置信息
		GeneralFlowNodeConfigEntity nextGeneralFlowNodeConfigEntity = getNodeConfigByNodeId(tenantId,nodeId);

		//处理 A,B 节点子节点同时是C的情况
		//1、根据租户号，流程id，查询该节点的父节点列表，将父节点的信息写入列表（查询性能差，建立全文索引）
		QueryWrapper<GeneralFlowNodeConfigEntity> nodeQueryWrapper = new QueryWrapper<GeneralFlowNodeConfigEntity>();
		nodeQueryWrapper.lambda().eq(GeneralFlowNodeConfigEntity::getTenantId, tenantId);
		nodeQueryWrapper.lambda().eq(GeneralFlowNodeConfigEntity::getFlowId, generalFlowProcessRecordEntity.getFlowId());
		nodeQueryWrapper.lambda().and(wrapper -> wrapper.like(GeneralFlowNodeConfigEntity::getExecStatusList, nextGeneralFlowNodeConfigEntity.getId()));
		List<GeneralFlowNodeConfigEntity> generalFlowNodeConfigEntityList = generalFlowNodeConfigMapper.selectList(nodeQueryWrapper);
		log.debug(">>>处理 A,B 节点子节点同时是C的情况时，查询记录条数为：{}", generalFlowNodeConfigEntityList.size());

		// 方法getNextNodeOptUserList会获取节点名字，所以将此处理提前（第三层）
		// 获取遍历该状态下可到达的所有下节点,获取下个节点的处理人 （第三层）
		Map<String,String> nodeNameMap = new HashMap<String, String>();
		JSONArray nextStatusArray = JSONObject.parseArray(nextGeneralFlowNodeConfigEntity.getExecStatusList());
		if(nextStatusArray.size() == 0) {
			//没有下个节点流程处理下个人记录中 流程结束标志置为 1-结束
			//TODO ...
			nodeType = "结束节点";
			flowEndFlag = "2";
		}
		List<JSONObject> optUserList = getNextNodeOptUserList(nextStatusArray,tenantId,nodeNameMap,generalFlowProcessRecordEntity.getLaunchUserName(),currentNodeUserName);

		//组装扩展表单信息 是否会签、是否转派、是否委托（第二层）
		JSONObject flowExtInfo = getFlowExtInfo(nextGeneralFlowNodeConfigEntity,nodeNameMap);
		//第三层返回赋值后置
		flowExtInfo.put("nodeName",nextGeneralFlowNodeConfigEntity.getNodeName());
		flowExtInfo.put("flowEndFlag",flowEndFlag);
		flowExtInfo.put("optUserList",optUserList);
		flowExtInfo.put("optUser","");
		flowExtInfo.put("isSubFlow","");
		flowExtInfo.put("fileList",new JSONArray());

		//(第二层)
		flowExtInfo.put("prevProcessUser",FormatUserName.getAllName(generalFlowProcessRecordEntity.getProcessRealName(), generalFlowProcessRecordEntity.getProcessUserName()));
		flowExtInfo.put("prevNodeOpinion",generalFlowProcessRecordEntity.getProcessOpinion());
		flowExtInfo.put("prevNodeNameList",generalFlowProcessRecordEntity.getNodeName());
		flowExtInfo.put("processOpinion","");

		Map<String,String> selectUserMap = new HashMap<String, String>();
		if(!StringUtil.isEmpty(flowExtData) && !StringUtil.isEmpty(flowExtData.getString("optUser"))) {
			JSONArray userJsonArray = JSONObject.parseArray(flowExtData.getString("optUser"));
			for(int i = 0; i< userJsonArray.size();i++) {
				selectUserMap.put(userJsonArray.getString(i),"");
			}
		}


		Map<String,String> selectccUserMap = new HashMap<String, String>();
		if(!StringUtil.isEmpty(flowExtData) && !StringUtil.isEmpty(flowExtData.getString("ccUser"))) {
			JSONArray ccUserJsonArray = JSONObject.parseArray(flowExtData.getString("ccUser"));
			for(int i = 0; i< ccUserJsonArray.size();i++) {
				selectccUserMap.put(ccUserJsonArray.getString(i),"");
			}
		}

		//如果非人工选择，并且非审批树的，则以配置的用户为准
		if(joinUserList.size() == 0) {
			// 用户输入人员信息应在配置人员范围内（如一个状态的下个节点配置了多个时，那么对应的人员也是多个节点的人员，这时候应该有效控制节点信息只发给对应的）
			JSONArray userJsonArray = JSONObject.parseArray(nextGeneralFlowNodeConfigEntity.getOptUserList());
			Map<String,String> userList = getAllUserList(userJsonArray,tenantId, generalFlowProcessRecordEntity.getLaunchUserName(),"");
			//组装会签的人员列表（第二层）
			for (Map.Entry<String,String> entry : userList.entrySet()) {
				if(!StringUtil.isEmpty(flowExtData) && !StringUtil.isEmpty(flowExtData.getString("optUser"))) {
					if(selectUserMap.containsKey(entry.getValue()+"("+entry.getKey()+")")) {
						joinUserList.add(entry.getValue()+"("+entry.getKey()+")");
					}
				}
				else
				{
					joinUserList.add(entry.getValue()+"("+entry.getKey()+")");
				}
			}
		}
		log.debug(">>>optUser=="+joinUserList.toString());
		flowExtInfo.put("joinUser",joinUserList);
		flowExtInfo.put("easyModeFlag",nextGeneralFlowNodeConfigEntity.getEasyModeFlag());
		//给抄送人员写消息 抄送人员不写处理记录
		//用户选择输入的抄送用户以为准，否则已配置的抄送用户为准
		List<Object> joinCcUserList = new ArrayList<Object>();
		if(!StringUtil.isEmpty(flowExtData) && !StringUtil.isEmpty(flowExtData.getString("ccUser")) && nodeSize == 1) {
			JSONArray ccUserJsonArray = JSONObject.parseArray(flowExtData.getString("ccUser"));
			for(int i = 0; i< ccUserJsonArray.size();i++) {
				joinCcUserList.add(ccUserJsonArray.getString(i));
			}
		}
		else {
			//组装当前抄送人员列表
			JSONArray ccUserList = new JSONArray();

			JSONObject ccUserObj = new JSONObject();
			ccUserObj.put("operatorType","user");
			JSONArray ccUserJsonArray = JSONObject.parseArray(nextGeneralFlowNodeConfigEntity.getCcUserList());
			ccUserObj.put("optValues",ccUserJsonArray);
			ccUserList.add(ccUserObj);

			JSONObject ccRoleObj = new JSONObject();
			ccRoleObj.put("operatorType","role");
			JSONArray ccRoleJsonArray = JSONObject.parseArray(nextGeneralFlowNodeConfigEntity.getCcRoleName());
			ccRoleObj.put("optValues",ccRoleJsonArray);
			ccUserList.add(ccRoleObj);
			Map<String,String> ccUserLists = getAllUserList(ccUserList,tenantId,generalFlowProcessRecordEntity.getLaunchUserName(),"");
			//组装会签的人员列表（第二层）
			for (Map.Entry<String,String> entry : ccUserLists.entrySet()) {
				if(!StringUtil.isEmpty(flowExtData) && !StringUtil.isEmpty(flowExtData.getString("ccUser"))) {
					if(selectccUserMap.containsKey(entry.getValue()+"("+entry.getKey()+")")) {
						joinCcUserList.add(entry.getValue()+"("+entry.getKey()+")");
					}
				}
				else
				{
					joinCcUserList.add(entry.getValue()+"("+entry.getKey()+")");
				}
			}
		}


		//写节点处理人员 待处理记录信息 并推送消息（第二层）
		List<GeneralFlowProcessRecordEntity> nextGeneralFlowProcessRecordEntitList = new ArrayList<GeneralFlowProcessRecordEntity>();
		for (int i = 0; i < joinUserList.size(); i++) {
			String[] users= FormatUserName.getUser(joinUserList.get(i).toString());

			GeneralFlowProcessRecordEntity flowProcessRecordEntity = new GeneralFlowProcessRecordEntity();
			//如果父节点多余一个，则判断是否最后一个处理的  controlWay 0-或控制，1-与控制（即父节点完全执行后再执行）
			//用租户号、业务编号、节点编号、处理用户ID查询记录是否存在，如果存在即为 A、B...到C模式 (委托，转派的除外)
			QueryWrapper<GeneralFlowProcessRecordEntity> otherRecordQueryWrapper = new QueryWrapper<GeneralFlowProcessRecordEntity>();
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getTenantId, tenantId);
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getBizId, generalFlowProcessRecordEntity.getBizId());
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getNodeId, nextGeneralFlowNodeConfigEntity.getId());
			otherRecordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getProcessUserName, users[1]);
			otherRecordQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getIsDelegateFlag, "1");
			otherRecordQueryWrapper.lambda().ne(GeneralFlowProcessRecordEntity::getIsTransferFlag, "1");
			flowProcessRecordEntity = generalFlowProcessRecordMapper.selectOne(otherRecordQueryWrapper);
			//if(generalFlowNodeConfigEntityList.size() > 1 && nextGeneralFlowNodeConfigEntity.getControlWay().equals("1")) {
			if(generalFlowNodeConfigEntityList.size() > 1) {
				if(flowProcessRecordEntity != null) {
					String[] nodeIdList = flowProcessRecordEntity.getPrevNodeIdList().split(",");
					//如果已处理的节点 小于 待处理的节点数，则直接更新记录
					if(nodeIdList.length+1 <= generalFlowNodeConfigEntityList.size()) {
						flowProcessRecordEntity.setPrevNodeIdList(flowProcessRecordEntity.getPrevNodeIdList()+","+generalFlowProcessRecordEntity.getNodeId());
						flowProcessRecordEntity.setPrevNodeNameList(flowProcessRecordEntity.getPrevNodeNameList()+","+generalFlowProcessRecordEntity.getNodeName());
						flowProcessRecordEntity.setPrevNodeOpinion(flowProcessRecordEntity.getPrevNodeOpinion()
								+ "\n"+generalFlowProcessRecordEntity.getProcessRealName()+"的处理意见："+generalFlowProcessRecordEntity.getProcessOpinion());
					}
					// 已到达最后一个处理父节点，则将记录推到相应的处理人
					if((nodeIdList.length + 1) == generalFlowNodeConfigEntityList.size())
					{
						flowProcessRecordEntity.setValidFlag("1");
						flowProcessRecordEntity.setProcessStatus("");
						flowExtInfo.put("prevNodeIdList",flowProcessRecordEntity.getPrevNodeIdList());
						flowExtInfo.put("prevNodeNameList",flowProcessRecordEntity.getPrevNodeNameList());
						flowProcessRecordEntity.setDataArea(flowExtInfo.toString());
					}
					//更新记录, 并设置节点开始时间
					flowProcessRecordEntity.setProcessBeginDatetime(nowDate);
					generalFlowProcessRecordMapper.update(flowProcessRecordEntity, otherRecordQueryWrapper);

					if(nextGeneralFlowNodeConfigEntity.getControlWay().equals("0")) {
						log.debug(">>> 同级节点先到达，已处理！");
					}
				}
				else
				{
					log.debug(">>>用户："+users[0]+" 无处理记录");
				}
			}
			//如果只有一个父节点时 或者多个父节点时没有处理记录时 写一条处理记录
			if(generalFlowNodeConfigEntityList.size() == 1 || (generalFlowNodeConfigEntityList.size() > 1 && flowProcessRecordEntity == null)) {
				flowProcessRecordEntity = new GeneralFlowProcessRecordEntity();
				log.debug(">>>开始组装用户："+users[0]+" 的处理记录。。。");
				//多个父节点时没有处理记录时, 将记录状态值为不可用
				if(generalFlowNodeConfigEntityList.size() > 1 && flowProcessRecordEntity == null && nextGeneralFlowNodeConfigEntity.getControlWay().equals("1")) {
					flowProcessRecordEntity = new GeneralFlowProcessRecordEntity();
					flowProcessRecordEntity.setValidFlag("0");
					flowProcessRecordEntity.setProcessStatus("待上一节点处理");
					flowProcessRecordEntity.setPrevNodeOpinion(generalFlowProcessRecordEntity.getProcessRealName()+"的处理意见："+generalFlowProcessRecordEntity.getProcessOpinion());
				}
				else
				{
					flowProcessRecordEntity.setProcessStatus("");
					flowProcessRecordEntity.setValidFlag("1");
					flowProcessRecordEntity.setProcessBeginDatetime(nowDate);
					flowProcessRecordEntity.setPrevNodeOpinion(generalFlowProcessRecordEntity.getProcessOpinion());

				}

				flowProcessRecordEntity.setFormId(generalFlowProcessRecordEntity.getFormId());
				flowProcessRecordEntity.setMainFlowId(generalFlowProcessRecordEntity.getMainFlowId());
				flowProcessRecordEntity.setMainNodeId(generalFlowProcessRecordEntity.getMainNodeId());
				flowProcessRecordEntity.setFlowId(generalFlowInfoEntity.getId());
				flowProcessRecordEntity.setFlowName(generalFlowInfoEntity.getFlowName());
				flowProcessRecordEntity.setNodeType(nodeType);  //开始节点
				flowProcessRecordEntity.setPrevNodeProcessId(generalFlowProcessRecordEntity.getId());
				flowProcessRecordEntity.setPrevNodeIdList(generalFlowProcessRecordEntity.getNodeId());
				flowProcessRecordEntity.setPrevNodeNameList(generalFlowProcessRecordEntity.getNodeName());
				flowProcessRecordEntity.setPrevNodeUserName(FormatUserName.getAllName(generalFlowProcessRecordEntity.getProcessRealName(), generalFlowProcessRecordEntity.getProcessUserName()));
				flowProcessRecordEntity.setLaunchUserName(generalFlowProcessRecordEntity.getLaunchUserName());
				flowProcessRecordEntity.setLaunchRealName(generalFlowProcessRecordEntity.getLaunchRealName());
				flowProcessRecordEntity.setLaunchDatetime(generalFlowProcessRecordEntity.getLaunchDatetime());
				flowProcessRecordEntity.setCcUserList(joinCcUserList.toString());
				flowProcessRecordEntity.setNodeId(nextGeneralFlowNodeConfigEntity.getId());
				flowProcessRecordEntity.setNodeName(nextGeneralFlowNodeConfigEntity.getNodeName());
				flowProcessRecordEntity.setBizId(generalFlowProcessRecordEntity.getBizId());
				flowProcessRecordEntity.setBizType(generalFlowProcessRecordEntity.getBizType());
				flowProcessRecordEntity.setBizName(generalFlowProcessRecordEntity.getBizName());
				flowProcessRecordEntity.setFlowEndFlag(flowEndFlag);
				flowProcessRecordEntity.setCcFlag("0");
				flowProcessRecordEntity.setProcessProgressStatus("待处理");
				flowProcessRecordEntity.setNodeStatus(nodeStatus);
				flowProcessRecordEntity.setNodeStageStatus(nodeStageStatusMap);
				flowProcessRecordEntity.setProcessStageStatus(nodeStatus);
				flowProcessRecordEntity.setProcessResult("");
				flowProcessRecordEntity.setIsDelegateFlag("0");
				flowProcessRecordEntity.setIsTransferFlag("0");
				flowProcessRecordEntity.setProcessUserName(users[1]);
				flowProcessRecordEntity.setProcessRealName(users[0]);
				flowProcessRecordEntity.setRollbackFlag(nextGeneralFlowNodeConfigEntity.getIsRollbackFlag());
				flowProcessRecordEntity.setOvertimeFlag("0");
				flowProcessRecordEntity.setAttList("[]");
				flowProcessRecordEntity.setIsOvertimeNoticeFlag(nextGeneralFlowNodeConfigEntity.getIsOvertimeNoticeFlag());
				flowProcessRecordEntity.setTimeUnit(nextGeneralFlowNodeConfigEntity.getTimeUnit());
				flowProcessRecordEntity.setFormType(generalFlowInfoEntity.getFormType());
				flowProcessRecordEntity.setNodeWaitDuration(nextGeneralFlowNodeConfigEntity.getNodeWaitDuration());
				flowProcessRecordEntity.setOvertimeNoticeWay(nextGeneralFlowNodeConfigEntity.getOvertimeNoticeWay());

				//组装当前超时通知人员列表
				JSONArray ccOvertimeUserList = new JSONArray();

				JSONObject ccOvertimeUserObj = new JSONObject();
				ccOvertimeUserObj.put("operatorType","user");
				JSONArray ccOvertimeUserJsonArray = JSONObject.parseArray(nextGeneralFlowNodeConfigEntity.getOvertimeNoticeList());
				ccOvertimeUserObj.put("optValues",ccOvertimeUserJsonArray);
				ccOvertimeUserList.add(ccOvertimeUserObj);

				JSONObject ccOvertimeRoleObj = new JSONObject();
				ccOvertimeRoleObj.put("operatorType","role");
				JSONArray ccOvertimeRoleJsonArray = JSONObject.parseArray(nextGeneralFlowNodeConfigEntity.getOvertimeNoticeRoleName());
				ccOvertimeRoleObj.put("optValues",ccOvertimeRoleJsonArray);
				ccOvertimeUserList.add(ccOvertimeRoleObj);

				Map<String,String> ccOvertimeUserLists = getAllUserList(ccOvertimeUserList,tenantId,generalFlowProcessRecordEntity.getLaunchUserName(),"");

				List<String> joinOvertimeCcUserList = new ArrayList<String>();
				//组装当前节点抄送人
				for (Map.Entry<String,String> entry : ccOvertimeUserLists.entrySet()) {
					if(!joinOvertimeCcUserList.contains(entry.getValue()+"("+entry.getKey()+")")) {
						joinOvertimeCcUserList.add(entry.getValue()+"("+entry.getKey()+")");
					}
				}

				flowProcessRecordEntity.setOvertimeNoticeList(joinOvertimeCcUserList.toString());
				flowProcessRecordEntity.setDataArea(flowExtInfo.toString());
				//如果是动态表单，获取表单的配置
				if(StringUtils.isNotEmpty(generalFlowInfoEntity.getFormType()) &&  generalFlowInfoEntity.getFormType().equals("2")) {
					String formId = generalFlowProcessRecordEntity.getFormId();
					if(StringUtils.isNotEmpty(nextGeneralFlowNodeConfigEntity.getFormId())) {
						formId = nextGeneralFlowNodeConfigEntity.getFormId();
					}
					flowProcessRecordEntity.setFormId(formId);
					SysDynamicFormEntity sysDynamicFormEntity = sysDynamicFormMapper.selectById(formId);
					if(sysDynamicFormEntity == null) {
						throw new BaseServiceException("未找到表单配置记录！", ServiceErrCode.NOTFOUND_RESULT_ERR);
					}
					else {
						flowProcessRecordEntity.setFormArea(sysDynamicFormEntity.getFormJson());
						flowProcessRecordEntity.setBizCode(sysDynamicFormEntity.getBizCode());
					}
				}
				flowProcessRecordEntity.setNodeOrderSeq(nextGeneralFlowNodeConfigEntity.getOrderSeq());

				//处理工作台内容
				flowProcessRecordEntity.setBizDataEntity(generalFlowProcessRecordEntity.getBizDataEntity());
				flowProcessRecordEntity.setWorkbenchData(getWorkbenchMsgData(flowProcessRecordEntity,nextGeneralFlowNodeConfigEntity));

				flowProcessRecordEntity.setDataSign(DooleenMD5Util.md5(flowProcessRecordEntity.toString(),  ConstantValue.md5Key));
				flowProcessRecordEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(flowProcessRecordEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate);
				nextGeneralFlowProcessRecordEntitList.add(flowProcessRecordEntity);
			}
		}
		// 父节点个数>=1时，批量写下个节点处理记录
		if(nextGeneralFlowProcessRecordEntitList.size() >= 1) {
			super.saveBatch(nextGeneralFlowProcessRecordEntitList);
			// 写消息 01-委托，02-转派 ，03-自动回退到上节点，04-已被回退，05-子流程结束,06-流转到下个节点
			sendMsg("06",null, nextGeneralFlowProcessRecordEntitList,null);
		}

		return "success";
	}

	/**
	 * 写抄送记录
	 * @param generalFlowProcessRecordEntity
	 * @param joinCcUserList
	 */
	private void writeCcUserRecord(GeneralFlowProcessRecordEntity generalFlowProcessRecordEntity,List<String> joinCcUserList,CommonMsg<?, ?> commonMsg) {
		List<GeneralFlowProcessCcRecordEntity> generalFlowProcessCcRecordList  = new ArrayList<GeneralFlowProcessCcRecordEntity>();
		generalFlowProcessRecordEntity.setCcFlag("1");
		for (int i = 0; i < joinCcUserList.size(); i++) {
			GeneralFlowProcessCcRecordEntity generalFlowProcessCcRecordEntity = new GeneralFlowProcessCcRecordEntity();
			String[] user =  FormatUserName.getUser(joinCcUserList.get(i).toString());
			generalFlowProcessCcRecordEntity.setId(null);
			generalFlowProcessCcRecordEntity.setProcessId(generalFlowProcessRecordEntity.getId());
			generalFlowProcessCcRecordEntity.setBizId(generalFlowProcessRecordEntity.getBizId());
			generalFlowProcessCcRecordEntity.setBizCode(generalFlowProcessRecordEntity.getBizCode());
			generalFlowProcessCcRecordEntity.setBizType(generalFlowProcessRecordEntity.getBizType());
			generalFlowProcessCcRecordEntity.setBizName(generalFlowProcessRecordEntity.getBizName());
			generalFlowProcessCcRecordEntity.setFlowId(generalFlowProcessRecordEntity.getFlowId());
			generalFlowProcessCcRecordEntity.setNodeName(generalFlowProcessRecordEntity.getNodeName());
			generalFlowProcessCcRecordEntity.setNodeWaitDuration(generalFlowProcessRecordEntity.getNodeWaitDuration());
			generalFlowProcessCcRecordEntity.setProcessBeginDatetime(generalFlowProcessRecordEntity.getProcessBeginDatetime());
			generalFlowProcessCcRecordEntity.setProcessFinishDatetime(generalFlowProcessRecordEntity.getProcessFinishDatetime());
			generalFlowProcessCcRecordEntity.setProcessProgressStatus(generalFlowProcessRecordEntity.getProcessProgressStatus());
			generalFlowProcessCcRecordEntity.setDataArea(generalFlowProcessRecordEntity.getDataArea());
			generalFlowProcessCcRecordEntity.setFormArea(generalFlowProcessRecordEntity.getFormArea());
			generalFlowProcessCcRecordEntity.setBizData(generalFlowProcessRecordEntity.getBizData());
			generalFlowProcessCcRecordEntity.setWorkbenchData(generalFlowProcessRecordEntity.getWorkbenchData());
			generalFlowProcessCcRecordEntity.setLaunchUserName(generalFlowProcessRecordEntity.getLaunchUserName());
			generalFlowProcessCcRecordEntity.setLaunchRealName(generalFlowProcessRecordEntity.getLaunchRealName());
			generalFlowProcessCcRecordEntity.setLaunchDatetime(generalFlowProcessRecordEntity.getLaunchDatetime());
			generalFlowProcessCcRecordEntity.setProcessRealName(user[0]);
			generalFlowProcessCcRecordEntity.setProcessUserName(user[1]);
			generalFlowProcessCcRecordEntity.setDataSign(DooleenMD5Util.md5(generalFlowProcessCcRecordEntity.toString(),  ConstantValue.md5Key));
			generalFlowProcessCcRecordEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalFlowProcessCcRecordEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate);
			generalFlowProcessCcRecordList.add(generalFlowProcessCcRecordEntity);
			generalFlowProcessCcRecordMapper.insert(generalFlowProcessCcRecordEntity);
		}

		if(generalFlowProcessCcRecordList.size() >= 1) {
			//给抄送用户发消息
			// 写消息 01-委托，02-转派 ，03-自动回退到上节点，04-已被回退，05-子流程结束,06-流转到下个节点,07- 08-抄送用户发消息
			List<GeneralFlowProcessRecordEntity> objList = new ArrayList<>();
			objList.add(generalFlowProcessRecordEntity);
			sendMsg("08", generalFlowProcessRecordEntity, objList,joinCcUserList);
		}
	}

	/**
	 * 处理扩展区信息
	 */
	public JSONObject getFlowExtInfo(GeneralFlowNodeConfigEntity nextGeneralFlowNodeConfigEntity,Map<String,String> nodeNameMap) {
		log.info("====nodeNameMap= found == {}", nodeNameMap);
		JSONObject flowExtInfo = new JSONObject();
		//判断是否可以转派
		if(nextGeneralFlowNodeConfigEntity.getIsTransferFlag().equals("1")) {
			flowExtInfo.put("isTransferFlag",true);
		}
		else {
			flowExtInfo.put("isTransferFlag",false);
		}
		//判断是否可以委托
		if(nextGeneralFlowNodeConfigEntity.getIsDelegateFlag().equals("1")) {
			flowExtInfo.put("isDelegateFlag",true);
		}
		else {
			flowExtInfo.put("isDelegateFlag",false);
		}
		//判断是否会签,（跳转方式为会签）
		if(nextGeneralFlowNodeConfigEntity.getGotoType().equals("1")) {
			flowExtInfo.put("isJoinFlag",true);
			//会签人员列表在方法外处理。
		}
		else {
			flowExtInfo.put("isJoinFlag",false);
		}
		//可流转状态列表
		JSONArray nextStatusArray = JSONObject.parseArray(nextGeneralFlowNodeConfigEntity.getExecStatusList());

		List<JSONObject> statList = new ArrayList<JSONObject>();
		List<JSONObject> nextNodeList = new ArrayList<JSONObject>();
		int index=0;
		for (Iterator<Object> iterator = nextStatusArray.iterator(); iterator.hasNext(); ) {
			JSONObject nextObj = (JSONObject) iterator.next();
			JSONObject statusObj = new  JSONObject();
			statusObj.put("label",nextObj.get("nodeStatus"));
			statusObj.put("value",nextObj.get("nodeStatus"));
			if(!statList.contains(statusObj)) {
				statList.add(statusObj);
			}
			//获取所有下个节点
			JSONObject nextNodeListObj = new  JSONObject();
			List<JSONObject> nextNodeDictList = new ArrayList<JSONObject>();
			List<String> nextNodes = new ArrayList<String>();
			JSONArray nextProccessIdArray = JSONObject.parseArray(nextObj.getString("nextProccessId"));
			for (Iterator<Object> nextProcessIterator = nextProccessIdArray.iterator(); nextProcessIterator.hasNext(); ) {
				JSONObject nextNodeDict = new  JSONObject();
				String nextNodeName = nextProcessIterator.next().toString();
				nextNodeDict.put("label",nodeNameMap.get(nextNodeName));
				nextNodeDict.put("value",nextNodeName);
				nextNodeDictList.add(nextNodeDict);
				nextNodes.add(nextNodeName);
			}
			nextNodeListObj.put("nextNodeStatus",nextObj.get("nodeStatus"));
			nextNodeListObj.put("gotoCondition",nextObj.get("gotoCondition"));
			nextNodeListObj.put("nextNode",nextNodes);
			nextNodeListObj.put("nextNodeDict",nextNodeDictList);
			nextNodeList.add(nextNodeListObj);

			//默认第一个状态为选中
			if(index == 0) {
				flowExtInfo.put("nodeStatus",nextObj.get("nodeStatus"));
				index++;
			}
		}
		flowExtInfo.put("nodeStatusList",statList);
		flowExtInfo.put("nextNodeList",nextNodeList);
		//处理表单组权限
		JSONArray hideFormGroupList = JSONObject.parseArray(nextGeneralFlowNodeConfigEntity.getHideFormGroupList());
		flowExtInfo.put("hideFormGroupList",hideFormGroupList);
		//处理表单栏位权限
		List<JSONObject> privilegeList = new ArrayList<JSONObject>();
		JSONArray formPrivilegeList = JSONObject.parseArray(nextGeneralFlowNodeConfigEntity.getFormPrivilegeList());
		for (Iterator<Object> iterator = formPrivilegeList.iterator(); iterator.hasNext(); ) {
			JSONObject nextObj = (JSONObject) iterator.next();
			JSONObject formObj = new  JSONObject();
			formObj.put("privilegeFlag",nextObj.getString("privilege"));
			formObj.put("privilegeFormList",nextObj.getJSONArray("fieldName"));
			privilegeList.add(formObj);
		}
		flowExtInfo.put("formPrivilege",privilegeList);
		return flowExtInfo;
	}
	/**
	 * 根据状态处理区execStatusList 获取下个处理节点的用户信息（第三层）
	 */
	public List<JSONObject> getNextNodeOptUserList(JSONArray nextStatusArray,String tenantId, Map<String,String> nodeNameMap,String launchUserName, String currentNodeUserName){
		List<JSONObject> optUserList =new ArrayList<JSONObject>();
		for (Iterator<Object> iterator = nextStatusArray.iterator(); iterator.hasNext(); ) {
			JSONObject nextObj = (JSONObject) iterator.next();
			List<Object> nexIdsArray = JSONObject.parseArray(nextObj.getString("nextProccessId"));
			JSONObject optUserListObj =new JSONObject();
			List<JSONObject> ccUserDictList = new ArrayList<JSONObject>();
			List<JSONObject> optUserDictList =new ArrayList<JSONObject>();
			List<String> optUsers = new ArrayList<String>();
			List<String> ccUsers = new ArrayList<String>();

			for(int i=0;i < nexIdsArray.size(); i++) {
				JSONObject optUserDictObj =new JSONObject();
				List<JSONObject> groupsList =new ArrayList<JSONObject>();
				//循环获取每个状态下每个节点配置 审批
				GeneralFlowNodeConfigEntity thirdGeneralFlowNodeConfigEntity = getNodeConfigByNodeId(tenantId,nexIdsArray.get(i).toString());
				nodeNameMap.put(thirdGeneralFlowNodeConfigEntity.getId(), thirdGeneralFlowNodeConfigEntity.getNodeName());
				JSONArray thirdUserJsonArray = JSONObject.parseArray(thirdGeneralFlowNodeConfigEntity.getOptUserList());
				Map<String,String> userLists = getAllUserList(thirdUserJsonArray,tenantId,launchUserName,currentNodeUserName);
				for (Map.Entry<String,String> entry : userLists.entrySet()) {
					JSONObject userObj =new JSONObject();
					userObj.put("label", entry.getValue()+"("+entry.getKey()+")");
					userObj.put("value", entry.getValue()+"("+entry.getKey()+")");
					optUsers.add(entry.getValue()+"("+entry.getKey()+")");
					groupsList.add(userObj);
				}
				optUserDictObj.put("label", nextObj.getString("nodeStatus")); //thirdGeneralFlowNodeConfigEntity.getNodeName());
				optUserListObj.put("isSubFlow",thirdGeneralFlowNodeConfigEntity.getIsSubFlowFlag());
				optUserDictObj.put("groups",groupsList);
				optUserDictList.add(optUserDictObj);

				//组装当前抄送人员列表
				JSONArray ccUserList = new JSONArray();
				JSONObject ccUserObj = new JSONObject();
				ccUserObj.put("operatorType","user");
				JSONArray ccUserJsonArray = JSONObject.parseArray(thirdGeneralFlowNodeConfigEntity.getCcUserList());
				ccUserObj.put("optValues",ccUserJsonArray);
				ccUserList.add(ccUserObj);

				JSONObject ccRoleObj = new JSONObject();
				ccRoleObj.put("operatorType","role");
				JSONArray ccRoleJsonArray = JSONObject.parseArray(thirdGeneralFlowNodeConfigEntity.getCcRoleName());
				ccRoleObj.put("optValues",ccRoleJsonArray);
				ccUserList.add(ccRoleObj);
				Map<String,String> ccUserLists = getAllUserList(ccUserList,tenantId,launchUserName,"");

				//组装当前节点抄送人
				JSONObject ccUserDictObj =new JSONObject();
				List<JSONObject> ccGroupsList =new ArrayList<JSONObject>();
				for (Map.Entry<String,String> entry : ccUserLists.entrySet()) {
					JSONObject userObj =new JSONObject();
					userObj.put("label", entry.getValue()+"("+entry.getKey()+")");
					userObj.put("value", entry.getValue()+"("+entry.getKey()+")");
					ccUsers.add(entry.getValue()+"("+entry.getKey()+")");
					ccGroupsList.add(userObj);
				}
				ccUserDictObj.put("label", nextObj.getString("nodeStatus")); //thirdGeneralFlowNodeConfigEntity.getNodeName());
				ccUserDictObj.put("groups",ccGroupsList);
				ccUserDictList.add(ccUserDictObj);
			}
			optUserListObj.put("optUserDict",optUserDictList);
			optUserListObj.put("ccUserDict",ccUserDictList);
			optUserListObj.put("nextNodeStatus",nextObj.getString("nodeStatus"));
			if(nextObj.getString("flowTransferMode").equals("0")) {
				optUserListObj.put("flowTransferMode",true);
			}
			else {
				optUserListObj.put("flowTransferMode",false);

			}
			optUserListObj.put("optUser",optUsers);
			optUserListObj.put("ccUser",ccUsers);

			optUserList.add(optUserListObj);
		}
		return optUserList;
	}
	/**
	 * 通过节点配置ID获取配置信息
	 */
	public GeneralFlowNodeConfigEntity getNodeConfigByNodeId(String tenantId,String nodeId) {
		QueryWrapper<GeneralFlowNodeConfigEntity> nodeQueryWrapper = new QueryWrapper<GeneralFlowNodeConfigEntity>();
		nodeQueryWrapper.lambda().eq(GeneralFlowNodeConfigEntity::getTenantId, tenantId);
		nodeQueryWrapper.lambda().eq(GeneralFlowNodeConfigEntity::getId, nodeId);
		GeneralFlowNodeConfigEntity generalFlowNodeConfigEntity = generalFlowNodeConfigMapper.selectOne(nodeQueryWrapper);
		return generalFlowNodeConfigEntity;
	}
	/**
	 * 获取节点处理用户Map<userName,realName>
	 */
	public Map<String,String> getAllUserList(JSONArray userJsonArray,String tenantId,String launchUserName, String currentNodeUserName){
		Map<String,String> userListMap = new HashMap<String, String>();
		//如果为空直接返回
		if(userJsonArray == null) {
			return userListMap;
		}
		List<String> roleNameList = new ArrayList<String>();
		for (Iterator<Object> iterator = userJsonArray.iterator(); iterator.hasNext(); ) {
			JSONObject nextObj = (JSONObject) iterator.next();
			if(nextObj.getString("operatorType").equals("user")) {
				JSONArray userArray = JSONObject.parseArray(nextObj.getString("optValues"));
				for (Iterator<Object> userIterator = userArray.iterator(); userIterator.hasNext(); ) {
					String[] user =  FormatUserName.getUser(userIterator.next().toString());
					userListMap.put(user[1].trim(),user[0]);
				}
			}
			else if(nextObj.getString("operatorType").equals("role")){
				JSONArray roleArray = JSONObject.parseArray(nextObj.getString("optValues"));
				for (Iterator<Object> roleIterator = roleArray.iterator(); roleIterator.hasNext(); ) {
					String[] role =  FormatUserName.getRole(roleIterator.next().toString());
					roleNameList.add(role[1]);
				}
			}

			//审批树
			else if(nextObj.getString("operatorType").equals("tree") && !StringUtil.isEmpty(currentNodeUserName)){
				userListMap = getTreeUserList(currentNodeUserName);
			}
		}
		if(roleNameList.size() > 0) {
			// 根据角色获取用户名userName
			List<GeneralFlowProcessUserEntity> userList = generalFlowProcessRecordMapper.queryUserNameByRoleList(roleNameList, tenantId);
			for (int i = 0; i < userList.size(); i++) {
				userListMap.put(userList.get(i).getUserName().trim(), userList.get(i).getRealName());
			}
		}
		log.debug("====user list==== "+userListMap.toString());
		return userListMap;
	}

	/**
	 * 审批树获取下个处理节点用户 Map<userName,realName>
	 */
	public Map<String,String> getTreeUserList(String handlerUserName){
		Map<String,String> userListMap = new HashMap<String, String>();
		//获取当前审批树下所有审批节点的编号。
		GeneralOrgInfoEntity generalOrgInfo = new GeneralOrgInfoEntity();
		generalOrgInfo.setTenantId(generalFlowInfoEntity.getTenantId());
		generalOrgInfo.setOrgNo(generalFlowInfoEntity.getApproveTreeNo());
		List<String> tmpList = new ArrayList<>();
		getAllChildrenOrgNo(generalOrgInfo, tmpList);
		//查询当前用户所在的层级机构号 的上级机构号
		log.info("==>> tmpList="+tmpList.toString());
		List<String> orgNoList = generalOrgInfoMapper.queryCurrentOrgNo(tmpList, generalFlowInfoEntity.getTenantId(), handlerUserName);

		//获取当前节点下的所有人
		QueryWrapper<GeneralOrgStaffRelationEntity> orgStaffQueryWrapper = new QueryWrapper<GeneralOrgStaffRelationEntity>();
		orgStaffQueryWrapper.lambda().eq(GeneralOrgStaffRelationEntity::getTenantId, generalFlowInfoEntity.getTenantId());
		orgStaffQueryWrapper.lambda().eq(GeneralOrgStaffRelationEntity::getBelongOrgNo, orgNoList.get(0));
		List<GeneralOrgStaffRelationEntity> generalOrgStaffRelationEntityList = generalOrgStaffRelationMapper.selectList(orgStaffQueryWrapper);
		for(GeneralOrgStaffRelationEntity generalOrgStaffRelationEntity: generalOrgStaffRelationEntityList){
			userListMap.put(generalOrgStaffRelationEntity.getStaffName().trim(), generalOrgStaffRelationEntity.getRealName());
		}
		log.info("==>> orgNoList="+orgNoList.toString());
		return userListMap;
	}

	//流程暂停处理
	//子流程完成后自动往下走

	/**
	 * 获取流程节点数据（用于绘制流程图）
	 */
	@Override
	public CommonMsg<GeneralFlowInfoEntity, NullEntity> getFlowChartData(CommonMsg<GeneralFlowInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}

		Map<String, String> map = new HashMap<String, String>(2);
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		commonMsg.getBody().setListBody(nullEntityList);
		String tenantId = "";
		//新流程开始根据流程ID获取流程及节点信息
		GeneralFlowInfoEntity  generalFlowInfoEntity = commonMsg.getBody().getSingleBody();
		generalFlowInfoEntity = generalFlowInfoMapper.selectById(generalFlowInfoEntity.getId());
		if (generalFlowInfoEntity == null) {
			// 设置失败返回信息
			map.put("E8001", RespStatus.json.getString("E8001"));
			// 设置失败返回值
			commonMsg.getHead().setRespCode(RespStatus.errorCode);
			commonMsg.getHead().setRespMsg(map);
			log.error("===error: ", map);
			return commonMsg;
		}
		tenantId = generalFlowInfoEntity.getTenantId();
		// 获取流程节点信息
		QueryWrapper<GeneralFlowNodeConfigEntity> nodeQueryWrapper = new QueryWrapper<GeneralFlowNodeConfigEntity>();
		nodeQueryWrapper.lambda().eq(GeneralFlowNodeConfigEntity::getTenantId, generalFlowInfoEntity.getTenantId());
		nodeQueryWrapper.lambda().eq(GeneralFlowNodeConfigEntity::getFlowId, generalFlowInfoEntity.getId());
		nodeQueryWrapper.orderByAsc("order_seq");
		List<GeneralFlowNodeConfigEntity> generalFlowNodeConfigEntityList = generalFlowNodeConfigMapper.selectList(nodeQueryWrapper);
		BizResponseEnum.FLOW_NODE_DATA_NOT_EXIST.assertNotEmpty(generalFlowNodeConfigEntityList,commonMsg);

		// 循环处理每个节点的信息
		JSONObject flowExtInfo = new  JSONObject();
		List<JSONObject> nodeList = new ArrayList<JSONObject>();
		List<JSONObject> edgesList = new ArrayList<JSONObject>();
		for(int i = 0; i < generalFlowNodeConfigEntityList.size(); i++) {
			//获取当前节点的处理人名单
			JSONArray userJsonArray = JSONObject.parseArray(generalFlowNodeConfigEntityList.get(i).getOptUserList());
			Map<String,String> userLists = getAllUserList(userJsonArray,tenantId,null,"");

			List<JSONObject> joinUserList = new ArrayList<JSONObject>();
			//组装当前节点处理人
			for (Map.Entry<String,String> entry : userLists.entrySet()) {
				if(!joinUserList.contains(entry.getValue()+"("+entry.getKey()+")")) {
					JSONObject user = new  JSONObject();
					user.put("label", "处理人");
					user.put("value", entry.getValue()+"("+entry.getKey()+")");
					joinUserList.add(user);
				}
			}

			//获取当前抄送人员列表
			JSONArray ccUserList = new JSONArray();
			JSONObject ccUserObj = new JSONObject();
			ccUserObj.put("operatorType","user");
			JSONArray ccUserJsonArray = JSONObject.parseArray(generalFlowNodeConfigEntityList.get(i).getCcUserList());
			ccUserObj.put("optValues",ccUserJsonArray);
			ccUserList.add(ccUserObj);

			JSONObject ccRoleObj = new JSONObject();
			ccRoleObj.put("operatorType","role");
			JSONArray ccRoleJsonArray = JSONObject.parseArray(generalFlowNodeConfigEntityList.get(i).getCcRoleName());
			ccRoleObj.put("optValues",ccRoleJsonArray);
			ccUserList.add(ccRoleObj);
			Map<String,String> ccUserLists = getAllUserList(ccUserList,tenantId,null,"");

			//组装当前节点抄送人
			for (Map.Entry<String,String> entry : ccUserLists.entrySet()) {
				if(!joinUserList.contains(entry.getValue()+"("+entry.getKey()+")")) {
					JSONObject user = new  JSONObject();
					user.put("label", "抄送");
					user.put("value", entry.getValue()+"("+entry.getKey()+")");
					joinUserList.add(user);
				}
			}

			JSONObject nodes = new  JSONObject();
			nodes.put("id", generalFlowNodeConfigEntityList.get(i).getId());
			nodes.put("dataType","alps");
			nodes.put("name", generalFlowNodeConfigEntityList.get(i).getNodeName());
			nodes.put("conf", joinUserList);
			nodes.put("isSubFlow", generalFlowNodeConfigEntityList.get(i).getIsSubFlowFlag());
			nodes.put("isBegin", i == 0? true : false);
			nodes.put("isEnd", i == (generalFlowNodeConfigEntityList.size() - 1) ? true: false);
			// 如果是子流程，同时返回子流程的id
			if("1".equals(generalFlowNodeConfigEntityList.get(i).getIsSubFlowFlag()) && StringUtils.isNotBlank(generalFlowNodeConfigEntityList.get(i).getSubFlowId())){
				nodes.put("subFlowId", generalFlowNodeConfigEntityList.get(i).getSubFlowId());
			}
			nodeList.add(nodes);

			//可流转状态列表
			JSONArray statusArray = JSONObject.parseArray(generalFlowNodeConfigEntityList.get(i).getExecStatusList());
			for (Iterator<Object> iterator = statusArray.iterator(); iterator.hasNext(); ) {
				JSONObject nextObj = (JSONObject) iterator.next();
				//获取所有下个节点
				List<String> nextNodes = new ArrayList<String>();
				JSONArray nextProccessIdArray = JSONObject.parseArray(nextObj.getString("nextProccessId"));
				for (Iterator<Object> nextProcessIterator = nextProccessIdArray.iterator(); nextProcessIterator.hasNext(); ) {
					JSONObject nextNodeDict = new  JSONObject();
					String nextNodeName = nextProcessIterator.next().toString();
					JSONObject edges = new  JSONObject();
					edges.put("source",generalFlowNodeConfigEntityList.get(i).getId());
					edges.put("target",nextNodeName);
					String condition = nextObj.getString("gotoCondition");
					if(!StringUtil.isEmpty(condition)) {
						condition = "\n条件：" + condition;
					}
					edges.put("label",nextObj.get("nodeStatus")+condition);
					edgesList.add(edges);
				}
			}
		}
		flowExtInfo.put("nodes", nodeList);
		flowExtInfo.put("edges", edgesList);
		log.info("flowExtInfo==="+flowExtInfo.toString());
		commonMsg.getBody().getFlowArea().setFlowExtData(flowExtInfo);
		//设置返回码
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	/**
	 * 获取业务流程实例实时处理信息数据（用于绘制流程图）
	 */
	@Override
	public CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> getProcessFlowChartData(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}

		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		commonMsg.getBody().setListBody(nullEntityList);
		String tenantId = "";
		//根据处理记录ID获取当前处理记录
		GeneralFlowProcessRecordEntity  generalFlowProcessRecordEntity = null;
		if(StringUtils.isNotBlank(commonMsg.getBody().getSingleBody().getId())){
			generalFlowProcessRecordEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
			BizResponseEnum.FLOW_NODE_PROCESS_DATA_NOT_EXIST.assertNotNull(generalFlowProcessRecordEntity,commonMsg);
		}else {
			generalFlowProcessRecordEntity = commonMsg.getBody().getSingleBody();
		}

		tenantId = generalFlowProcessRecordEntity.getTenantId();
		// 获取流程节点配置信息
		QueryWrapper<GeneralFlowNodeConfigEntity> nodeQueryWrapper = new QueryWrapper<GeneralFlowNodeConfigEntity>();
		nodeQueryWrapper.lambda().eq(GeneralFlowNodeConfigEntity::getTenantId, tenantId);
		nodeQueryWrapper.lambda().eq(GeneralFlowNodeConfigEntity::getFlowId, generalFlowProcessRecordEntity.getFlowId());
		nodeQueryWrapper.orderByAsc("order_seq");
		List<GeneralFlowNodeConfigEntity> generalFlowNodeConfigEntityList = generalFlowNodeConfigMapper.selectList(nodeQueryWrapper);
		BizResponseEnum.FLOW_NODE_DATA_NOT_EXIST.assertNotEmpty(generalFlowNodeConfigEntityList,commonMsg);

		// 循环处理每个节点的信息
		JSONObject flowExtInfo = new  JSONObject();
		List<JSONObject> nodeList = new ArrayList<JSONObject>();
		List<JSONObject> edgesList = new ArrayList<JSONObject>();
		List<String> nodeMapList = new ArrayList<String>();
		for(int i = 0; i < generalFlowNodeConfigEntityList.size(); i++) {
			//获取当前节点的处理人名单
			JSONArray userJsonArray = JSONObject.parseArray(generalFlowNodeConfigEntityList.get(i).getOptUserList());
			Map<String,String> userLists = getAllUserList(userJsonArray,tenantId, null,"");

			List<JSONObject> joinUserList = new ArrayList<JSONObject>();
			JSONObject processObject = getProcessResult(generalFlowProcessRecordEntity, generalFlowNodeConfigEntityList.get(i));

			String processResult = processObject.getString("status");
			//如果已处理 将处理人放入处理人列表
			if(processResult.equals("1") || processResult.equals("2")) {
				joinUserList = (List) processObject.getJSONArray("userList");
			}
			else {
				//组装当前节点处理人
				for (Map.Entry<String,String> entry : userLists.entrySet()) {
					if(!joinUserList.contains(entry.getValue()+"("+entry.getKey()+")")) {
						JSONObject user = new  JSONObject();
						user.put("label", "处理人");
						user.put("value", entry.getValue()+"("+entry.getKey()+")");
						joinUserList.add(user);
					}
				}
			}
			JSONObject nodes = new  JSONObject();
			nodes.put("id", generalFlowNodeConfigEntityList.get(i).getId());
			nodes.put("dataType","alps");
			nodes.put("name", generalFlowNodeConfigEntityList.get(i).getNodeName());
			nodes.put("conf", joinUserList);
			nodes.put("isSubFlow", generalFlowNodeConfigEntityList.get(i).getIsSubFlowFlag());
			nodes.put("isBegin", i == 0? true : false);
			nodes.put("isEnd", i == (generalFlowNodeConfigEntityList.size() - 1) ? true: false);
			// 如果是子流程，同时返回子流程的id
			if("1".equals(generalFlowNodeConfigEntityList.get(i).getIsSubFlowFlag()) && StringUtils.isNotBlank(generalFlowNodeConfigEntityList.get(i).getSubFlowId())){
				nodes.put("subFlowId", generalFlowNodeConfigEntityList.get(i).getSubFlowId());
			}

			//可流转状态列表(下节点)
			JSONArray statusArray = JSONObject.parseArray(generalFlowNodeConfigEntityList.get(i).getExecStatusList());
			for (Iterator<Object> iterator = statusArray.iterator(); iterator.hasNext(); ) {
				JSONObject nextObj = (JSONObject) iterator.next();
				//获取所有下个节点
				List<String> nextNodes = new ArrayList<String>();
				JSONArray nextProccessIdArray = JSONObject.parseArray(nextObj.getString("nextProccessId"));
				for (Iterator<Object> nextProcessIterator = nextProccessIdArray.iterator(); nextProcessIterator.hasNext(); ) {
					JSONObject nextNodeDict = new  JSONObject();
					String nextNodeId = nextProcessIterator.next().toString();
					JSONObject edges = new  JSONObject();
					edges.put("source",generalFlowNodeConfigEntityList.get(i).getId());
					edges.put("target",nextNodeId);
					String condition = nextObj.getString("gotoCondition");
					if(!StringUtil.isEmpty(condition)) {
						condition = "\n条件：" + condition;
					}
					edges.put("label",nextObj.get("nodeStatus")+condition);
					edgesList.add(edges);
					nodeMapList.add(generalFlowNodeConfigEntityList.get(i).getId()+"#"+processResult+"#"+nextNodeId);
				}
			}
			//处理结束节点
			//如果没有子节点，便是结束节点
			boolean isEndFlag = false;
			if(statusArray.size() == 0) {
				isEndFlag = true;
				for(int j = 0; j<nodeMapList.size(); j++) {
					String[] arrayStr = nodeMapList.get(j).split("#");
					if(arrayStr[2].equals(generalFlowNodeConfigEntityList.get(i).getId())) {
						if(!arrayStr[1].equals("2")) {
							isEndFlag = false;
						}
					}
				}
			}
			//processResult = 0-未处理 1-处理中 2-处理完成 3-处理超时TODO
			nodes.put("processResult", processResult);
			if(isEndFlag) {
				nodes.put("processResult", "2");
			}
			nodeList.add(nodes);
		}
		flowExtInfo.put("nodes", nodeList);
		flowExtInfo.put("edges", edgesList);
		log.info("flowExtInfo==="+flowExtInfo.toString());
		commonMsg.getBody().getFlowArea().setFlowExtData(flowExtInfo);
		// 返回成功
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	/**
	 * 获取每个节点的流程实例处理结果
	 */
	public JSONObject getProcessResult(GeneralFlowProcessRecordEntity generalFlowProcessRecordEntity, GeneralFlowNodeConfigEntity generalFlowNodeConfigEntity) {
		String nodeId = generalFlowNodeConfigEntity.getId();

		JSONObject resultJson = new JSONObject();
		List<JSONObject> handleUser = new ArrayList<>();

		QueryWrapper<GeneralFlowProcessRecordEntity> recordQueryWrapper = new QueryWrapper<GeneralFlowProcessRecordEntity>();
		recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getTenantId, generalFlowProcessRecordEntity.getTenantId());
		recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getBizId, generalFlowProcessRecordEntity.getBizId());
		// 1、如果是子流程(isSubFlowFlag == "1")，则查询出子流程中的处理节点信息，判断出主流程上子流程节点的处理结果
		// 2、如果不是子流程，则直接查询处理节点信息，判断处理结果
		if("1".equals(generalFlowNodeConfigEntity.getIsSubFlowFlag())){
			recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getFlowId, generalFlowNodeConfigEntity.getSubFlowId());
		}else {
			recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getNodeId, nodeId);
		}
		recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getValidFlag, "1");
		List<GeneralFlowProcessRecordEntity> generalFlowProcessRecordEntityList = generalFlowProcessRecordMapper.selectList(recordQueryWrapper);
		//判断处理结果  人工撤回不用考虑（validFlag = 0了）
		int processCnt=0;

		for(int i = 0; i < generalFlowProcessRecordEntityList.size(); i++ ) {
			if(!StringUtil.isEmpty(generalFlowProcessRecordEntityList.get(i).getProcessStatus().trim())) {
				processCnt++;
			}
			JSONObject user = new  JSONObject();
			user.put("label", "处理人");
			user.put("value", generalFlowProcessRecordEntityList.get(i).getProcessRealName()+"("+generalFlowProcessRecordEntityList.get(i).getProcessUserName()+")");
			handleUser.add(user);
		}
		log.debug(">>>当前节点"+generalFlowProcessRecordEntity.getNodeName()+"；总记录条数："+generalFlowProcessRecordEntityList.size()+",处理记录条数："+processCnt);
		//0-未处理 1-处理中 2-处理完成 3-处理超时TODO
		String returnStatus = "";
		if(generalFlowProcessRecordEntityList.size() != 0 && generalFlowProcessRecordEntityList.size() == processCnt) {
			returnStatus = "2";
		}
		else if(generalFlowProcessRecordEntityList.size() > 0){
			returnStatus = "1";
		}
		else {
			returnStatus = "0";
		}
		resultJson.put("status",returnStatus);
		resultJson.put("userList",handleUser);
		return resultJson;
	}

	/**
	 * 获取流程实例的处理时间轴
	 */
	@Override
	public CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> getProcessFlowTimelineData(CommonMsg<GeneralFlowProcessRecordEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}

		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		commonMsg.getBody().setListBody(nullEntityList);
		String tenantId = "";
		//根据处理记录ID获取当前处理记录
		GeneralFlowProcessRecordEntity generalFlowProcessRecordEntity = null;
		if(StringUtils.isNotBlank(commonMsg.getBody().getSingleBody().getId())){
			generalFlowProcessRecordEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
			BizResponseEnum.FLOW_NODE_PROCESS_DATA_NOT_EXIST.assertNotNull(generalFlowProcessRecordEntity,commonMsg);
		}else {
			generalFlowProcessRecordEntity = commonMsg.getBody().getSingleBody();
		}

		tenantId = generalFlowProcessRecordEntity.getTenantId();
		// 获取流程节点信息
		QueryWrapper<GeneralFlowProcessRecordEntity> allNodeQueryWrapper = new QueryWrapper<GeneralFlowProcessRecordEntity>();
		allNodeQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getTenantId, tenantId);
		allNodeQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getBizId, generalFlowProcessRecordEntity.getBizId());
		allNodeQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getValidFlag, "1");
		allNodeQueryWrapper.groupBy("process_begin_datetime","node_id");
		allNodeQueryWrapper.orderByAsc("process_begin_datetime","id");
		List<GeneralFlowProcessRecordEntity> generalFlowProcessRecordEntityListByGroup = generalFlowProcessRecordMapper.selectList(allNodeQueryWrapper);
		BizResponseEnum.FLOW_NODE_PROCESS_DATA_NOT_EXIST.assertNotEmpty(generalFlowProcessRecordEntityListByGroup,commonMsg);

		// 循环处理每个节点的信息
		JSONObject flowExtInfo = new  JSONObject();
		List<JSONObject> nodeList = new ArrayList<JSONObject>();
		//处理完成的条数
		int handleCnt = 0;
		for(int i = 0; i < generalFlowProcessRecordEntityListByGroup.size(); i++) {
			inHandle = false;
			List<JSONObject> processList = getProcessDetail(generalFlowProcessRecordEntityListByGroup.get(i));
			//有处理记录才显示
			if(processList.size() > 0) {
				JSONObject nodes = new  JSONObject();
				nodes.put("nodeId", generalFlowProcessRecordEntityListByGroup.get(i).getNodeId());
				nodes.put("nodeName",generalFlowProcessRecordEntityListByGroup.get(i).getNodeName());
				nodes.put("processList", processList);
				nodeList.add(nodes);
				if(!inHandle) {
					handleCnt++;
				}
			}
		}
		flowExtInfo.put("handleCnt", handleCnt);
		flowExtInfo.put("nodes", nodeList);
		commonMsg.getBody().getFlowArea().setFlowExtData(flowExtInfo);
		// 返回成功
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	/**
	 * 获取每个节点的流程实例处理明细-时间轴
	 */
	public List<JSONObject> getProcessDetail(GeneralFlowProcessRecordEntity generalFlowProcessRecordEntity) {
		QueryWrapper<GeneralFlowProcessRecordEntity> recordQueryWrapper = new QueryWrapper<GeneralFlowProcessRecordEntity>();
		recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getTenantId, generalFlowProcessRecordEntity.getTenantId());
		recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getBizId, generalFlowProcessRecordEntity.getBizId());
		recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getNodeId, generalFlowProcessRecordEntity.getNodeId());
		recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getProcessBeginDatetime, generalFlowProcessRecordEntity.getProcessBeginDatetime());
		recordQueryWrapper.lambda().eq(GeneralFlowProcessRecordEntity::getValidFlag, "1");
		recordQueryWrapper.orderByAsc("process_begin_datetime");
		List<GeneralFlowProcessRecordEntity> generalFlowProcessRecordEntityList = generalFlowProcessRecordMapper.selectList(recordQueryWrapper);

		//判断处理结果  不用考虑人工撤回（validFlag = 0了）
		int processCnt=0;
		List<JSONObject> processList = new ArrayList<JSONObject>();
		for(int i = 0; i < generalFlowProcessRecordEntityList.size(); i++ ) {
			//获取当前时间
			String  nowDate = DateUtils.getLongDateStr();
			JSONObject processObj = new  JSONObject();
			String opinion = generalFlowProcessRecordEntityList.get(i).getProcessOpinion();
			if(StringUtil.isEmpty(opinion)) {
				opinion = generalFlowProcessRecordEntityList.get(i).getProcessResult();
			}
			String finishDate = generalFlowProcessRecordEntityList.get(i).getProcessFinishDatetime();
			if(finishDate.equals(DateUtils.initialDatatime())) {
				finishDate = "";
			}

			String status = generalFlowProcessRecordEntityList.get(i).getProcessStatus().trim();
			if(StringUtil.isEmpty(status)) {
				inHandle = true;
				log.info("+++++++===="+inHandle);
				status = "";
			}
			//处理时间差
			if(StringUtils.isNotEmpty(generalFlowProcessRecordEntityList.get(i).getProcessFinishDatetime()) && !generalFlowProcessRecordEntityList.get(i).getProcessFinishDatetime().equals(DateUtils.initialDatatime())) {
				nowDate = generalFlowProcessRecordEntityList.get(i).getProcessFinishDatetime();
			}
			log.debug(generalFlowProcessRecordEntityList.get(i).getId()+"===begin == "+ generalFlowProcessRecordEntityList.get(i).getProcessBeginDatetime()+ " == now="+ nowDate);
			generalFlowProcessRecordEntityList.get(i).setNodeWaitDuration(DateUtilPro.timeDiff(generalFlowProcessRecordEntityList.get(i).getProcessBeginDatetime(),nowDate));
			processObj.put("processOpinion",opinion);
			processObj.put("processStatus",status);
			processObj.put("nodeWaitDuration",generalFlowProcessRecordEntityList.get(i).getNodeWaitDuration());
			processObj.put("processBeginDateTime",generalFlowProcessRecordEntityList.get(i).getProcessBeginDatetime());
			processObj.put("processFinishDateTime",finishDate);
			processObj.put("processUserName",generalFlowProcessRecordEntityList.get(i).getProcessUserName());
			processObj.put("processRealName",generalFlowProcessRecordEntityList.get(i).getProcessRealName());
			processObj.put("launchUserName",generalFlowProcessRecordEntityList.get(i).getLaunchUserName());
			processObj.put("launchRealName",generalFlowProcessRecordEntityList.get(i).getLaunchRealName());
			processObj.put("launchDateTime",generalFlowProcessRecordEntityList.get(i).getLaunchDatetime());
			if(StringUtil.isJSONArray(generalFlowProcessRecordEntityList.get(i).getAttList())) {
				processObj.put("attList",JSONArray.parse(generalFlowProcessRecordEntityList.get(i).getAttList()));
			}
			else {
				processObj.put("attList",new JSONArray());
			}
			processObj.put("overTimeFlag",'0');
			processList.add(processObj);
		}
		return processList;
	}

	/**
	 * 工作台数据组装
	 */
	public String getWorkbenchMsgData(GeneralFlowProcessRecordEntity generalFlowProcessRecordEntity,GeneralFlowNodeConfigEntity generalFlowNodeConfigEntity){
		if(!StringUtil.isEmpty(generalFlowNodeConfigEntity.getNoticeProcessClassName())) {
			try {
				Class<?> cls = Class.forName(generalFlowNodeConfigEntity.getNoticeProcessClassName());
				Method method = cls.getDeclaredMethod("getWorkbenchMsgData", GeneralFlowProcessRecordEntity.class);
				Object result = method.invoke(SpringUtil.getBean(cls), generalFlowProcessRecordEntity);
				return (String) result;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 发消息
	 * @param joinCcUserList
	 */
	private void sendMsg(String type, GeneralFlowProcessRecordEntity generalFlowProcessRecordEntity, List<GeneralFlowProcessRecordEntity> generalFlowProcessRecordEntitList, List<String> joinCcUserList) {
		//List<String> userList = new ArrayList<>();
		JSONArray userList = new JSONArray();
		SysSendLogEntity sysSendLogEntity = new SysSendLogEntity();
		int index = 0;
		//处理抄送用户
		if (type.equals("08")) {
			for(String ccUser: joinCcUserList){
				String[] user =  FormatUserName.getUser(ccUser);
				userList.add(user[1]);
			}
		}
		for(GeneralFlowProcessRecordEntity generalFlowProcessRecord: generalFlowProcessRecordEntitList) {
			if (!type.equals("08")) {
				userList.add(generalFlowProcessRecord.getProcessUserName());
			}
			if(index == 0) {
				String msgTitleTag = "";
				String opinion = "";
				// 写消息 01-委托，02-转派 ，03-自动回退到上节点，04-已被回退，05-子流程结束,06-流转到下个节点 ，07-流程被撤回
				if (type.equals("01")) {
					if(generalFlowInfoEntity.getFlowType().equals("2")){
						msgTitleTag = "工作委托给您";
					}
					else if(generalFlowInfoEntity.getFlowType().equals("1")){
						msgTitleTag = "审批委托给您";
					}
					opinion = generalFlowProcessRecord.getPrevNodeOpinion();
					// XXX委托您处理《新项目建设需求》，请登录系统处理！
				} else if (type.equals("02")) {
					if(generalFlowInfoEntity.getFlowType().equals("2")){
						msgTitleTag = "工作转派给您";
					}
					else if(generalFlowInfoEntity.getFlowType().equals("1")){
						msgTitleTag = "审批转派给您";
					}
					opinion = generalFlowProcessRecord.getPrevNodeOpinion();
					// XXX转派您处理《新项目建设需求》，请登录系统处理！
				} else if (type.equals("03")) {
					if(generalFlowInfoEntity.getFlowType().equals("2")){
						msgTitleTag = "工作退回给您";
					}
					else if(generalFlowInfoEntity.getFlowType().equals("1")){
						msgTitleTag = "审批退回给您";
					}
					opinion = generalFlowProcessRecord.getPrevNodeOpinion();
					// 《新项目建设需求》会签不通过，已回退到您处，请登录系统处理！
				} else if (type.equals("04")) {
					if(generalFlowInfoEntity.getFlowType().equals("2")){
						msgTitleTag = "工作会签不通过";
					}
					else if(generalFlowInfoEntity.getFlowType().equals("1")){
						msgTitleTag = "审批会签不通过";
					}
					opinion = generalFlowProcessRecord.getPrevNodeOpinion();
					// 《新项目建设需求》会签不通过，已自动回退到XXX，请悉知！
				} else if (type.equals("05")) {
					if(generalFlowInfoEntity.getFlowType().equals("2")){
						msgTitleTag = "工作已完成";
					}
					else if(generalFlowInfoEntity.getFlowType().equals("1")){
						msgTitleTag = "审批已完成";
					}
					opinion = generalFlowProcessRecord.getPrevNodeOpinion();
					// 你发起的《新项目建设需求》流程处理已结束，请您继续处理！
				} else if (type.equals("06")) {
					if(generalFlowInfoEntity.getFlowType().equals("2")){
						msgTitleTag = "工作需要您处理";
					}
					else if(generalFlowInfoEntity.getFlowType().equals("1")){
						msgTitleTag = "审批需要您处理";
					}
					opinion = generalFlowProcessRecord.getPrevNodeOpinion();
					// 您有《新项目建设需求》任务需要处理，请登录系统处理！
				} else if (type.equals("07")) {
					if(generalFlowInfoEntity.getFlowType().equals("2")){
						msgTitleTag = "工作已被撤回";
					}
					else if(generalFlowInfoEntity.getFlowType().equals("1")){
						msgTitleTag = "审批已被撤回";
					}
					opinion = generalFlowProcessRecord.getProcessResult();
					// 任务 《新项目建设需求》已被撤回，请悉知！
				} else if (type.equals("08")) {
					if(generalFlowInfoEntity.getFlowType().equals("2")){
						msgTitleTag = "工作已抄送给您";
					}
					else if(generalFlowInfoEntity.getFlowType().equals("1")){
						msgTitleTag = "审批已抄送给您";
					}
					opinion = generalFlowProcessRecord.getPrevNodeOpinion();
					// 任务 《新项目建设需求》已抄送给您，请查看详情！
				}

				String msgTitle = "有一条"+generalFlowInfoEntity.getBizType()+ msgTitleTag+"，请立即登录系统查看>>";
				sysSendLogEntity.setResourceId(generalFlowProcessRecord.getBizId());
				sysSendLogEntity.setMsgTitle(msgTitle);
				sysSendLogEntity.setMsgContent(msgTitle);
				sysSendLogEntity.setMsgContentJson("{ \"first\": \""+msgTitle+"\", \"label1\":\"事项标题\", \"keyword1\": \""+generalFlowProcessRecord.getBizName()+"\", \"label2\":\"处 理 人\", \"keyword2\": \""+handlerRealName+"("+handlerUserName+")\", \"label3\":\"处理时间\", \"keyword3\": \""+generalFlowProcessRecord.getProcessBeginDatetime()+"\", \"label4\":\"处理意见\", \"keyword4\": \""+opinion+"\"}");
			}
			index++;
		}
		sysSendLogEntity.setTenantId(generalFlowInfoEntity.getTenantId());
		sysSendLogEntity.setSenderRealName(handlerRealName);
		sysSendLogEntity.setSenderId(handlerUserName);
		sysSendLogEntity.setHeadImgUrl(handlerHeadImgUrl);
		sysSendLogEntity.setSendAddressList(userList.toJSONString());//("[\"admin\",\"liqh\"]");  // all 表示发送全部
		sysSendLogEntity.setBizSceneName(generalFlowInfoEntity.getBizType());
		msgSendUtil.sendMsgToMq(sysSendLogEntity);
	}

	/**
	 * 递归获取所有节点的机构号
	 *
	 * @Author : apple
	 * @CreateTime : 2020年6月5日 上午10:57:03
	 * @Param : generalOrgInfo
	 * @Return : List<JSONObject>
	 */
	public List<String> getAllChildrenOrgNo(GeneralOrgInfoEntity generalOrgInfo, List<String> tmpList){
		List<GeneralOrgInfoEntity> children = generalOrgInfoMapper.queryByParentOrgNo(generalOrgInfo);
		for (GeneralOrgInfoEntity generalOrgInfoEntity : children) {
			tmpList.add(generalOrgInfoEntity.getOrgNo());
			getAllChildrenOrgNo(generalOrgInfoEntity,tmpList);
		}
		return tmpList;
	}
}
