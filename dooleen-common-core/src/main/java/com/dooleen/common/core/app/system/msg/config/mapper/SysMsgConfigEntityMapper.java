package com.dooleen.common.core.app.system.msg.config.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.system.msg.config.entity.SysMsgConfigEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-13 19:12:18
 * @Description : 消息配置管理DAO
 * @Author : apple
 * @Update: 2020-07-13 19:12:18
 */
@Mapper
public interface SysMsgConfigEntityMapper extends BaseMapper<SysMsgConfigEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysMsgConfigEntity> queryMessageConfigInfoByCondition(@Param("messageConfigInfoEntity") SysMsgConfigEntity messageConfigInfoEntity);

    @Select("<script>"
    		+ "SELECT  " + 
    		"	send_scene AS sendScene,  " + 
    		"	msg_type AS msgType,  " + 
    		"	template_id AS templateId,  " + 
    		"	msg_template_content AS msgTemplateContent  " + 
    		"FROM  " + 
    		"	sys_msg_config  " + 
    		"WHERE  " + 
    		"   tenant_id = #{tenantId}  " + 
    		"AND send_scene = #{sendScene}  " + 
    		"AND valid_flag = '1'"
    		+ "</script>")
	List<SysMsgConfigEntity> selectByScene(@Param("tenantId") String tenantId,
			                               @Param("sendScene") String sendScene);
}
