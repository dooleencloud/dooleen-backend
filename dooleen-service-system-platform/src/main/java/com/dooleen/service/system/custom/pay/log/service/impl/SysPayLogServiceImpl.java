package com.dooleen.service.system.custom.pay.log.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.service.system.custom.pay.log.entity.SysPayLogEntity;
import org.apache.commons.lang3.StringUtils;
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
import com.dooleen.common.core.app.general.common.service.impl.GetBizParamsService;
import com.dooleen.service.system.custom.pay.log.mapper.SysPayLogMapper;
import com.dooleen.service.system.custom.pay.log.service.SysPayLogService;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessRecordEntity;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowProcessRecordMapper;
import com.dooleen.common.core.app.general.flow.service.GeneralFlowProcessService;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SQLConditionEntity;
import com.dooleen.common.core.common.entity.SQLOrderEntity;
import com.dooleen.common.core.enums.TableEntityORMEnum;


import com.dooleen.common.core.utils.ConstantValue;
import com.dooleen.common.core.utils.BeanUtils;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.QueryWrapperUtil;
import com.dooleen.common.core.utils.RespStatus;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.common.core.utils.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 北京数云信息服务有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-11-17 21:38:54
 * @Description : 支付日志管理服务实现
 * @Author : apple
 * @Update: 2020-11-17 21:38:54
 */

/**
 * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysPayLogServiceImpl extends ServiceImpl<SysPayLogMapper, SysPayLogEntity> implements SysPayLogService {

   public static final String CHECK_CONTENT = "checkContent";
   public static final String DELETE = "delete";
   public static final String CACHEABLE = "false";

   @Autowired
   public GeneralFlowProcessService  generalFlowProcessService;

   @Autowired
   private  GeneralFlowProcessRecordMapper generalFlowProcessRecordMapper;

   @Autowired
   private  SysPayLogMapper sysPayLogMapper;

   @Autowired
   private  GetBizParamsService getBizParamsService;


   /**
    * 例子 general_note_boook
    * 请将此段copy到 com.dooleen.common.core.enums TableEntityORMEnum  中
    * AAAA为ID的键值关键字 如： XRAY1001AAAA10000001
    */
   //LEARN_PAY_LOG("learn_pay_log", "SysPayLog", "AAAA"),


   private  static String seqPrefix= TableEntityORMEnum.SYS_ROLE.getSeqPrefix();
   private  static String tableName = TableEntityORMEnum.SYS_ROLE.getEntityName();

   @Autowired
   private RedisTemplate<String, ?> redisTemplate;
   @Override
   @Cacheable(value = "sysPayLog", key = "#commonMsg.body.singleBody.outTradeNo", condition = CACHEABLE)
   public CommonMsg<SysPayLogEntity, NullEntity> querySysPayLog(CommonMsg<SysPayLogEntity, NullEntity> commonMsg) {
       CommonUtil.service(commonMsg);
       List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
       // 根据Key值查询
       SysPayLogEntity sysPayLogEntity = super.getById(commonMsg.getBody().getSingleBody().getId());

       commonMsg.getBody().setListBody(nullEntityList);

       BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sysPayLogEntity,commonMsg);
       /** 不需要流程请自行删除  开始 */
       //判断是否需要查流程信息
       if(!StringUtil.isEmpty(commonMsg.getBody().getFlowArea().getIsFlow()) && commonMsg.getBody().getFlowArea().getIsFlow().equals("true")) {
           GeneralFlowProcessRecordEntity  generalFlowProcessRecordEntity = generalFlowProcessRecordMapper.selectById(commonMsg.getBody().getFlowArea().getProcessRecordId());
           if(generalFlowProcessRecordEntity != null) {
               commonMsg.getBody().getFlowArea().setProcessRecordId(generalFlowProcessRecordEntity.getId());
               commonMsg.getBody().getFlowArea().setFlowExtData((JSONObject) JSON.parse(generalFlowProcessRecordEntity.getDataArea()));
               commonMsg.getBody().getFlowArea().setFlowEndFlag(generalFlowProcessRecordEntity.getFlowEndFlag());
           }
       }
       /** 不需要流程请自行删除  结束 */

       commonMsg.getBody().setSingleBody(sysPayLogEntity);
       //设置返回值
       CommonUtil.successReturn(commonMsg);
       return commonMsg;
   }

   @Override
   public CommonMsg<NullEntity, SysPayLogEntity> querySysPayLogList(CommonMsg<NullEntity, SysPayLogEntity> commonMsg) {
       CommonUtil.service(commonMsg);
       NullEntity nullEntity = new NullEntity();
       // 获取所有列表
       List<SysPayLogEntity> sysPayLogEntityList = super.list();
       commonMsg.getBody().setSingleBody(nullEntity);
       commonMsg.getBody().setListBody(sysPayLogEntityList);
       //设置返回值
       CommonUtil.successReturn(commonMsg);
       return commonMsg;
   }

   @Override
   public CommonMsg<SysPayLogEntity, SysPayLogEntity> querySysPayLogListPage(
           CommonMsg<SysPayLogEntity, SysPayLogEntity> commonMsg) {
       CommonUtil.service(commonMsg);
       // 默认以传入的sqlCondition查询
       List<SQLConditionEntity> sqlConditionList = new ArrayList<SQLConditionEntity>();
       // 设定只查当前租户ID条件

       // 如果singlebody不为空解析Single body 组装查询条件
       Map<String, Object> singleBodyMap = BeanUtils.beanToMap(commonMsg.getBody().getSingleBody());
       boolean isInSingleBody = false;
       for (Map.Entry<String, Object> entry : singleBodyMap.entrySet()) {
           if(entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString()))
           {
               if (!(StringUtil.isNumeric(entry.getValue().toString()) && (entry.getValue().equals("0") || entry.getValue().equals(0)))) {
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
       QueryWrapper<SysPayLogEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysPayLogEntity.class);
       if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
           sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
           queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysPayLogEntity.class);
       }
       //处理OR查询条件
       else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
           sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
           queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, SysPayLogEntity.class);
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
       Page<SysPayLogEntity> pages = new Page<>();
       // 当前页
       pages.setCurrent(commonMsg.getBody().getCurrentPage());
       // 每页条数
       pages.setSize(commonMsg.getBody().getPageSize());
       IPage<SysPayLogEntity> list =  super.page(pages, queryWrapper);

       commonMsg.getBody().setListBody(list.getRecords());
       commonMsg.getBody().setCurrentPage((int) list.getCurrent());
       commonMsg.getBody().setPageSize((int) list.getSize());
       commonMsg.getBody().setTotal(list.getTotal());
       //设置返回值
       CommonUtil.successReturn(commonMsg);
       return commonMsg;
   }

   @Override
   public CommonMsg<SysPayLogEntity, SysPayLogEntity> querySysPayLogListMap(
           CommonMsg<SysPayLogEntity, SysPayLogEntity> commonMsg) {
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
       Collection<SysPayLogEntity> sysPayLogEntityList =  super.listByMap(conditionMap);
       List<SysPayLogEntity> sysPayLogMapList = new ArrayList<SysPayLogEntity>(sysPayLogEntityList);
       commonMsg.getBody().setListBody(sysPayLogMapList);
       //设置返回值
       CommonUtil.successReturn(commonMsg);
       return commonMsg;
   }

   @Override
   @CachePut(value = "sysPayLog", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and " + CACHEABLE )
   public CommonMsg<SysPayLogEntity, NullEntity> saveSysPayLog(CommonMsg<SysPayLogEntity, NullEntity> commonMsg) {
       CommonUtil.service(commonMsg);
       // 如果内容为空返回错误信息
       if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
           return commonMsg;
       }
       // 如果内容为空且不合法返回错误信息
       if (! CommonUtil.commonMsgValidation(commonMsg)) {
           return commonMsg;
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
   public CommonMsg<NullEntity, SysPayLogEntity> saveSysPayLogList(CommonMsg<NullEntity, SysPayLogEntity> commonMsg) {
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
   @CachePut(value = "sysPayLog", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and " + CACHEABLE )
   public CommonMsg<SysPayLogEntity, NullEntity> updateSysPayLog(CommonMsg<SysPayLogEntity, NullEntity> commonMsg) {
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
   @CachePut(value = "sysPayLog", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and " + CACHEABLE )
   public CommonMsg<SysPayLogEntity, NullEntity> saveOrUpdateSysPayLog(CommonMsg<SysPayLogEntity, NullEntity> commonMsg) {

       return commonMsg;
   }

   @Override
   public CommonMsg<NullEntity, SysPayLogEntity> saveOrUpdateSysPayLogList(CommonMsg<NullEntity, SysPayLogEntity> commonMsg) {
       CommonUtil.service(commonMsg);
       // 如果内容为空返回错误信息
       if (!CommonUtil.commonMsgListBodyLength(commonMsg, CHECK_CONTENT)) {
           return commonMsg;
       }

       // 批量保存
       for (int i = 0; i < commonMsg.getBody().getListBody().size(); i++) {
           super.saveOrUpdate(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getListBody().get(i), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
       }
       //设置返回值
       CommonUtil.successReturn(commonMsg);
       return commonMsg;
   }

   @Override
   @CacheEvict(value = "sysPayLog", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = CACHEABLE)
   public CommonMsg<SysPayLogEntity, NullEntity> deleteSysPayLog(CommonMsg<SysPayLogEntity, NullEntity> commonMsg) {
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
   public CommonMsg<NullEntity, SysPayLogEntity> deleteSysPayLogList(CommonMsg<NullEntity, SysPayLogEntity> commonMsg) {
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
   public void exportSysPayLogExcel(CommonMsg<SysPayLogEntity, SysPayLogEntity> commonMsg, HttpServletResponse response) {

       log.info("====exportSysPayLogExcel service end == " + RespStatus.json.getString("S0000"));
   }
}
