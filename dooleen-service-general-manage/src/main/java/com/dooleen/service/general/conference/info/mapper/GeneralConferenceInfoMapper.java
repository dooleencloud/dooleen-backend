package com.dooleen.service.general.conference.info.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.service.general.conference.info.entity.GeneralConferenceInfoEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2021-04-26 14:09:14
 * @Description : 会议信息表DAO
 * @Author : apple
 * @Update: 2021-04-26 14:09:14
 */
@Service
public interface GeneralConferenceInfoMapper extends BaseMapper<GeneralConferenceInfoEntity> {
    /**
     * 按时间段查询会议
     * @param tenantId
     * @param startTime
     * @param endTime
     * @param conferenceRoomId
     * @return
     */
    @Select("<script>"
            + "SELECT  " +
            "	*  " +
            "FROM  " +
            "	general_conference_info  " +
            "WHERE  " +
            "	tenant_id = #{tenantId}  " +
            "AND (  " +
            "	(  " +
            "		conference_begin_datetime BETWEEN #{startTime}  " +
            "		AND #{endTime}  " +
            "	)  " +
            "	OR (  " +
            "		conference_end_datetime BETWEEN #{startTime}  " +
            "		AND #{endTime}  " +
            "	)  " +
            "	OR (  " +
            "		conference_begin_datetime &lt;= #{startTime}  " +
            "		AND conference_end_datetime &gt;= #{endTime}  " +
            "	)  " +
            ")  " +
            "<if test='conferenceRoomId != \"\"'>"+
              "and conference_room_id=#{conferenceRoomId}  "+
            "</if>"+
            "AND valid_flag = '1'"
            + "</script>")
    List<GeneralConferenceInfoEntity> selectConferenceDuration(@Param("tenantId") String tenantId,
                                                               @Param("startTime") String startTime,
                                                               @Param("endTime") String endTime,
                                                               @Param("conferenceRoomId") String conferenceRoomId);

    /**
     * 检查会议室冲突
     * @param tenantId
     * @param startTime
     * @param endTime
     * @param conferenceRoomId
     * @return
     */
    @Select("<script>"
            + "SELECT  " +
            "	count(*)  " +
            "FROM  " +
            "	general_conference_info  " +
            "WHERE  " +
            "	 tenant_id = #{tenantId}  " +
            "<if test='conferenceType != \"\"'>"+
            "AND conference_type=#{conferenceType}  "+
            "</if>"+
            "<if test='conferenceRoomId != \"\"'>"+
            "AND conference_room_id=#{conferenceRoomId}  "+
            "</if>"+
            "AND (  " +
            "	(  " +
            "		conference_begin_datetime BETWEEN #{startTime}  " +
            "		AND #{endTime}  " +
            "	)  " +
            "	OR (  " +
            "		conference_end_datetime BETWEEN #{startTime}  " +
            "		AND #{endTime}  " +
            "	)  " +
            ")  " +
            "AND valid_flag = '1'"
            + "</script>")
    int selectConferenceConflict(@Param("tenantId") String tenantId,
                                 @Param("startTime") String startTime,
                                 @Param("endTime") String endTime,
                                 @Param("conferenceType") String conferenceType,
                                 @Param("conferenceRoomId") String conferenceRoomId);

    /**
     * 检查会议人员时间冲突
     * @param tenantId
     * @param startTime
     * @param endTime
     * @param conferenceRoomId
     * @return
     */
    @Select("<script>"
            + "SELECT  " +
            "	*  " +
            "FROM  " +
            "	general_conference_info  " +
            "WHERE  " +
            "	 tenant_id = #{tenantId}  " +
            "AND (  " +
            "	(  " +
            "		conference_begin_datetime BETWEEN #{startTime}  " +
            "		AND #{endTime}  " +
            "	)  " +
            "	OR (  " +
            "		conference_end_datetime BETWEEN #{startTime}  " +
            "		AND #{endTime}  " +
            "	)  " +
            ")  " +
            "AND valid_flag = '1'"
            + "</script>")
    List<GeneralConferenceInfoEntity> selectConferenceUserConflict(@Param("tenantId") String tenantId,
                                 @Param("startTime") String startTime,
                                 @Param("endTime") String endTime);

}
