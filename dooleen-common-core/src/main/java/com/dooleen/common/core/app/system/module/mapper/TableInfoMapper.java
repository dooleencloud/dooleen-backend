package com.dooleen.common.core.app.system.module.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.system.module.entity.TableInfo;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-07-22 10:20:14
 * @Description : 系统数据表管理DAO
 * @Author : apple
 * @Update: 2020-07-22 10:20:14
 */
public interface TableInfoMapper extends BaseMapper<TableInfo> {

	@Select("<script>"
			+ "SELECT TABLE_NAME AS tableName FROM information_schema.`TABLES` where TABLE_SCHEMA = #{tenantId}"
			+ "</script>")
	List<TableInfo> selectTableNames(@Param("tenantId") String tenantId);
	
}
