<#setting number_format="#">
package ${sysToolTablesEntity.packageName}.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.*;
import com.dooleen.common.core.common.entity.BaseEntity;
import com.dooleen.common.core.aop.annos.DataName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
<#function camelToDashed(s)>
  <#return s
      <#-- "fooBar" to "foo_bar": -->
      ?replace('([a-z])([A-Z])', '$1_$2', 'r')
      ?lower_case
  >
</#function>

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : ${.now?string("yyyy-MM-dd HH:mm:ss")}
 * @Description : ${sysToolTablesEntity.serviceName}实体
 * @Author : name
 * @Update : ${.now?string("yyyy-MM-dd HH:mm:ss")}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("${camelToDashed(sysToolTablesEntity.tableName)}")
@ApiModel(value = "${sysToolTablesEntity.serviceName}实体")
@ToString(callSuper = true)
public class ${sysToolTablesEntity.tableName ? cap_first}Entity  extends BaseEntity implements Serializable  {
	//@Excel(name = "状态", replace = {"初始化_0", "正常_1", "注销_2"}, addressList = true)下拉框支持
	private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @DataName(name = "主键", isKeyId = true, isRecordHistory = true)
    @ApiModelProperty(value = "id", required=true, position = 0)
	@TableId(type = IdType.INPUT)
	@NotBlank(message = "ID不能为空")
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String id; 
	
	/**
	* 租户ID
	*/
	@DataName(name = "租户ID")
	@ApiModelProperty(value = "租户ID", required=true, position = 1)
	@NotBlank(message = "租户ID不能为空")
	@TableField(updateStrategy = FieldStrategy.NEVER)
	public String tenantId;
<#list sysToolColumnsList as column>
    <#assign type="String"> 
    <#if column.dataType = "varchar">
   		<#assign type="String">  
	<#elseif column.dataType = "char">
   		<#assign type="String">  
   	<#elseif column.dataType = "text">
   		<#assign type="String">  
	<#elseif column.dataType = "int">
   		<#assign type="int"> 
   	<#elseif column.dataType = "bigint">
   		<#assign type="Long">  
    <#elseif column.dataType = "decimal">
   		<#assign type="BigDecimal">  
    <#elseif column.dataType = "datetime">
   		<#assign type="String">  
	</#if> 
   <#if column.columnName != "id" && 
   		column.columnName != "tenantId" &&
   	    column.columnName != "createId" &&
   	    column.columnName != "createName" &&
   	    column.columnName != "createDatetime" &&
   	    column.columnName != "updateId" &&
   	    column.columnName != "updateName" &&
   	    column.columnName != "updateDatetime" &&
   	    column.columnName != "reserveString1" &&
   	    column.columnName != "reserveString2" &&
   	    column.columnName != "reserveDecimal1" &&
   	    column.columnName != "reserveDecimal2"
   >
   
	<#assign num=""> 
	<#list 0..(column.length - column.decimalLength -1) as i>
		<#assign num+="9"> 
	</#list>
	
	/**
	* ${column.columnComment}
	*/
	<#if column.excel = "true">
	@Excel(name ="${column.columnComment}")
	</#if>
	@DataName(name = "${column.columnComment}", isRecordHistory = true)
	<#if column.nullable = "true">
	@ApiModelProperty(value = "${column.columnComment}", required=true, position = ${column_index})
	<#else>
	@ApiModelProperty(value = "${column.columnComment}", required=false,  position = ${column_index})
	</#if>
	<#if type !="int" && type !="Long" && type !="BigDecimal">
	@Length(max=${column.length},message="${column.columnComment}长度不能大于${column.length}")
    </#if> 
	<#if type="int" || type="Long">
    @DecimalMax(value="${num}",message="${column.columnComment}不能大于${num}")
	@DecimalMin(value="0",message="${column.columnComment}不能小于0")
    <#elseif type="BigDecimal">
	@Digits(integer=${column.length - column.decimalLength},fraction=#{column.decimalLength}, message = "${column.columnComment}请填${num}以内数字")
	<#else>
	</#if>
     <#if column.nullable = "true">
	@NotBlank(message = "${column.columnComment}不能为空")
	 </#if>
	  <#if column.columnName = "dataSign">
	public ${type} ${column.columnName};
	  <#else>
	private ${type} ${column.columnName};	  
	  </#if>
   </#if> 
</#list> 
 }
