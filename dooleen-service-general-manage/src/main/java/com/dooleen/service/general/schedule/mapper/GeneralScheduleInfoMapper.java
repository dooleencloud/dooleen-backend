package com.dooleen.service.general.schedule.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.general.schedule.entity.GeneralScheduleInfoEntity;
import org.springframework.stereotype.Service;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-08-03 18:44:09
 * @Description : 日程管理DAO
 * @Author : apple
 * @Update: 2020-08-03 18:44:09
 */
@Service
public interface GeneralScheduleInfoMapper extends BaseMapper<GeneralScheduleInfoEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<GeneralScheduleInfoEntity> queryGeneralScheduleInfoByCondition(@Param("generalScheduleInfoEntity") GeneralScheduleInfoEntity generalScheduleInfoEntity);

    @Select("<script>"
    		+ "SELECT  "
    		+ " * " + 
    		"FROM  " + 
    		"	general_schedule_info  " + 
    		"WHERE  " + 
    		"	tenant_id = #{tenantId}  " + 

    		"AND (  " + 
    		"	(  " + 
    		"		schedule_begin_datetime BETWEEN #{startTime}  " + 
    		"		AND #{endTime}  " + 
    		"	)  " + 
    		"	OR (  " + 
    		"		schedule_end_datetime BETWEEN #{startTime}  " + 
    		"		AND #{endTime}  " + 
    		"	)  " + 
    		"	OR (  " + 
    		"		schedule_begin_datetime &lt;= #{startTime}  " + 
    		"		AND schedule_end_datetime &gt;= #{endTime}  " + 
    		"	)  " + 
    		")  " + 
    		
    		"AND valid_flag = '1'  " + 
    		
			"<if test='calendarIds != null and calendarIds.size() > 0'>"  

    		+ "AND calendar_id in  "
    		
			+ "<foreach item=\"item\" index=\"index\" collection=\"calendarIds\" open=\"(\" separator=\",\" close=\")\">"
			+ "#{item}"
			+ "</foreach>"
			
			+ "</if>"
    		
    		+ "</script>")
	List<GeneralScheduleInfoEntity> selectScheduleDuration(String tenantId, String startTime, String endTime,
			List<String> calendarIds);
}
