package com.dooleen.service.general.conference.room.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.general.conference.room.entity.GeneralConferenceRoomEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-25 16:35:56
 * @Description : 会议室管理DAO
 * @Author : apple
 * @Update: 2020-08-25 16:35:56
 */
public interface GeneralConferenceRoomMapper extends BaseMapper<GeneralConferenceRoomEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<GeneralConferenceRoomEntity> queryGeneralConferenceRoomByCondition(@Param("generalConferenceRoomEntity") GeneralConferenceRoomEntity generalConferenceRoomEntity);
}
