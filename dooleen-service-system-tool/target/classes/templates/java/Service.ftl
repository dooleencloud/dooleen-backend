package ${sysToolTablesEntity.packageName}.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ${sysToolTablesEntity.packageName}.entity.${sysToolTablesEntity.tableName ? cap_first}Entity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : ${.now?string("yyyy-MM-dd HH:mm:ss")}
 * @Description : ${sysToolTablesEntity.serviceName}服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface ${sysToolTablesEntity.tableName ? cap_first}Service extends IService<${sysToolTablesEntity.tableName ? cap_first}Entity> {

	/**
	 * 
	 * 根据主键查询记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<${sysToolTablesEntity.tableName ? cap_first}Entity, NullEntity> query${sysToolTablesEntity.tableName ? cap_first}(CommonMsg<${sysToolTablesEntity.tableName ? cap_first}Entity, NullEntity> commonMsg);

	/**
	 * 
	 * 获取所有记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,${sysToolTablesEntity.tableName ? cap_first}Entity> query${sysToolTablesEntity.tableName ? cap_first}List(CommonMsg<NullEntity,${sysToolTablesEntity.tableName ? cap_first}Entity> commonMsg);

	/**
	 * 
	 * 根据条件分页查询
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<${sysToolTablesEntity.tableName ? cap_first}Entity,${sysToolTablesEntity.tableName ? cap_first}Entity> query${sysToolTablesEntity.tableName ? cap_first}ListPage(CommonMsg<${sysToolTablesEntity.tableName ? cap_first}Entity,${sysToolTablesEntity.tableName ? cap_first}Entity> commonMsg);

	/**
	 * 
	 * 根据条件查询Map集合
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<${sysToolTablesEntity.tableName ? cap_first}Entity,${sysToolTablesEntity.tableName ? cap_first}Entity> query${sysToolTablesEntity.tableName ? cap_first}ListMap(CommonMsg<${sysToolTablesEntity.tableName ? cap_first}Entity,${sysToolTablesEntity.tableName ? cap_first}Entity> commonMsg);

	/**
	 * 
	 * 新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<${sysToolTablesEntity.tableName ? cap_first}Entity, NullEntity> save${sysToolTablesEntity.tableName ? cap_first}(CommonMsg<${sysToolTablesEntity.tableName ? cap_first}Entity, NullEntity> commonMsg);

	/**
	 * 
	 * 新增多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity,${sysToolTablesEntity.tableName ? cap_first}Entity> save${sysToolTablesEntity.tableName ? cap_first}List(CommonMsg<NullEntity,${sysToolTablesEntity.tableName ? cap_first}Entity> commonMsg);
	
	/**
	 * 
	 * 根据主键修改记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<${sysToolTablesEntity.tableName ? cap_first}Entity, NullEntity> update${sysToolTablesEntity.tableName ? cap_first}(CommonMsg<${sysToolTablesEntity.tableName ? cap_first}Entity, NullEntity> commonMsg);

	/**
	 * 
	 * 如果存在修改录，不存在就新增记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<${sysToolTablesEntity.tableName ? cap_first}Entity, NullEntity> saveOrUpdate${sysToolTablesEntity.tableName ? cap_first}(CommonMsg<${sysToolTablesEntity.tableName ? cap_first}Entity, NullEntity> commonMsg);
	/**
	 * 
	 * 批量添加或更新记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, ${sysToolTablesEntity.tableName ? cap_first}Entity>  saveOrUpdate${sysToolTablesEntity.tableName ? cap_first}List(CommonMsg<NullEntity, ${sysToolTablesEntity.tableName ? cap_first}Entity> commonMsg);

	/**
	 * 
	 * 根据主键删除记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<${sysToolTablesEntity.tableName ? cap_first}Entity, NullEntity> delete${sysToolTablesEntity.tableName ? cap_first}(CommonMsg<${sysToolTablesEntity.tableName ? cap_first}Entity, NullEntity> commonMsg);
	
	/**
	 * 
	 * 删除多条记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	CommonMsg<NullEntity, ${sysToolTablesEntity.tableName ? cap_first}Entity> delete${sysToolTablesEntity.tableName ? cap_first}List(CommonMsg<NullEntity, ${sysToolTablesEntity.tableName ? cap_first}Entity> commonMsg);

	/**
	 * 导出数据
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月20日 上午8:46:28
	 * @Param : commonMsg 
	 * @Return : 
	 */
	void export${sysToolTablesEntity.tableName ? cap_first}Excel(CommonMsg<${sysToolTablesEntity.tableName ? cap_first}Entity,${sysToolTablesEntity.tableName ? cap_first}Entity> commonMsg,HttpServletResponse response);

	/**
	 * 批量导入数据
	 * @param file
	 * @return
	 */
	CommonMsg<${sysToolTablesEntity.tableName ? cap_first}Entity, NullEntity> uploadExcel(MultipartFile file, HttpServletRequest request) throws IOException;
}
