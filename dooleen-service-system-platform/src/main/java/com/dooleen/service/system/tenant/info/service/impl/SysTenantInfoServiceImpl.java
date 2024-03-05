package com.dooleen.service.system.tenant.info.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.MutBean;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SQLConditionEntity;
import com.dooleen.common.core.common.entity.SQLOrderEntity;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.utils.BeanUtils;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.ConstantValue;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.ExcelUtil;
import com.dooleen.common.core.utils.QueryWrapperUtil;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.service.system.tenant.info.entity.SysTenantInfoEntity;
import com.dooleen.service.system.tenant.info.mapper.SysTenantInfoMapper;
import com.dooleen.service.system.tenant.info.service.SysTenantInfoService;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-04 15:51:08
 * @Description : 租户管理服务实现
 * @Author : apple
 * @Update: 2020-06-04 15:51:08
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysTenantInfoServiceImpl extends ServiceImpl<SysTenantInfoMapper, SysTenantInfoEntity> implements SysTenantInfoService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
    public static final String SUPER_TENANT_ID = "DOOL1001";


	//@Autowired
	//private  SysColumnsMapper sysToolColumnsMapper;
	
	private  static String seqPrefix= TableEntityORMEnum.SYS_TENANT_INFO.getSeqPrefix();
	private  static String tableName = TableEntityORMEnum.SYS_TENANT_INFO.getEntityName();
	
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Override
	@Cacheable(value = "sysTenantInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<SysTenantInfoEntity, NullEntity> querySysTenantInfo(CommonMsg<SysTenantInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
		// 根据Key值查询
		SysTenantInfoEntity sysTenantInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());
       
		commonMsg.getBody().setListBody(nullEntityList);
		BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sysTenantInfoEntity,commonMsg);
		commonMsg.getBody().setSingleBody(sysTenantInfoEntity);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, SysTenantInfoEntity> querySysTenantInfoList(CommonMsg<NullEntity, SysTenantInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		NullEntity nullEntity = new NullEntity();
		// 获取所有列表
		List<SysTenantInfoEntity> sysTenantInfoEntityList = super.list();
		commonMsg.getBody().setSingleBody(nullEntity);
		commonMsg.getBody().setListBody(sysTenantInfoEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<SysTenantInfoEntity, SysTenantInfoEntity> querySysTenantInfoListPage(
			CommonMsg<SysTenantInfoEntity, SysTenantInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		// 默认以传入的sqlCondition查询
		List<SQLConditionEntity> sqlConditionList = new ArrayList<SQLConditionEntity>();
		// 设定只查当前租户ID条件,DOOL1001 超级租户可以查询所有的租户
		if(!SUPER_TENANT_ID.equals(commonMsg.getHead().getTenantId())) {
			SQLConditionEntity sqlConditionEntity = new SQLConditionEntity();
			sqlConditionEntity.setColumn("tenantId");
			sqlConditionEntity.setType("=");
			sqlConditionEntity.setValue(commonMsg.getHead().getTenantId());
			sqlConditionList.add(sqlConditionEntity);
		}
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
		QueryWrapper<SysTenantInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysTenantInfoEntity.class);
		if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
			queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysTenantInfoEntity.class);
		}
		//处理OR查询条件
		else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
			queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, SysTenantInfoEntity.class);
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
		Page<SysTenantInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysTenantInfoEntity> list =  super.page(pages, queryWrapper);
		
		commonMsg.getBody().setListBody(list.getRecords());
		commonMsg.getBody().setCurrentPage((int) list.getCurrent());
		commonMsg.getBody().setPageSize((int) list.getSize());
		commonMsg.getBody().setTotal(list.getTotal());
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

	@Override
	public CommonMsg<SysTenantInfoEntity, SysTenantInfoEntity> querySysTenantInfoListMap(
			CommonMsg<SysTenantInfoEntity, SysTenantInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		// 解析Single body 组装查询条件
		Map<String, Object> conditionMap = new HashMap<String, Object>(16);
		if(!SUPER_TENANT_ID.equals(commonMsg.getHead().getTenantId())) {
			conditionMap.put("tenantId", commonMsg.getHead().getTenantId());
		}
		Map<String, Object> singleBodyMap = BeanUtils.beanToMap(commonMsg.getBody().getSingleBody());
		for (Map.Entry<String, Object> entry : singleBodyMap.entrySet()) {
			if (entry.getValue() != null) {
				// kay是字段名 value是字段值
				conditionMap.put(entry.getKey(), entry.getValue());
			}
		}
		Collection<SysTenantInfoEntity> sysTenantInfoEntityList =  super.listByMap(conditionMap);
		List<SysTenantInfoEntity> sysTenantInfoMapList = new ArrayList<SysTenantInfoEntity>(sysTenantInfoEntityList);
		commonMsg.getBody().setListBody(sysTenantInfoMapList);
		
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	@CachePut(value = "sysTenantInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysTenantInfoEntity, NullEntity> saveSysTenantInfo(CommonMsg<SysTenantInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		log.info("=========tenantId1  ==== {}", commonMsg.getBody().getSingleBody().getTenantId());
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}
		// 如果内容为空且不合法返回错误信息
		if (! CommonUtil.commonMsgValidation(commonMsg)) {
			return commonMsg;
		}
		
		/**
		 * 查询租户是否存在
		 */
		QueryWrapper<SysTenantInfoEntity> queryWrapper = new QueryWrapper<SysTenantInfoEntity>();
		queryWrapper.eq("tenant_id", commonMsg.getBody().getSingleBody().getTenantId())
					.eq("valid_flag", "1");
		int count = super.count(queryWrapper);
		if(count > 0) {
			BizResponseEnum.TENANT_HAS_EXIST.assertFail(commonMsg);
		}
		
		commonMsg.getBody().getSingleBody().setDataSign(DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key));
		
		// 执行保存
		log.info("=========tenantId2  ==== {}", commonMsg.getBody().getSingleBody().getTenantId());

		boolean result =  super.save(EntityInitUtils.initEntityPublicInfoForInsertOrUpdateForSuper(commonMsg.getBody().getSingleBody(), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		log.info("=========tenantId3  ==== {}", commonMsg.getBody().getSingleBody().getTenantId());

		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, SysTenantInfoEntity> saveSysTenantInfoList(CommonMsg<NullEntity, SysTenantInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		// 如果listBody为空返回错误信息
		if (! CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		} 
		// 初始化数据
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			commonMsg.getBody().getListBody().get(i).setDataSign(DooleenMD5Util.md5(commonMsg.getBody().getListBody().get(i).toString(), ConstantValue.md5Key));
			commonMsg.getBody().getListBody().set(i, EntityInitUtils.initEntityPublicInfoForInsertOrUpdateForSuper(commonMsg.getBody().getListBody().get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		}
		// 批量保存
		boolean result =  super.saveBatch(commonMsg.getBody().getListBody());
		
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

	@Override
	@CachePut(value = "sysTenantInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysTenantInfoEntity, NullEntity> updateSysTenantInfo(CommonMsg<SysTenantInfoEntity, NullEntity> commonMsg) {
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
		
		/**
		 * 查询租户是否存在
		 */
//		QueryWrapper<SysTenantInfoEntity> queryWrapper = new QueryWrapper<SysTenantInfoEntity>();
//		queryWrapper.eq("tenant_id", commonMsg.getBody().getSingleBody().getTenantId())
//					.eq("valid_flag", "1");
//		List<SysTenantInfoEntity> list = super.list(queryWrapper);
//		if(null != list) {
//			list.forEach(item -> {
//				if(item.getTenantId().equals(commonMsg.getBody().getSingleBody().getTenantId())) {					
//					BizResponseEnum.TENANT_HAS_EXIST.assertFail();
//				}
//			});
//		}
		
		// 根据Key值查询修改记录，需进行深拷贝！！
		log.debug("====开始调用查询方法 ====== ");
		CommonMsg<SysTenantInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<SysTenantInfoEntity, NullEntity>();
		//定义singleBody
		SysTenantInfoEntity singleBody = new SysTenantInfoEntity();
		singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		//定义body
		MutBean<SysTenantInfoEntity, NullEntity> mutBean= new MutBean<SysTenantInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysTenantInfoServiceImpl.class).querySysTenantInfo(queryCommonMsg); 
		
		//判断修改内容是否被其他用户修改
		if (!CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
			return commonMsg;
		} 
		
		//设置需要修改的签名
		String bodySign = DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key);
		commonMsg.getBody().getSingleBody().setDataSign(bodySign);

		// 保存数据
		boolean result =  super.updateById(EntityInitUtils.initEntityPublicInfoForInsertOrUpdateForSuper(commonMsg.getBody().getSingleBody(), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}

	@Override
	@CachePut(value = "sysTenantInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000'")
	public CommonMsg<SysTenantInfoEntity, NullEntity> saveOrUpdateSysTenantInfo(CommonMsg<SysTenantInfoEntity, NullEntity> commonMsg) {
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
		CommonMsg<SysTenantInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<SysTenantInfoEntity, NullEntity>();
		//定义singleBody
		SysTenantInfoEntity singleBody = new SysTenantInfoEntity();
		
		//如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
		if(commonMsg.getBody().getSingleBody().getId() != null){
			singleBody.setId(commonMsg.getBody().getSingleBody().getId());
		}
		else {
			singleBody.setId("0");
		}
		//定义body
		MutBean<SysTenantInfoEntity, NullEntity> mutBean= new MutBean<SysTenantInfoEntity, NullEntity>();
		mutBean.setSingleBody(singleBody);
		//深拷贝数据进行AOP查询
		queryCommonMsg.setHead(commonMsg.getHead());
		queryCommonMsg.setBody(mutBean); 
		queryCommonMsg = SpringUtil.getBean(SysTenantInfoServiceImpl.class).querySysTenantInfo(queryCommonMsg); 
		
		//判断修改内容是否被其他用户修改
		if (!singleBody.getId().equals("0") && !CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
			return commonMsg;
		} 
		
		//设置需要修改的签名
		String bodySign = DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key);
		commonMsg.getBody().getSingleBody().setDataSign(bodySign);
	
		// 保存数据
		boolean result =  super.saveOrUpdate(EntityInitUtils.initEntityPublicInfoForInsertOrUpdateForSuper(commonMsg.getBody().getSingleBody(),
				commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		
		return commonMsg;
	}
	
	@Override
	public CommonMsg<NullEntity, SysTenantInfoEntity> saveOrUpdateSysTenantInfoList(CommonMsg<NullEntity, SysTenantInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		// 如果内容为空返回错误信息
		if (!CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
			return commonMsg;
		}
		
		// 批量保存
		for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
			super.saveOrUpdate(EntityInitUtils.initEntityPublicInfoForInsertOrUpdateForSuper(commonMsg.getBody().getListBody().get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
		}
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
    @CacheEvict(value = "sysTenantInfo", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id")
	public CommonMsg<SysTenantInfoEntity, NullEntity> deleteSysTenantInfo(CommonMsg<SysTenantInfoEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
		// 如果内容为空返回错误信息
		if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
			return commonMsg;
		}

		// 执行删除
		boolean result =  super.removeById(commonMsg.getBody().getSingleBody().getId());
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}

	@Override
	public CommonMsg<NullEntity, SysTenantInfoEntity> deleteSysTenantInfoList(CommonMsg<NullEntity, SysTenantInfoEntity> commonMsg) {
		CommonUtil.service(commonMsg);
		
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
		BizResponseEnum.DELETE_DATA_FAIL.assertIsTrue(result,commonMsg);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
	
	@Override
	public void exportSysTenantInfoExcel(CommonMsg<SysTenantInfoEntity, SysTenantInfoEntity> commonMsg, HttpServletResponse response) {
		CommonUtil.service(commonMsg);
		
		// 默认以传入的sqlCondition查询
		List<SQLConditionEntity> sqlConditionList = new ArrayList<SQLConditionEntity>();
		// 设定只查当前租户ID条件
		SQLConditionEntity sqlConditionEntity = new SQLConditionEntity();
		if(!SUPER_TENANT_ID.equals(commonMsg.getHead().getTenantId())) {
			sqlConditionEntity.setColumn("tenantId");
			sqlConditionEntity.setType("=");
			sqlConditionEntity.setValue(commonMsg.getHead().getTenantId());
			sqlConditionList.add(sqlConditionEntity);
		}
		// 如果搜索查询为空，并且过滤器查询有值时将过滤器的查询条件赋值给sqlConditionList
		if (commonMsg.getBody().getSqlCondition().size() > 0) {
			sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
		}

		QueryWrapper<SysTenantInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysTenantInfoEntity.class);
		
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
		Page<SysTenantInfoEntity> pages = new Page<>();
		// 当前页
		pages.setCurrent(commonMsg.getBody().getCurrentPage());
		// 每页条数
		pages.setSize(commonMsg.getBody().getPageSize());
		IPage<SysTenantInfoEntity> list =  super.page(pages, queryWrapper);
		commonMsg.getBody().setListBody(list.getRecords());
		
		/**
		 * 生成excel并输出
		 */
		String sheetName = "租户表";
		String fileName = "租户表";
		List<List<String>> excelData = new ArrayList<>();
		List<String> headList = new ArrayList<>();
		headList.add("ID");
		headList.add("租户ID");
		headList.add("用户租户ID");
		headList.add("租户名称");
		headList.add("联系人名");
		headList.add("联系电话号码");
		headList.add("联系地址");
		headList.add("绑定域名");
		headList.add("客户端ID");
		headList.add("客户秘钥");
		headList.add("系统版本号");
		headList.add("菜单版本号");
		headList.add("顶部菜单版本号");
		headList.add("重置编号");
		headList.add("消息编号");
		headList.add("通知消息");
		headList.add("默认系统颜色");
		headList.add("默认密码");
		headList.add("购买方式");
		headList.add("账户余额");
		headList.add("生效日期");
		headList.add("失效日期");
		excelData.add(headList);
		for (SysTenantInfoEntity sysTenantInfoEntity: commonMsg.getBody().getListBody()) {
			List<String> lists= new ArrayList<String>();
			lists.add(sysTenantInfoEntity.getId());	    		
			lists.add(sysTenantInfoEntity.getTenantId());	    		
			lists.add(sysTenantInfoEntity.getUserTenantId());	    		
			lists.add(sysTenantInfoEntity.getTenantName());	    		
			lists.add(sysTenantInfoEntity.getContactName());	    		
			lists.add(sysTenantInfoEntity.getContactPhoneNo());	    		
			lists.add(sysTenantInfoEntity.getContactAddress());	    		
			lists.add(sysTenantInfoEntity.getBindDomainName());	    		
			lists.add(sysTenantInfoEntity.getClientId());	    		
			lists.add(sysTenantInfoEntity.getCustSecretKey());	    		
			lists.add(sysTenantInfoEntity.getSystemVersionNo());	    		
			lists.add(sysTenantInfoEntity.getMenuVersionNo());	    		
			lists.add(sysTenantInfoEntity.getTopMenuVersionNo());	    		
			lists.add(sysTenantInfoEntity.getResetNo());	    		
			lists.add(sysTenantInfoEntity.getMsgNo());	    		
			lists.add(sysTenantInfoEntity.getNoticeMsg());	    		
			lists.add(sysTenantInfoEntity.getDefaultSystemColor());	    		
			lists.add(sysTenantInfoEntity.getDefaultPassword());	    		
			lists.add(sysTenantInfoEntity.getBuyWay());	    		
			lists.add(String.valueOf(sysTenantInfoEntity.getAcctBal()));	    		
			lists.add(sysTenantInfoEntity.getEffectDate());	    		
			lists.add(sysTenantInfoEntity.getInvalidDate());	    		
			excelData.add(lists);
		} 

		try {
			ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
		} catch (Exception e) {
			log.info("导出失败");
		}
		
	}
}
