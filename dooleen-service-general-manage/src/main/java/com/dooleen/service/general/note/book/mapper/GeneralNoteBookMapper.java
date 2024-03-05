package com.dooleen.service.general.note.book.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.general.note.book.entity.GeneralNoteBookEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-23 17:01:01
 * @Description : 业务需求管理DAO
 * @Author : apple
 * @Update: 2020-07-23 17:01:01
 */
public interface GeneralNoteBookMapper extends BaseMapper<GeneralNoteBookEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<GeneralNoteBookEntity> queryGeneralNoteBookByCondition(@Param("generalNoteBookEntity") GeneralNoteBookEntity generalNoteBookEntity);
}
