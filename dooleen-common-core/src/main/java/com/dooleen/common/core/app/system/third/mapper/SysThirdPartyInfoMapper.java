package com.dooleen.common.core.app.system.third.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.system.third.entity.SysThirdPartyInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-18 15:47:33
 * @Description : 第三方配置管理DAO
 * @Author : apple
 * @Update: 2020-08-18 15:47:33
 */
@Service
public interface SysThirdPartyInfoMapper extends BaseMapper<SysThirdPartyInfoEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysThirdPartyInfoEntity> querySysThirdPartyInfoByCondition(@Param("sysThirdPartyInfoEntity") SysThirdPartyInfoEntity sysThirdPartyInfoEntity);
}
