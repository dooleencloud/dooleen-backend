package com.dooleen.service.system.msg.notice.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dooleen.common.core.app.general.common.service.impl.GetBizParamsService;
import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;
import com.dooleen.common.core.app.general.flow.entity.GeneralFlowProcessRecordEntity;
import com.dooleen.common.core.app.general.flow.mapper.GeneralFlowProcessRecordMapper;
import com.dooleen.common.core.app.general.flow.service.GeneralFlowProcessService;
import com.dooleen.common.core.app.send.log.entity.SysSendLogEntity;
import com.dooleen.common.core.app.system.msg.notice.entity.SysMsgNoticeEntity;
import com.dooleen.common.core.app.system.msg.notice.mapper.SysMsgNoticeMapper;
import com.dooleen.common.core.app.system.user.info.entity.SysUserInfoEntity;
import com.dooleen.common.core.app.system.user.info.mapper.SysUserInfoMapper;
import com.dooleen.common.core.common.entity.*;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.mq.utils.MsgSendUtil;
import com.dooleen.common.core.utils.*;
import com.dooleen.service.system.msg.notice.service.SysMsgNoticeService;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static java.net.URLDecoder.decode;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-06-05 13:38:58
 * @Description : 消息通知管理服务实现
 * @Author : apple
 * @Update: 2021-06-05 13:38:58
 */

/**
 * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysMsgNoticeServiceImpl extends ServiceImpl<SysMsgNoticeMapper, SysMsgNoticeEntity> implements SysMsgNoticeService {

    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";

    @Autowired
    public GeneralFlowProcessService  generalFlowProcessService;

    @Autowired
    private  GeneralFlowProcessRecordMapper generalFlowProcessRecordMapper;

    @Autowired
    private  SysMsgNoticeMapper sysMsgNoticeMapper;

    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;


    @Autowired
    private  GetBizParamsService getBizParamsService;

    @Autowired
    private MsgSendUtil msgSendUtil;
    /**
     * 例子 general_note_boook
     * 请将此段copy到 com.dooleen.common.core.enums TableEntityORMEnum  中
     * AAAA为ID的键值关键字 如： DOOL1001AAAA10000001
     */
    //SYS_MSG_NOTICE("sys_msg_notice", "SysMsgNotice", "AAAA"),


    private  static String seqPrefix= TableEntityORMEnum.SYS_MSG_NOTICE.getSeqPrefix();
    private  static String tableName = TableEntityORMEnum.SYS_MSG_NOTICE.getEntityName();

    @Autowired
    private RedisTemplate<String, ?> redisTemplate;
    @Override
    @Cacheable(value = "sysMsgNotice", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
    public CommonMsg<SysMsgNoticeEntity, NullEntity> querySysMsgNotice(CommonMsg<SysMsgNoticeEntity, NullEntity> commonMsg) {
        CommonUtil.service(commonMsg);
        List<NullEntity> nullEntityList = new ArrayList<NullEntity>();
        // 根据Key值查询
        SysMsgNoticeEntity sysMsgNoticeEntity = super.getById(commonMsg.getBody().getSingleBody().getId());

        commonMsg.getBody().setListBody(nullEntityList);

        BizResponseEnum.DATA_NOT_FOUND.assertNotNull(sysMsgNoticeEntity,commonMsg);
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

        commonMsg.getBody().setSingleBody(sysMsgNoticeEntity);
        //设置返回值
        CommonUtil.successReturn(commonMsg);
        return commonMsg;
    }

    /**
     * 查询消息提示列表
     * @param commonMsg
     * @return
     */
    @Override
    public CommonMsg<NullEntity, SysMsgNoticeEntity> querySysMsgNoticeList(CommonMsg<NullEntity, SysMsgNoticeEntity> commonMsg) {
        CommonUtil.service(commonMsg);
        // 设定只查当前租户ID条件
        String userName =  commonMsg.getHead().getUserName();
        SysMsgNoticeEntity sysMsgNoticeEntity = new SysMsgNoticeEntity();
        sysMsgNoticeEntity.setAcceptMsgUserName(userName);
        sysMsgNoticeEntity.setTenantId(commonMsg.getHead().getTenantId());
        List<SysMsgNoticeEntity> noticeList = sysMsgNoticeMapper.querySysMsgNoticeList(sysMsgNoticeEntity);
        //按照发送对象处理归档列表
        Map<String, SysMsgNoticeEntity> msgListMap = new HashMap<>();
        Map<String, Integer> notreadCntMap = new HashMap<>();
        Map<String, String> userHeadImgMap = new HashMap<>();
        //按时间先后处理接收人是我，和发送人是我；
        for(SysMsgNoticeEntity msgNoticeEntity : noticeList){
            if(!msgListMap.containsKey(msgNoticeEntity.getBizSceneName() +"#"+msgNoticeEntity.getSendMsgUserName()) && !msgListMap.containsKey(msgNoticeEntity.getBizSceneName() +"#"+msgNoticeEntity.getAcceptMsgUserName())){
                if(msgNoticeEntity.getSendMsgUserName().equals(userName)) {
                    msgListMap.put(msgNoticeEntity.getBizSceneName() + "#" + msgNoticeEntity.getAcceptMsgUserName(), msgNoticeEntity);
                }
                else{
                    msgListMap.put(msgNoticeEntity.getBizSceneName() + "#" + msgNoticeEntity.getSendMsgUserName(), msgNoticeEntity);
                }
            }
            //累加未读数量
            if(msgNoticeEntity.getAcceptMsgUserName().equals(userName) && msgNoticeEntity.getReadStatus().equals("0")){
                if(notreadCntMap.containsKey(msgNoticeEntity.getBizSceneName() + "#" + msgNoticeEntity.getSendMsgUserName())){
                    notreadCntMap.put(msgNoticeEntity.getBizSceneName() + "#" + msgNoticeEntity.getSendMsgUserName(),notreadCntMap.get(msgNoticeEntity.getBizSceneName() + "#" + msgNoticeEntity.getSendMsgUserName())+1);
                }
                else{
                    notreadCntMap.put(msgNoticeEntity.getBizSceneName() + "#" + msgNoticeEntity.getSendMsgUserName(),1);
                }
            }
            if(!userHeadImgMap.containsKey(msgNoticeEntity.getBizSceneName() + "#" + msgNoticeEntity.getSendMsgUserName()) || StringUtil.isEmpty(userHeadImgMap.get(msgNoticeEntity.getBizSceneName() + "#" + msgNoticeEntity.getSendMsgUserName()))){
                userHeadImgMap.put(msgNoticeEntity.getBizSceneName() + "#" + msgNoticeEntity.getSendMsgUserName(),msgNoticeEntity.getSenderHeadImgUrl());
            }
        }

        //遍历消息map整理成输出数据
        noticeList = new ArrayList<>();
        for (Map.Entry<String, SysMsgNoticeEntity> entry : msgListMap.entrySet()) {
            String[] keyParam = entry.getKey().split("#");
            SysMsgNoticeEntity msgNoticeEntity = entry.getValue();
            //当发送人是自己时处理消息头像等信息
            if(msgNoticeEntity.getSendMsgUserName().equals(userName)){
                msgNoticeEntity.setSendMsgUserName(msgNoticeEntity.getAcceptMsgUserName());
                msgNoticeEntity.setSendRealName(msgNoticeEntity.getAcceptRealName());
                if(userHeadImgMap.containsKey(entry.getKey()) && StringUtils.isNotEmpty(userHeadImgMap.get(entry.getKey()))) {
                    msgNoticeEntity.setSenderHeadImgUrl(userHeadImgMap.get(entry.getKey()));
                }
                else{ //如果以上方式获取不到图片则从数据库中获取用户头像
                    QueryWrapper<SysUserInfoEntity> queryWrapper =new QueryWrapper<SysUserInfoEntity>();
                    queryWrapper.lambda().eq(SysUserInfoEntity::getTenantId,commonMsg.getHead().getTenantId());
                    queryWrapper.lambda().eq(SysUserInfoEntity::getUserName,msgNoticeEntity.getSendMsgUserName());
                    SysUserInfoEntity sysUserInfoEntity = sysUserInfoMapper.selectOne(queryWrapper);
                    if(!StringUtil.isEmpty(sysUserInfoEntity)){
                        msgNoticeEntity.setSenderHeadImgUrl(sysUserInfoEntity.getHeadImgUrl());
                    }
                }
            }
            //将未读条数输出
            if(notreadCntMap.containsKey(entry.getKey())){
                msgNoticeEntity.setNotReadCnt(notreadCntMap.get(entry.getKey()));
            }
            if(msgNoticeEntity.getMsgType().equals("0")){
                msgNoticeEntity.setMsgTitle(msgNoticeEntity.getMsgContent());
                msgNoticeEntity.setBizSceneName(msgNoticeEntity.getSendRealName());
            }
            else{
                if(msgNoticeEntity.getBizSceneName().equals("会议通知")){
                }
                else {
                    msgNoticeEntity.setMsgTitle("您有一条待办事项，请点击查看>>");
                }
            }
            noticeList.add(msgNoticeEntity);
        }
        commonMsg.getBody().setListBody(noticeList);
        //设置返回值
        CommonUtil.successReturn(commonMsg);
        return commonMsg;
    }

    /**
     * 查询消息列表
     * @param commonMsg
     * @return
     */
    @Override
    public CommonMsg<SysMsgNoticeEntity, SysMsgNoticeEntity> querySysMsgList(CommonMsg<SysMsgNoticeEntity, SysMsgNoticeEntity> commonMsg) {
        CommonUtil.service(commonMsg);
        // 设定只查当前租户ID条件
        String userName =  commonMsg.getHead().getUserName();
        SysMsgNoticeEntity sysMsgNoticeEntity = commonMsg.getBody().getSingleBody();
        sysMsgNoticeEntity.setAcceptMsgUserName(userName);
        sysMsgNoticeEntity.setTenantId(commonMsg.getHead().getTenantId());
        List<SysMsgNoticeEntity> noticeList = sysMsgNoticeMapper.querySysMsgList(sysMsgNoticeEntity);

        commonMsg.getBody().setListBody(noticeList);
        //设置返回值
        CommonUtil.successReturn(commonMsg);
        return commonMsg;
    }

    @Override
    public CommonMsg<SysMsgNoticeEntity, SysMsgNoticeEntity> querySysMsgNoticeListPage(CommonMsg<SysMsgNoticeEntity, SysMsgNoticeEntity> commonMsg) {
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
                if (!(StringUtil.isNumeric(entry.getValue().toString()) && (entry.getValue().equals("0")  || entry.getValue().equals("0.0") || entry.getValue().equals(0) || entry.getValue().equals(0.0)))) {
                    log.debug(entry.getKey() + "========解析Single body 组装查询条件==" + String.valueOf(entry.getValue()));
                    SQLConditionEntity sqlConditionEntity0 = new SQLConditionEntity();
                    sqlConditionEntity0.setColumn(entry.getKey());
                    sqlConditionEntity0.setType("=");
                    if(entry.getKey().equals("acceptMsgUserName")){
                        sqlConditionEntity0.setType("like");
                    }
                    if(entry.getKey().equals("acceptRealName")){
                        sqlConditionEntity0.setType("like");
                    }
                    if(entry.getKey().equals("sendMsgUserName")){
                        sqlConditionEntity0.setType("like");
                    }
                    if(entry.getKey().equals("sendRealName")){
                        sqlConditionEntity0.setType("like");
                    }
                    sqlConditionEntity0.setValue(String.valueOf(entry.getValue()));
                    sqlConditionList.add(sqlConditionEntity0);
                    isInSingleBody = true;
                }
            }
        }
        // 如果搜索查询为空，并且过滤器查询有值时将过滤器的查询条件赋值给sqlConditionList
        QueryWrapper<SysMsgNoticeEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysMsgNoticeEntity.class);
        if (!isInSingleBody && commonMsg.getBody().getSqlCondition().size() > 0) {
            sqlConditionList.addAll(commonMsg.getBody().getSqlCondition());
            queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysMsgNoticeEntity.class);
        }
        //处理OR查询条件
        else if(!isInSingleBody && commonMsg.getBody().getSqlOrCondition().size() > 0) {
            sqlConditionList.addAll(commonMsg.getBody().getSqlOrCondition());
            queryWrapper = QueryWrapperUtil.parseWhereOrSql(sqlConditionList, SysMsgNoticeEntity.class);
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
        Page<SysMsgNoticeEntity> pages = new Page<>();
        // 当前页
        pages.setCurrent(commonMsg.getBody().getCurrentPage());
        // 每页条数
        pages.setSize(commonMsg.getBody().getPageSize());
        IPage<SysMsgNoticeEntity> list =  super.page(pages, queryWrapper);

        commonMsg.getBody().setListBody(list.getRecords());
        commonMsg.getBody().setCurrentPage((int) list.getCurrent());
        commonMsg.getBody().setPageSize((int) list.getSize());
        commonMsg.getBody().setTotal(list.getTotal());
        //设置返回值
        CommonUtil.successReturn(commonMsg);
        return commonMsg;
    }

    @Override
    public CommonMsg<SysMsgNoticeEntity, SysMsgNoticeEntity> querySysMsgNoticeListMap(
            CommonMsg<SysMsgNoticeEntity, SysMsgNoticeEntity> commonMsg) {
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
        Collection<SysMsgNoticeEntity> sysMsgNoticeEntityList =  super.listByMap(conditionMap);
        List<SysMsgNoticeEntity> sysMsgNoticeMapList = new ArrayList<SysMsgNoticeEntity>(sysMsgNoticeEntityList);
        commonMsg.getBody().setListBody(sysMsgNoticeMapList);
        //设置返回值
        CommonUtil.successReturn(commonMsg);
        return commonMsg;
    }

    @Override
    @CachePut(value = "sysMsgNotice", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
    public CommonMsg<SysMsgNoticeEntity, NullEntity> saveSysMsgNotice(CommonMsg<SysMsgNoticeEntity, NullEntity> commonMsg) {
        CommonUtil.service(commonMsg);
        // 如果内容为空返回错误信息
        if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
            return commonMsg;
        }
        // 如果内容为空且不合法返回错误信息
        if (! CommonUtil.commonMsgValidation(commonMsg)) {
            return commonMsg;
        }
//        commonMsg.getBody().getSingleBody().setDataSign(DooleenMD5Util.md5(commonMsg.getBody().getSingleBody().toString(),  ConstantValue.md5Key));
//        // 执行保存
//        boolean result =  super.save(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(commonMsg.getBody().getSingleBody(), commonMsg.getHead(),tableName, seqPrefix, redisTemplate));
//        BizResponseEnum.SAVE_DATA_FAIL.assertIsTrue(result,commonMsg);

        //直接推送消息给用户
        log.debug("===>> 开始发送消息给用户："+commonMsg.getBody().getSingleBody().getAcceptRealName());
        SysMsgNoticeEntity sysMsgNoticeEntity = commonMsg.getBody().getSingleBody();
        SysSendLogEntity sysSendLogEntity = new SysSendLogEntity();
        sysSendLogEntity.setTenantId(commonMsg.getHead().getTenantId());
        sysSendLogEntity.setMsgNoticeType("0");
        sysSendLogEntity.setSenderRealName(sysMsgNoticeEntity.getSendRealName());
        sysSendLogEntity.setSenderId(sysMsgNoticeEntity.getSendMsgUserName());
        sysSendLogEntity.setHeadImgUrl(sysMsgNoticeEntity.getSenderHeadImgUrl());
        sysSendLogEntity.setSendAddressList("[\""+sysMsgNoticeEntity.getAcceptMsgUserName()+"\"]");   //("[\"admin\",\"liqh\"]");  // all 表示发送全部
        sysSendLogEntity.setBizSceneName(sysMsgNoticeEntity.getBizSceneName());
        sysSendLogEntity.setMsgTitle(sysMsgNoticeEntity.getMsgTitle());
        sysSendLogEntity.setMsgContent(sysMsgNoticeEntity.getMsgContent());
        sysSendLogEntity.setMsgContentJson("{ \"first\": \""+sysMsgNoticeEntity.getMsgTitle()+"\", \"label1\":\"发送人\", \"keyword1\": \""+sysMsgNoticeEntity.getSendRealName()+"\", \"label2\":\"发送时间\", \"keyword2\": \""+sysMsgNoticeEntity.getCreateDatetime()+"\"}");
        msgSendUtil.sendMsgToMq(sysSendLogEntity);

        //设置返回值
        CommonUtil.successReturn(commonMsg);
        return commonMsg;
    }

    @Override
    public CommonMsg<NullEntity, SysMsgNoticeEntity> saveSysMsgNoticeList(CommonMsg<NullEntity, SysMsgNoticeEntity> commonMsg) {
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
    @CachePut(value = "sysMsgNotice", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and #commonMsg.head.cacheAble eq 'true'" )
    public CommonMsg<SysMsgNoticeEntity, NullEntity> updateSysMsgNotice(CommonMsg<SysMsgNoticeEntity, NullEntity> commonMsg) {
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
        CommonMsg<SysMsgNoticeEntity, NullEntity> queryCommonMsg = new CommonMsg<SysMsgNoticeEntity, NullEntity>();
        //定义body
        MutBean<SysMsgNoticeEntity, NullEntity> mutBean= new MutBean<SysMsgNoticeEntity, NullEntity>();
        FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
        mutBean.setSingleBody(commonMsg.getBody().getSingleBody());
        mutBean.setFlowArea(flowArea);
        //深拷贝数据进行AOP查询
        queryCommonMsg.setHead(commonMsg.getHead());
        queryCommonMsg.setBody(mutBean);
        queryCommonMsg = SpringUtil.getBean(SysMsgNoticeServiceImpl.class).querySysMsgNotice(queryCommonMsg);

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
    @CachePut(value = "sysMsgNotice", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id",condition = "#commonMsg.head.respCode eq 'S0000' and  #commonMsg.head.cacheAble eq 'true'" )
    public CommonMsg<SysMsgNoticeEntity, NullEntity> saveOrUpdateSysMsgNotice(CommonMsg<SysMsgNoticeEntity, NullEntity> commonMsg) {
        CommonUtil.service(commonMsg);
        // 如果内容为空返回错误信息
        if (! CommonUtil.commonMsgSingleBodyIsNotNull(commonMsg)) {
            return commonMsg;
        }
        // 如果ID为空返回错误信息
        if (! CommonUtil.commonMsgValidationId(commonMsg)) {
            return commonMsg;
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
    public CommonMsg<NullEntity, SysMsgNoticeEntity> saveOrUpdateSysMsgNoticeList(CommonMsg<NullEntity, SysMsgNoticeEntity> commonMsg) {
        CommonUtil.service(commonMsg);
        // 如果内容为空返回错误信息
        if (!CommonUtil.commonMsgListBodyLength(commonMsg, "noCheck")) {
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
    @CacheEvict(value = "sysMsgNotice", key = "#commonMsg.head.tenantId+':'+#commonMsg.body.singleBody.id", condition = "#commonMsg.head.cacheAble eq 'true'")
    public CommonMsg<SysMsgNoticeEntity, NullEntity> deleteSysMsgNotice(CommonMsg<SysMsgNoticeEntity, NullEntity> commonMsg) {
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
    public CommonMsg<NullEntity, SysMsgNoticeEntity> deleteSysMsgNoticeList(CommonMsg<NullEntity, SysMsgNoticeEntity> commonMsg) {
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
    public void exportSysMsgNoticeExcel(CommonMsg<SysMsgNoticeEntity, SysMsgNoticeEntity> commonMsg, HttpServletResponse response) {
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

        QueryWrapper<SysMsgNoticeEntity> queryWrapper = QueryWrapperUtil.parseWhereSql(sqlConditionList, SysMsgNoticeEntity.class);

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
        Page<SysMsgNoticeEntity> pages = new Page<>();
        // 当前页
        pages.setCurrent(commonMsg.getBody().getCurrentPage());
        // 每页条数
        pages.setSize(commonMsg.getBody().getPageSize());
        IPage<SysMsgNoticeEntity> list =  super.page(pages, queryWrapper);
        commonMsg.getBody().setListBody(list.getRecords());
        //防止导出异常，如果列表为null，初始化一条空记录
        if(list.getRecords().size() == 0){
            List<SysMsgNoticeEntity> listEntity = new ArrayList<SysMsgNoticeEntity>();
            listEntity.add(new SysMsgNoticeEntity());
            commonMsg.getBody().setListBody(listEntity);
        }
        /**
         * 生成excel并输出
         */
        Date start = new Date();
        String sheetName = "消息通知表";
        String fileName = "消息通知表-"+start.getTime();
        ExportParams params = new ExportParams();
        Workbook workbook = ExcelExportUtil.exportExcel(params, SysMsgNoticeEntity.class, commonMsg.getBody().getListBody());
        //导出Excel数据流
        try {
            ExcelUtil.exportPoi(response,workbook,fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("====exportSysMsgNoticeExcel service end == " + RespStatus.json.getString("S0000"));
    }
    /**
     * 导入excel新增
     */
    @Override
    public CommonMsg<SysMsgNoticeEntity, NullEntity> uploadExcel(MultipartFile file, HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        CommonMsg<SysMsgNoticeEntity,NullEntity> commonMsg = new CommonMsg<SysMsgNoticeEntity,NullEntity>();
        HeadEntity head =new HeadEntity();
        commonMsg.setHead(head);
        commonMsg.getHead().setTenantId(request.getHeader("tenantid"));
        commonMsg.getHead().setUserName(request.getHeader("username"));
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        List<SysMsgNoticeEntity> list = new ArrayList<>();
        try {
            commonMsg.getHead().setRealName(decode(request.getHeader("realname").toString(),"UTF-8"));
            list = ExcelImportUtil.importExcel(file.getInputStream(), SysMsgNoticeEntity.class, params);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("E0002","导入出现异常！");
            commonMsg.getHead().setRespCode(RespStatus.errorCode);
            commonMsg.getHead().setRespMsg(map);
            return commonMsg;
        }
        //批量导入数据并做校验
        List<SysMsgNoticeEntity> tmpList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SysMsgNoticeEntity sysMsgNoticeEntity = list.get(i);

            //检查字段的有效性
            ValidationResult validationResult = ValidationUtils.validateEntity(sysMsgNoticeEntity);
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
                sysMsgNoticeEntity.setDataSign(DooleenMD5Util.md5(sysMsgNoticeEntity.toString(),  ConstantValue.md5Key));
                // 初始化数据
                sysMsgNoticeEntity = EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(sysMsgNoticeEntity, commonMsg.getHead(),tableName, seqPrefix, redisTemplate);
                tmpList.add(sysMsgNoticeEntity);
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
