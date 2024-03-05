package ${sysToolTablesEntity.packageName}.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${sysToolTablesEntity.packageName}.entity.${sysToolTablesEntity.tableName ? cap_first}Entity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : ${.now?string("yyyy-MM-dd HH:mm:ss")}
 * @Description : ${sysToolTablesEntity.serviceName}DAO
 * @Author : apple
 * @Update: ${.now?string("yyyy-MM-dd HH:mm:ss")}
 */
@Service
public interface ${sysToolTablesEntity.tableName ? cap_first}Mapper extends BaseMapper<${sysToolTablesEntity.tableName ? cap_first}Entity> {

    /**
     * 自定义查询模板
     * @Author liqh
     * @CreateTime 2020/6/9 14:28
     * @Param  page  分页参数
     */
    @Select("SELECT * FROM sys_tables WHERE test=#/{test}")
    List<${sysToolTablesEntity.tableName ? cap_first}Entity> query${sysToolTablesEntity.tableName ? cap_first}ByCondition(@Param("${sysToolTablesEntity.tableName}Entity") ${sysToolTablesEntity.tableName ? cap_first}Entity ${sysToolTablesEntity.tableName}Entity);
}
