package com.dooleen.common.core.app.system.module.mapper;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dooleen.common.core.app.system.module.entity.SysModuleConfigInfoEntity;
import org.springframework.stereotype.Service;

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
@Service
public interface SysModuleConfigInfoMapper extends BaseMapper<SysModuleConfigInfoEntity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("<script>"
    		+ "select * from ${tableName} where tenant_id = #{sourceTenantId} "
    		
			+ "<if test='tableName==\"sys_menu\"'>"  
			+ 	"  and route_address not like '/system/sysTenantInfo%'  "
			+ "</if>"
			
    		+ "</script>")
	List<LinkedHashMap<String, Object>> dynamicSelectData(@Param("sourceTenantId") String sourceTenantId, 
											    @Param("tableName") String tableName);

    @Insert("${sql}")
	int insertBaseDataBatch(@Param("sql") String sql);

    @Delete("${sql}")
	int deleteBaseDataBatch(@Param("sql") String sql);
}
