package com.dooleen.service.system.calender.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.system.calender.entity.SysCalenderEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-07-02 20:34:51
 * @Description : 系统日历管理DAO
 * @Author : apple
 * @Update: 2021-07-02 20:34:51
 */
@Service
public interface SysCalenderMapper extends BaseMapper<SysCalenderEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysCalenderEntity> querySysCalenderByCondition(@Param("sysCalenderEntity") SysCalenderEntity sysCalenderEntity);
}
