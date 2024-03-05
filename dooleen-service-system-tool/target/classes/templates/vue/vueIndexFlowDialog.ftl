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
     <avue-skeleton v-if="!processFlowDialog"  avatar :rows="4" :number="3"></avue-skeleton>
     <!-- 
      流程处理对话框
     -->
      <avue-form v-if="processFlowDialog" ref="flowProcessForm" v-model="form" :option="option"  @submit="onProcessSubmit">
        <template slot-scope="scope" slot="flowExtInfo">
          <div style="">
            <flow-common-area
                :flowExtInfo="flowExtInfo"
                :processData="processData"
                :viewOnly="viewOnly"
                @handleFlowChange="handleFlowExtInfoChange">
            </flow-common-area>
          </div>
        </template>
        <template slot-scope="scope" slot="menuForm">
          <el-row>
            <el-col :span="8"></el-col>
            <el-col :span="8">
              <el-button v-if="processStatus == ''" type="primary" icon="el-icon-check" @click="doProcessSubmit">提交</el-button>
              <el-button v-if="processStatus != '' && processStatus != '人工撤回'" type="primary" icon="el-icon-refresh-right" @click="onProcessCallBackSubmit">撤回</el-button>
            </el-col>
            <el-col :span="8" style="text-align:right;">
              <el-button type="text" size="small" @click="openFlowCharts">
                <svg-icon style="font-size:10px;" iconClass="flowIcon"/>
                流程
              </el-button>
            </el-col>
          </el-row>
        </template>
      </avue-form> 
    <!-- 
      流程图对话框
     -->
    <el-dialog title="流程信息" top='0vh' width="90%" style="height:100%" @opened="flowChartDialogOpened" :close-on-click-modal='false' :visible.sync="flowCharts">
      <el-tabs type="border-card" v-model="activeName"  @tab-click="handleTabChange">
        <el-tab-pane name="flowChart">
          <span slot="label"><svg-icon style="font-size:10px;" iconClass="flowIcon"/>流程图</span>
          <flow-charts ref="flowChart"></flow-charts>
        </el-tab-pane>
        <el-tab-pane name="flowTimeline">
          <span slot="label"><i class="el-icon-time"></i>流程处理记录</span>
          <flow-process-timeline ref="flowTimeline"></flow-process-timeline>
        </el-tab-pane>
      </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button   icon="el-icon-close"  size="small" @click="flowCharts = false">关闭</el-button>
      </div>
    </el-dialog>
  </basic-container>
</template>

<script>
import {query${sysToolTablesEntity.tableName ? cap_first},update${sysToolTablesEntity.tableName ? cap_first}} from '@/api/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}Api';
import {mapGetters} from 'vuex';
import { reqCommonMsg } from '@/util/reqCommonMsg';
import FlowCommonArea from '@/views/general/flow/generalFlowCommonArea.vue'
import FlowCharts from '@/views/general/flow/generalFlowProcessCharts.vue';
import FlowProcessTimeline from '@/views/general/flow/generalFlowProcessTimeline.vue';
//1、将下列import 内容copy到myTask.vue 的import中
import ${sysToolTablesEntity.tableName ? cap_first} from '@/views/${sysToolTablesEntity.moduleName}/${sysToolTablesEntity.tableName}Dialog.vue'
//2、在components中引入 ${sysToolTablesEntity.tableName ? cap_first}
//3、<!--每个任务的具体内容-->中加入组件

export default {
  components: {
    FlowCommonArea,
    FlowCharts,
    FlowProcessTimeline
  },
  props: {
    processData: {
      type: Object
    },
    viewOnly: {
      type: Boolean
    }
  },
  data() {
    return {
      // 流程图对话框
      flowCharts: false,
      activeName: 'flowChart',

      //如果做流程表单 请将次开关设置为true，否则设置为false
      isFlow: true,
      flowArea: {},
      flowExtInfo: {},
      processFlowDialog:false,
      currentRow: {}, 
      processStatus: '',

      form: {},
      selectionList: [],
      loading: true, 
      showColumn: [],
      option: {
        labelPosition: 'right',
        labelSuffix: '：',
        labelWidth: 120,
        emptyBtn:false,
        submitBtn: false,
        column: [
    <#list sysToolColumnsList as column> 
       <#if column.showInForm = "true">
          {
            label: '${column.columnComment}',
            prop: '${column.columnName}', 
            type: '${column.type}', 
            search: ${column.search},
            searchMmultiple: ${column.searchMultiple},
            searchLabelWidth: ${column.searchLabelWidth},
            multiple: ${column.multiple},
         <#if column.width != "0">
            width: ${column.width},
         </#if> 
            span: ${column.span},
            sortable: ${column.sortable},
            hide: ${column.hide},
         <#if column.value != "">
            value: '${column.value}',
         </#if> 
            maxlength: ${column.length},
            overHidden: ${column.overHidden},
            display: true,
            disabled: false,
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
                if(value != '' && !this.$validate.cardid(value)){
                  callback(new Error('请输入正确的身份证号码！'));
                }
                else{
                  callback();
                }
              },
            <#else>
              message: '${column.message}',
			</#if> 
              required:  ${column.nullable},
              trigger: 'blur'
            },
            {
              max: ${column.length},
              message: '内容长度不能超过${column.length}字符',
              trigger: 'blur'
            }],
            dicData:${column.dicData}
          },
        </#if> 
     </#list>
        ],
        group:[
          {
            arrow: false,
            column: [
              //以下区域为流程专用
              { 
                prop: 'spaceLine', 
                formslot: true,
                span: 24
              },
              { 
                prop: 'flowExtInfo', 
                formslot: true,
                multiple: false,
                span: 258,
                dicData:[]
              }
            ]
          },
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
        orderRule: {},
        currentPage: 1,
        pageSize: 10
      },
      commonData: null,
      conditions: [],
      orderRules: {},
      singleBody: {}
    };
  },

  computed: {
    ...mapGetters(['website','permission']),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permission.general_biz_demand_add, false),
        editBtn: this.vaildData(this.permission.general_biz_demand_edit, false),
      };
    }
  },
  methods: { 
    /**
     * 切换流程图标签时
     */
    handleTabChange(tab, event) {
      if(tab.name == 'flowTimeline'){
        this.$refs.flowTimeline.loadTimelineData(this.processData.id);
      }
    },
    /**
     * 打开流程图对话框
     */
    openFlowCharts(){
      this.flowCharts = true;
    },
    /**
     * 流程对话框打开之后
     */
    flowChartDialogOpened(){
      this.$refs.flowChart.getFlowChartData(this.processData.id);
    },
    /**
     * 根据ID获取记录，由主组件触发
     */
    async getRecordById(){
      this.processStatus = this.processData.processStatus ;
      this.reqParams.singleBody =  {
        id: this.processData.bizId
      }
      this.reqParams.flowArea = {
        isFlow: this.isFlow,
        processRecordId: this.processData.id
      }
      let commonData = reqCommonMsg(this.reqParams)
      await query${sysToolTablesEntity.tableName ? cap_first}(commonData).then(res => {
        this.form = res.data.body.singleBody;
        this.currentRow = res.data.body.singleBody;
        this.flowArea = res.data.body.flowArea
        this.flowExtInfo = res.data.body.flowArea.flowExtData;
        this.$emit('closeLoading','');
        this.$console.log('this.flowExtInfo==== ',this.flowExtInfo);
        //表单group控制
        if(this.flowExtInfo){
          this.displayGroup(this.flowExtInfo.hideFormGroupList);
        }
        //表单栏位控制
        if(this.flowExtInfo){
          this.handelFormPrivilege(this.flowExtInfo.formPrivilege);
        }
      }, error => {
        this.$console.log(error);
      });
    },
    
    /**
     * group显示控制
     */  
    displayGroup(formGroupList){
      this.$console.log('formGroupList === ',formGroupList);
      // 按照group标签控制 
      if(this.option.group){
        formGroupList.forEach(element => {
          this.option.group.forEach((groupElement,index) => {
            if(groupElement.prop == element){
              groupElement.display = false;
            }
          });
        });
      }
    },
    /**
     * 处理表单权限
     */
    handelFormPrivilege(formPrivilege){
      formPrivilege.forEach(element => {
        // 不可见
        if(element.privilegeFlag == '0'){ 
          element.privilegeFormList.forEach(formElement => {
            this.hideForm(this.$toolUtil.toHump(formElement));
          });
        }
        //只读
        if(element.privilegeFlag == '1'){ 
          element.privilegeFormList.forEach(formElement => {
            this.readOnly(this.$toolUtil.toHump(formElement));
          });
        }
        // 必输
        if(element.privilegeFlag == '2'){ 
          element.privilegeFormList.forEach(formElement => {
            this.inputRequired(this.$toolUtil.toHump(formElement));
          });
        }
      });
      this.processFlowDialog = true;
    },
    //隐藏栏位
    hideForm(field){
      //无group的情况
      if(this.option.column){
        if(this.findObject(this.option.column, field) != -1){
          this.findObject(this.option.column, field).display = false;
          this.findObject(this.option.column, field).disabled = true;
        }
      }
      if(this.option.group){
        this.option.group.forEach((element,index) => {
          if(element.column){
            if(this.findObject(this.option.group[index].column, field) != -1){
              this.findObject(this.option.group[index].column, field).display = false;
              this.findObject(this.option.group[index].column, field).disabled = true;
            }
          }
        });
      }
    },
    //控制是否必输
    inputRequired(field){
      //无group的情况
      if(this.option.column){
        if(this.findObject(this.option.column, field) != -1){
          this.findObject(this.option.column, field).rules[0].required = true;
        }
      }
      //有group的情况
      if(this.option.group){
        this.option.group.forEach((element,index) => {
          if(element.column){
            if(this.findObject(this.option.group[index].column, field) != -1){
              this.findObject(this.option.group[index].column, field).rules[0].required = true;
              this.findObject(this.option.group[index].column, field).rules[0].required = true;
            }
          }
        });
      }
    },
    //只读控制
    readOnly(field){
      //无group的情况
      if(this.option.column){
        if(this.findObject(this.option.column, field) != -1){
          this.findObject(this.option.column, field).display = true;
          this.findObject(this.option.column, field).disabled = true;
        }
      }
      //有group的情况
      if(this.option.group){
        this.option.group.forEach((element,index) => {
          if(element.column){
            if(this.findObject(this.option.group[index].column, field) != -1){
              this.findObject(this.option.group[index].column, field).display = true;
              this.findObject(this.option.group[index].column, field).disabled = true;
            }
          }
        });
      }
    },
    
    /**
     * 由于表单验证需要submit触发，此处触发提交事件
     */
    doProcessSubmit(){
      this.currentRow = this.form;
      this.flowArea.flowExtData = this.flowExtInfo; 
      //flowOptFlag 1-开始启动 2-处理流程
      this.flowArea.isFlow = true;
      this.flowArea.flowOptFlag = '2';
      this.$refs.flowProcessForm.submit();
    },

    /**
     * 提交撤回
     */
    onProcessCallBackSubmit(){
      this.currentRow = this.form;
      this.flowArea.flowExtData = this.flowExtInfo; 
      //flowOptFlag 1-开始启动 2-处理流程
      this.flowArea.isFlow = true;
      this.flowArea.flowOptFlag = '3';
      this.$refs.flowProcessForm.submit();
    },

    /**
     * 提交审批
     */
    onProcessSubmit(form,done){
      this.processFlow();
    },
    /**
     * flow-common-area 组件回调同步修改扩展信息
     */
    handleFlowExtInfoChange(val){
      this.$console.log('this.flowExtInfo====',val)
      this.flowExtInfo = val;
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
          type: 'success',
          message: '操作成功!'
        });
        //执行成功 关闭对话框
        this.$emit('closeProcessFlowDialog','');
        loading.close();
      }, error => {
        this.$console.log(error); 
        loading.close();
      });
    },
    /**
     * 获取业务字典参数(暂不使用)
     */
    async getBizDict() {
      let dicData = await this.$params.getBizDict('["bizType"]');
      this.findObject(this.option.column, 'bizType').dicData = dicData.bizType;
      //如果用了流程 需要做表单切换,此处将赋值好字典的表单做备份
      if(this.isFlow){
        this.optionTmp = this.deepClone(this.option);
      }
    },
    
  },
  created() {
       
  },
  mounted() {
    this.showColumn = this.$getStore({name: 'generalBizDemandColumn'});
    //this.getRecordById();
    //获取业务字典
    // setTimeout(() => {
    //  this.getBizDict();
    // }, 1000);
  },
  watch: {
    showColumn() {
      this.$setStore({name: 'generalBizDemandColumn', content: this.showColumn});
    }
  }
};
</script>
<style>

</style>

