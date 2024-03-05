//URL相关参数

//${sysToolTablesEntity.serviceName}API
import ${sysToolTablesEntity.tableName}AppApi from '@/common/api/${sysToolTablesEntity.tableName}AppApi.js'
Vue.use(${sysToolTablesEntity.tableName}AppApi, app)

import website from '@/config/website';

//${sysToolTablesEntity.serviceName}单条查询
let v_query${sysToolTablesEntity.tableName ? cap_first} = '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/query${sysToolTablesEntity.tableName ? cap_first}';

//${sysToolTablesEntity.serviceName}多条列表查询，支持条件查询
let v_query${sysToolTablesEntity.tableName ? cap_first}ListPage = '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/query${sysToolTablesEntity.tableName ? cap_first}ListPage';

//${sysToolTablesEntity.serviceName}新增记录
let v_save${sysToolTablesEntity.tableName ? cap_first} = '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/save${sysToolTablesEntity.tableName ? cap_first}';

//${sysToolTablesEntity.serviceName}新增或修改记录
let v_saveOrUpdate${sysToolTablesEntity.tableName ? cap_first} = '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/saveOrUpdate${sysToolTablesEntity.tableName ? cap_first}';

//${sysToolTablesEntity.serviceName}新增多条记录
let v_save${sysToolTablesEntity.tableName ? cap_first}List = '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/save${sysToolTablesEntity.tableName ? cap_first}List';

//${sysToolTablesEntity.serviceName}修改记录
let v_update${sysToolTablesEntity.tableName ? cap_first} = '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/update${sysToolTablesEntity.tableName ? cap_first}';

//${sysToolTablesEntity.serviceName}删除记录
let v_delete${sysToolTablesEntity.tableName ? cap_first} = '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/delete${sysToolTablesEntity.tableName ? cap_first}';

//${sysToolTablesEntity.serviceName}删除多条记录
let v_delete${sysToolTablesEntity.tableName ? cap_first}List = '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/delete${sysToolTablesEntity.tableName ? cap_first}List';


const install = (Vue, vm) => {
	//${sysToolTablesEntity.serviceName}单条查询
	let query${sysToolTablesEntity.tableName ? cap_first} = (params = {}) => vm.$u.post(v_query${sysToolTablesEntity.tableName ? cap_first}, params);

	//${sysToolTablesEntity.serviceName}多条列表查询，支持条件查询
	let query${sysToolTablesEntity.tableName ? cap_first}ListPage = (params = {}) => vm.$u.post(v_query${sysToolTablesEntity.tableName ? cap_first}ListPage, params);

	//${sysToolTablesEntity.serviceName}新增记录
	let save${sysToolTablesEntity.tableName ? cap_first} = (params = {}) => vm.$u.post(v_save${sysToolTablesEntity.tableName ? cap_first}, params);

	//${sysToolTablesEntity.serviceName}新增或修改记录
	let saveOrUpdate${sysToolTablesEntity.tableName ? cap_first} = (params = {}) => vm.$u.post(v_saveOrUpdate${sysToolTablesEntity.tableName ? cap_first}, params);

	//${sysToolTablesEntity.serviceName}新增多条记录
	let save${sysToolTablesEntity.tableName ? cap_first}List = (params = {}) => vm.$u.post(v_save${sysToolTablesEntity.tableName ? cap_first}List, params);

	//${sysToolTablesEntity.serviceName}修改记录
	let update${sysToolTablesEntity.tableName ? cap_first} = (params = {}) => vm.$u.post(v_update${sysToolTablesEntity.tableName ? cap_first}, params);

	//${sysToolTablesEntity.serviceName}删除记录
	let delete${sysToolTablesEntity.tableName ? cap_first} = (params = {}) => vm.$u.post(v_delete${sysToolTablesEntity.tableName ? cap_first}, params);

	//${sysToolTablesEntity.serviceName}删除多条记录
	let delete${sysToolTablesEntity.tableName ? cap_first}List = (params = {}) => vm.$u.post(v_delete${sysToolTablesEntity.tableName ? cap_first}List, params);
	
	// 将各个定义的接口名称，统一放进对象挂载到vm.$u.api(因为vm就是this，也即this.$u.api)下
	vm.$u.${sysToolTablesEntity.tableName}AppApi = {
		//${sysToolTablesEntity.serviceName}单条查询
		query${sysToolTablesEntity.tableName ? cap_first},

		//${sysToolTablesEntity.serviceName}多条列表查询，支持条件查询
		query${sysToolTablesEntity.tableName ? cap_first}ListPage,

		//${sysToolTablesEntity.serviceName}新增记录
		save${sysToolTablesEntity.tableName ? cap_first},

		//${sysToolTablesEntity.serviceName}新增或修改记录
		saveOrUpdate${sysToolTablesEntity.tableName ? cap_first},

		//${sysToolTablesEntity.serviceName}新增多条记录
		save${sysToolTablesEntity.tableName ? cap_first}List,

		//${sysToolTablesEntity.serviceName}修改记录
		update${sysToolTablesEntity.tableName ? cap_first},

		//${sysToolTablesEntity.serviceName}删除记录
		delete${sysToolTablesEntity.tableName ? cap_first},

		//${sysToolTablesEntity.serviceName}删除多条记录
		delete${sysToolTablesEntity.tableName ? cap_first}List
	};
}

export default {
	install
}
