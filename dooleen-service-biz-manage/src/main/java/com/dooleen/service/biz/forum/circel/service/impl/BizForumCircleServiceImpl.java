package com.dooleen.service.biz.forum.circel.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.extra.spring.SpringUtil;
import com.dooleen.service.biz.forum.posts.entity.BizForumPostsEntity;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.dooleen.common.core.app.general.common.service.impl.GetBizParamsService;
import com.dooleen.service.biz.forum.circel.entity.BizForumCircleEntity;
import com.dooleen.service.biz.forum.circel.mapper.BizForumCircleMapper;
import com.dooleen.service.biz.forum.circel.service.BizForumCircleService;
import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowExtInfoEntity;
import com.dooleen.common.core.app.general.flow.util.GeneralFlowProcessUtil;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowProcessRecordMapper;
import com.dooleen.common.core.app.general.flow.service.GeneralFlowProcessService;

import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.common.entity.HeadEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SQLConditionEntity;
import com.dooleen.common.core.common.entity.SQLOrderEntity;
import com.dooleen.common.core.common.entity.ValidationResult;
import com.dooleen.common.core.common.entity.MutBean;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.mq.utils.MsgSendUtil;
import com.dooleen.common.core.utils.ConstantValue;
import com.dooleen.common.core.utils.ExcelUtil;
import com.dooleen.common.core.utils.BeanUtils;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.QueryWrapperUtil;
import com.dooleen.common.core.utils.RespStatus;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.ValidationUtils;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.common.core.utils.CommonUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import static java.net.URLDecoder.decode;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-07-21 10:19:44
 * @Description : 圈子管理服务实现
 * @Author : apple
 * @Update: 2021-07-21 10:19:44
 */
 /**
  * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
  */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BizForumCircleServiceImpl extends ServiceImpl<BizForumCircleMapper, BizForumCircleEntity> implements BizForumCircleService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
    
    @Autowired
    public GeneralFlowProcessService  generalFlowProcessService;
    
	@Autowired
	private  GeneralFlowProcessRecordMapper generalFlowProcessRecordMapper;
	
	@Autowired
	private  BizForumCircleMapper bizForumCircleMapper;
	
	@Autowired
	private  GetBizParamsService getBizParamsService;

	@Autowired
	private GeneralFlowProcessUtil generalFlowProcessUtil;
	
	/**
	 * 例子 general_note_boook
	 * 请将此段copy到 com.dooleen.common.core.enums TableEntityORMEnum  中
	 * AAAA为ID的键值关键字 如： DOOL1001AAAA10000001
	 */
	//BIZ_FORUM_CIRCLE("biz_forum_circle", "BizForumCircle", "AAAA"),


	private  static String seqPrefix= TableEntityORMEnum.BIZ_FORUM_CIRCLE.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.BIZ_FORUM_CIRCLE.getEntityName();

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Autowired
	private MsgSendUtil msgSendUtil;

	@Override
	@Cacheable(value = "bizForumCircle", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<BizForumCircleEntity, NullEntity> queryBizForumCircle(CommonMsg<BizForumCircleEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		BizForumCircleEntity bizForumCircleEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(bizForumCircleEntity,commonMsg);

		//获取流程相关数据
		generalFlowProcessUtil.queryExtData(commonMsg);

		commonMsg.getBody().setSingleBody(bizForumCircleEntity);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, BizForumCircleEntity> queryBizForumCircleList(CommonMsg<NullEntity, BizForumCircleEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<BizForumCircleEntity> bizForumCircleEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(bizForumCircleEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<BizForumCircleEntity, BizForumCircleEntity> queryBizForumCircleListPage(
			CommonMsg<BizForumCircleEntity, BizForumCircleEntity> commonMsg) {
		CommonUtil.service(commonMsg); 
		// 默认以传入的sqlCondition查询
		List<SQLConditionEntity> sqlConditionList = new ArrayList<SQLConditionEntity>();
		// 设定只查当前租户ID条件
		SQLConditionEntity sqlConditionEntity = new SQLConditionEntity();
		sqlConditionEntity.setColumn("tenantId");
		sqlConditionEntity.setType("=");
		if(StringUtil.isEmpty(commonMsg.getBody().getSingleBody().getTenantId())) {
			sqlConditionEntity.setValue(commonMsg.getHead().getTenantId());
		}
		else
		{
			sqlConditionEntity.setValue(commonMsg.getBody().getSingleBody().getTenantId());
		}
		sqlConditionList.add(sqlConditionEntity);
		// 如果singlebody不为空解析Single body 组装查询条件
		Map<String, Object> singleBodyMap = BeanUtils.beanToMap(commonMsg.getBody().getSingleBody());
		boolean isInSingleBody = false;
		for (Map.Entry<String, Object> entry : singleBodyMap.entrySet()) {
			if(entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString()))
			{
				if (!(StringUtil.isNumeric(entry.getValue().toString()) && (entry.getValue().equals("0")  || entry.getValue().equals("0.0") || entry.getValue().equals(0) || entry.getValue().equals(0.0))) && !entry.getKey().equals("queryPosts") && !entry.getKey().equals("bizForumPostsEntityList")) {
					log.debug(entry.getKey() + "========解析Single body 组装查询条件==" + String.valueOf(entry.getValue()));
					SQLConditionEntity sqlConditionEntity0 = new SQLConditionEntity();
					sqlConditionEntity0.setColumn(entry.getKey());
					sqlConditionEntity0.setType("=");
					if(entry.getKey().equals("circleName")){
						sqlConditionEntity0.setType("like");
					}
					sqlConditionEntity0.setValue(String.valueOf(entry.getValue()));
					sqlConditionList.add(sqlConditionEntity0);
					isInSingleBody = true;
				}
			}
		}
		// 如果搜索查询为空，并且过滤器查询有值时将过滤器的查询条件赋值给sqlConditionList
		QueryWrapper<BizForumCircleEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizForumCircleEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizForumCircleEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, BizForumCircleEntity.class);
		}
		
		// 处理排序
		List<SQLOrderEntity> sqlOrderList = commonMsg.getBody().getOrderRule();
		for(int i = 0; i < sqlOrderList.size(); i++)
		{
			if(sqlOrderList.get(i).getProp() != null && sqlOrderList.get(i).getOrder() != null)
			{
				if(sqlOrderList.get(i).getOrder().equals("ascending"))
				{
					queryWrapper.orderByAsc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,sqlOrderList.get(i).getProp()));
				}
				else
				{
					queryWrapper.orderByDesc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,sqlOrderList.get(i).getProp()));
				}
			}
		}
		
		// 定义分页查询
		Page<BizForumCircleEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<BizForumCircleEntity> list =  super.page(pages, queryWrapper);

		//移动端 - 判断是否需要获取一条动态
		if("1".equals(commonMsg.getBody().getSingleBody().getQueryPosts())){
			List<String> ids = new ArrayList<String>();
			for(BizForumCircleEntity bizForumCircleEntity : list.getRecords()){
				ids.add(bizForumCircleEntity.getId());
			}
			if(ids.size() == 0){
				ids.add("");
			}
			//查询动态
			List<BizForumPostsEntity>  bizForumPostsEntityList = bizForumCircleMapper.queryBizForumPostsByCircleIds(ids, commonMsg.getHead().getTenantId());

			//将最新的一条动态加入到列表中返回
			for(BizForumCircleEntity bizForumCircleEntity : list.getRecords()){
				for(BizForumPostsEntity bizForumPostsEntity : bizForumPostsEntityList){
					if(bizForumCircleEntity.getBizForumPostsEntityList().size() == 0 && bizForumCircleEntity.id.equals(bizForumPostsEntity.getBeFollowUserName())){
						bizForumCircleEntity.getBizForumPostsEntityList().add(bizForumPostsEntity);
					}
				}
			}
		}
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<BizForumCircleEntity, BizForumCircleEntity> queryBizForumCircleListMap(
			CommonMsg<BizForumCircleEntity, BizForumCircleEntity> commonMsg) {
		CommonUtil.service(commonMsg); 
		// 解析Single body 组装查询条件
		Map<String, Object> conditionMap = new HashMap<String, Object>(16);
		conditionMap.put("tenantId", commonMsg.getHead().getTenantId());
		Map<String, Object> singleBodyMap = BeanUtils.beanToMap(commonMsg.getBody().getSingleBody());
		for (Map.Entry<String, Object> entry : singleBodyMap.entrySet()) {
			if (entry.getValue() != null) {
				// kay是字段名 value是字段值
				conditionMap.put(entry.getKey(), entry.getValue());
			}
		}
		Collection<BizForumCircleEntity> bizForumCircleEntityList =  super.listByMap(conditionMap);
		List<BizForumCircleEntity> bizForumCircleMapList = new ArrayList<BizForumCircleEntity>(bizForumCircleEntityList);
		commonMsg.getBody().setListBody(bizForumCircleMapList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "bizForumCircle", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizForumCircleEntity, NullEntity> saveBizForumCircle(CommonMsg<BizForumCircleEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg); 
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果内容为空且不合法返回错误信息
		if (! CommonUtil.commonMsgValidation(commonMsg)) {
			return commonMsg;
		}
		//自动启动流程
		if(!StringUtil.isEmpty(commonMsg.getBody().getFlowArea().getIsFlow()) && commonMsg.getBody().getFlowArea().getIsFlow().equals("true") && commonMsg.getBody().getFlowArea().getFlowBeginWay().equals("1")) {
			GeneralFlowExtInfoEntity generalFlowExtInfoEntity = new GeneralFlowExtInfoEntity();
			generalFlowExtInfoEntity.setBizType("业务类型");
			generalFlowExtInfoEntity.setFormType("2");
			generalFlowExtInfoEntity.setBizId(commonMsg.getBody().getSingleBody().getId());
			generalFlowExtInfoEntity.setBizName("填入自己的业务名称");
			generalFlowExtInfoEntity.setBizCode("填入自己的业务码"); //后续通过业务码获取字典表里的流程ID
			//generalFlowExtInfoEntity.setFormId(commonMsg.getBody().getSingleBody().get("formId").toString());
			//generalFlowExtInfoEntity.setFlowId(commonMsg.getBody().getFlowArea().getFlowId());
			generalFlowProcessUtil.handleProccess(commonMsg, generalFlowExtInfoEntity);
			//判断流程是否处理成功，失败直接返回
			if (!commonMsg.getHead().getRespCode().equals("S0000")) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return commonMsg;
			}
		}

		commonMsg.getBody().getSingleBody().setDataSign(DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key));
		// 执行保存
		boolean result =  super.save(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, BizForumCircleEntity> saveBizForumCircleList(CommonMsg<NullEntity, BizForumCircleEntity> commonMsg) {
		CommonUtil.service(commonMsg); 
		// 如果listBody为空返回错误信息
		if (! CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		} 
		// 初始化数据
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().get(i).setDataSign(DooleenMD5Util.md5(commonMsg.getBody().getListBody().get(i).toString(), ConstantValue.md5Key));
			commonMsg.getBody().getListBody().set(i, EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getListBody().get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		}
		// 批量保存
		boolean result =  super.saveBatch(commonMsg.getBody().getListBody());
		
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "bizForumCircle", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizForumCircleEntity, NullEntity> updateBizForumCircle(CommonMsg<BizForumCircleEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果ID为空返回错误信息
		if (! CommonUtil.commonMsgValidationId(commonMsg)) {
			return commonMsg;
		}

		// 判断内容是否发生更改
		if (! CommonUtil.commonMsgIsUpdateContent(commonMsg, commonMsg.getBody().getSingleBody().getDataSign())) {
			return commonMsg;
		}
		// 根据Key值查询修改记录，需进行深拷贝！！
		log.debug("====开始调用查询方法 ====== ");
		CommonMsg<BizForumCircleEntity, NullEntity> queryCommonMsg = new CommonMsg<BizForumCircleEntity, NullEntity>();
		//定义body
		MutBean<BizForumCircleEntity, NullEntity> mutBean= new MutBean<BizForumCircleEntity, NullEntity>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		mutBean.setSingleBody(commonMsg.getBody().getSingleBody());
		mutBean.setFlowArea(flowArea);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean);
		queryCommonMsg = SpringUtil.getBean(BizForumCircleServiceImpl.class).queryBizForumCircle(queryCommonMsg);

		//更新圈子人数
		if("add".equals(commonMsg.getBody().getSingleBody().getAddMemberFlag())){
			commonMsg.getBody().getSingleBody().setCircleMemberCount(queryCommonMsg.getBody().getSingleBody().getCircleMemberCount() + 1);
		}
		else if("sub".equals(commonMsg.getBody().getSingleBody().getAddMemberFlag())){
			if(queryCommonMsg.getBody().getSingleBody().getCircleMemberCount() > 0) {
				commonMsg.getBody().getSingleBody().setCircleMemberCount(queryCommonMsg.getBody().getSingleBody().getCircleMemberCount() - 1);
			}
			else{
				commonMsg.getBody().getSingleBody().setCircleMemberCount(0);
			}
		}

		//设置需要修改的签名
		String bodySign = DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key);
		commonMsg.getBody().getSingleBody().setDataSign(bodySign);

		// 保存数据
		boolean result =  super.updateById(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "bizForumCircle", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
	public CommonMsg<BizForumCircleEntity, NullEntity> saveOrUpdateBizForumCircle(CommonMsg<BizForumCircleEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果ID为空返回错误信息
		if (! CommonUtil.commonMsgValidationId(commonMsg)) {
			return commonMsg;
		}
		// 判断内容是否发生更改
		if (!commonMsg.getBody().getSingleBody().getId().equals("0") && !CommonUtil.commonMsgIsUpdateContent(commonMsg, commonMsg.getBody().getSingleBody().getDataSign())) {
			return commonMsg;
		}
		// 根据Key值查询修改记录，需进行深拷贝！！
		log.debug("====开始调用查询方法 ====== ");
		CommonMsg<BizForumCircleEntity, NullEntity> queryCommonMsg = new CommonMsg<BizForumCircleEntity, NullEntity>();
		//定义singleBody
		BizForumCircleEntity singleBody = new BizForumCircleEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(!StringUtil.isEmptyAndZero(commonMsg.getBody().getSingleBody().getId())){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
			//定义body
			MutBean<BizForumCircleEntity, NullEntity> mutBean= new MutBean<BizForumCircleEntity, NullEntity>();
			FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
			mutBean.setSingleBody(singleBody);
			mutBean.setFlowArea(flowArea);
			//深拷贝数据进行AOP查询
			queryCommonMsg.setHead(commonMsg.getHead());
			queryCommonMsg.setBody(mutBean);
			queryCommonMsg = SpringUtil.getBean(BizForumCircleServiceImpl.class).queryBizForumCircle(queryCommonMsg);

			//判断修改内容是否被其他用户修改
			if (!CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
				return commonMsg;
			}
		}
		else {
			singleBody.setId("0");
		}

		
		//设置需要修改的签名
		String bodySign = DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key);
		commonMsg.getBody().getSingleBody().setDataSign(bodySign);
	
		// 保存数据
		boolean result =  super.saveOrUpdate(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(),
				commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		// 保存失败
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	public CommonMsg<NullEntity, BizForumCircleEntity> saveOrUpdateBizForumCircleList(CommonMsg<NullEntity, BizForumCircleEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		}
		 
		// 批量保存
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().get(i).setDataSign(DooleenMD5Util.md5(commonMsg.getBody().getListBody().get(i).toString(),  ConstantValue.md5Key));
			super.saveOrUpdate(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getListBody().get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		}
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
    @CacheEvict(value = "bizForumCircle", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
	public CommonMsg<BizForumCircleEntity, NullEntity> deleteBizForumCircle(CommonMsg<BizForumCircleEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg); 
		
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}

		// 执行删除
		boolean result =  super.removeById(commonMsg.getBody().getSingleBody().getId());
		// 删除失败
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, BizForumCircleEntity> deleteBizForumCircleList(CommonMsg<NullEntity, BizForumCircleEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		Map<String, String> map = new HashMap<String, String>(2);
		
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, DELETE)) {
			return commonMsg;
		}
		
		List<String> keylist = new ArrayList<>();
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			keylist.add(String.valueOf(commonMsg.getBody().getListBody().get(i).getId()));
		}
		// 执行删除
		boolean result =  super.removeByIds(keylist);
		// 删除失败
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	public void exportBizForumCircleExcel(CommonMsg<BizForumCircleEntity, BizForumCircleEntity> commonMsg, HttpServletResponse response) {
		CommonUtil.service(commonMsg); 
		// 默认以传入的sqlCondition查询
		List<SQLConditionEntity> sqlConditionList = new ArrayList<SQLConditionEntity>();
		// 设定只查当前租户ID条件
		SQLConditionEntity sqlConditionEntity = new SQLConditionEntity();
		sqlConditionEntity.setColumn("tenantId");
		sqlConditionEntity.setType("=");
		sqlConditionEntity.setValue(commonMsg.getHead().getTenantId());
		sqlConditionList.add(sqlConditionEntity);
		// 如果singlebody不为空解析Single body 组装查询条件
		Map<String, Object> singleBodyMap = BeanUtils.beanToMap(commonMsg.getBody().getSingleBody());
		boolean isInSingleBody = false;
		for (Map.Entry<String, Object> entry : singleBodyMap.entrySet()) {
			if(entry.getValue() != null)
			{
				if (!(StringUtil.isNumeric(entry.getValue().toString()) && Integer.parseInt(entry.getValue().toString()) == 0 )) {
					log.debug(entry.getKey() + "========解析Single body 组装查询条件==" + String.valueOf(entry.getValue()));
					SQLConditionEntity sqlConditionEntity0 = new SQLConditionEntity();
					sqlConditionEntity0.setColumn(entry.getKey());
					sqlConditionEntity0.setType("=");
					sqlConditionEntity0.setValue(String.valueOf(entry.getValue()));
					sqlConditionList.add(sqlConditionEntity0);
					isInSingleBody = true;
				}
			}
		}		
		// 如果搜索查询为空，并且过滤器查询有值时将过滤器的查询条件赋值给sqlConditionList
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
		}
		
		QueryWrapper<BizForumCircleEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, BizForumCircleEntity.class);
		
		// 处理排序
		List<SQLOrderEntity> sqlOrderList = commonMsg.getBody().getOrderRule();
		for(int i = 0; i < sqlOrderList.size(); i++)
		{
			if(sqlOrderList.get(i).getProp() != null && sqlOrderList.get(i).getOrder() != null)
			{
				if(sqlOrderList.get(i).getOrder().equals("ascending"))
				{
					queryWrapper.orderByAsc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,sqlOrderList.get(i).getProp()));
				}
				else
				{
					queryWrapper.orderByDesc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,sqlOrderList.get(i).getProp()));
				}
			}
		}
		
		// 定义分页查询
		Page<BizForumCircleEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<BizForumCircleEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		//防止导出异常，如果列表为null，初始化一条空记录
		if(list.getRecords().size() == 0){
			List<BizForumCircleEntity> listEntity = new ArrayList<BizForumCircleEntity>();
			listEntity.add(new BizForumCircleEntity());
			commonMsg.getBody().setListBody(listEntity);
		}
		/**
		 * 生成excel并输出
		 */
		Date start = new Date();
		String sheetName = "圈子表";
		String fileName = "圈子表-"+start.getTime();
		ExportParams params = new ExportParams();
		Workbook workbook = ExcelExportUtil.exportExcel(params, BizForumCircleEntity.class, commonMsg.getBody().getListBody());
		//导出Excel数据流
		try {
			ExcelUtil.exportPoi(response,workbook,fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("====exportBizForumCircleExcel service end == " + RespStatus.json.getString("S0000"));
	}
	/**
	* 导入excel新增
	*/
	@Override
	public CommonMsg<BizForumCircleEntity, NullEntity> uploadExcel(MultipartFile file, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		CommonMsg<BizForumCircleEntity,NullEntity> commonMsg = new CommonMsg<BizForumCircleEntity,NullEntity>();
		HeadEntity head =new HeadEntity();
		commonMsg.setHead(head);
		commonMsg.getHead().setTenantId(request.getHeader("tenantid"));
		commonMsg.getHead().setUserName(request.getHeader("username"));
		ImportParams params = new ImportParams();
		params.setTitleRows(0);
		List<BizForumCircleEntity> list = new ArrayList<>();
		try {
			commonMsg.getHead().setRealName(decode(request.getHeader("realname").toString(),"UTF-8"));
			list = ExcelImportUtil.importExcel(file.getInputStream(), BizForumCircleEntity.class, params);
		} catch (Exception e) {
			e.printStackTrace();
	    	map.put("E0002","导入出现异常！");
	    	commonMsg.getHead().setRespCode(RespStatus.errorCode);
	    	commonMsg.getHead().setRespMsg(map);
	    	return commonMsg;
		}
		//批量导入数据并做校验
		List<BizForumCircleEntity> tmpList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			BizForumCircleEntity bizForumCircleEntity = list.get(i);

			//检查字段的有效性
			ValidationResult validationResult = ValidationUtils.validateEntity(bizForumCircleEntity);
			boolean isError = validationResult.isHasErrors();
			// 检查前端传入字段
			if (isError == true) {
				String errStr = "";
				for(String value : validationResult.getErrorMsg().values()){
				  errStr += value+";";
			    }
				map.put("第"+i+"行",errStr);
			}
			else if(map.isEmpty()){
				bizForumCircleEntity.setDataSign(DooleenMD5Util.md5(bizForumCircleEntity.toString(),  ConstantValue.md5Key));
				// 初始化数据
				bizForumCircleEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(bizForumCircleEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate);
				tmpList.add(bizForumCircleEntity);
			}
		}
		if(!map.isEmpty()){
			commonMsg.getHead().setRespCode(RespStatus.errorCode);
			commonMsg.getHead().setRespMsg(map);
			return commonMsg;
		}
		// 保存记录
		boolean result =  super.saveBatch(tmpList);
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
}
