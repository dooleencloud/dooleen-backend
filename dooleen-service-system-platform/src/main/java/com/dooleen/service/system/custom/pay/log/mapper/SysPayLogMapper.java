package com.dooleen.service.system.custom.pay.log.mapper;

import java.util.List;

import com.dooleen.service.system.custom.pay.log.entity.SysPayLogEntity;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Service;

/**
 * @Copy Right Information : 北京数云信息服务有限公司
 * @Project : Learning平台
 * @Project No : Learning
 * @Version : 1.0.0
 * @CreateDate : 2020-11-17 21:38:54
 * @Description : 支付日志管理DAO
 * @Author : apple
 * @Update: 2020-11-17 21:38:54
 */
@Service
public interface SysPayLogMapper extends BaseMapper<SysPayLogEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysPayLogEntity> querySysPayLogByCondition(@Param("sysPayLogEntity") SysPayLogEntity sysPayLogEntity);
}
