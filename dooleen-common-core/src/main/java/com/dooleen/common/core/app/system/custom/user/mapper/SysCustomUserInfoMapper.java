package com.dooleen.common.core.app.system.custom.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.system.custom.user.entity.SysCustomUserInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-02-21 16:40:24
 * @Description : 系统客户用户表DAO
 * @Author : apple
 * @Update: 2021-02-21 16:40:24
 */
@Service
public interface SysCustomUserInfoMapper extends BaseMapper<SysCustomUserInfoEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysCustomUserInfoEntity> querySysCustomUserInfoByCondition(@Param("sysCustomUserInfoEntity") SysCustomUserInfoEntity sysCustomUserInfoEntity);
}
