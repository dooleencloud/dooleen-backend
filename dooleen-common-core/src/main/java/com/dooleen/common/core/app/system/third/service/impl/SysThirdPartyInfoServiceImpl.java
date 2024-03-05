package com.dooleen.common.core.app.system.third.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.dooleen.common.core.app.system.third.mapper.SysThirdPartyInfoMapper;
import com.dooleen.common.core.app.system.third.service.SysThirdPartyInfoService;
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
import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;
import com.dooleen.common.core.app.system.third.entity.SysThirdPartyInfoEntity;
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

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-18 15:47:33
 * @Description : 第三方配置管理服务实现
 * @Author : apple
 * @Update: 2020-08-18 15:47:33
 */

/**
 * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysThirdPartyInfoServiceImpl extends ServiceImpl<SysThirdPartyInfoMapper, SysThirdPartyInfoEntity> implements SysThirdPartyInfoService {

   public static final String CHECK_CONTENT = "checkContent";
   public static final String DELETE = "delete";

   @Autowired
   private  SysThirdPartyInfoMapper sysThirdPartyInfoMapper;

   private  static String seqPrefix= TableEntityORMEnum.SYS_THIRD_PARTY_INFO.getSeqPrefix();
   private  static String tableName = TableEntityORMEnum.SYS_THIRD_PARTY_INFO.getEntityName();

   @Autowired
   private RedisTemplate<String, ?> redisTemplate;

    /**
     * 供第三方平台查询
     * @param commonMsg
     * @return
     */
    @Override
    @Cacheable(value = "sysThirdPartyInfo", key = "#commonMsg.body.singleBody.tenantId + #commonMsg.body.singleBody.type")
    public CommonMsg<SysThirdPartyInfoEntity, NullEntity> querySysThirdPartyInfoByType(CommonMsg<SysThirdPartyInfoEntity, NullEntity> commonMsg) {
        CommonUtil.service(commonMsg);

        List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
        // 根据Key值查询
        QueryWrapper<SysThirdPartyInfoEntity> wrapper = new QueryWrapper<SysThirdPartyInfoEntity>();
        wrapper.lambda().eq(SysThirdPartyInfoEntity::getTenantId,commonMsg.getBody().getSingleBody().getTenantId());
        wrapper.lambda().eq(SysThirdPartyInfoEntity::getType,commonMsg.getBody().getSingleBody().getType());
        SysThirdPartyInfoEntity sysThirdPartyInfoEntity = sysThirdPartyInfoMapper.selectOne(wrapper);
        BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sysThirdPartyInfoEntity,commonMsg);
        commonMsg.getBody().setSingleBody(sysThirdPartyInfoEntity);
        //设置返回码
        CommonUtil.successReturn(commonMsg);
        return commonMsg;
    }
   @Override
   @Cacheable(value = "sysThirdPartyInfo", key = "#commonMsg.body.singleBody.tenantId + #commonMsg.body.singleBody.type")
   public CommonMsg<SysThirdPartyInfoEntity, NullEntity> querySysThirdPartyInfo(CommonMsg<SysThirdPartyInfoEntity, NullEntity> commonMsg) {
       CommonUtil.service(commonMsg);

       List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
       // 根据Key值查询
       SysThirdPartyInfoEntity sysThirdPartyInfoEntity = super.getById(commonMsg.getBody().getSingleBody().getId());

       commonMsg.getBody().setListBody(nullEntityList);

       BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sysThirdPartyInfoEntity,commonMsg);

       commonMsg.getBody().setSingleBody(sysThirdPartyInfoEntity);

       //设置返回码
       CommonUtil.successReturn(commonMsg);
       return commonMsg;
   }

   @Override
   public CommonMsg<NullEntity, SysThirdPartyInfoEntity> querySysThirdPartyInfoList(CommonMsg<NullEntity, SysThirdPartyInfoEntity> commonMsg) {
       CommonUtil.service(commonMsg);

       NullEntity nullEntity = new NullEntity();
       // 获取所有列表
       List<SysThirdPartyInfoEntity> sysThirdPartyInfoEntityList = super.list();
       commonMsg.getBody().setSingleBody(nullEntity);
       commonMsg.getBody().setListBody(sysThirdPartyInfoEntityList);

       //设置返回值
       CommonUtil.successReturn(commonMsg);
       return commonMsg;
   }

   @Override
   public CommonMsg<SysThirdPartyInfoEntity, SysThirdPartyInfoEntity> querySysThirdPartyInfoListPage(
           CommonMsg<SysThirdPartyInfoEntity, SysThirdPartyInfoEntity> commonMsg) {
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
       QueryWrapper<SysThirdPartyInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysThirdPartyInfoEntity.class);
       if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
           sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
           queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysThirdPartyInfoEntity.class);
       }
       //处理OR查询条件
       else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
           sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
           queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, SysThirdPartyInfoEntity.class);
       }

       // 处理排序
       List<SQLOrderEntity> sqlOrderList = commonMsg.getBody().getOrderRule();
       if(null != sqlOrderList && !sqlOrderList.isEmpty()) {
           for(int i = 0; i < sqlOrderList.size(); i++){
               if(sqlOrderList.get(i).getProp() != null && sqlOrderList.get(i).getOrder() != null){
                   if(sqlOrderList.get(i).getOrder().equals("ascending")){
                       queryWrapper.orderByAsc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,sqlOrderList.get(i).getProp()));
                   }else{
                       queryWrapper.orderByDesc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,sqlOrderList.get(i).getProp()));
                   }
               }
           }
       }

       // 定义分页查询
       Page<SysThirdPartyInfoEntity> pages = new Page<>();
       // 当前页
       pages.setCurrent(commonMsg.getBody().getCurrentPage());
       // 每页条数
       pages.setSize(commonMsg.getBody().getPageSize());
       IPage<SysThirdPartyInfoEntity> list =  super.page(pages, queryWrapper);

       commonMsg.getBody().setListBody(list.getRecords());
       commonMsg.getBody().setCurrentPage((int) list.getCurrent());
       commonMsg.getBody().setPageSize((int) list.getSize());
       commonMsg.getBody().setTotal(list.getTotal());

       //设置返回值
       CommonUtil.successReturn(commonMsg);
       return commonMsg;
   }

   @Override
   public CommonMsg<SysThirdPartyInfoEntity, SysThirdPartyInfoEntity> querySysThirdPartyInfoListMap(
           CommonMsg<SysThirdPartyInfoEntity, SysThirdPartyInfoEntity> commonMsg) {
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
       Collection<SysThirdPartyInfoEntity> sysThirdPartyInfoEntityList =  super.listByMap(conditionMap);
       List<SysThirdPartyInfoEntity> sysThirdPartyInfoMapList = new ArrayList<SysThirdPartyInfoEntity>(sysThirdPartyInfoEntityList);
       commonMsg.getBody().setListBody(sysThirdPartyInfoMapList);

       //设置返回值
       CommonUtil.successReturn(commonMsg);
       return commonMsg;
   }

   @Override
   @CachePut(value = "sysThirdPartyInfo", key = "#commonMsg.body.singleBody.tenantId + #commonMsg.body.singleBody.type" ,condition = "#commonMsg.head.respCode eq 'S0000'")
   public CommonMsg<SysThirdPartyInfoEntity, NullEntity> saveSysThirdPartyInfo(CommonMsg<SysThirdPartyInfoEntity, NullEntity> commonMsg) {
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
   public CommonMsg<NullEntity, SysThirdPartyInfoEntity> saveSysThirdPartyInfoList(CommonMsg<NullEntity, SysThirdPartyInfoEntity> commonMsg) {
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
   @CachePut(value = "sysThirdPartyInfo", key = "#commonMsg.body.singleBody.tenantId + #commonMsg.body.singleBody.type",condition = "#commonMsg.head.respCode eq 'S0000'")
   public CommonMsg<SysThirdPartyInfoEntity, NullEntity> updateSysThirdPartyInfo(CommonMsg<SysThirdPartyInfoEntity, NullEntity> commonMsg) {
       CommonUtil.service(commonMsg);
       // 如果内容为空返回错误信息
       if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
           return commonMsg;
       }
       // 如果内容为空返回错误信息
       if (! CommonUtil.commonMsgValidation(commonMsg)) {
           return commonMsg;
       }

       // 判断内容是否发生更改
       if (! CommonUtil.commonMsgIsUpdateContent(commonMsg, commonMsg.getBody().getSingleBody().getDataSign())) {
           return commonMsg;
       }
       // 根据Key值查询修改记录，需进行深拷贝！！
       log.debug("====开始调用查询方法 ====== ");
       CommonMsg<SysThirdPartyInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<SysThirdPartyInfoEntity, NullEntity>();

       //定义body
       MutBean<SysThirdPartyInfoEntity, NullEntity> mutBean= new MutBean<SysThirdPartyInfoEntity, NullEntity>();
       FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
       mutBean.setSingleBody(commonMsg.getBody().getSingleBody());
       mutBean.setFlowArea(flowArea);
       //深拷贝数据进行AOP查询
       queryCommonMsg.setHead(commonMsg.getHead());
       queryCommonMsg.setBody(mutBean);
       queryCommonMsg = SpringUtil.getBean(SysThirdPartyInfoServiceImpl.class).querySysThirdPartyInfo(queryCommonMsg);

       //判断修改内容是否被其他用户修改
       if (!CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
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
   @CachePut(value = "sysThirdPartyInfo", key = "#commonMsg.body.singleBody.tenantId + #commonMsg.body.singleBody.type",condition = "#commonMsg.head.respCode eq 'S0000'")
   public CommonMsg<SysThirdPartyInfoEntity, NullEntity> saveOrUpdateSysThirdPartyInfo(CommonMsg<SysThirdPartyInfoEntity, NullEntity> commonMsg) {
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
       CommonMsg<SysThirdPartyInfoEntity, NullEntity> queryCommonMsg = new CommonMsg<SysThirdPartyInfoEntity, NullEntity>();
       //定义singleBody
       SysThirdPartyInfoEntity singleBody = new SysThirdPartyInfoEntity();

       //如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
       if(commonMsg.getBody().getSingleBody().getId() != null){
           singleBody.setId(commonMsg.getBody().getSingleBody().getId());
       }
       else {
           singleBody.setId("0");
       }
       //定义body
       MutBean<SysThirdPartyInfoEntity, NullEntity> mutBean= new MutBean<SysThirdPartyInfoEntity, NullEntity>();
       FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
       mutBean.setSingleBody(singleBody);
       mutBean.setFlowArea(flowArea);
       //深拷贝数据进行AOP查询
       queryCommonMsg.setHead(commonMsg.getHead());
       queryCommonMsg.setBody(mutBean);
       queryCommonMsg = SpringUtil.getBean(SysThirdPartyInfoServiceImpl.class).querySysThirdPartyInfo(queryCommonMsg);

       //判断修改内容是否被其他用户修改
       if (!singleBody.getId().equals("0") && !CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
           return commonMsg;
       }


       //设置需要修改的签名
       String bodySign = DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key);
       commonMsg.getBody().getSingleBody().setDataSign(bodySign);

       // 保存数据
       boolean result =  super.saveOrUpdate(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(),
               commonMsg.getHead(),tableName, seqPrefix, redisTemplate));

       BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);

       //设置返回值
       CommonUtil.successReturn(commonMsg);
       return commonMsg;
   }

   @Override
   public CommonMsg<NullEntity, SysThirdPartyInfoEntity> saveOrUpdateSysThirdPartyInfoList(CommonMsg<NullEntity, SysThirdPartyInfoEntity> commonMsg) {
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
   @CacheEvict(value = "sysThirdPartyInfo", key = "#commonMsg.body.singleBody.tenantId + #commonMsg.body.singleBody.type")
   public CommonMsg<SysThirdPartyInfoEntity, NullEntity> deleteSysThirdPartyInfo(CommonMsg<SysThirdPartyInfoEntity, NullEntity> commonMsg) {
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
   public CommonMsg<NullEntity, SysThirdPartyInfoEntity> deleteSysThirdPartyInfoList(CommonMsg<NullEntity, SysThirdPartyInfoEntity> commonMsg) {
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
   public void exportSysThirdPartyInfoExcel(CommonMsg<SysThirdPartyInfoEntity, SysThirdPartyInfoEntity> commonMsg, HttpServletResponse response) {
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

       QueryWrapper<SysThirdPartyInfoEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysThirdPartyInfoEntity.class);

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
       Page<SysThirdPartyInfoEntity> pages = new Page<>();
       // 当前页
       pages.setCurrent(commonMsg.getBody().getCurrentPage());
       // 每页条数
       pages.setSize(commonMsg.getBody().getPageSize());
       IPage<SysThirdPartyInfoEntity> list =  super.page(pages, queryWrapper);
       commonMsg.getBody().setListBody(list.getRecords());

       /**
        * 生成excel并输出
        */
       String sheetName = "第三方配置表";
       String fileName = "第三方配置表";
       List<List<String>> excelData = new ArrayList<>();
       List<String> headList = new ArrayList<>();
       headList.add("ID");
       headList.add("租户ID");
       headList.add("类型");
       headList.add("第三方配置内容");
       excelData.add(headList);
       for (SysThirdPartyInfoEntity sysThirdPartyInfoEntity: commonMsg.getBody().getListBody()) {
           List<String> lists= new ArrayList<String>();
           lists.add(sysThirdPartyInfoEntity.getId());
           lists.add(sysThirdPartyInfoEntity.getTenantId());
           lists.add(sysThirdPartyInfoEntity.getType());
           lists.add(sysThirdPartyInfoEntity.getThirdPartyConfigContent());
           excelData.add(lists);
       }

       try {
           ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
       } catch (Exception e) {
           log.info("导出失败");
       }

   }
}
