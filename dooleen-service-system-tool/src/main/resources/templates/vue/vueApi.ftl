import request from '@/router/axios';
import {downloadExcel} from '@/router/axios';
import {formatCondition} from '@/util/util';

//${sysToolTablesEntity.serviceName}单条查询
export const query${sysToolTablesEntity.tableName ? cap_first} = (params) => {
  params.head.transCode = 'query${sysToolTablesEntity.tableName ? cap_first}';
  return request({
    url: '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/query${sysToolTablesEntity.tableName ? cap_first}',
    method: 'post',
    data: params
  })
}
//${sysToolTablesEntity.serviceName}多条列表查询，支持条件查询
export const query${sysToolTablesEntity.tableName ? cap_first}ListPage = (params) => {
  params.head.transCode = 'query${sysToolTablesEntity.tableName ? cap_first}ListPage';
  return request({
    url: '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/query${sysToolTablesEntity.tableName ? cap_first}ListPage',
    method: 'post',
    data: params
  })
}
//${sysToolTablesEntity.serviceName}新增单条记录
export const save${sysToolTablesEntity.tableName ? cap_first} = (params) => {
  params.head.transCode = 'save${sysToolTablesEntity.tableName ? cap_first}';
  return request({
    url: '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/save${sysToolTablesEntity.tableName ? cap_first}',
    method: 'post',
    data: params
  })
}
//${sysToolTablesEntity.serviceName}新增多条记录
export const save${sysToolTablesEntity.tableName ? cap_first}List = (params) => {
  params.head.transCode = 'save${sysToolTablesEntity.tableName ? cap_first}List';
  return request({
    url: '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/save${sysToolTablesEntity.tableName ? cap_first}List',
    method: 'post',
    data: params
  })
}
//${sysToolTablesEntity.serviceName}新增或修改单条记录
export const saveOrUpdate${sysToolTablesEntity.tableName ? cap_first} = (params) => {
  params.head.transCode = 'saveOrUpdate${sysToolTablesEntity.tableName ? cap_first}';
  return request({
    url: '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/saveOrUpdate${sysToolTablesEntity.tableName ? cap_first}',
    method: 'post',
    data: params
  })
}


//${sysToolTablesEntity.serviceName}新增或修改单条记录
export const saveOrUpdate${sysToolTablesEntity.tableName ? cap_first}List = (params) => {
  params.head.transCode = 'saveOrUpdate${sysToolTablesEntity.tableName ? cap_first}List';
  return request({
    url: '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/saveOrUpdate${sysToolTablesEntity.tableName ? cap_first}List',
    method: 'post',
    data: params
  })
}

//${sysToolTablesEntity.serviceName}修改单条记录
export const update${sysToolTablesEntity.tableName ? cap_first} = (params) => {
  params.head.transCode = 'update${sysToolTablesEntity.tableName ? cap_first}';
  return request({
    url: '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/update${sysToolTablesEntity.tableName ? cap_first}',
    method: 'post',
    data: params
  })
}

//${sysToolTablesEntity.serviceName}删除单条记录
export const delete${sysToolTablesEntity.tableName ? cap_first} = (params) => {
  params.head.transCode = 'delete${sysToolTablesEntity.tableName ? cap_first}';
  return request({
    url: '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/delete${sysToolTablesEntity.tableName ? cap_first}',
    method: 'post',
    data: params
  })
}

//${sysToolTablesEntity.serviceName}删除多条记录
export const delete${sysToolTablesEntity.tableName ? cap_first}List = (params) => {
  params.head.transCode = 'delete${sysToolTablesEntity.tableName ? cap_first}List';
  return request({
    url: '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/delete${sysToolTablesEntity.tableName ? cap_first}List',
    method: 'post',
    data: params
  })
}
//${sysToolTablesEntity.serviceName}导出excel
export const export${sysToolTablesEntity.tableName ? cap_first}Excel = (params) => {
  downloadExcel({
    url: '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/excelDownload/export${sysToolTablesEntity.tableName ? cap_first}Excel',
    method: 'get',
    data: 
    {
      tenantId: params.head.tenantId,
      orders:'',
      condition: formatCondition(params.body.sqlCondition)
    }
  })
}
