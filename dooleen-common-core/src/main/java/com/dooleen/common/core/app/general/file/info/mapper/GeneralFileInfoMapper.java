package com.dooleen.common.core.app.general.file.info.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.general.file.info.entity.GeneralFileInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-21 07:40:13
 * @Description : 文档信息管理DAO
 * @Author : apple
 * @Update: 2020-06-21 07:40:13
 */
@Service
public interface GeneralFileInfoMapper extends BaseMapper<GeneralFileInfoEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<GeneralFileInfoEntity> queryGeneralFileInfoByCondition(@Param("generalFileInfoEntity") GeneralFileInfoEntity generalFileInfoEntity);
}
