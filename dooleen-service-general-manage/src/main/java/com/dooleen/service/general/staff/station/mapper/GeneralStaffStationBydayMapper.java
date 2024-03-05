package com.dooleen.service.general.staff.station.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.general.staff.station.entity.GeneralStaffStationBydayEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
public interface GeneralStaffStationBydayMapper extends BaseMapper<GeneralStaffStationBydayEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT count(*) AS normal FROM general_staff_station_byday WHERE tenant_id = #{generalStaffStationBydayEntity.tenantId} AND post_date >= #{generalStaffStationBydayEntity.postDate} AND floor_no = #{generalStaffStationBydayEntity.floorNo} AND belong_dept_name = #{generalStaffStationBydayEntity.belongDeptName} AND attendance_status = '正常'")
//    @Select("SELECT" +
//            " ( SELECT count(*) FROM general_staff_station_byday WHERE tenant_id = #{generalStaffStationBydayEntity.tenantId} AND post_date >= #{generalStaffStationBydayEntity.postDate} AND floor_no = #{generalStaffStationBydayEntity.floorNo} AND belong_dept_name = #{generalStaffStationBydayEntity.belongDeptName} AND attendance_status = '正常' ) AS normal, " +
//            " ( SELECT count(*) FROM general_staff_station_byday WHERE tenant_id = #{generalStaffStationBydayEntity.tenantId} AND post_date >= #{generalStaffStationBydayEntity.postDate} AND floor_no = #{generalStaffStationBydayEntity.floorNo} AND belong_dept_name = #{generalStaffStationBydayEntity.belongDeptName} AND attendance_status = '迟到' ) AS later, " +
//            " ( SELECT count(*) FROM general_staff_station_byday WHERE tenant_id = #{generalStaffStationBydayEntity.tenantId} AND post_date >= #{generalStaffStationBydayEntity.postDate} AND floor_no = #{generalStaffStationBydayEntity.floorNo} AND belong_dept_name = #{generalStaffStationBydayEntity.belongDeptName} AND attendance_status = '请假' ) AS apply, " +
//            " ( SELECT count(*) FROM general_staff_station_byday WHERE tenant_id = #{generalStaffStationBydayEntity.tenantId} AND post_date >= #{generalStaffStationBydayEntity.postDate} AND floor_no = #{generalStaffStationBydayEntity.floorNo} AND belong_dept_name = #{generalStaffStationBydayEntity.belongDeptName} AND attendance_status = '早退' ) AS early, " +
//            " ( SELECT count(*) FROM general_staff_station_byday WHERE tenant_id = #{generalStaffStationBydayEntity.tenantId} AND post_date >= #{generalStaffStationBydayEntity.postDate} AND floor_no = #{generalStaffStationBydayEntity.floorNo} AND belong_dept_name = #{generalStaffStationBydayEntity.belongDeptName} AND attendance_status = '缺卡' ) AS lost")
   int queryGeneralStaffStationListByCondition(@Param("generalStaffStationBydayEntity") GeneralStaffStationBydayEntity generalStaffStationBydayEntity);
}
