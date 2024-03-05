package com.dooleen.service.system.send.log.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.app.send.log.mapper.SysSendLogMapper;
import com.dooleen.common.core.mq.utils.MsgSendUtil;
import com.dooleen.service.system.send.log.service.SysSendLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
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
import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessRecordEntity;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowProcessRecordMapper;
import com.dooleen.common.core.app.general.flow.service.GeneralFlowProcessService;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.common.entity.SQLConditionEntity;
import com.dooleen.common.core.common.entity.SQLOrderEntity;
import com.dooleen.common.core.common.entity.ValidationResult;
import com.dooleen.common.core.common.entity.MutBean;
import com.dooleen.common.core.enums.TableEntityORMEnum;


import com.dooleen.common.core.utils.ConstantValue;
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.common.core.utils.ExcelUtil;
import com.dooleen.common.core.utils.BeanUtils;
import com.dooleen.common.core.utils.EntityInitUtils;
import com.dooleen.common.core.utils.QueryWrapperUtil;
import com.dooleen.common.core.utils.RespStatus;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.ValidationUtils;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.common.core.utils.CommonUtil;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : dooleen平台
 * @Project No : dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-11-07 22:12:08
 * @Description : 敏感词管理服务实现
 * @Author : apple
 * @Update: 2020-11-07 22:12:08
 */
/**
 * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysSendLogServiceImpl extends ServiceImpl<SysSendLogMapper, SysSendLogEntity> implements SysSendLogService {

    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
    public static final String CACHEABLE = "false";

    @Autowired
    public GeneralFlowProcessService  generalFlowProcessService;

    @Autowired
    private  GeneralFlowProcessRecordMapper generalFlowProcessRecordMapper;

    @Autowired
    private  SysSendLogMapper sysSendLogMapper;

    @Autowired
    private  GetBizParamsService getBizParamsService;

    @Autowired
    private MsgSendUtil msgSendUtil;
    /**
     * 例子 general_note_boook
     * 请将此段copy到 com.dooleen.common.core.enums TableEntityORMEnum  中
     * AAAA为ID的键值关键字 如： DOOL1001AAAA10000001
     */
    //LEARN_SEND_LOG("learn_send_log", "SysSendLog", "AAAA"),


    private  static String seqPrefix= TableEntityORMEnum.SYS_SEND_LOG.getSeqPrefix();
    private  static String tableName = TableEntityORMEnum.SYS_SEND_LOG.getEntityName();

    @Autowired
    private RedisTemplate<String, ?> redisTemplate;
    @Override
    @Cacheable(value = "sysSendLog", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = CACHEABLE)
    public CommonMsg<SysSendLogEntity, NullEntity> querySysSendLog(CommonMsg<SysSendLogEntity, NullEntity> commonMsg) {
        CommonUtil.service(commonMsg);
        List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
        // 根据Key值查询
        SysSendLogEntity sysSendLogEntity = super.getById(commonMsg.getBody().getSingleBody().getId());

        commonMsg.getBody().setListBody(nullEntityList);

        BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sysSendLogEntity,commonMsg);
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

        commonMsg.getBody().setSingleBody(sysSendLogEntity);
        //设置返回值
        CommonUtil.successReturn(commonMsg);
        return commonMsg;
    }

    @Override
    public CommonMsg<NullEntity, SysSendLogEntity> querySysSendLogList(CommonMsg<NullEntity, SysSendLogEntity> commonMsg) {
        CommonUtil.service(commonMsg);
        NullEntity nullEntity = new NullEntity();
        // 获取所有列表
        List<SysSendLogEntity> sysSendLogEntityList = super.list();
        commonMsg.getBody().setSingleBody(nullEntity);
        commonMsg.getBody().setListBody(sysSendLogEntityList);
        //设置返回值
        CommonUtil.successReturn(commonMsg);
        return commonMsg;
    }

    @Override
    public CommonMsg<SysSendLogEntity, SysSendLogEntity> querySysSendLogListPage(
            CommonMsg<SysSendLogEntity, SysSendLogEntity> commonMsg) {
        CommonUtil.service(commonMsg);
        // 默认以传入的sqlCondition查询
        List<SQLConditionEntity> sqlConditionList = new ArrayList<SQLConditionEntity>();

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
        QueryWrapper<SysSendLogEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysSendLogEntity.class);
        if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
            sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
            queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysSendLogEntity.class);
        }
        //处理OR查询条件
        else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
            sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
            queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, SysSendLogEntity.class);
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
        Page<SysSendLogEntity> pages = new Page<>();
        // 当前页
        pages.setCurrent(commonMsg.getBody().getCurrentPage());
        // 每页条数
        pages.setSize(commonMsg.getBody().getPageSize());
        IPage<SysSendLogEntity> list =  super.page(pages, queryWrapper);

        commonMsg.getBody().setListBody(list.getRecords());
        commonMsg.getBody().setCurrentPage((int) list.getCurrent());
        commonMsg.getBody().setPageSize((int) list.getSize());
        commonMsg.getBody().setTotal(list.getTotal());
        //设置返回值
        CommonUtil.successReturn(commonMsg);
        return commonMsg;
    }

    @Override
    public CommonMsg<SysSendLogEntity, SysSendLogEntity> querySysSendLogListMap(
            CommonMsg<SysSendLogEntity, SysSendLogEntity> commonMsg) {
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
        Collection<SysSendLogEntity> sysSendLogEntityList =  super.listByMap(conditionMap);
        List<SysSendLogEntity> sysSendLogMapList = new ArrayList<SysSendLogEntity>(sysSendLogEntityList);
        commonMsg.getBody().setListBody(sysSendLogMapList);
        //设置返回值
        CommonUtil.successReturn(commonMsg);
        return commonMsg;
    }

    @Override
    @CachePut(value = "sysSendLog", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and " + CACHEABLE )
    public CommonMsg<SysSendLogEntity, NullEntity> saveSysSendLog(CommonMsg<SysSendLogEntity, NullEntity> commonMsg) {
        CommonUtil.service(commonMsg);
        // 如果内容为空返回错误信息
        if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
            return commonMsg;
        }
        // 如果内容为空且不合法返回错误信息
        if (! CommonUtil.commonMsgValidation(commonMsg)) {
            return commonMsg;
        }
        msgSendUtil.sendMsgToMq(commonMsg.getBody().getSingleBody());
        log.info("====发送结束。。。");
        //设置返回值
        CommonUtil.successReturn(commonMsg);
        return commonMsg;
    }

    @Override
    public CommonMsg<NullEntity, SysSendLogEntity> saveSysSendLogList(CommonMsg<NullEntity, SysSendLogEntity> commonMsg) {
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
    @CachePut(value = "sysSendLog", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and " + CACHEABLE )
    public CommonMsg<SysSendLogEntity, NullEntity> updateSysSendLog(CommonMsg<SysSendLogEntity, NullEntity> commonMsg) {
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
        CommonMsg<SysSendLogEntity, NullEntity> queryCommonMsg = new CommonMsg<SysSendLogEntity, NullEntity>();
        //定义singleBody
        SysSendLogEntity singleBody = new SysSendLogEntity();
        singleBody.setId(commonMsg.getBody().getSingleBody().getId());
        //定义body
        MutBean<SysSendLogEntity, NullEntity> mutBean= new MutBean<SysSendLogEntity, NullEntity>();
        FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
        mutBean.setSingleBody(singleBody);
        mutBean.setFlowArea(flowArea);
        //深拷贝数据进行AOP查询
        queryCommonMsg.setHead(commonMsg.getHead());
        queryCommonMsg.setBody(mutBean);
        queryCommonMsg = SpringUtil.getBean(SysSendLogServiceImpl.class).querySysSendLog(queryCommonMsg);

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
    @CachePut(value = "sysSendLog", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and " + CACHEABLE )
    public CommonMsg<SysSendLogEntity, NullEntity> saveOrUpdateSysSendLog(CommonMsg<SysSendLogEntity, NullEntity> commonMsg) {
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
        if(!commonMsg.getBody().getSingleBody().getId().equals("0")) {
            CommonMsg<SysSendLogEntity, NullEntity> queryCommonMsg = new CommonMsg<SysSendLogEntity, NullEntity>();
            //定义singleBody
            SysSendLogEntity singleBody = new SysSendLogEntity();

            //如果新增时Id为null，此时给ID赋值为0，（为0的ID不存在）
            if(commonMsg.getBody().getSingleBody().getId() != null){
                singleBody.setId(commonMsg.getBody().getSingleBody().getId());
            }
            else {
                singleBody.setId("0");
            }
            //定义body
            MutBean<SysSendLogEntity, NullEntity> mutBean= new MutBean<SysSendLogEntity, NullEntity>();
            FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
            mutBean.setSingleBody(singleBody);
            mutBean.setFlowArea(flowArea);
            //深拷贝数据进行AOP查询
            queryCommonMsg.setHead(commonMsg.getHead());
            queryCommonMsg.setBody(mutBean);
            queryCommonMsg = SpringUtil.getBean(SysSendLogServiceImpl.class).querySysSendLog(queryCommonMsg);

            //判断修改内容是否被其他用户修改
            if (!CommonUtil.commonMsgIsChangedByOthers(commonMsg, queryCommonMsg)) {
                return commonMsg;
            }
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
    public CommonMsg<NullEntity, SysSendLogEntity> saveOrUpdateSysSendLogList(CommonMsg<NullEntity, SysSendLogEntity> commonMsg) {
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
    @CacheEvict(value = "sysSendLog", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = CACHEABLE)
    public CommonMsg<SysSendLogEntity, NullEntity> deleteSysSendLog(CommonMsg<SysSendLogEntity, NullEntity> commonMsg) {
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
    public CommonMsg<NullEntity, SysSendLogEntity> deleteSysSendLogList(CommonMsg<NullEntity, SysSendLogEntity> commonMsg) {
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
    public void exportSysSendLogExcel(CommonMsg<SysSendLogEntity, SysSendLogEntity> commonMsg, HttpServletResponse response) {
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

        QueryWrapper<SysSendLogEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysSendLogEntity.class);

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
        Page<SysSendLogEntity> pages = new Page<>();
        // 当前页
        pages.setCurrent(commonMsg.getBody().getCurrentPage());
        // 每页条数
        pages.setSize(commonMsg.getBody().getPageSize());
        IPage<SysSendLogEntity> list =  super.page(pages, queryWrapper);
        commonMsg.getBody().setListBody(list.getRecords());

        /**
         * 生成excel并输出
         */
        String sheetName = "发送日志表";
        String fileName = "发送日志表";
        List<List<String>> excelData = new ArrayList<>();
        List<String> headList = new ArrayList<>();
        headList.add("ID");
        headList.add("发送人ID");
        headList.add("消息类型");
        headList.add("发送渠道");
        headList.add("发送地址");
        headList.add("消息标题");
        headList.add("消息内容");
        headList.add("发送状态");
        excelData.add(headList);
        for (SysSendLogEntity sysSendLogEntity: commonMsg.getBody().getListBody()) {
            List<String> lists= new ArrayList<String>();
            lists.add(sysSendLogEntity.getId());
            lists.add(sysSendLogEntity.getSenderId());
            lists.add(sysSendLogEntity.getMsgType());
            lists.add(sysSendLogEntity.getSendChannel());
            lists.add(sysSendLogEntity.getSendAddress());
            lists.add(sysSendLogEntity.getMsgTitle());
            lists.add(sysSendLogEntity.getMsgContent());
            lists.add(sysSendLogEntity.getSendStatus());
            excelData.add(lists);
        }

        try {
            ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 30);
        } catch (Exception e) {
            log.info("导出失败");
        }
        log.info("====exportSysSendLogExcel service end == " + RespStatus.json.getString("S0000"));
    }
}
