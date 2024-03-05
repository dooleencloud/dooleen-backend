package com.dooleen.common.core.enums;

public enum TableEntityORMEnum {
	/**
	 * 业务建模
	 */
	BIZ_MODEL_ACTIVITY("biz_model_activity", "BizModelActivity", "BMAT"),
	BIZ_MODEL_ACTIVITY_TASK_GROUP("biz_model_activity_task_group", "BizModelActivityTaskGroup", "BMAG"),
	BIZ_MODEL_TASK_GROUP("biz_model_task_group", "BizModelTaskGroup", "BMTG"),
	BIZ_MODEL_TASK_GROUP_TASK_ITEM("biz_model_task_group_task_item", "BizModelTaskGroupTaskItem", "BMTT"),
	BIZ_MODEL_TASK_ITEM("biz_model_task_item", "BizModelTaskItem", "BMTI"),
	/**
	 * 论坛相关
	 */
	BIZ_FORUM_CIRCLE("biz_forum_circle", "BizForumCircle", "FMCL"),
	BIZ_FORUM_COMMENT("biz_forum_comment", "BizForumComment", "FLCM"),
	BIZ_FORUM_FOLLOW("biz_forum_follow", "BizForumFollow", "FLFL"),
	BIZ_FORUM_MY_CIRCLE("biz_forum_my_circle", "BizForumMyCircle", "FMYC"),
	BIZ_FORUM_POSTS("biz_forum_posts", "BizForumPosts", "FLPS"),
	BIZ_FORUM_QUESTION("biz_forum_question", "BizForumQuestion", "FLQS"),
	BIZ_FORUM_QUESTION_ANSWER("biz_forum_question_answer", "BizForumQuestionAnswer", "FLQA"),
	BIZ_FORUM_TOPIC("biz_forum_topic", "BizForumTopic", "FLTP"),
	BIZ_FORUM_THUMB_UP("biz_forum_thumb_up", "BizForumThumbUp", "FLTU"),
	/**
	 * 16pf相关表
	 */
	BIZ_SIXTEEN_EIGHT_SF("biz_sixteen_eight_sf", "BizSixteenEightSf", "EISF"),
	BIZ_SIXTEEN_PF("biz_sixteen_pf", "BizSixteenPf", "STPF"),
	BIZ_SIXTEEN_TOPIC_DB("biz_sixteen_topic_db", "BizSixteenTopicDb", "TPDB"),
	BIZ_SIXTEEN_USER_ANALYSIS("biz_sixteen_user_analysis", "BizSixteenUserAnalysis", "USLY"),
	BIZ_SIXTEEN_USER_ANSWERS("biz_sixteen_user_answers", "BizSixteenUserAnswers", "USAS"),
	BIZ_SIXTEEN_USER_INFO("biz_sixteen_user_info", "BizSixteenUserInfo", "STUI"),
	BIZ_SIXTEEN_USER_PF_EXCH("biz_sixteen_user_pf_exch", "BizSixteenUserPfExch", "USPF"),


	/**
	 * 迭代
	 */
	BIZ_PLAN_ITERATION("biz_plan_iteration", "BizPlanIteration", "ITER"),
	/**
	 * 里程碑
	 */
	BIZ_PLAN_MILESTONE("biz_plan_milestone", "BizPlanMilestone", "MIST"),
	/**
	 * 发送日志
	 */
	SYS_SEND_LOG("sys_send_log", "sysSendLog", "SDLG"),
	/**
	 * 机构信息登记
	 */
	SYS_CUSTOM_ORG_INFO("sys_custom_org_info", "SysCustomOrgInfo", "ORIF"),
	/**
	 * 问卷结果整理
	 */
	BIZ_QUESTION_SEND_RESULT("biz_question_send_result", "BizQuestionSendResult", "SDRS"),
	/**
	 * 下发记录表
	 */
	BIZ_QUESTION_SEND_RECORD("biz_question_send_record", "BizQuestionSendRecord", "SDRC"),
	/**
	 * 问卷模板列表
	 */
	BIZ_QUESTION_TEMPLATE("biz_question_template", "BizQuestionTemplate", "QSTP"),
	/**
	 * 问卷题目配置
	 */
	BIZ_QUESTION_SUBJECT_CONFIG("biz_question_subject_config", "BizQuestionSubjectConfig", "SBCF"),
	/**
	 * 下发题目明细
	 */
	BIZ_QUESTION_SEND_SUBJECT("biz_question_send_subject", "BizQuestionSendSubject", "SDSJ"),
	/**
	 * 下发用户列表
	 */
	BIZ_QUESTION_SEND_LIST("biz_question_send_list", "BizQuestionSendList", "SLST"),
	/**
	 * 客户用户表
	 */
	SYS_CUSTOM_USER_INFO("sys_custom_user_info", "SysCustomUserInfo", "CUST"),

	BIZ_DEMAN_INFOMATION("biz_deman_infomation", "BizDemanInfomation", "DMAN"),
	/**
	 * 工位历史
	 */
	GENERAL_STAFF_STATION_BYDAY("general_staff_station_byday", "GeneralStaffStationByday", "SFSD"),

	/**
	 * 工位管理
	 */
	GENERAL_STAFF_STATION("general_staff_station", "GeneralStaffStation", "SFST"),

	/**
	 * 背景设置
	 */
	VISUAL_BACKGROUND("visual_background", "VisualBackground", "VSBG"),

	/**
	 * 可视化地图配置
	 */
	VISUAL_MAP("visual_map", "VisualMap", "VMAP"),
	/**
	 * 可视化配置
	 */
	VISUAL_CONFIG("visual_config", "VisualConfig", "VCNF"),
	/**
	 * 视图列表
	 */
	VISUAL_VIEW_LIST("visual_view_list", "VisualViewList", "VLST"),
	/**
	 * 视图工具分类
	 */
	VISUAL_CATEGORY("visual_category", "VisualCategory", "CTGR"),
	/**
	 * 系统功能点
	 */
	BIZ_APPARCH_SYSTEM_FUNCTION("biz_apparch_system_function", "BizApparchSystemFunction", "SYFC"),
	/**
	 * 外联系统
	 */
	BIZ_APPARCH_OUT_SYSTEM("biz_apparch_out_system", "BizApparchOutSystem", "OUTS"),
	/**
	 * 应用系统
	 */
	BIZ_APPARCH_SYSTEM("biz_apparch_system", "BizApparchSystem", "ASYS"),
	/**
	 * 应用架构层级
	 */
	GENERAL_APPARCH_LEVEL("general_apparch_level", "GeneralApparchLevel", "APLV"),
	/**
	 * 业务功能点
	 */
	BIZ_APPARCH_BIZ_FUNCTION("biz_apparch_biz_function", "BizApparchBizFunction", "BFNC"),
	/**
	 * 业务场景
	 */
	BIZ_APPARCH_SCENE("biz_apparch_scene", "BizApparchScene", "BSCN"),
	/**
	 * 业务需求
	 */
	BIZ_APPARCH_DEMAND("biz_demand", "BizDemand", "BDMD"),

	/**
	 * 收藏
	 */
	GENERAL_COLLECTION("general_collection", "GeneralCollection", "GECT"),
	/**
	 * 产品及服务
	 */
	GENERAL_PRODUCT_SERVICE("general_product_service", "GeneralProductService", "PRSV"),
	/**
	 * 业务种类定义
	 */
	GENERAL_BIZ_CATEGORY("general_biz_category", "GeneralBizCategory", "BIZC"),
	/**
	 * 项目权限关系表
	 */
	GENERAL_PROJECT_PRIVILEGE_RELATION("general_project_privilege_relation", "GeneralProjectPrivilegeRelation", "AAAA"),
	/**
	 * 报表目录
	 */
	GENERAL_REPORT_CATALOG("general_report_catalog", "GeneralReportCatalog", "RPCT"),
	/**
	 * 报表文件记录
	 */
	GENERAL_REPORT_FILE_INFO("general_report_file_info", "GeneralReportFileInfo", "RPIN"),
	/**
	 * 流程抄送日志
	 */
	GENERAL_FLOW_PROCESS_CC_RECORD("general_flow_process_cc_record", "GeneralFlowProcessCcRecord", "PRCC"),

	/**
	 * 日记便签 general_note_boook
	 */
	GENERAL_NOTE_BOOK("general_note_boook", "GeneralNoteBook", "NOTE"),

	/**
	 * 日历 general_calendar_info
	 */
	GENERAL_CALENDAR_INFO("general_calendar_info", "GeneralCalendarInfo", "CALD"),

	/**
	 * 日历权限 general_calendar_privilege_relation
	 */
	GENERAL_CALENDAR_PRIVILEGE_RELATION("general_calendar_privilege_relation", "GeneralCalendarPrivilegeRelation", "CALP"),

	/**
	 * 日程信息 general_schedule_info
	 */
	GENERAL_SCHEDULE_INFO("general_schedule_info", "GeneralScheduleInfo", "SCHE"),

	/**
	 * 会议信息general_conference_info
	 */
	GENERAL_CONFERENCE_INFO("general_conference_info", "GeneralConferenceInfo", "COFR"),
	
	/**
	 * 会议室 general_conference_room
	 */
	GENERAL_CONFERENCE_ROOM("general_conference_room", "GeneralConferenceRoom", "CFRM"),

	/**
	 * 微服务相关
	 */
	SYS_MICRO_COMPONENT("sys_micro_component", "SysMicroComponent", "MCCM"),
	SYS_MICRO_SERVICE_CONFIG("sys_micro_service_config", "SysMicroServiceConfig", "MCSF"),
	SYS_MICRO_SERVICE("sys_micro_service", "SysMicroService", "MCSV"),
	SYS_MICRO_SERVICE_COMPONENT("sys_micro_service_component", "SysMicroServiceComponent", "MCSC"),
	/**
	 * 用户账户交易明细
	 */
	SYS_USER_TRANS_DETAIL("sys_user_trans_detail", "SysUserTransDetail", "SYTD"),

	/**
	 * 用户账户表
	 */
	SYS_USER_ACCOUNT("sys_user_account", "SysUserAccount", "SYAC"),
	/**
	 * 系统日历表 助手
	 */
	SYS_CALENDER("sys_calender", "SysCalender", "SYCL"),
	/**
	 * 消息通知记录
	 */
	SYS_MSG_NOTICE("sys_msg_notice", "SysMsgNotice", "MSNT"),
	/**
	 * 埋点
	 */
	SYS_BURYING_POINT("sys_burying_point", "SysBuryingPoint", "BYPT"),
	/**
	 * 业务消息配置表
	 */
	SYS_BIZ_MSG_CONFIG("sys_biz_msg_config", "SysBizMsgConfig", "BZMG"),
	/**
	 * 模块配置 sys_module_config_info
	 */
	SYS_MODULE_CONFIG_INFO("sys_module_config_info", "SysModuleConfigInfo", "MODC"),

	/**
	 * 历史记录 sys_history_info
	 */
	SYS_HISTORY_INFO("sys_history_info", "SysHistoryInfo", "HISI"),

	/**
	 * 消息配置 sys_msg_config
	 */
	SYS_MSG_CONFIG("sys_msg_config", "MsgConfigInfo", "MSGC"),

	/**
	 * 消息日志 sys_msg_log
	 */
	SYS_MSG_LOG("sys_msg_log", "SysMsgLog", "MSGL"),

	/**
	 * 系统日志 sys_log
	 */
	SYS_LOG("sys_log", "SysLog", "LOG"),

	/**
	 * 角色 sys_role
	 */
	SYS_ROLE("sys_role", "SysRole", "ROLE"),

	/**
	 * 系统字典 sys_dict
	 */
	SYS_DICT("sys_dict", "SysDict", "SDIC"),

	/**
	 * 系统参数 sys_param
	 */
	SYS_PARAM("sys_param", "SysParam", "SPAR"),

	/**
	 * 菜单表 sys_menu
	 */
	SYS_MENU("sys_menu", "SysMenu", "SMEN"),

	/**
	 * 历史消息表 mc_history_msg_record
	 */
	MC_HISTORY_MSG_RECORD("mc_history_msg_record", "McHistoryMsgRecord", "MHMR"),

	/**
	 * 流程处理记录管理表 general_flow_process_record
	 */
	GENERAL_FLOW_PROCESS_RECORD("general_flow_process_record", "GeneralFlowProcessRecord", "PR"),

	/**
	 * 业务字典 general_biz_dict
	 */
	GENERAL_BIZ_DICT("general_biz_dict", "GeneralBizDict", "BDIC"),

	/**
	 * 业务参数 general_biz_param
	 */
	GENERAL_BIZ_PARAM("general_biz_param", "GeneralBizParam", "BPAR"),

	/**
	 * 文档目录 general_file_catalog
	 */
	GENERAL_FILE_CATALOG("general_file_catalog", "GeneralFileCatalog", "FC"),

	/**
	 * 文档历史 general_file_history
	 */
	GENERAL_FILE_HISTORY("general_file_history", "GeneralFileHistory", "FILH"),

	/**
	 * 文档信息 general_file_info
	 */
	GENERAL_FILE_INFO("general_file_info", "GeneralFileInfo", "FILI"),

	/**
	 * 文档模板 general_file_template
	 */
	GENERAL_FILE_TEMPLATE("general_file_template", "GeneralFileTemplate", "FILT"),

	/**
	 * 流程信息 general_flow_info
	 */
	GENERAL_FLOW_INFO("general_flow_info", "GeneralFlowInfo", "FLIF"),

	/**
	 * 流程节点配置 general_flow_node_config
	 */
	GENERAL_FLOW_NODE_CONFIG("general_flow_node_config", "GeneralFlowNodeConfig", "FLNC"),

	/**
	 * 组织机构 general_org_info
	 */
	GENERAL_ORG_INFO("general_org_info", "GeneralOrgInfo", "GOGI"),

	/**
	 * 系统组织用户关系 general_org_staff_relation
	 */
	GENERAL_ORG_STAFF_RELATION("general_org_staff_relation", "GeneralOrgStaffRelation", "OSRL"),

	/**
	 * 项目信息 general_project_info
	 */
	GENERAL_PROJECT_INFO("general_project_info", "GeneralProjectInfo", "PRJI"),

	/**
	 * 人员管理 general_staff_info
	 */
	GENERAL_STAFF_INFO("general_staff_info", "GeneralStaffInfo", "STAI"),

	/**
	 * 系统错误编码维护
	 */
	SYS_ERROR_CODE("sys_error_code", "SysErrorCode", "SYCD"),
	/**
	 * 系统接口 sys_api_scope
	 */
	SYS_API_SCOPE("sys_api_scope", "SysApiScope", "APIS"),

	/**
	 * 系统动态表单 sys_dynamic_form
	 */
	SYS_DYNAMIC_FORM("sys_dynamic_form", "SysDynamicForm", "DNFM"),

	/**
	 * 系统用户组 sys_group_info
	 */
	SYS_GROUP_INFO("sys_group_info", "SysGroupInfo", "GRPI"),

	/**
	 * 系统用户组角色关系 sys_group_role_relation
	 */
	SYS_GROUP_ROLE_RELATION("sys_group_role_relation", "SysGroupRoleRelation", "GRRL"),

	/**
	 * 系统顶部菜单 sys_menu_top
	 */
	SYS_MENU_TOP("sys_menu_top", "SysMenuTop", "MENT"),

	/**
	 * 系统组织机构 sys_org_info
	 */
	SYS_ORG_INFO("sys_org_info", "SysOrgInfo", "SOGI"),

	/**
	 * 系统组织用户关系 sys_org_user_relation
	 */
	SYS_ORG_USER_RELATION("sys_org_user_relation", "SysOrgUserRelation", "SOUR"),

	/**
	 * 系统角色资源权限关系 sys_role_privilege_relation
	 */
	SYS_ROLE_PRIVILEGE_RELATION("sys_role_privilege_relation", "SysRolePrivilegeRelation", "SRPR"),

	/**
	 * 租户信息 sys_tenant_info
	 */
	SYS_TENANT_INFO("sys_tenant_info", "SysTenantInfo", "STNI"),

	/**
	 * 系统租户权限关系 sys_tenant_privilege_relation
	 */
	SYS_TENANT_PRIVILEGE_RELATION("sys_tenant_privilege_relation", "SysTenantPrivilegeRelation", "STPR"),

	/**
	 * 数据库字段 sys_tool_columns
	 */
	SYS_TOOL_COLUMNS("sys_tool_columns", "SysToolColumns", "STCL"),

	/**
	 * 系统标准定语 sys_tool_dict_attr
	 */
	SYS_TOOL_DICT_ATTR("sys_tool_dict_attr", "SysToolDictAttr", "STDA"),

	/**
	 * 数据标准变量 sys_tool_dict_field
	 */
	SYS_TOOL_DICT_FIELD("sys_tool_dict_field", "SysToolDictField", "STDF"),

	/**
	 * 系统标准词根 sys_tool_dict_root
	 */
	SYS_TOOL_DICT_ROOT("sys_tool_dict_root", "SysToolDictRoot", "STDR"),

	/**
	 * 系统表 sys_tool_tables
	 */
	SYS_TOOL_TABLES("sys_tool_tables", "SysToolTables", "STTB"),

	/**
	 * 系统用户组关系 sys_user_group_relation
	 */
	SYS_USER_GROUP_RELATION("sys_user_group_relation", "SysUserGroupRelation", "SUGR"),

	/**
	 * 系统用户信息 sys_user_info
	 */
	SYS_USER_INFO("sys_user_info", "SysUserInfo", "SUIF"),

	/**
	 * 系统用户角色关系 sys_user_role_relation
	 */
	SYS_USER_ROLE_RELATION("sys_user_role_relation", "SysUserRoleRelation", "SURR"),
	
	/**
	 * 计划管理 biz_plan_manage
	 */
	BIZ_PLAN_MANAGE("biz_plan_manage", "BizPlanManage", "BPLM"),

	/**
	 * 里程碑管理 biz_milestone_manage
	 */
	BIZ_MILESTONE_MANAGE("biz_milestone_manage", "BizMilestoneManage", "BMST"),

	/**
	 * 系统用户设置 sys_user_setting
	 */
	SYS_USER_SETTING("sys_user_setting", "SysUserSetting", "SUST"),
	/**
	 * 系统错误日志
	 */
	SYS_ERROR_LOG("sys_error_log", "SysErrorLog", "ERLG"),
	/**
	 * 第三方配置表 sys_third_party_info
	 */
	SYS_THIRD_PARTY_INFO("sys_third_party_info", "SysThirdPartyInfo", "STPI");

	private final String tableName;
	private final String entityName;
	private final String seqPrefix;

	TableEntityORMEnum(String tableName, String entityName, String seqPrefix) {
		this.tableName = tableName;
		this.entityName = entityName;
		this.seqPrefix = seqPrefix;
	}

	public String getTableName() {
		return tableName;
	}

	public String getEntityName() {
		return entityName;
	}

	public String getSeqPrefix() {
		return seqPrefix;
	}

	public static String getEntityByTable(String tableName) {
		TableEntityORMEnum[] values = TableEntityORMEnum.values();
		for (TableEntityORMEnum value : values) {
			if (value.getTableName().equals(tableName)) {
				return value.getEntityName();
			}
		}
		return null;
	}
}
