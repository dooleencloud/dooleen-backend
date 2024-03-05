package com.dooleen.common.core.app.system.user.info.mapper;

import java.util.List;
import java.util.Map;

import com.dooleen.common.core.common.entity.SendMsgUserInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.system.user.info.entity.SysUserInfoEntity;
import org.springframework.stereotype.Service;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-06 19:37:42
 * @Description : 系统用户管理DAO
 * @Author : apple
 * @Update: 2020-06-06 19:37:42
 */
@Mapper
@Service
public interface SysUserInfoMapper extends BaseMapper<SysUserInfoEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2019/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<SysUserInfoEntity> querySysUserInfoByCondition(String test);

    @Select("<script>"
    		+ "SELECT  " +
    		"   id, " +
    		"   real_name AS realName, " +
    		"	email,  " +
    		"	wx_open_id AS wxOpenId  " +
    		"FROM  " +
    		"	sys_user_info  " +
    		"WHERE  " +
			"tenant_id=#{tenantId} and"+
    		"	id IN  " +
			"<foreach item=\"item\" index=\"index\" collection=\"receiverUserId\" open=\"(\" separator=\",\" close=\")\">" +
				"#{item}" +
			"</foreach>"
    		+ "</script>")
	List<SysUserInfoEntity> selectByIdList(@Param("tenantId") List<String> tenantId,@Param("receiverUserId") List<String> receiverUserId);


	@Select("<script>"
			+ "SELECT  " +
			"	id," +
			"	user_name," +
			"	real_name," +
			"	wx_open_id," +
			"	client_id," +"	" +
			"	dingtalk_user_id," +
			"   email," +
			"   mobile_no," +
			"   belong_dept_id," +
			"   belong_dept_name," +
			"   head_img_url " +
			"FROM  " +
			"	sys_user_info  " +
			"WHERE  " +
			"tenant_id=#{tenantId} and"+
			"	user_name IN  " +
			"<foreach item=\"item\" index=\"index\" collection=\"userNames\" open=\"(\" separator=\",\" close=\")\">" +
			"#{item}" +
			"</foreach>"
			+ "</script>")
	List<SendMsgUserInfoEntity> selectByUserNameList(@Param("tenantId") String tenantId, @Param("userNames") List<String> userNames);

	SysUserInfoEntity findOneByNickName(@Param("nickName")String nickName);

	SysUserInfoEntity findOneByWxOpenId(@Param("wxOpenId")String wxOpenId);

	@Update("<script>"
			+ "update sys_user_info set wx_open_id = #{openId} where id = #{userId}"
			+ "</script>")
	int updateOpenId(@Param("userId") String id, 
					 @Param("openId") String fromUserName);


	/**
	 * 获取用户对文件的读写权限
	 */
	@Select("select max(read_flag) as read_flag from sys_user_info user, sys_user_role_relation userRole, sys_role_privilege_relation rolePriv "+
			"where user.tenant_id=#{tenantId} " +
			"and  userRole.tenant_id=#{tenantId}  " +
			"and  rolePriv.tenant_id=#{tenantId} " +
			"and user.user_name=#{userName} " +
			"and userRole.user_id = user.id  " +
			"and rolePriv.role_id = userRole.role_id " +
			"and rolePriv.resource_type = '3' " +
			"and rolePriv.resource_id=#{catalogId}")
	Object getUserFilePrivilege(@Param("tenantId") String tenantId, @Param("userName") String userName, @Param("catalogId") String catalogId);


}
