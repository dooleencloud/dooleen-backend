package com.dooleen.service.general.staff.station.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.general.staff.station.entity.GeneralStaffStationEntity;
import org.springframework.stereotype.Service;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-10-10 10:15:54
 * @Description : 工位信息表DAO
 * @Author : apple
 * @Update: 2020-10-10 10:15:54
 */
@Service
public interface GeneralStaffStationMapper extends BaseMapper<GeneralStaffStationEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<GeneralStaffStationEntity> queryGeneralStaffStationListByCondition(@Param("generalStaffStationEntity") GeneralStaffStationEntity generalStaffStationEntity);
}
