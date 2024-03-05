<#function camelToDashed(s)>
  <#return s
      <#-- "fooBar" to "foo_bar": -->
      ?replace('([a-z])([A-Z])', '$1_$2', 'r')
      ?lower_case
  >
</#function>
<#setting number_format="#">
<template>
  <basic-container>
    <el-breadcrumb class="projectNameCrumb">
        <el-breadcrumb-item class="projectNameCrumbItem">
          <span>当前项目：</span>
          {{projectInfo.projectName}}
        </el-breadcrumb-item>
    </el-breadcrumb>
    <avue-crud :option="option"
               :table-loading="loading"
               :data="data"
               ref="crud"
               v-model="form"
               :defaults.sync="showColumn"
               :permission="permissionList"
               :page.sync="page"
               :before-open="beforeOpen"
               @sortable-change="sortableChange"
               @row-del="rowDel"
               @row-update="rowUpdate"
               @row-save="rowSave"
               @refresh-change="refreshChange"
               @search-change="searchChange"
               @search-reset="searchReset"
               @selection-change="selectionChange"
               @current-change="currentChange"
               @size-change="sizeChange"
               @filter="filterChange"
               @sort-change="sortChange"
               @row-dblclick="handleRowDBLClick"               
               @on-load="onLoad">
      <template slot-scope="scope" slot="menu">
        <el-button type="text"
                   size="small"
                   icon="el-icon-s-operation"
                   v-if="permission.database_delete"
                   @click="startFlow(scope.row)">启动流程
        </el-button>
      </template>
      <template slot-scope="scope" slot="menuForm">
        <div  style="text-align:right; float:right">
          <el-button type="text"  v-if="operateType == 'edit'"   icon="el-icon-time" style="font-weight:normal;" size="large" @click.stop="getHistoryRecord">修改历史</el-button>
        </div> 
      </template>
   <#list sysToolColumnsList as column> 
     <#if column.columnName ? ends_with('FileAddress') >
      <template slot-scope="scope" slot="${column.columnName}Form">
        <file-selector
              :fileList="currentRow.${column.columnName}"
              :openType="openType"
              :multiple="true"
              @changeFileList='changeFileList'
            >
        </file-selector>
      </template>
     </#if> 
   </#list>
      <template slot-scope="scope" slot="menuLeft">
        <el-button type="primary"
                   size="small"
                   icon="el-icon-plus"
                   v-if="permission.${camelToDashed(sysToolTablesEntity.menuNo)}_batch_add"
                   @click.stop="openBatchAddDialog">批量新增
        </el-button>
        <el-button type="danger"
                   size="small"
                   icon="el-icon-delete"
                   v-if="permission.${camelToDashed(sysToolTablesEntity.menuNo)}_batch_delete"
                   @click.stop="handleDelete">删 除
        </el-button>
      </template>
      <template slot="menuRight">
        <el-button v-if="permission.${camelToDashed(sysToolTablesEntity.menuNo)}_upload" type="default" @click.stop="openBatchAddDialog"  title="上传" icon="el-icon-upload" circle size="small"></el-button>
        <el-button v-if="permission.${camelToDashed(sysToolTablesEntity.menuNo)}_download" type="default" @click.stop="exportExcel"  title="下载" icon="el-icon-download" circle size="small"></el-button>
      </template>
    </avue-crud>
    <!-- 
      批量添加数据对话框
     -->
    <el-dialog title="批量导入" width='80%' :close-on-click-modal='false' :visible.sync="batchAddDialog">
      <el-tag>
 <#list sysToolColumnsList as column>
    <#if column.excel = "true">
        ${column.columnComment}，
	</#if>
 </#list>
      </el-tag>
      <el-input
        type="textarea"
        :autosize="{ minRows: 10, maxRows: 20}"
        placeholder="请直接从excel粘贴内容"
        v-model="importDataArea">
      </el-input>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" id="copyBtn" @click="handleImport($event)" data-clipboard-target="#pathText">开始导入</el-button>
        <el-button size="small" @click="batchAddDialog = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 批量导入数据 -->
    <upload-excel ref="uploadExcel" :actionUrl='actionUrl' ></upload-excel>
    <!-- 历史记录 -->
    <update-history ref="historyRecord"></update-history>
  </basic-container>
</template>

<script>
import {export${sysToolTablesEntity.tableName ? cap_first}Excel,saveOrUpdate${sysToolTablesEntity.tableName ? cap_first},saveOrUpdate${sysToolTablesEntity.tableName ? cap_first}List,save${sysToolTablesEntity.tableName ? cap_first},save${sysToolTablesEntity.tableName ? cap_first}List,delete${sysToolTablesEntity.tableName ? cap_first},query${sysToolTablesEntity.tableName ? cap_first},query${sysToolTablesEntity.tableName ? cap_first}ListPage,update${sysToolTablesEntity.tableName ? cap_first},delete${sysToolTablesEntity.tableName ? cap_first}List} from '@/api/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}Api';
import {mapGetters} from 'vuex';
import { reqCommonMsg } from '@/util/reqCommonMsg';
import {filterFormat} from '@/util/util';
export default {
  data() {
    return {
      //批量导入
      actionUrl: '/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}/uploadExcel',

      //文件选择器的打开方式 add新增、edit修改、view 查看
      openType: 'edit',
      // 当前打开form对话框操作类型 
      operateType: 'add',

      //如果做流程表单 请将次开关设置为true，否则设置为false
      isFlow: true,
      flowArea: {},
      
      importDataArea: '',
      batchAddDialog: false,
      form: {},
      currentRow: {},
      selectionList: [],
      loading: true, 
      showColumn: [],
      option: {
        labelPosition: 'right',
        labelSuffix: '：',
        labelWidth: 125,
        dialogTop: '50',
        indexFixed:true, 
        stripe:true,
        calcHeight: 80,
        searchShow: true,
        searchMenuSpan: 6,
        tip: false,
        border: true,
        index: true,
        selection: true,
        addBtn: true,
        viewBtn: true,
        delBtn: true,
        cancelBtn: true,
        printBtn:false,
        saveBtn: true,
        filterBtn:true,          
        menuWidth: 200,
        dialogMenuPosition: 'center',
        dialogDirection:'rtl',
        dialogType:'dialog',
        dialogFullscreen:false,
        dialogClickModal:false,
        dialogWidth: '70%',
        highlightCurrentRow:  true,
        sortable: true,
        column: [
    <#list sysToolColumnsList as column> 
       <#if column.showInForm = "true">
          {
            label: '${column.columnComment}',
            prop: '${column.columnName}', 
            type: '${column.type}', 
         <#if column.columnName ?  ends_with('Date') >
            format:'yyyy-MM-dd',
            valueFormat: 'yyyy-MM-dd',
            value: this.$toolUtil.formatDate(new Date()),
            pickerOptions: {
              disabledDate(time) {
                return time.getTime() + 3600 * 1000 * 24 <= Date.now();
              }
            },
         </#if>
            search: ${column.search},
            searchMmultiple: ${column.searchMultiple},
            searchLabelWidth: ${column.searchLabelWidth},
            multiple: ${column.multiple},
            filterable: true,
         <#if column.width != "0">
            width: ${column.width},
         </#if> 
            span: ${column.span},
            sortable: ${column.sortable},
            hide: ${column.hide},
         <#if column.value != "">
            value: '${column.value}',
         </#if> 
         <#if column.columnName ?  ends_with('FileAddress') >
            formslot:true,
         </#if> 
            maxlength: ${column.length},
            overHidden: ${column.overHidden},
            rules: [{
            <#if column.columnName ? ends_with('password') || column.columnName ? ends_with('Password')>
              // 检查密码
              validator: (rule, value, callback) => {
                if(!this.$validate.checkPwdStrong(value)){
                  callback(new Error('必须6位及以上，且含大、小写字母、数字或特殊字符'));
                }
                else{
                  callback();
                }
              }, 
            <#elseif column.columnName ? ends_with('mobileNo') ||  column.columnName ? ends_with('MobileNo')>
              // 检查手机号
              validator: (rule, value, callback) => {
                if(value != '' && !this.$validate.isMobile(value)){
                  callback(new Error('请输入11位手机号码！'));
                }
                else{
                  callback();
                }
              },
           	<#elseif column.columnName ? ends_with('phoneNo') ||  column.columnName ? ends_with('PhoneNo') >
           	  // 检查电话号码
              validator: (rule, value, callback) => {
                if(value != '' && !this.$validate.isPhone(value)){
                  callback(new Error('请输入有效的电话号码！'));
                }
                else{
                  callback();
                }
              },
           	<#elseif column.columnName ? ends_with('url') || column.columnName ? ends_with('Url')>
           	  // 检查URL地址
              validator: (rule, value, callback) => {
                if(value != '' && !this.$validate.isURL(value)){
                  callback(new Error('请输入正确的地址！'));
                }
                else{
                  callback();
                }
              },
           	<#elseif column.columnName ? ends_with('email') || column.columnName ? ends_with('Email')>
           	  // 检查Emial地址
              validator: (rule, value, callback) => {
                if(value != '' && !this.$validate.isEmail(value)){
                  callback(new Error('请输入正确的邮件地址！'));
                }
                else{
                  callback();
                }
              },
            <#elseif column.columnName ? ends_with('idCardNo') || column.columnName ? ends_with('IdCardNo')>
			  // 检查身份证号码
              validator: (rule, value, callback) => {
                var validate = this.$validate.cardid(value)
                if(!validate.result){
                  callback(new Error(validate.msg));
                }
                else{
                  callback();
                }
              },
            <#else>
              <#if column.message == '请输入内容' >
              message: '请输入${column.columnComment}',
              <#else>
              message: '${column.message}',
              </#if> 
			</#if> 
              required:  ${column.nullable},
              trigger: 'blur'
            },
            {
              max: ${column.length},
              message: '内容长度不能超过${column.length}字符',
              trigger: 'blur'
            }],
            dicData:${column.dicData},
         <#if column.columnName ?  ends_with('DeptName') || column.columnName ?  ends_with('orgName') >
            props: {
              label: 'orgName',
              value: 'orgName'
            },
         </#if>
         <#if column.columnName ?  ends_with('UserName') || column.columnName ?  ends_with('UserName') >
            remote: true,
            dicMethod:'get',
            dicUrl: '/platform/sysUserInfo/querySysUserInfoListPage?optFlag=org&columnValue={{key}}&columnName=realName&tenantId='+sessionStorage.getItem('tenantId'),
            props: {
              label: 'userName',
              value: 'realName'
            },
         </#if>
          },
         <#if column.columnName ?  ends_with('labelInfo') >
          {
            label: '标签信息',
            prop: 'labelInfo', 
            type: 'dynamic', 
            span: 24,
            children: {
              align: 'center',
              headerAlign: 'center', 
              column: [
                {
                  label: '标签类型',
                  prop: 'labelType',
                  type: 'select',
                  width: 120,
                  value: '通用',
                  rules:[{
                    required:  true,
                    message: '请输入内容',
                  }],
                  dicData: [{
                    label: '测试',
                    value: '测试'
                  }, 
                  {
                    label: '通用',
                    value: '通用'
                  }]
                },
                {
                  label: '标签值',
                  prop: 'labelValues',
                  type: 'select',
                  multiple: true,
                  filterable: true,
                  rules: [{
                    type: 'array',
                    required: true,
                    message: '请输入内容',
                  }],
                  dicData:[],
                }, 
              ]
            }
          },
         </#if>
        </#if> 
     </#list>
        ]
      },
      data: [],
      // 请求参数区
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0
      },
      reqParams: {
        singleBody: {},
        listBody: [{}],
        sqlCondition:[],
        orderRule: [],
        currentPage: 1,
        pageSize: 10
      },
      commonData: null,
      conditions: [],
      orderRules: [],
      singleBody: {}
    };
  },
  computed: {
    ...mapGetters(['website','orgTree','genOrgTree','userInfo','permission','projectInfo']),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permission.${camelToDashed(sysToolTablesEntity.menuNo)}_add, false),
        viewBtn: this.vaildData(this.permission.${camelToDashed(sysToolTablesEntity.menuNo)}_view, false),
        delBtn: this.vaildData(this.permission.${camelToDashed(sysToolTablesEntity.menuNo)}_delete, false),
        editBtn: this.vaildData(this.permission.${camelToDashed(sysToolTablesEntity.menuNo)}_edit, false),
        printBtn: this.vaildData(this.permission.${camelToDashed(sysToolTablesEntity.menuNo)}_print, false),
        searchBtn:this.vaildData(this.permission.${camelToDashed(sysToolTablesEntity.menuNo)}_search, false)
      };
    },
    ids() {
      let ids = [];
      this.selectionList.forEach(ele => {
        ids.push({id : ele.id});
      });
      return ids;
    } 
  },
  methods: {
    /**
     * 启动流程
     */
    startFlow(row){
      this.currentRow = row;
      //flowOptFlag 1-开始启动 2-处理流程
      this.flowArea.isFlow = this.isFlow;
      this.flowArea.flowOptFlag = '1';
      this.processFlow();
    },
    /**
     * 启动流程，执行流程方法
     */
    processFlow(){
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
      });
      this.reqParams.flowArea = this.flowArea
      this.reqParams.singleBody = this.currentRow;
      this.commonData = reqCommonMsg(this.reqParams)
      update${sysToolTablesEntity.tableName ? cap_first}(this.commonData).then(() => {
        this.$message({
          customClass:'zZindex',
          type: 'success',
          message: '操作成功!'
        });
        loading.close();
      }, error => {
        window.z(error); 
        loading.close();
      });
    },
    /**
     * 加载列表数据
     */
    onLoad(page) {
   	  //如果和项目相关，请打开项目ID作为查询条件
      //if(!this.$project.checkChooseProject()){
      //  return false;
      //}
      //this.singleBody.projectId = this.projectInfo.id;
      this.loading = true;
      this.reqParams =  {
        singleBody: this.singleBody,
        listBody: [{}],
        sqlCondition: this.conditions,
        orderRule: this.orderRules,
        currentPage: page.currentPage, 
        pageSize: page.pageSize
      }
      this.commonData = reqCommonMsg(this.reqParams)
      query${sysToolTablesEntity.tableName ? cap_first}ListPage(this.commonData).then(res => {
        const data = res.data.body;
        this.page.total = data.total;
        this.data = data.listBody;
        this.loading = false;
        this.selectionClear();
      }, error => {
        this.loading = false; 
      });
    },
    /**
     * 新增一条记录
     */
    rowSave(row, done, loading) {
      this.reqParams.singleBody = row;
      let formatUser = {};
 <#list sysToolColumnsList as column>
    <#if column.columnName ?  ends_with('UserName')>
      // 解析用户名（用户ID）
      formatUser = this.$util.formatUserName(row.${column.columnName});
      this.reqParams.singleBody.${column.columnName} = formatUser.userId; 
      this.reqParams.singleBody.${column.columnName ? substring(0, column.columnName ? index_of('UserName'))}RealName = formatUser.userName;
	</#if>
    <#if column.columnName ?  ends_with('userName') >
      // 解析用户名（用户ID）
      formatUser = this.$util.formatUserName(row.${column.columnName});
      this.reqParams.singleBody.${column.columnName} = formatUser.userId;
      this.reqParams.singleBody.${column.columnName ? substring(0, column.columnName ? index_of('userName'))}RealName = formatUser.userName;
    </#if>
	<#if column.columnName ? ends_with('FileAddress') >
	  if(this.reqParams.singleBody.${column.columnName} == ''){
        this.reqParams.singleBody.${column.columnName} =  '[]';
      }
      else{
        this.reqParams.singleBody.${column.columnName} =  JSON.stringify(row.${column.columnName});
      }
    </#if>
 </#list>
      this.commonData = reqCommonMsg(this.reqParams)
      save${sysToolTablesEntity.tableName ? cap_first}(this.commonData).then(() => {
        done();
        this.singleBody={};
        this.onLoad(this.page);
        this.$message({
          type: 'success',
          message: '操作成功!'
        });
      }, error => {
        this.$console.log(error);
        done();
      });         
      //this.$console.log("row = ",row ,loading)
    },
    /**
     * 修改一条记录
     */
    rowUpdate(row, index, done, loading) {
      this.reqParams.singleBody = row;
      let formatUser = {};
 <#list sysToolColumnsList as column>
    <#if column.columnName ?  ends_with('UserName')>
      // 解析用户名（用户ID）
      formatUser = this.$util.formatUserName(row.${column.columnName});
      this.reqParams.singleBody.${column.columnName} = formatUser.userId; 
      this.reqParams.singleBody.${column.columnName ? substring(0, column.columnName ? index_of('UserName'))}RealName = formatUser.userName;
	</#if>
    <#if column.columnName ?  ends_with('userName') >
      // 解析用户名（用户ID）
      formatUser = this.$util.formatUserName(row.${column.columnName});
      this.reqParams.singleBody.${column.columnName} = formatUser.userId;
      this.reqParams.singleBody.${column.columnName ? substring(0, column.columnName ? index_of('userName'))}RealName = formatUser.userName;
    </#if>
	<#if column.columnName ? ends_with('FileAddress') >
	  if(this.reqParams.singleBody.${column.columnName} == ''){
        this.reqParams.singleBody.${column.columnName} =  '[]';
      }
      else{
        this.reqParams.singleBody.${column.columnName} =  JSON.stringify(row.${column.columnName});
      }
    </#if>
 </#list>
      this.commonData = reqCommonMsg(this.reqParams)
      update${sysToolTablesEntity.tableName ? cap_first}(this.commonData).then(() => {
        done();
        this.singleBody={}
        this.onLoad(this.page);
        this.$message({
          type: 'success',
          message: '操作成功!'
        });
      }, error => {
        this.$console.log(error);
        done();
      });
    },
    /**
     * 删除一条记录
     */
    rowDel(row) {
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.reqParams.singleBody = row;
          this.commonData = reqCommonMsg(this.reqParams);
          return delete${sysToolTablesEntity.tableName ? cap_first}(this.commonData);
        })
        .then(() => {
          this.onLoad(this.page);
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
        }).catch(() => {});
    },
    /**
     * 双击打开详细窗口
     */
    handleRowDBLClick (row, event) {
      if(this.permission.${camelToDashed(sysToolTablesEntity.menuNo)}_dbclick){
        this.$refs.crud.rowView(row,row.$index);
      }
    },
    /**
     * 批量新增记录
     */
    openBatchAddDialog(){
      this.$refs.uploadExcel.openUploadDialog();
    },
    /**
     * 批量导入
     */
    handleImport() {
      const loading = this.$loading({
        lock: true,
        text: '处理中...',
        spinner: 'el-icon-loading',
      });
      var dataList = new Array();
      if(this.importDataArea){
        var lineList = this.importDataArea.split('\n')
        lineList.forEach((element,index) => {
          if(element.trim() != ''){
            var field = element.split('\t')
            var fieldList = {
        <#assign tempNum=0>
		<#list sysToolColumnsList as column>
			<#if column.excel = "true">
              ${column.columnName}:field[${tempNum}].trim(),
              <#assign tempNum=tempNum+1>
			</#if>
		</#list>
            }
            dataList[index] = fieldList
            //this.$console.log('fieldList = ',fieldList)
          }
        });
        //批量写入记录
        this.reqParams.listBody = dataList;
        this.commonData = reqCommonMsg(this.reqParams)
        save${sysToolTablesEntity.tableName ? cap_first}List(this.commonData).then(() => {
          this.onLoad(this.page);
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
          loading.close();
        }, error => {
          this.$console.log(error);
          loading.close();
        });      
      }
      else{
        this.loading=false;
        this.$message({
          type: 'warning',
          message: '数据格式不正确!'
        });
      }
    },
    /**
     * 搜索重置
     */
    searchReset() {
      this.conditions =[];
      this.singleBody = {};
      this.onLoad(this.page);
    },
    /**
     * 搜索
     */
    searchChange(params, done) {
      this.singleBody = params
      this.page.currentPage = 1;
      this.onLoad(this.page);
      done();
    },
    refreshChange(){
      this.onLoad(this.page);
    },
    selectionChange(list) {
      this.selectionList = list;
    },
    selectionClear() {
      this.selectionList = [];
      this.$refs.crud.toggleSelection();
    },
    /**
     * 处理批量删除
     */
    handleDelete() {
      if (this.selectionList.length === 0) {
        this.$message.warning('请选择至少一条数据');
        return;
      }
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.reqParams.listBody = this.ids;
          this.commonData = reqCommonMsg(this.reqParams);
          return delete${sysToolTablesEntity.tableName ? cap_first}List(this.commonData);
        })
        .then(() => {
          this.onLoad(this.page);
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
          this.$refs.crud.toggleSelection();
        }).catch(() => {});
    },
    /**
     * 拷贝记录
     */
    handleCopy(row){
      this.$confirm('确定复制数据?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading=true;
        let copyValue=row;
        copyValue.id='0';
        copyValue.dataSign='0'
        copyValue.tableName='拷贝 '+ copyValue.tableName; 
        this.reqParams.singleBody = copyValue;
        this.commonData = reqCommonMsg(this.reqParams)
        saveSysToolTables(this.commonData).then(() => {
          this.singleBody={};
          this.onLoad(this.page);
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
          this.loading=false;
        }, error => {
          this.$console.log(error);
          this.loading=false;
        });
      }).catch(() => {});       
    },
    /**
     * 打开编辑、查询弹窗前查询记录
     */  
    beforeOpen(done, type) {
      if (['edit', 'view'].includes(type)) {
        this.operateType = 'edit';
        this.reqParams =  {
          singleBody: {
            id:this.form.id
          }
        }
        let commonData = reqCommonMsg(this.reqParams)
        const loading = this.$loading({
          lock: true,
          text: 'Loading',
          spinner: 'el-icon-loading'
        });
        query${sysToolTablesEntity.tableName ? cap_first}(commonData).then(res => {
          this.form = res.data.body.singleBody;
<#list sysToolColumnsList as column>
    <#if column.columnName ?  ends_with('UserName')>
          if(res.data.body.singleBody.${column.columnName}){
          	this.form.${column.columnName} = res.data.body.singleBody.${column.columnName ? substring(0, column.columnName ? index_of('UserName'))}RealName+'('+ res.data.body.singleBody.${column.columnName} +')';
          }
    </#if>
    <#if column.columnName ?  ends_with('userName') >
          if(res.data.body.singleBody.${column.columnName}){
            this.form.${column.columnName} = res.data.body.singleBody.${column.columnName ? substring(0, column.columnName ? index_of('userName'))}RealName+'('+ res.data.body.singleBody.${column.columnName} +')';
          }
    </#if>
    <#if column.columnName ?  ends_with('FileAddress') || column.columnName ?  ends_with('fileAddress') >
     	  if(this.form.${column.columnName}.indexOf('[') > -1 && this.form.${column.columnName}.indexOf(']') > -1){
          	this.form.${column.columnName}  = JSON.parse(this.form.${column.columnName});
          }
	</#if>
	<#if column.columnName ?  ends_with('labelInfo') >
          this.form.labelInfo = JSON.parse(this.form.labelInfo);
	</#if>
 </#list>
          loading.close();
          done();
        }, error => {
          this.$console.log(error);
          loading.close();
          done();
        });
      }
      if (['add'].includes(type)) {
        this.operateType = 'add';
        done();
      }
    },
    /**
     * 翻页查询
     */
    currentChange(currentPage) {
      this.page.currentPage = currentPage;
    },
    /**
     * 更改每页条数
     */
    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
    },
    /**
     * 自定义查询条件
     */
    filterChange(result) {
      this.page.currentPage = 1;
      this.conditions = filterFormat(result)
      this.onLoad(this.page);
    },
    /**
     * 列表排序
     */
    sortChange(val){
      this.orderRules=val
      this.onLoad(this.page);
    },
  
    /**
     * 导出Excel
     */
    exportExcel() {
      this.reqParams =  {
        singleBody: this.singleBody,
        listBody: [{}],
        sqlCondition: this.conditions,
        orderRule: this.orderRules,
        currentPage:1, 
        pageSize: 100
      }
      // this.$console.log('reqParams = ',this.reqParams)
      this.commonData = reqCommonMsg(this.reqParams)
      export${sysToolTablesEntity.tableName ? cap_first}Excel(this.commonData);
    },
    /**
     * 获取业务字典参数
     */
    async getBizDict() {
 <#assign hasLabelInfo='false'>
 <#list sysToolColumnsList as column>
    <#if column.columnName ?  ends_with('labelInfo')>
      <#assign hasLabelInfo='true'>
    </#if>
 </#list>
 <#if hasLabelInfo='true'>
      let dicData = await this.$params.getBizDict('["bizType","labelType", "generalLabelValues"]');
 <#else>
      let dicData = await this.$params.getBizDict('["bizType"]');
 </#if>
      this.findObject(this.option.column, 'bizType').dicData = dicData.bizType;
 <#list sysToolColumnsList as column>
    <#if column.columnName ?  ends_with('labelInfo')>
      this.findObject(this.option.column, 'labelInfo').children.column[0].dicData = dicData.labelType;
      this.findObject(this.option.column, 'labelInfo').children.column[1].dicData = dicData.generalLabelValues;
	</#if>
 </#list>
    },
    
    /**
     * 调用子组件方法查询历史
     */
    getHistoryRecord () {
      setTimeout(() => {
        this.$refs.historyRecord.getUpdateHistoryRecord(this.form.id);
      })
    },
   <#list sysToolColumnsList as column> 
     <#if column.columnName ? ends_with('FileAddress') >
    /**
     * 选择文件后由子组件触发修改fileList
     */
    changeFileList(fileList){
      this.currentRow.${column.columnName} = fileList;
    }
     </#if> 
   </#list>    
  },
  created() {
    //如果和项目相关，请打开
    // if(!this.projectInfo || !this.projectInfo.projectName){
    //   this.$message({
    //     type: 'warning',
    //     message: '请选择一个项目再进入！'
    //   });
    //   this.$router.push({path: '/general/project/generalMyProjectSelector'});
    // }
    this.showColumn = this.$getStore({name: this.userInfo.userId+'${sysToolTablesEntity.tableName}Column'});
    //获取业务字典
    // setTimeout(() => {
    //  this.getBizDict();
    // }, 1000);
    
    //加载机构
    let getOrgTreeTimer = setInterval(() => {
      if(this.genOrgTree){
        let orgTree = [];
        this.genOrgTree.forEach(element => {
          if(element.orgType == '1'){
            orgTree.push(element);
          }
        });
 <#list sysToolColumnsList as column>
    <#if column.columnName ?  ends_with('DeptName') || column.columnName ?  ends_with('deptName') || column.columnName ?  ends_with('OrgName') >
    
        this.findObject(this.option.column, '${column.columnName}').dicData = [];
        this.findObject(this.option.column, '${column.columnName}').dicData = this.deepClone(orgTree); 
	</#if>
 </#list>
        clearInterval(getOrgTreeTimer);
      }
    }, 100);
  },
  watch: {
    showColumn: {
      handler(newValue, oldValue) {
        this.$setStore({name: this.userInfo.userId+'${sysToolTablesEntity.tableName}Column', content: this.showColumn});
      },
      deep: true
    }
  }
};
</script>
