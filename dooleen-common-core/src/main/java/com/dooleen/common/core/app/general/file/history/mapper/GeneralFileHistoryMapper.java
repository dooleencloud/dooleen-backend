package com.dooleen.common.core.app.general.file.history.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.general.file.history.entity.GeneralFileHistoryEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-21 07:48:23
 * @Description : 文档历史管理DAO
 * @Author : apple
 * @Update: 2020-06-21 07:48:23
 */
@Service
public interface GeneralFileHistoryMapper extends BaseMapper<GeneralFileHistoryEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<GeneralFileHistoryEntity> queryGeneralFileHistoryByCondition(@Param("generalFileHistoryEntity") GeneralFileHistoryEntity generalFileHistoryEntity);
}
