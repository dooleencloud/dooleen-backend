/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.43.122
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 192.168.43.122:3306
 Source Schema         : DOOL1002

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 27/02/2024 17:51:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for biz_apparch_biz_function
-- ----------------------------
DROP TABLE IF EXISTS `biz_apparch_biz_function`;
CREATE TABLE `biz_apparch_biz_function` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `biz_scene_no` varchar(30) NOT NULL DEFAULT '' COMMENT '业务场景编号',
  `biz_function_no` varchar(30) NOT NULL DEFAULT '' COMMENT '业务功能点编号',
  `version_no` varchar(30) DEFAULT '' COMMENT '版本号',
  `asset_flag` char(1) DEFAULT '' COMMENT '资产标志',
  `biz_scene_name` varchar(50) DEFAULT '' COMMENT '业务场景名称',
  `biz_function_name` varchar(50) NOT NULL DEFAULT '' COMMENT '业务功能点名称',
  `biz_function_type` varchar(30) DEFAULT '' COMMENT '业务功能点类型',
  `process_way` varchar(30) DEFAULT '' COMMENT '处理方式',
  `biz_seq` int(10) DEFAULT NULL COMMENT '业务序号',
  `project_id` varchar(30) DEFAULT '' COMMENT '项目ID',
  `project_name` varchar(50) DEFAULT '' COMMENT '项目名称',
  `specific_rule_desc` varchar(200) DEFAULT '' COMMENT '特有规则描述',
  `accounting_rule_no` varchar(30) DEFAULT '' COMMENT '核算规则编号',
  `general_rule_no` varchar(30) DEFAULT '' COMMENT '通用规则编号',
  `biz_function_desc` varchar(2000) DEFAULT '' COMMENT '业务功能点描述',
  `change_date` varchar(20) DEFAULT '' COMMENT '变更日期',
  `biz_function_status` varchar(30) DEFAULT '' COMMENT '业务功能点状态',
  `label_info` varchar(2000) NOT NULL DEFAULT '' COMMENT '标签信息',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_apparch_biz_function
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_apparch_demand
-- ----------------------------
DROP TABLE IF EXISTS `biz_apparch_demand`;
CREATE TABLE `biz_apparch_demand` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `project_id` varchar(30) DEFAULT '' COMMENT '项目ID',
  `project_name` varchar(50) DEFAULT '' COMMENT '项目名称',
  `demand_name` varchar(50) NOT NULL DEFAULT '' COMMENT '需求名称',
  `demand_source` varchar(100) NOT NULL DEFAULT '' COMMENT '需求来源',
  `demand_category` varchar(30) NOT NULL DEFAULT '' COMMENT '需求类别',
  `demand_file_address` varchar(2000) DEFAULT '' COMMENT '需求文档地址',
  `demand_desc` varchar(2000) DEFAULT '' COMMENT '需求描述',
  `prior_level` varchar(30) DEFAULT '' COMMENT '优先级',
  `master_dept_name` varchar(30) DEFAULT '' COMMENT '主责部门',
  `assist_dept_name` varchar(30) DEFAULT '' COMMENT '协办部门',
  `demand_propose_user_name` varchar(50) DEFAULT '' COMMENT '需求提出人',
  `demand_propose_real_name` varchar(50) DEFAULT '' COMMENT '需求提出人名',
  `demand_propose_date` varchar(20) DEFAULT '' COMMENT '需求提出日期',
  `demand_accept_user_name` varchar(50) DEFAULT '' COMMENT '需求受理人',
  `demand_accept_real_name` varchar(50) DEFAULT '' COMMENT '需求受理人名',
  `demand_accept_date` varchar(20) DEFAULT '' COMMENT '需求受理日期',
  `review_no` varchar(30) DEFAULT '' COMMENT '评审编号',
  `review_status` varchar(30) DEFAULT '' COMMENT '评审状态',
  `review_pass_date` varchar(20) DEFAULT '' COMMENT '评审通过日期',
  `window_no` varchar(30) DEFAULT '' COMMENT '窗口编号',
  `plan_online_date` varchar(20) DEFAULT '' COMMENT '计划上线日期',
  `actual_online_date` varchar(20) DEFAULT '' COMMENT '实际上线日期',
  `biz_analyse_user_name` varchar(50) DEFAULT '' COMMENT '业务分析人',
  `biz_analyse_real_name` varchar(50) DEFAULT '' COMMENT '业务分析人名',
  `system_analyse_user_name` varchar(50) DEFAULT '' COMMENT '系统分析人',
  `system_analyse_real_name` varchar(50) DEFAULT '' COMMENT '系统分析人名',
  `version_no` varchar(30) DEFAULT '' COMMENT '版本号',
  `change_date` varchar(20) DEFAULT '' COMMENT '变更日期',
  `demand_status` varchar(30) DEFAULT '' COMMENT '需求状态',
  `label_info` varchar(2000) NOT NULL DEFAULT '' COMMENT '标签信息',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_apparch_demand
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_apparch_out_system
-- ----------------------------
DROP TABLE IF EXISTS `biz_apparch_out_system`;
CREATE TABLE `biz_apparch_out_system` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `system_category` varchar(30) NOT NULL DEFAULT '' COMMENT '系统类别',
  `system_name` varchar(50) NOT NULL DEFAULT '' COMMENT '系统名称',
  `system_short_name` varchar(30) DEFAULT '' COMMENT '系统简称',
  `system_english_name` varchar(50) DEFAULT '' COMMENT '系统英文名称',
  `system_function_desc` varchar(2000) DEFAULT '' COMMENT '系统功能描述',
  `join_date` varchar(20) DEFAULT '' COMMENT '对接日期',
  `offline_date` varchar(20) DEFAULT '' COMMENT '下线日期',
  `domestic_abroad_flag` char(1) DEFAULT '' COMMENT '境内外标志',
  `belong_company_name` varchar(50) DEFAULT '' COMMENT '所属单位',
  `contact_user_name` varchar(50) DEFAULT '' COMMENT '联系人',
  `contact_phone_no` varchar(30) DEFAULT '' COMMENT '联系电话',
  `is_local_deploy_flag` char(1) DEFAULT '' COMMENT '是否本地部署标志',
  `data_center_name` varchar(50) DEFAULT '' COMMENT '数据中心名称',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_apparch_out_system
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_apparch_scene
-- ----------------------------
DROP TABLE IF EXISTS `biz_apparch_scene`;
CREATE TABLE `biz_apparch_scene` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `demand_no` varchar(30) NOT NULL DEFAULT '' COMMENT '需求编号',
  `biz_scene_no` varchar(30) DEFAULT '' COMMENT '业务场景编号',
  `version_no` varchar(30) DEFAULT '' COMMENT '版本号',
  `asset_flag` char(1) DEFAULT '0' COMMENT '资产标志',
  `demand_name` varchar(50) NOT NULL DEFAULT '' COMMENT '需求名称',
  `biz_scene_name` varchar(50) NOT NULL DEFAULT '' COMMENT '业务场景名称',
  `scene_category` varchar(30) DEFAULT '' COMMENT '场景类别',
  `product_no` varchar(30) NOT NULL DEFAULT '' COMMENT '产品编号',
  `product_name` varchar(50) NOT NULL DEFAULT '' COMMENT '产品名称',
  `biz_channel` varchar(50) DEFAULT '' COMMENT '业务渠道',
  `service_entrance_name` varchar(50) DEFAULT '' COMMENT '服务入口名称',
  `important_flag` char(1) NOT NULL DEFAULT '' COMMENT '重要性标识',
  `important_grade` varchar(30) NOT NULL DEFAULT '' COMMENT '重要性级别',
  `specific_rule_desc` varchar(200) DEFAULT '' COMMENT '特有规则描述',
  `accounting_rule_no` varchar(30) DEFAULT '' COMMENT '核算规则编号',
  `general_rule_no` varchar(30) DEFAULT '' COMMENT '通用规则编号',
  `project_id` varchar(20) DEFAULT '' COMMENT '项目ID',
  `project_name` varchar(50) DEFAULT '' COMMENT '项目名称',
  `change_date` varchar(20) DEFAULT '' COMMENT '变更日期',
  `scene_status` varchar(30) DEFAULT '' COMMENT '场景状态',
  `label_info` varchar(2000) NOT NULL DEFAULT '' COMMENT '标签信息',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_apparch_scene
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_apparch_system
-- ----------------------------
DROP TABLE IF EXISTS `biz_apparch_system`;
CREATE TABLE `biz_apparch_system` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `parent_system_id` varchar(20) NOT NULL DEFAULT '' COMMENT '上级系统ID',
  `parent_system_name` varchar(50) NOT NULL DEFAULT '' COMMENT '上级系统名称',
  `system_name` varchar(50) NOT NULL DEFAULT '' COMMENT '系统名称',
  `system_short_name` varchar(30) NOT NULL DEFAULT '' COMMENT '系统简称',
  `system_english_name` varchar(50) DEFAULT '' COMMENT '系统英文名称',
  `system_grade` varchar(30) DEFAULT '' COMMENT '系统级别',
  `system_category` varchar(30) NOT NULL DEFAULT '' COMMENT '系统类别',
  `system_type` varchar(30) DEFAULT '' COMMENT '系统类型',
  `belong_dept_name` varchar(50) DEFAULT '' COMMENT '所属部门名称',
  `develop_dept_name` varchar(50) DEFAULT '' COMMENT '开发部门名称',
  `develop_team_name` varchar(50) DEFAULT '' COMMENT '开发团队名称',
  `system_respsb_user_name` varchar(50) DEFAULT '' COMMENT '系统负责人',
  `system_respsb_real_name` varchar(50) DEFAULT '' COMMENT '系统负责人名',
  `build_way` varchar(30) DEFAULT '' COMMENT '建设方式',
  `build_type` varchar(30) DEFAULT '' COMMENT '建设类型',
  `system_version_no` varchar(30) DEFAULT '' COMMENT '系统版本号',
  `system_status` varchar(30) DEFAULT '' COMMENT '系统状态',
  `online_date` varchar(20) DEFAULT '' COMMENT '上线日期',
  `offline_date` varchar(100) DEFAULT '' COMMENT '下线日期',
  `system_function_desc` varchar(2000) DEFAULT '' COMMENT '系统功能描述',
  `architecture_level` varchar(100) NOT NULL DEFAULT '' COMMENT '架构层级',
  `app_group` varchar(100) NOT NULL DEFAULT '' COMMENT '应用群组',
  `function_group` varchar(2000) DEFAULT '' COMMENT '功能组',
  `important_grade` varchar(30) DEFAULT '' COMMENT '重要级别',
  `security_level` varchar(10) DEFAULT '' COMMENT '安全等级',
  `rto_score` decimal(8,2) DEFAULT NULL COMMENT 'RTO分值',
  `rpo_score` decimal(8,2) DEFAULT NULL COMMENT 'RPO分值',
  `system_run_date` varchar(20) DEFAULT '' COMMENT '系统运行日期',
  `system_run_way` varchar(30) NOT NULL DEFAULT '' COMMENT '系统运行方式',
  `may_alone_deploy_flag` char(1) DEFAULT '' COMMENT '可单独部署标志',
  `middleware_list` varchar(2000) DEFAULT '' COMMENT '中间件列表',
  `database_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '数据库列表',
  `label_info` varchar(2000) NOT NULL DEFAULT '' COMMENT '标签信息',
  `project_no` varchar(30) NOT NULL DEFAULT '' COMMENT '项目编号',
  `project_name` varchar(50) NOT NULL DEFAULT '' COMMENT '项目名称',
  `supplier_name` varchar(50) DEFAULT '' COMMENT '供应商名称',
  `supplier_respsb_name` varchar(50) DEFAULT '' COMMENT '供应商负责人',
  `supplier_contact_phone_no` varchar(30) DEFAULT '' COMMENT '供应商联系电话',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_apparch_system
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_apparch_system_function
-- ----------------------------
DROP TABLE IF EXISTS `biz_apparch_system_function`;
CREATE TABLE `biz_apparch_system_function` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `biz_function_no` varchar(30) NOT NULL DEFAULT '' COMMENT '业务功能点编号',
  `system_function_no` varchar(30) NOT NULL DEFAULT '' COMMENT '系统功能点编号',
  `version_no` varchar(30) NOT NULL DEFAULT '' COMMENT '版本号',
  `asset_flag` char(1) DEFAULT '' COMMENT '资产标志',
  `system_type` varchar(30) NOT NULL DEFAULT '' COMMENT '系统类型',
  `system_no` varchar(30) NOT NULL DEFAULT '' COMMENT '系统编号',
  `system_name` varchar(50) NOT NULL DEFAULT '' COMMENT '系统名称',
  `system_status` varchar(30) DEFAULT '' COMMENT '系统状态',
  `function_type` varchar(30) NOT NULL DEFAULT '' COMMENT '功能点类型',
  `biz_function_name` varchar(50) DEFAULT '' COMMENT '业务功能点名称',
  `system_function_name` varchar(50) NOT NULL DEFAULT '' COMMENT '系统功能点名称',
  `system_function_seq` int(10) DEFAULT NULL COMMENT '系统功能点序号',
  `is_reform_flag` char(1) DEFAULT '' COMMENT '是否改造标志',
  `demand_change_no` varchar(30) DEFAULT '' COMMENT '需求变更编号',
  `project_no` varchar(30) DEFAULT '' COMMENT '项目编号',
  `project_name` varchar(50) DEFAULT '' COMMENT '项目名称',
  `change_date` varchar(20) DEFAULT '' COMMENT '变更日期',
  `new_version_no` varchar(30) NOT NULL DEFAULT '' COMMENT '最新版本号',
  `function_status` varchar(30) DEFAULT '' COMMENT '功能点状态',
  `label_info` varchar(2000) DEFAULT '' COMMENT '标签信息',
  `interface_flag` char(1) DEFAULT '' COMMENT '接口标志',
  `interface_no` varchar(30) DEFAULT '' COMMENT '接口编号',
  `interface_name` varchar(50) DEFAULT '' COMMENT '接口名称',
  `link_way` varchar(30) DEFAULT '' COMMENT '连接方式',
  `interactive_type` varchar(30) DEFAULT '' COMMENT '交互类型',
  `service_system_no` varchar(30) DEFAULT '' COMMENT '服务方系统编号',
  `service_system_name` varchar(50) DEFAULT '' COMMENT '服务方系统名称',
  `service_system_status` varchar(30) DEFAULT '' COMMENT '服务方系统状态',
  `service_system_function_no` varchar(30) DEFAULT '' COMMENT '服务方系统功能点编号',
  `service_system_function_name` varchar(50) DEFAULT '' COMMENT '服务方系统功能点名称',
  `service_interface_no` varchar(30) DEFAULT '' COMMENT '服务方接口编号',
  `service_interface_name` varchar(50) DEFAULT '' COMMENT '服务方接口名称',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `systemNo_systemType_validFlag` (`tenant_id`,`system_no`,`system_type`,`valid_flag`) USING BTREE,
  KEY `bizFunctionNo` (`tenant_id`,`biz_function_no`) USING BTREE,
  KEY `systemNo_systemFunctionSeq` (`tenant_id`,`system_function_no`,`version_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_apparch_system_function
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_deman_infomation
-- ----------------------------
DROP TABLE IF EXISTS `biz_deman_infomation`;
CREATE TABLE `biz_deman_infomation` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `demand_no` varchar(30) NOT NULL DEFAULT '' COMMENT '需求编号',
  `demand_name` varchar(50) NOT NULL DEFAULT '' COMMENT '需求名称',
  `demand_desc` varchar(2000) DEFAULT '' COMMENT '需求描述',
  `demand_date` varchar(20) DEFAULT '' COMMENT '需求日期',
  `design_source` varchar(30) DEFAULT '' COMMENT '设计来源',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_deman_infomation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_forum_circle
-- ----------------------------
DROP TABLE IF EXISTS `biz_forum_circle`;
CREATE TABLE `biz_forum_circle` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `circle_name` varchar(50) NOT NULL DEFAULT '' COMMENT '圈子名称',
  `circle_type` varchar(100) NOT NULL COMMENT '圈子类型',
  `approve_flag` char(2) NOT NULL COMMENT '审批标志',
  `img_address` varchar(100) DEFAULT NULL COMMENT '图片地址',
  `circle_desc` varchar(2000) NOT NULL DEFAULT '' COMMENT '圈子描述',
  `circle_member_count` int(10) DEFAULT NULL COMMENT '圈子成员数量',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_forum_circle
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_forum_comment
-- ----------------------------
DROP TABLE IF EXISTS `biz_forum_comment`;
CREATE TABLE `biz_forum_comment` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `posts_id` varchar(20) NOT NULL DEFAULT '' COMMENT '贴子ID',
  `comment_user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '评论人',
  `comment_real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '评论人名',
  `comment_head_img_address` varchar(100) DEFAULT '' COMMENT '评论头像地址',
  `comment_type` varchar(30) DEFAULT '' COMMENT '评论类型',
  `comment_content` text NOT NULL COMMENT '评论内容',
  `comment_img_address` varchar(1000) DEFAULT '' COMMENT '评论图片地址',
  `reply_comment_id` varchar(20) DEFAULT '' COMMENT '回复评论ID',
  `reply_user_name` varchar(50) DEFAULT '' COMMENT '回复人',
  `reply_real_name` varchar(50) DEFAULT '' COMMENT '回复人名',
  `comment_date` varchar(20) DEFAULT '' COMMENT '评论日期',
  `admire_times` int(10) DEFAULT NULL COMMENT '点赞次数',
  `reply_times` int(10) DEFAULT NULL COMMENT '回复次数',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `idx_postsId` (`tenant_id`,`posts_id`,`valid_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_forum_comment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_forum_follow
-- ----------------------------
DROP TABLE IF EXISTS `biz_forum_follow`;
CREATE TABLE `biz_forum_follow` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `be_follow_user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '被关注用户名',
  `user_real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `user_head_img_address` varchar(100) DEFAULT '' COMMENT '用户头像地址',
  `be_follow_user_real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '被关注用户姓名',
  `be_follow_user_head_img_address` varchar(100) DEFAULT '' COMMENT '被关注用户头像地址',
  `follow_datetime` datetime DEFAULT '1990-01-01 00:00:00' COMMENT '关注时间',
  `follow_status` varchar(30) DEFAULT '' COMMENT '关注状态',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_userName_beFollowUserName` (`tenant_id`,`user_name`,`be_follow_user_name`,`follow_status`) USING BTREE,
  KEY `tenantId` (`tenant_id`),
  KEY `idx_beFollowUserName` (`tenant_id`,`be_follow_user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_forum_follow
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_forum_my_circle
-- ----------------------------
DROP TABLE IF EXISTS `biz_forum_my_circle`;
CREATE TABLE `biz_forum_my_circle` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `user_real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `user_head_img_address` varchar(100) DEFAULT '' COMMENT '用户头像地址',
  `circle_id` varchar(20) NOT NULL DEFAULT '' COMMENT '圈子ID',
  `circle_name` varchar(50) NOT NULL DEFAULT '' COMMENT '圈子名称',
  `join_datetime` datetime DEFAULT '1990-01-01 00:00:00' COMMENT '加入时间',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_forum_my_circle
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_forum_posts
-- ----------------------------
DROP TABLE IF EXISTS `biz_forum_posts`;
CREATE TABLE `biz_forum_posts` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `posts_type` varchar(30) NOT NULL DEFAULT '' COMMENT '贴子类型',
  `posts_category` varchar(30) NOT NULL DEFAULT '' COMMENT '贴子分类',
  `title` varchar(200) NOT NULL DEFAULT '' COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `img_list` varchar(2000) DEFAULT '' COMMENT '图片列表',
  `deploy_user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '发布人',
  `deploy_real_name` varchar(50) DEFAULT '' COMMENT '发布人名',
  `deploy_user_head_img_address` varchar(100) DEFAULT '' COMMENT '发布人头像地址',
  `deploy_user_belong_org` varchar(10) DEFAULT '' COMMENT '发布人所属机构',
  `deploy_datetime` datetime DEFAULT '1990-01-01 00:00:00' COMMENT '发布时间',
  `reminder_list` varchar(2000) DEFAULT '' COMMENT '提醒人列表',
  `quote_topic_list` varchar(2000) DEFAULT '' COMMENT '引用话题列表',
  `comment_times` int(10) DEFAULT '0' COMMENT '评论次数',
  `admire_times` int(10) DEFAULT '0' COMMENT '点赞次数',
  `forward_times` int(10) DEFAULT '0' COMMENT '转发次数',
  `view_times` int(10) DEFAULT '0' COMMENT '查看次数',
  `reply_score` decimal(8,2) DEFAULT NULL COMMENT '回复分值',
  `admire_score` decimal(8,2) NOT NULL COMMENT '点赞分值',
  `status` varchar(30) NOT NULL DEFAULT '' COMMENT '状态',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `idx_validFlag` (`tenant_id`,`valid_flag`) USING BTREE,
  KEY `idx_deplayUserName` (`tenant_id`,`deploy_user_name`,`valid_flag`,`create_datetime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_forum_posts
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_forum_question
-- ----------------------------
DROP TABLE IF EXISTS `biz_forum_question`;
CREATE TABLE `biz_forum_question` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `relation_system_name` varchar(50) DEFAULT '' COMMENT '关联系统名称',
  `problem_type` varchar(30) NOT NULL DEFAULT '' COMMENT '问题类型',
  `title` varchar(200) NOT NULL DEFAULT '' COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `img_list` varchar(2000) DEFAULT '' COMMENT '图片列表',
  `deploy_user_name` varchar(50) DEFAULT '' COMMENT '发布人',
  `deploy_real_name` varchar(50) DEFAULT '' COMMENT '发布人名',
  `deploy_user_head_img_address` varchar(100) DEFAULT '' COMMENT '发布人头像地址',
  `deploy_user_belong_org` varchar(10) DEFAULT '' COMMENT '发布人所属机构',
  `deploy_datetime` datetime DEFAULT '1990-01-01 00:00:00' COMMENT '发布时间',
  `reminder_list` varchar(2000) DEFAULT '' COMMENT '提醒人列表',
  `quote_topic_list` varchar(2000) DEFAULT '' COMMENT '引用话题列表',
  `comment_times` int(10) DEFAULT NULL COMMENT '评论次数',
  `admire_times` int(10) DEFAULT NULL COMMENT '点赞次数',
  `forward_times` int(10) DEFAULT NULL COMMENT '转发次数',
  `view_times` int(10) DEFAULT NULL COMMENT '查看次数',
  `reward_score` decimal(8,2) DEFAULT NULL COMMENT '奖励分值',
  `status` varchar(30) DEFAULT '' COMMENT '状态',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `idx_userName` (`tenant_id`,`deploy_user_name`),
  KEY `idx_validFlag` (`tenant_id`,`valid_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_forum_question
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_forum_question_answer
-- ----------------------------
DROP TABLE IF EXISTS `biz_forum_question_answer`;
CREATE TABLE `biz_forum_question_answer` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `question_id` varchar(20) NOT NULL DEFAULT '' COMMENT '问题ID',
  `comment_user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '评论人',
  `comment_real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '评论人名',
  `comment_type` varchar(30) DEFAULT '' COMMENT '评论类型',
  `comment_content` text NOT NULL COMMENT '评论内容',
  `comment_img_address` varchar(1000) DEFAULT '' COMMENT '评论图片地址',
  `reply_id` varchar(20) DEFAULT '' COMMENT '回复ID',
  `reply_user_name` varchar(50) DEFAULT '' COMMENT '回复人',
  `reply_real_name` varchar(50) DEFAULT '' COMMENT '回复人名',
  `comment_date` varchar(20) DEFAULT '' COMMENT '评论日期',
  `admire_times` int(10) DEFAULT NULL COMMENT '点赞次数',
  `reply_times` int(10) DEFAULT NULL COMMENT '回复次数',
  `status` varchar(30) DEFAULT '' COMMENT '状态',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_forum_question_answer
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_forum_thumb_up
-- ----------------------------
DROP TABLE IF EXISTS `biz_forum_thumb_up`;
CREATE TABLE `biz_forum_thumb_up` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `resource_id` varchar(20) NOT NULL DEFAULT '' COMMENT '资源ID',
  `resource_title` varchar(200) DEFAULT '' COMMENT '资源标题',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `user_real_name` varchar(50) DEFAULT '' COMMENT '用户姓名',
  `user_head_img_address` varchar(100) DEFAULT '' COMMENT '用户头像地址',
  `status` varchar(30) NOT NULL DEFAULT '' COMMENT '状态',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点赞记录表';

-- ----------------------------
-- Records of biz_forum_thumb_up
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_forum_topic
-- ----------------------------
DROP TABLE IF EXISTS `biz_forum_topic`;
CREATE TABLE `biz_forum_topic` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `topic_title` varchar(200) NOT NULL DEFAULT '' COMMENT '话题标题',
  `topic_flag` char(1) DEFAULT '' COMMENT '话题标志',
  `topic_posts_count` int(10) DEFAULT NULL COMMENT '话题贴子数量',
  `topic_desc` varchar(2000) DEFAULT '' COMMENT '话题描述',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_forum_topic
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_model_activity
-- ----------------------------
DROP TABLE IF EXISTS `biz_model_activity`;
CREATE TABLE `biz_model_activity` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `project_id` varchar(20) DEFAULT '' COMMENT '项目ID',
  `project_name` varchar(50) DEFAULT '' COMMENT '项目名称',
  `biz_channel` varchar(50) NOT NULL DEFAULT '' COMMENT '业务渠道',
  `activity_name` varchar(50) NOT NULL DEFAULT '' COMMENT '活动名称',
  `activity_desc` varchar(2000) NOT NULL DEFAULT '' COMMENT '活动描述',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模型活动表';

-- ----------------------------
-- Records of biz_model_activity
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_model_activity_task_group
-- ----------------------------
DROP TABLE IF EXISTS `biz_model_activity_task_group`;
CREATE TABLE `biz_model_activity_task_group` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `activity_id` varchar(20) NOT NULL DEFAULT '' COMMENT '活动ID',
  `task_group_id` varchar(20) NOT NULL DEFAULT '' COMMENT '任务组ID',
  `order_seq` int(10) NOT NULL COMMENT '排序序号',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模型活动任务组关系表';

-- ----------------------------
-- Records of biz_model_activity_task_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_model_task_group
-- ----------------------------
DROP TABLE IF EXISTS `biz_model_task_group`;
CREATE TABLE `biz_model_task_group` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `project_id` varchar(20) NOT NULL DEFAULT '' COMMENT '项目ID',
  `project_name` varchar(50) NOT NULL DEFAULT '' COMMENT '项目名称',
  `task_group_name` varchar(50) NOT NULL DEFAULT '' COMMENT '任务组名称',
  `task_group_desc` varchar(2000) NOT NULL DEFAULT '' COMMENT '任务组描述',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模型任务组表';

-- ----------------------------
-- Records of biz_model_task_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_model_task_group_task_item
-- ----------------------------
DROP TABLE IF EXISTS `biz_model_task_group_task_item`;
CREATE TABLE `biz_model_task_group_task_item` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `task_group_id` varchar(20) NOT NULL DEFAULT '' COMMENT '任务组ID',
  `task_item_id` varchar(20) NOT NULL DEFAULT '' COMMENT '任务项ID',
  `order_seq` int(10) NOT NULL COMMENT '排序序号',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模型任务组任务项关系表';

-- ----------------------------
-- Records of biz_model_task_group_task_item
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_model_task_item
-- ----------------------------
DROP TABLE IF EXISTS `biz_model_task_item`;
CREATE TABLE `biz_model_task_item` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `belong_system_id` varchar(20) DEFAULT '' COMMENT '所属系统ID',
  `belong_system_name` varchar(50) DEFAULT '' COMMENT '所属系统名',
  `order_seq` int(10) DEFAULT NULL COMMENT '排序序号',
  `handle_name` varchar(50) NOT NULL DEFAULT '' COMMENT '操作名称',
  `biz_entity_name` varchar(50) NOT NULL DEFAULT '' COMMENT '业务实体名称',
  `judge_condition` varchar(500) DEFAULT '' COMMENT '判断条件',
  `change_attribute_value` varchar(2000) DEFAULT '' COMMENT '变更属性值',
  `public_rule_group` varchar(2000) DEFAULT '' COMMENT '公共规则组',
  `specific_rule_group` varchar(2000) DEFAULT '' COMMENT '特有规则组',
  `accounting_rule_group` varchar(2000) DEFAULT '' COMMENT '核算规则组',
  `demand_item_name` varchar(50) DEFAULT '' COMMENT '需求条目名称',
  `task_exec_premise_condition` varchar(500) DEFAULT '' COMMENT '任务执行前提条件',
  `task_exec_role_name` varchar(50) DEFAULT '' COMMENT '任务执行角色名称',
  `task_input_group` varchar(2000) DEFAULT '' COMMENT '任务输入组',
  `task_output_group` varchar(2000) DEFAULT '' COMMENT '任务输出组',
  `belong_demand_chapter_name` varchar(50) DEFAULT '' COMMENT '所属需求章节名称',
  `belong_domain_center_name` varchar(50) DEFAULT '' COMMENT '所属领域中心名称',
  `belong_product_component` text COMMENT '所属产品组件',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模型任务项表';

-- ----------------------------
-- Records of biz_model_task_item
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_plan_iteration
-- ----------------------------
DROP TABLE IF EXISTS `biz_plan_iteration`;
CREATE TABLE `biz_plan_iteration` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `project_no` varchar(30) NOT NULL DEFAULT '' COMMENT '项目编号',
  `project_name` varchar(50) NOT NULL DEFAULT '' COMMENT '项目名称',
  `iteration_name` varchar(50) NOT NULL DEFAULT '' COMMENT '迭代名称',
  `iteration_desc` varchar(2000) NOT NULL DEFAULT '' COMMENT '迭代描述',
  `milestone_template_id` varchar(20) DEFAULT '' COMMENT '里程碑模板ID',
  `milestone_template_name` varchar(50) DEFAULT '' COMMENT '里程碑模板名称',
  `version_type` varchar(30) DEFAULT '' COMMENT '版本类型',
  `deploy_frequency` varchar(10) DEFAULT '' COMMENT '发布频率',
  `version_window_date` varchar(20) DEFAULT '' COMMENT '版本窗口日期',
  `iteration_begin_date` varchar(20) DEFAULT '' COMMENT '迭代开始日期',
  `iteration_end_date` varchar(20) DEFAULT '' COMMENT '迭代结束日期',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_plan_iteration
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_plan_manage
-- ----------------------------
DROP TABLE IF EXISTS `biz_plan_manage`;
CREATE TABLE `biz_plan_manage` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `project_no` varchar(30) NOT NULL DEFAULT '' COMMENT '项目编号',
  `project_name` varchar(50) NOT NULL DEFAULT '' COMMENT '项目名称',
  `iteration_no` varchar(30) NOT NULL DEFAULT '' COMMENT '迭代编号',
  `iteration_name` varchar(50) NOT NULL DEFAULT '' COMMENT '迭代名称',
  `milestone_id` varchar(20) NOT NULL DEFAULT '' COMMENT '里程碑ID',
  `milestone_name` varchar(50) NOT NULL DEFAULT '' COMMENT '里程碑名称',
  `plan_level` varchar(100) DEFAULT '' COMMENT '计划层级',
  `parent_plan_id` varchar(20) DEFAULT '' COMMENT '父级计划ID',
  `parent_plan_name` varchar(50) DEFAULT '' COMMENT '父级计划名称',
  `prev_plan_id` varchar(20) NOT NULL DEFAULT '' COMMENT '前置计划ID',
  `prev_plan_name` varchar(50) NOT NULL DEFAULT '' COMMENT '前置计划名称',
  `plan_type` varchar(30) NOT NULL DEFAULT '' COMMENT '计划类型',
  `plan_name` varchar(50) NOT NULL DEFAULT '' COMMENT '计划名称',
  `plan_desc` varchar(2000) DEFAULT '' COMMENT '计划描述',
  `respsblt_person_name` varchar(50) NOT NULL DEFAULT '' COMMENT '责任人姓名',
  `respsblt_person_user_name` varchar(20) NOT NULL DEFAULT '' COMMENT '责任人用户名',
  `stakeholder_list` varchar(2000) DEFAULT '' COMMENT '干系人列表',
  `plan_begin_date` varchar(20) NOT NULL DEFAULT '' COMMENT '计划开始日期',
  `plan_finish_date` varchar(20) NOT NULL DEFAULT '' COMMENT '计划完成日期',
  `actual_begin_date` varchar(20) DEFAULT '' COMMENT '实际开始日期',
  `actual_finish_date` varchar(20) DEFAULT '' COMMENT '实际完成日期',
  `plan_investment_time` varchar(100) DEFAULT '' COMMENT '计划投入用时',
  `actual_investment_time` varchar(100) DEFAULT '' COMMENT '实际投入用时',
  `left_time` varchar(100) DEFAULT '' COMMENT '剩余用时',
  `progress` int(11) DEFAULT NULL COMMENT '进度',
  `status` varchar(30) DEFAULT '' COMMENT '状态',
  `prior_level` varchar(30) DEFAULT '' COMMENT '优先级',
  `relation_plan_id` varchar(20) DEFAULT '' COMMENT '关联计划ID',
  `color` varchar(50) DEFAULT '' COMMENT '颜色',
  `order_seq` int(10) DEFAULT NULL COMMENT '排序序号',
  `drag_flag` char(1) DEFAULT '' COMMENT '拖动标志',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `milestone` (`tenant_id`,`project_no`,`iteration_no`,`milestone_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_plan_manage
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_plan_milestone
-- ----------------------------
DROP TABLE IF EXISTS `biz_plan_milestone`;
CREATE TABLE `biz_plan_milestone` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `project_no` varchar(30) NOT NULL DEFAULT '' COMMENT '项目编号',
  `project_name` varchar(50) NOT NULL DEFAULT '' COMMENT '项目名称',
  `iteration_no` varchar(30) NOT NULL DEFAULT '' COMMENT '迭代编号',
  `iteration_name` varchar(50) NOT NULL DEFAULT '' COMMENT '迭代名称',
  `milestone_name` varchar(50) NOT NULL DEFAULT '' COMMENT '里程碑名称',
  `milestone_desc` varchar(2000) DEFAULT '' COMMENT '里程碑描述',
  `order_seq` int(10) DEFAULT NULL COMMENT '排序序号',
  `plan_begin_date` varchar(20) NOT NULL DEFAULT '' COMMENT '计划开始日期',
  `plan_end_date` varchar(20) NOT NULL DEFAULT '' COMMENT '计划结束日期',
  `investment_time` varchar(100) NOT NULL DEFAULT '' COMMENT '投入用时',
  `progress` int(11) NOT NULL COMMENT '进度',
  `color` varchar(50) DEFAULT '' COMMENT '颜色',
  `add_plan_flag` char(1) DEFAULT '' COMMENT '添加计划标志',
  `width_value` int(10) DEFAULT NULL COMMENT '宽度值',
  `height_value` int(10) DEFAULT NULL COMMENT '高度值',
  `drag_flag` char(1) DEFAULT '' COMMENT '拖动标志',
  `flag_name` varchar(50) NOT NULL DEFAULT '' COMMENT '标志名称',
  `flag_style_name` varchar(50) NOT NULL DEFAULT '' COMMENT '标志样式名称',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `iteration` (`tenant_id`,`project_no`,`iteration_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_plan_milestone
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_question_send_list
-- ----------------------------
DROP TABLE IF EXISTS `biz_question_send_list`;
CREATE TABLE `biz_question_send_list` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `project_no` varchar(30) NOT NULL DEFAULT '' COMMENT '项目编号',
  `project_name` varchar(50) DEFAULT '' COMMENT '项目名称',
  `batch_no` varchar(30) NOT NULL DEFAULT '' COMMENT '批次号',
  `fill_in_user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '填报人',
  `fill_in_real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '填报人姓名',
  `questionnaire_template_id` varchar(20) NOT NULL DEFAULT '' COMMENT '问卷模板ID',
  `questionnaire_type` varchar(30) NOT NULL DEFAULT '' COMMENT '问卷类型',
  `questionnaire_title` varchar(200) NOT NULL DEFAULT '' COMMENT '问卷标题',
  `questionnaire_notice` varchar(10000) NOT NULL DEFAULT '' COMMENT '问卷公告',
  `mobile_no` varchar(30) DEFAULT '' COMMENT '手机号码',
  `generate_frequency` varchar(10) DEFAULT '' COMMENT '生成频率',
  `send_datetime` varchar(100) DEFAULT '' COMMENT '发送时间',
  `valid_date` varchar(20) DEFAULT '' COMMENT '有效日期',
  `begin_datetime` varchar(100) NOT NULL DEFAULT '' COMMENT '开始时间',
  `fill_in_datetime` datetime DEFAULT '1990-01-01 00:00:00' COMMENT '填报时间',
  `take_time` varchar(20) DEFAULT '' COMMENT '耗时',
  `status` varchar(30) NOT NULL DEFAULT '' COMMENT '状态',
  `progress_rate` varchar(5) NOT NULL DEFAULT '' COMMENT '进度比例',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_question_send_list
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_question_send_record
-- ----------------------------
DROP TABLE IF EXISTS `biz_question_send_record`;
CREATE TABLE `biz_question_send_record` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `batch_no` varchar(30) NOT NULL DEFAULT '' COMMENT '批次号',
  `template_id` varchar(20) NOT NULL DEFAULT '' COMMENT '模板ID',
  `project_no` varchar(30) NOT NULL DEFAULT '' COMMENT '项目编号',
  `project_name` varchar(50) NOT NULL DEFAULT '' COMMENT '项目名称',
  `questionnaire_type` varchar(30) NOT NULL DEFAULT '' COMMENT '问卷类型',
  `questionnaire_title` varchar(200) NOT NULL DEFAULT '' COMMENT '问卷标题',
  `questionnaire_notice` varchar(10000) NOT NULL DEFAULT '' COMMENT '问卷公告',
  `mobile_no` varchar(30) DEFAULT '' COMMENT '手机号码',
  `generate_frequency` varchar(10) DEFAULT '' COMMENT '生成频率',
  `generate_month` varchar(10) DEFAULT '' COMMENT '生成月',
  `generate_day` varchar(10) DEFAULT '' COMMENT '生成日',
  `generate_datetime` varchar(20) DEFAULT '1990-01-01 00:00:00' COMMENT '生成时间',
  `send_datetime` varchar(20) NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '发送时间',
  `valid_date` varchar(20) NOT NULL DEFAULT '' COMMENT '有效日期',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `batchNo` (`tenant_id`,`batch_no`) USING BTREE,
  KEY `templateId` (`tenant_id`,`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_question_send_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_question_send_result
-- ----------------------------
DROP TABLE IF EXISTS `biz_question_send_result`;
CREATE TABLE `biz_question_send_result` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `project_no` varchar(30) NOT NULL DEFAULT '' COMMENT '项目编号',
  `questionnaire_id` varchar(20) NOT NULL DEFAULT '' COMMENT '问卷ID',
  `batch_no` varchar(30) NOT NULL DEFAULT '' COMMENT '批次号',
  `questionnaire_title` varchar(200) DEFAULT '' COMMENT '问卷标题',
  `subject_type` varchar(30) DEFAULT '' COMMENT '题目类型',
  `subject_answer` varchar(500) DEFAULT '' COMMENT '题目答案',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_question_send_result
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_question_send_subject
-- ----------------------------
DROP TABLE IF EXISTS `biz_question_send_subject`;
CREATE TABLE `biz_question_send_subject` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `project_no` varchar(30) NOT NULL DEFAULT '' COMMENT '项目编号',
  `questionnaire_id` varchar(20) NOT NULL DEFAULT '' COMMENT '问卷ID',
  `show_seq` int(10) NOT NULL COMMENT '显示序号',
  `questionnaire_type` varchar(30) NOT NULL DEFAULT '' COMMENT '问卷类型',
  `questionnaire_title` varchar(200) NOT NULL DEFAULT '' COMMENT '问卷标题',
  `batch_no` varchar(30) NOT NULL DEFAULT '' COMMENT '批次号',
  `fill_in_user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '填报人',
  `fill_in_real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '填报人姓名',
  `subject_config_id` varchar(20) NOT NULL DEFAULT '' COMMENT '题目配置ID',
  `subject_title` varchar(200) NOT NULL DEFAULT '' COMMENT '题目标题',
  `subject_type` varchar(30) NOT NULL DEFAULT '' COMMENT '题目类型',
  `subject_properties` varchar(30) NOT NULL DEFAULT '' COMMENT '题目性质',
  `subject_content` text COMMENT '题目内容',
  `subject_answer` varchar(500) DEFAULT '' COMMENT '题目答案',
  `answer_result` varchar(5000) DEFAULT '' COMMENT '答题结果',
  `score` decimal(8,2) NOT NULL COMMENT '分值',
  `is_required` char(1) DEFAULT '' COMMENT '是否必填',
  `project_name` varchar(50) DEFAULT '' COMMENT '项目名称',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `questionnaire_id` (`questionnaire_id`) USING BTREE,
  KEY `subjectTitle` (`tenant_id`,`batch_no`,`subject_title`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_question_send_subject
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_question_subject_config
-- ----------------------------
DROP TABLE IF EXISTS `biz_question_subject_config`;
CREATE TABLE `biz_question_subject_config` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `project_no` varchar(30) NOT NULL DEFAULT '' COMMENT '项目编号',
  `questionnaire_template_id` varchar(20) NOT NULL DEFAULT '' COMMENT '问卷模板ID',
  `show_seq` int(10) NOT NULL COMMENT '显示序号',
  `questionnaire_template_title` varchar(200) NOT NULL DEFAULT '' COMMENT '问卷模板标题',
  `project_name` varchar(50) DEFAULT '' COMMENT '项目名称',
  `subject_type` varchar(30) NOT NULL DEFAULT '' COMMENT '题目类型',
  `subject_properties` varchar(30) NOT NULL DEFAULT '' COMMENT '题目性质',
  `subject_title` varchar(200) NOT NULL DEFAULT '' COMMENT '题目标题',
  `subject_content` text NOT NULL COMMENT '题目内容',
  `subject_answer` varchar(500) DEFAULT '' COMMENT '题目答案',
  `is_required` char(1) NOT NULL DEFAULT '' COMMENT '是否必填',
  `score` decimal(8,2) DEFAULT NULL COMMENT '分值',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `questionnaire_template_id` (`questionnaire_template_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_question_subject_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_question_template
-- ----------------------------
DROP TABLE IF EXISTS `biz_question_template`;
CREATE TABLE `biz_question_template` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `project_no` varchar(30) NOT NULL DEFAULT '' COMMENT '项目编号',
  `project_name` varchar(50) NOT NULL DEFAULT '' COMMENT '项目名称',
  `questionnaire_type` varchar(30) NOT NULL DEFAULT '' COMMENT '问卷类型',
  `questionnaire_title` varchar(200) NOT NULL DEFAULT '' COMMENT '问卷标题',
  `questionnaire_notice` varchar(10000) NOT NULL DEFAULT '' COMMENT '问卷公告',
  `mobile_no` varchar(30) DEFAULT '' COMMENT '手机号码',
  `generate_frequency` varchar(10) NOT NULL DEFAULT '' COMMENT '生成频率',
  `generate_month` varchar(10) NOT NULL DEFAULT '' COMMENT '生成月',
  `generate_day` varchar(10) NOT NULL DEFAULT '' COMMENT '生成日',
  `generate_date` varchar(20) NOT NULL DEFAULT '' COMMENT '生成日期',
  `generate_datetime` varchar(100) NOT NULL DEFAULT '00:00' COMMENT '生成时间',
  `send_datetime` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '发送时间',
  `valid_date` varchar(20) NOT NULL DEFAULT '' COMMENT '有效日期',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_question_template
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_sixteen_eight_sf
-- ----------------------------
DROP TABLE IF EXISTS `biz_sixteen_eight_sf`;
CREATE TABLE `biz_sixteen_eight_sf` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `factor_id` varchar(10) NOT NULL DEFAULT '' COMMENT '因子ID',
  `factor` varchar(50) NOT NULL DEFAULT '' COMMENT '因子名称',
  `low_score_min` int(10) DEFAULT NULL COMMENT '向下最小值',
  `low_score_max` int(10) DEFAULT NULL COMMENT '向下最大值',
  `low_factor_content` varchar(5000) DEFAULT '' COMMENT '向下因子内容',
  `hight_score_min` int(10) DEFAULT NULL COMMENT '向上最小值',
  `hight_score_max` int(10) DEFAULT NULL COMMENT '向上最大值',
  `hight_factor_content` varchar(5000) DEFAULT '' COMMENT '向上因子内容',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_sixteen_eight_sf
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_sixteen_pf
-- ----------------------------
DROP TABLE IF EXISTS `biz_sixteen_pf`;
CREATE TABLE `biz_sixteen_pf` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `factor_id` varchar(10) NOT NULL DEFAULT '' COMMENT '因子ID',
  `factor` varchar(50) NOT NULL DEFAULT '' COMMENT '因子名称',
  `low_score_min` int(10) DEFAULT NULL COMMENT '向下最小值',
  `low_score_max` int(10) DEFAULT NULL COMMENT '向下最大值',
  `low_score_features` text COMMENT '向下分数特征',
  `high_score_min` int(10) DEFAULT NULL COMMENT '向上最小值',
  `high_score_max` int(10) DEFAULT NULL COMMENT '向上最大值',
  `high_score_features` text COMMENT '向上分数特征',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_sixteen_pf
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_sixteen_topic_db
-- ----------------------------
DROP TABLE IF EXISTS `biz_sixteen_topic_db`;
CREATE TABLE `biz_sixteen_topic_db` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `topic_num` int(5) NOT NULL COMMENT '题目编号',
  `factor_id` varchar(10) NOT NULL DEFAULT '' COMMENT '因子ID',
  `topic_content` varchar(1000) NOT NULL DEFAULT '' COMMENT '题目内容',
  `options` varchar(2000) NOT NULL DEFAULT '' COMMENT '选项',
  `right_answer` varchar(10) DEFAULT '' COMMENT '正确答案',
  `right_answer_score` int(10) DEFAULT NULL COMMENT '正确答案分数',
  `pre_answer` varchar(10) DEFAULT '' COMMENT '备选答案',
  `pre_answer_score` int(10) DEFAULT NULL COMMENT '备选答案分数',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_sixteen_topic_db
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_sixteen_user_analysis
-- ----------------------------
DROP TABLE IF EXISTS `biz_sixteen_user_analysis`;
CREATE TABLE `biz_sixteen_user_analysis` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `factor_id` varchar(10) NOT NULL DEFAULT '' COMMENT '因子ID ',
  `factor_type` varchar(10) NOT NULL DEFAULT '' COMMENT '因子类型',
  `factor` varchar(100) NOT NULL DEFAULT '' COMMENT '因子',
  `score` decimal(10,0) DEFAULT NULL COMMENT '分值',
  `status_score` decimal(10,2) DEFAULT NULL COMMENT '标准分值',
  `status` varchar(10) DEFAULT '' COMMENT '状态',
  `low_score_features` varchar(1000) DEFAULT '' COMMENT '向下分特征',
  `high_score_features` varchar(1000) DEFAULT '' COMMENT '向上分特征',
  `date` varchar(20) DEFAULT '' COMMENT '时间',
  `answer_duration` varchar(30) DEFAULT '' COMMENT '回答耗时',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `userName` (`tenant_id`,`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_sixteen_user_analysis
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_sixteen_user_answers
-- ----------------------------
DROP TABLE IF EXISTS `biz_sixteen_user_answers`;
CREATE TABLE `biz_sixteen_user_answers` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `topic_num` int(10) NOT NULL COMMENT '题目编号',
  `sex` varchar(10) DEFAULT '' COMMENT '性别',
  `age` int(10) DEFAULT NULL COMMENT '年龄',
  `factor_id` varchar(10) DEFAULT '' COMMENT '因子ID',
  `topic_content` varchar(500) DEFAULT '' COMMENT '题目内容',
  `options` varchar(2000) DEFAULT '' COMMENT '选项',
  `right_answer` varchar(10) DEFAULT '' COMMENT '正确答案',
  `right_answer_score` int(100) DEFAULT NULL COMMENT '正确答案分值',
  `pre_answer` varchar(10) DEFAULT '' COMMENT '备选答案',
  `pre_answer_score` int(10) NOT NULL COMMENT '备选答案分值',
  `user_answer` varchar(10) NOT NULL DEFAULT '' COMMENT '用户答案',
  `user_answer_score` int(10) NOT NULL COMMENT '用户答案分值',
  `date_time` varchar(20) NOT NULL DEFAULT '' COMMENT '时间',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `userName` (`tenant_id`,`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_sixteen_user_answers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_sixteen_user_info
-- ----------------------------
DROP TABLE IF EXISTS `biz_sixteen_user_info`;
CREATE TABLE `biz_sixteen_user_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `wx_open_id` varchar(50) NOT NULL DEFAULT '' COMMENT '微信openID',
  `app_wx_open_id` varchar(50) NOT NULL DEFAULT '' COMMENT 'APP微信openID',
  `org_name` varchar(50) DEFAULT NULL COMMENT '组织名称',
  `email` varchar(100) DEFAULT '' COMMENT '邮箱',
  `phone` varchar(20) DEFAULT '' COMMENT '电话',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `sex` varchar(10) DEFAULT '' COMMENT '性别',
  `age` int(10) DEFAULT NULL COMMENT '年龄',
  `answer_begin_time` varchar(20) DEFAULT '' COMMENT '答题开始时间',
  `answer_end_time` varchar(20) DEFAULT '' COMMENT '答题结束时间',
  `take_time` int(10) DEFAULT NULL COMMENT '消耗时间',
  `progress_rate` decimal(10,4) DEFAULT NULL COMMENT '进度比例',
  `end_flag` varchar(10) DEFAULT '' COMMENT '结束标志',
  `reg_time` varchar(20) DEFAULT '' COMMENT '注册时间',
  `analysis_status` varchar(10) DEFAULT '' COMMENT '分析状态',
  `analysis_desc` varchar(1000) DEFAULT '' COMMENT '分析描述',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userName` (`tenant_id`,`user_name`) USING BTREE,
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_sixteen_user_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_sixteen_user_pf_exch
-- ----------------------------
DROP TABLE IF EXISTS `biz_sixteen_user_pf_exch`;
CREATE TABLE `biz_sixteen_user_pf_exch` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `factor_id` varchar(10) NOT NULL DEFAULT '' COMMENT '因子ID',
  `real_name` varchar(50) DEFAULT '' COMMENT '用户姓名',
  `sex` varchar(10) DEFAULT '' COMMENT '性别',
  `age` int(10) DEFAULT NULL COMMENT '年龄',
  `origin_score` int(10) DEFAULT NULL COMMENT '原始分值',
  `compa_score` int(10) DEFAULT NULL COMMENT '复合分值',
  `status_score` int(10) DEFAULT NULL COMMENT '状态分值',
  `date` varchar(10) DEFAULT '' COMMENT '日期',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_sixteen_user_pf_exch
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for biz_test_form
-- ----------------------------
DROP TABLE IF EXISTS `biz_test_form`;
CREATE TABLE `biz_test_form` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `age` int(4) NOT NULL COMMENT '年龄',
  `sex` varchar(2) NOT NULL DEFAULT '' COMMENT '性别',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模型任务组任务项关系表';

-- ----------------------------
-- Records of biz_test_form
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_cron_job
-- ----------------------------
DROP TABLE IF EXISTS `davinci_cron_job`;
CREATE TABLE `davinci_cron_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '租户ID',
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `job_type` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `job_status` varchar(10) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `cron_expression` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `config` text COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `exec_log` text COLLATE utf8_unicode_ci,
  `create_by` bigint(20) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `full_parent_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_folder` tinyint(1) DEFAULT NULL,
  `index` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name_UNIQUE` (`name`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of davinci_cron_job
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_dashboard
-- ----------------------------
DROP TABLE IF EXISTS `davinci_dashboard`;
CREATE TABLE `davinci_dashboard` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `name` varchar(255) NOT NULL,
  `dashboard_portal_id` bigint(20) NOT NULL,
  `type` smallint(1) NOT NULL,
  `index` int(4) NOT NULL,
  `parent_id` bigint(20) NOT NULL DEFAULT '0',
  `config` text,
  `full_parent_Id` varchar(100) DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_dashboard_id` (`dashboard_portal_id`) USING BTREE,
  KEY `idx_parent_id` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of davinci_dashboard
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_dashboard_portal
-- ----------------------------
DROP TABLE IF EXISTS `davinci_dashboard_portal`;
CREATE TABLE `davinci_dashboard_portal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `project_id` bigint(20) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `publish` tinyint(1) NOT NULL DEFAULT '0',
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_project_id` (`project_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of davinci_dashboard_portal
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_davinci_statistic_duration
-- ----------------------------
DROP TABLE IF EXISTS `davinci_davinci_statistic_duration`;
CREATE TABLE `davinci_davinci_statistic_duration` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `user_id` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `org_id` bigint(20) DEFAULT NULL COMMENT '报表关联组织ID',
  `project_id` bigint(20) DEFAULT NULL COMMENT '报表关联项目ID',
  `project_name` varchar(255) DEFAULT NULL COMMENT '报表关联项目名称',
  `viz_type` varchar(10) DEFAULT NULL COMMENT '报表关联应用类型（dashboard/display）',
  `viz_id` bigint(20) DEFAULT NULL COMMENT '报表关联应用ID',
  `viz_name` varchar(255) DEFAULT NULL COMMENT '报表关联应用名称',
  `sub_viz_id` bigint(20) DEFAULT NULL COMMENT '报表ID',
  `sub_viz_name` varchar(255) DEFAULT NULL COMMENT '报表名称',
  `start_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of davinci_davinci_statistic_duration
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_davinci_statistic_terminal
-- ----------------------------
DROP TABLE IF EXISTS `davinci_davinci_statistic_terminal`;
CREATE TABLE `davinci_davinci_statistic_terminal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `user_id` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `browser_name` varchar(255) DEFAULT NULL,
  `browser_version` varchar(255) DEFAULT NULL,
  `engine_name` varchar(255) DEFAULT NULL,
  `engine_version` varchar(255) DEFAULT NULL,
  `os_name` varchar(255) DEFAULT NULL,
  `os_version` varchar(255) DEFAULT NULL,
  `device_model` varchar(255) DEFAULT NULL,
  `device_type` varchar(255) DEFAULT NULL,
  `device_vendor` varchar(255) DEFAULT NULL,
  `cpu_architecture` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of davinci_davinci_statistic_terminal
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_davinci_statistic_visitor_operation
-- ----------------------------
DROP TABLE IF EXISTS `davinci_davinci_statistic_visitor_operation`;
CREATE TABLE `davinci_davinci_statistic_visitor_operation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `user_id` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL COMMENT 'login/visit/initial/sync/search/linkage/drill/download/print',
  `org_id` bigint(20) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `viz_type` varchar(255) DEFAULT NULL COMMENT 'dashboard/display',
  `viz_id` bigint(20) DEFAULT NULL,
  `viz_name` varchar(255) DEFAULT NULL,
  `sub_viz_id` bigint(20) DEFAULT NULL,
  `sub_viz_name` varchar(255) DEFAULT NULL,
  `widget_id` bigint(20) DEFAULT NULL,
  `widget_name` varchar(255) DEFAULT NULL,
  `variables` varchar(500) DEFAULT NULL,
  `filters` varchar(500) DEFAULT NULL,
  `groups` varchar(500) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of davinci_davinci_statistic_visitor_operation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_display
-- ----------------------------
DROP TABLE IF EXISTS `davinci_display`;
CREATE TABLE `davinci_display` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `project_id` bigint(20) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `publish` tinyint(1) NOT NULL,
  `config` text,
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_project_id` (`project_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of davinci_display
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_display_slide
-- ----------------------------
DROP TABLE IF EXISTS `davinci_display_slide`;
CREATE TABLE `davinci_display_slide` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `display_id` bigint(20) NOT NULL,
  `index` int(12) NOT NULL,
  `config` text NOT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_display_id` (`display_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of davinci_display_slide
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_download_record
-- ----------------------------
DROP TABLE IF EXISTS `davinci_download_record`;
CREATE TABLE `davinci_download_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `name` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `path` varchar(255) DEFAULT NULL,
  `status` smallint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `last_download_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of davinci_download_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_favorite
-- ----------------------------
DROP TABLE IF EXISTS `davinci_favorite`;
CREATE TABLE `davinci_favorite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `user_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_user_project` (`user_id`,`project_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of davinci_favorite
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_mem_dashboard_widget
-- ----------------------------
DROP TABLE IF EXISTS `davinci_mem_dashboard_widget`;
CREATE TABLE `davinci_mem_dashboard_widget` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `alias` varchar(30) DEFAULT NULL,
  `dashboard_id` bigint(20) NOT NULL,
  `widget_Id` bigint(20) DEFAULT NULL,
  `x` int(12) NOT NULL,
  `y` int(12) NOT NULL,
  `width` int(12) NOT NULL,
  `height` int(12) NOT NULL,
  `polling` tinyint(1) NOT NULL DEFAULT '0',
  `frequency` int(12) DEFAULT NULL,
  `config` text,
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_protal_id` (`dashboard_id`) USING BTREE,
  KEY `idx_widget_id` (`widget_Id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of davinci_mem_dashboard_widget
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_mem_display_slide_widget
-- ----------------------------
DROP TABLE IF EXISTS `davinci_mem_display_slide_widget`;
CREATE TABLE `davinci_mem_display_slide_widget` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `display_slide_id` bigint(20) NOT NULL,
  `widget_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `params` text NOT NULL,
  `type` smallint(1) NOT NULL,
  `sub_type` smallint(2) DEFAULT NULL,
  `index` int(12) NOT NULL DEFAULT '0',
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_slide_id` (`display_slide_id`) USING BTREE,
  KEY `idx_widget_id` (`widget_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of davinci_mem_display_slide_widget
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_organization
-- ----------------------------
DROP TABLE IF EXISTS `davinci_organization`;
CREATE TABLE `davinci_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `project_num` int(20) DEFAULT '0',
  `member_num` int(20) DEFAULT '0',
  `role_num` int(20) DEFAULT '0',
  `allow_create_project` tinyint(1) DEFAULT '1',
  `member_permission` smallint(1) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` timestamp NULL DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of davinci_organization
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_platform
-- ----------------------------
DROP TABLE IF EXISTS `davinci_platform`;
CREATE TABLE `davinci_platform` (
  `id` bigint(20) NOT NULL,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `name` varchar(255) NOT NULL,
  `platform` varchar(255) NOT NULL,
  `code` varchar(32) NOT NULL,
  `checkCode` varchar(255) DEFAULT NULL,
  `checkSystemToken` varchar(255) DEFAULT NULL,
  `checkUrl` varchar(255) DEFAULT NULL,
  `alternateField1` varchar(255) DEFAULT NULL,
  `alternateField2` varchar(255) DEFAULT NULL,
  `alternateField3` varchar(255) DEFAULT NULL,
  `alternateField4` varchar(255) DEFAULT NULL,
  `alternateField5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of davinci_platform
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_project
-- ----------------------------
DROP TABLE IF EXISTS `davinci_project`;
CREATE TABLE `davinci_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `pic` varchar(255) DEFAULT NULL,
  `org_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `visibility` tinyint(1) DEFAULT '1',
  `star_num` int(11) DEFAULT '0',
  `is_transfer` tinyint(1) NOT NULL DEFAULT '0',
  `initial_org_id` bigint(20) NOT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of davinci_project
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_rel_project_admin
-- ----------------------------
DROP TABLE IF EXISTS `davinci_rel_project_admin`;
CREATE TABLE `davinci_rel_project_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `project_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_project_user` (`project_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='project admin表';

-- ----------------------------
-- Records of davinci_rel_project_admin
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_rel_role_dashboard
-- ----------------------------
DROP TABLE IF EXISTS `davinci_rel_role_dashboard`;
CREATE TABLE `davinci_rel_role_dashboard` (
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `role_id` bigint(20) NOT NULL,
  `dashboard_id` bigint(20) NOT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT '0',
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`,`dashboard_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of davinci_rel_role_dashboard
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_rel_role_dashboard_widget
-- ----------------------------
DROP TABLE IF EXISTS `davinci_rel_role_dashboard_widget`;
CREATE TABLE `davinci_rel_role_dashboard_widget` (
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `role_id` bigint(20) NOT NULL,
  `mem_dashboard_widget_id` bigint(20) NOT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT '0',
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`,`mem_dashboard_widget_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of davinci_rel_role_dashboard_widget
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_rel_role_display
-- ----------------------------
DROP TABLE IF EXISTS `davinci_rel_role_display`;
CREATE TABLE `davinci_rel_role_display` (
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `role_id` bigint(20) NOT NULL,
  `display_id` bigint(20) NOT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT '0',
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`,`display_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of davinci_rel_role_display
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_rel_role_display_slide_widget
-- ----------------------------
DROP TABLE IF EXISTS `davinci_rel_role_display_slide_widget`;
CREATE TABLE `davinci_rel_role_display_slide_widget` (
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `role_id` bigint(20) NOT NULL,
  `mem_display_slide_widget_id` bigint(20) NOT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT '0',
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`,`mem_display_slide_widget_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of davinci_rel_role_display_slide_widget
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_rel_role_portal
-- ----------------------------
DROP TABLE IF EXISTS `davinci_rel_role_portal`;
CREATE TABLE `davinci_rel_role_portal` (
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `role_id` bigint(20) NOT NULL,
  `portal_id` bigint(20) NOT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT '0',
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`,`portal_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of davinci_rel_role_portal
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_rel_role_project
-- ----------------------------
DROP TABLE IF EXISTS `davinci_rel_role_project`;
CREATE TABLE `davinci_rel_role_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `project_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `source_permission` smallint(1) NOT NULL DEFAULT '1',
  `view_permission` smallint(1) NOT NULL DEFAULT '1',
  `widget_permission` smallint(1) NOT NULL DEFAULT '1',
  `viz_permission` smallint(1) NOT NULL DEFAULT '1',
  `schedule_permission` smallint(1) NOT NULL DEFAULT '1',
  `share_permission` tinyint(1) NOT NULL DEFAULT '0',
  `download_permission` tinyint(1) NOT NULL DEFAULT '0',
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_role_project` (`project_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of davinci_rel_role_project
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_rel_role_slide
-- ----------------------------
DROP TABLE IF EXISTS `davinci_rel_role_slide`;
CREATE TABLE `davinci_rel_role_slide` (
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `role_id` bigint(20) NOT NULL,
  `slide_id` bigint(20) NOT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT '0',
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`,`slide_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of davinci_rel_role_slide
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_rel_role_user
-- ----------------------------
DROP TABLE IF EXISTS `davinci_rel_role_user`;
CREATE TABLE `davinci_rel_role_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_role_user` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of davinci_rel_role_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_rel_role_view
-- ----------------------------
DROP TABLE IF EXISTS `davinci_rel_role_view`;
CREATE TABLE `davinci_rel_role_view` (
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `view_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `row_auth` text,
  `column_auth` text,
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`view_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of davinci_rel_role_view
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_rel_user_organization
-- ----------------------------
DROP TABLE IF EXISTS `davinci_rel_user_organization`;
CREATE TABLE `davinci_rel_user_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `org_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `role` smallint(1) NOT NULL DEFAULT '0',
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_org_user` (`org_id`,`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of davinci_rel_user_organization
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_role
-- ----------------------------
DROP TABLE IF EXISTS `davinci_role`;
CREATE TABLE `davinci_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `org_id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_orgid` (`org_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ----------------------------
-- Records of davinci_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_share_download_record
-- ----------------------------
DROP TABLE IF EXISTS `davinci_share_download_record`;
CREATE TABLE `davinci_share_download_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `uuid` varchar(50) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `path` varchar(255) DEFAULT NULL,
  `status` smallint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `last_download_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of davinci_share_download_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_source
-- ----------------------------
DROP TABLE IF EXISTS `davinci_source`;
CREATE TABLE `davinci_source` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `config` text NOT NULL,
  `type` varchar(10) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `full_parent_id` varchar(255) DEFAULT NULL,
  `is_folder` tinyint(1) DEFAULT NULL,
  `index` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_project_id` (`project_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of davinci_source
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_star
-- ----------------------------
DROP TABLE IF EXISTS `davinci_star`;
CREATE TABLE `davinci_star` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `target` varchar(20) NOT NULL,
  `target_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `star_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_target_id` (`target_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of davinci_star
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_user
-- ----------------------------
DROP TABLE IF EXISTS `davinci_user`;
CREATE TABLE `davinci_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `admin` tinyint(1) NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` bigint(20) NOT NULL DEFAULT '0',
  `update_time` timestamp NULL DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of davinci_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_view
-- ----------------------------
DROP TABLE IF EXISTS `davinci_view`;
CREATE TABLE `davinci_view` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `project_id` bigint(20) NOT NULL,
  `source_id` bigint(20) NOT NULL,
  `sql` text,
  `model` text,
  `variable` text,
  `config` text,
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `full_parent_id` varchar(255) DEFAULT NULL,
  `is_folder` tinyint(1) DEFAULT NULL,
  `index` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_project_id` (`project_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of davinci_view
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for davinci_widget
-- ----------------------------
DROP TABLE IF EXISTS `davinci_widget`;
CREATE TABLE `davinci_widget` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `view_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `type` bigint(20) NOT NULL,
  `publish` tinyint(1) NOT NULL,
  `config` longtext NOT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `full_parent_id` varchar(255) DEFAULT NULL,
  `is_folder` tinyint(1) DEFAULT NULL,
  `index` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_project_id` (`project_id`) USING BTREE,
  KEY `idx_view_id` (`view_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of davinci_widget
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for deposit_account
-- ----------------------------
DROP TABLE IF EXISTS `deposit_account`;
CREATE TABLE `deposit_account` (
  `id` varchar(10) NOT NULL,
  `amt` int(10) DEFAULT NULL,
  `freeze_amt` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of deposit_account
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_apparch_level
-- ----------------------------
DROP TABLE IF EXISTS `general_apparch_level`;
CREATE TABLE `general_apparch_level` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `architecture_level` varchar(100) NOT NULL DEFAULT '' COMMENT '架构层级',
  `app_group` varchar(100) DEFAULT '' COMMENT '应用群组',
  `function_group` varchar(100) DEFAULT '' COMMENT '功能组',
  `belong_dept_name` varchar(30) DEFAULT '' COMMENT '所属部门',
  `respsb_user_name` varchar(50) DEFAULT '' COMMENT '负责人',
  `respsb_real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '负责人名',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_apparch_level
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_biz_category
-- ----------------------------
DROP TABLE IF EXISTS `general_biz_category`;
CREATE TABLE `general_biz_category` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `biz_plate` varchar(30) NOT NULL DEFAULT '' COMMENT '业务板块',
  `biz_lines` varchar(30) NOT NULL DEFAULT '' COMMENT '业务条线',
  `biz_category` varchar(30) NOT NULL DEFAULT '' COMMENT '业务种类',
  `belong_dept_name` varchar(30) DEFAULT '' COMMENT '所属部门',
  `respsb_user_name` varchar(50) DEFAULT '' COMMENT '负责人',
  `respsb_real_name` varchar(50) DEFAULT '' COMMENT '负责人名',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_biz_category
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_biz_demand
-- ----------------------------
DROP TABLE IF EXISTS `general_biz_demand`;
CREATE TABLE `general_biz_demand` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `demand_no` varchar(30) DEFAULT '' COMMENT '需求编号',
  `demand_name` varchar(50) DEFAULT '' COMMENT '需求名称',
  `demand_file_name` varchar(50) DEFAULT '' COMMENT '需求文档名称',
  `demand_belong_user_name` varchar(50) DEFAULT '' COMMENT '需求所属用户名',
  `demand_status` varchar(30) DEFAULT '' COMMENT '需求状态',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_biz_demand
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_biz_dict
-- ----------------------------
DROP TABLE IF EXISTS `general_biz_dict`;
CREATE TABLE `general_biz_dict` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `dict_category` varchar(30) NOT NULL DEFAULT '' COMMENT '字典分类',
  `dict_no` varchar(200) NOT NULL DEFAULT '' COMMENT '字典编号',
  `dict_name` varchar(50) NOT NULL DEFAULT '' COMMENT '字典名称',
  `dict_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '字典列表',
  `dict_status` varchar(30) DEFAULT '' COMMENT '字典状态',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tenantIdDictNo` (`tenant_id`,`dict_no`) USING BTREE,
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_biz_dict
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_biz_param
-- ----------------------------
DROP TABLE IF EXISTS `general_biz_param`;
CREATE TABLE `general_biz_param` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `param_type` varchar(30) NOT NULL DEFAULT '' COMMENT '参数类型',
  `param_name` varchar(50) NOT NULL DEFAULT '' COMMENT '参数名',
  `param_key` varchar(50) NOT NULL DEFAULT '' COMMENT '参数键',
  `param_value` varchar(2000) NOT NULL DEFAULT '' COMMENT '参数值',
  `param_value_tow` varchar(2000) DEFAULT '' COMMENT '参数值2',
  `param_value_three` varchar(2000) DEFAULT '' COMMENT '参数值3',
  `remark` varchar(5000) DEFAULT '' COMMENT '备注',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `tenantId` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_biz_param
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_calendar_info
-- ----------------------------
DROP TABLE IF EXISTS `general_calendar_info`;
CREATE TABLE `general_calendar_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `calendar_name` varchar(50) NOT NULL DEFAULT '' COMMENT '日历名称',
  `calendar_desc` varchar(2000) NOT NULL DEFAULT '' COMMENT '日历描述',
  `calendar_type` varchar(30) NOT NULL DEFAULT '' COMMENT '日历类型',
  `owner_id` varchar(20) NOT NULL DEFAULT '' COMMENT '拥有人ID',
  `share_flag` char(1) NOT NULL DEFAULT '' COMMENT '分享标志',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_calendar_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_calendar_privilege_relation
-- ----------------------------
DROP TABLE IF EXISTS `general_calendar_privilege_relation`;
CREATE TABLE `general_calendar_privilege_relation` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `calendar_id` varchar(20) NOT NULL DEFAULT '' COMMENT '日历ID',
  `privilege_id` varchar(20) NOT NULL DEFAULT '' COMMENT '权限ID',
  `handle_type` varchar(30) NOT NULL DEFAULT '' COMMENT '操作类型',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_calendar_privilege_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_collection
-- ----------------------------
DROP TABLE IF EXISTS `general_collection`;
CREATE TABLE `general_collection` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `resource_id` varchar(20) NOT NULL DEFAULT '' COMMENT '资源ID',
  `resource_title` varchar(200) DEFAULT '' COMMENT '资源标题',
  `resource_source` varchar(100) DEFAULT '' COMMENT '资源来源',
  `img_list` varchar(2000) DEFAULT '' COMMENT '图片列表',
  `collect_type` varchar(30) NOT NULL DEFAULT '' COMMENT '收藏类型',
  `collect_datetime` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '收藏时间',
  `status` varchar(30) NOT NULL DEFAULT '' COMMENT '状态',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `idx_userName` (`tenant_id`,`user_name`,`resource_id`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收藏表';

-- ----------------------------
-- Records of general_collection
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_conference_info
-- ----------------------------
DROP TABLE IF EXISTS `general_conference_info`;
CREATE TABLE `general_conference_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `conference_type` varchar(30) NOT NULL DEFAULT '' COMMENT '会议类型',
  `conference_room_id` varchar(20) NOT NULL DEFAULT '' COMMENT '会议室ID',
  `conference_room` varchar(100) NOT NULL DEFAULT '' COMMENT '会议室',
  `conference_subject` varchar(1000) NOT NULL DEFAULT '' COMMENT '会议主题',
  `convener_user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '主持人',
  `convener_real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '主持人名',
  `project_no` varchar(30) DEFAULT '' COMMENT '项目编号',
  `project_name` varchar(50) DEFAULT '' COMMENT '项目名称',
  `participants_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '参与人列表',
  `conference_begin_datetime` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '会议开始时间',
  `conference_end_datetime` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '会议结束时间',
  `conference_issue` text COMMENT '会议议题',
  `conference_no` varchar(30) DEFAULT '' COMMENT '会议号',
  `conference_url` varchar(100) DEFAULT '' COMMENT '会议链接',
  `conference_att_list` varchar(2000) DEFAULT '' COMMENT '会议附件列表',
  `color` varchar(20) DEFAULT '' COMMENT '颜色',
  `all_day_show_flag` char(1) DEFAULT '' COMMENT '全天显示标志',
  `generate_frequency` varchar(10) DEFAULT '' COMMENT '生成频率',
  `generate_month` varchar(10) DEFAULT '' COMMENT '生成月',
  `generate_day` varchar(10) DEFAULT '' COMMENT '生成日',
  `generate_date` varchar(20) DEFAULT '' COMMENT '生成日期',
  `generate_datetime` varchar(100) DEFAULT '' COMMENT '生成时间',
  `valid_date` varchar(20) DEFAULT '' COMMENT '有效日期',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `roomid` (`tenant_id`,`conference_type`,`conference_room_id`,`conference_begin_datetime`,`conference_end_datetime`),
  KEY `index2` (`tenant_id`,`conference_begin_datetime`,`conference_end_datetime`,`conference_room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_conference_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_conference_room
-- ----------------------------
DROP TABLE IF EXISTS `general_conference_room`;
CREATE TABLE `general_conference_room` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `office_area_name` varchar(50) NOT NULL DEFAULT '' COMMENT '办公区名称',
  `floor_no` varchar(30) NOT NULL DEFAULT '' COMMENT '楼层号',
  `conference_room_name` varchar(50) NOT NULL DEFAULT '' COMMENT '会议室名称',
  `conference_room_capacity` int(10) NOT NULL DEFAULT '0' COMMENT '会议室容量',
  `device_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '设备列表',
  `conference_room_manage_user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '会议室管理员',
  `conference_room_manage_real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '会议室管理员姓名',
  `status` varchar(30) NOT NULL DEFAULT '正常' COMMENT '状态',
  `remark` varchar(5000) DEFAULT '' COMMENT '备注',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_conference_room
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_file_catalog
-- ----------------------------
DROP TABLE IF EXISTS `general_file_catalog`;
CREATE TABLE `general_file_catalog` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `catalog_name` varchar(50) NOT NULL DEFAULT '' COMMENT '目录名称',
  `parent_catalog_id` varchar(20) NOT NULL DEFAULT '' COMMENT '上级目录ID',
  `parent_catalog_name` varchar(50) NOT NULL DEFAULT '' COMMENT '上级目录名称',
  `owner_id` varchar(20) DEFAULT '' COMMENT '拥有人ID',
  `order_seq` int(10) DEFAULT NULL COMMENT '排序序号',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_file_catalog
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_file_history
-- ----------------------------
DROP TABLE IF EXISTS `general_file_history`;
CREATE TABLE `general_file_history` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `file_id` varchar(20) NOT NULL DEFAULT '' COMMENT '文档ID',
  `file_name` varchar(200) NOT NULL DEFAULT '' COMMENT '文档名称',
  `file_path` varchar(500) NOT NULL DEFAULT '' COMMENT '文档路径',
  `file_version_no` varchar(30) NOT NULL DEFAULT '' COMMENT '文档版本号',
  `file_sign` varchar(128) DEFAULT '' COMMENT '文档签名',
  `file_size` varchar(20) NOT NULL DEFAULT '0' COMMENT '文档大小',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `fileId` (`tenant_id`,`file_id`),
  KEY `version` (`tenant_id`,`file_id`,`file_version_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_file_history
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_file_info
-- ----------------------------
DROP TABLE IF EXISTS `general_file_info`;
CREATE TABLE `general_file_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `catalog_id` varchar(20) NOT NULL DEFAULT '' COMMENT '目录ID',
  `file_name` varchar(200) NOT NULL DEFAULT '' COMMENT '文档名称',
  `file_type` varchar(30) NOT NULL DEFAULT '' COMMENT '文档类型',
  `file_size` varchar(20) NOT NULL DEFAULT '' COMMENT '文档大小',
  `file_path` varchar(500) NOT NULL DEFAULT '' COMMENT '文档路径',
  `file_sign` varchar(128) DEFAULT '' COMMENT '文档签名',
  `last_version_no` varchar(30) DEFAULT '' COMMENT '最后版本号',
  `valid_date` varchar(20) DEFAULT '' COMMENT '有效日期',
  `share_times` int(10) DEFAULT NULL COMMENT '分享次数',
  `share_flag` char(1) NOT NULL DEFAULT '0' COMMENT '分享标志',
  `share_valid_date` varchar(20) DEFAULT '' COMMENT '分享有效期',
  `owner_id` varchar(20) DEFAULT '' COMMENT '拥有人ID',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `tenantIdANDownerId` (`tenant_id`,`owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_file_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_file_template
-- ----------------------------
DROP TABLE IF EXISTS `general_file_template`;
CREATE TABLE `general_file_template` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `type_id` varchar(20) NOT NULL DEFAULT '' COMMENT '类型ID',
  `type_name` varchar(50) DEFAULT '' COMMENT '类型名称',
  `template_name` varchar(50) NOT NULL DEFAULT '' COMMENT '模板名称',
  `img_url` varchar(100) NOT NULL DEFAULT '' COMMENT '图片链接',
  `file_id` varchar(20) NOT NULL DEFAULT '' COMMENT '文档ID',
  `view_flag` char(1) NOT NULL DEFAULT '' COMMENT '查看标志',
  `order_seq` int(10) NOT NULL COMMENT '排序序号',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_file_template
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_flow_info
-- ----------------------------
DROP TABLE IF EXISTS `general_flow_info`;
CREATE TABLE `general_flow_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `flow_no` varchar(30) NOT NULL DEFAULT '' COMMENT '流程编号',
  `biz_type` varchar(30) NOT NULL DEFAULT '' COMMENT '业务类型',
  `flow_name` varchar(50) NOT NULL DEFAULT '' COMMENT '流程名称',
  `flow_type` varchar(30) NOT NULL DEFAULT '' COMMENT '流程类型',
  `form_type` varchar(30) NOT NULL DEFAULT '' COMMENT '表单类型',
  `form_id` varchar(20) DEFAULT '' COMMENT '表单ID',
  `node_status_no` varchar(30) NOT NULL DEFAULT '' COMMENT '节点状态编号',
  `node_stage_status_no` varchar(30) NOT NULL DEFAULT '' COMMENT '节点阶段状态编号',
  `role_category` varchar(30) NOT NULL DEFAULT '' COMMENT '角色类别',
  `role_type` varchar(30) NOT NULL DEFAULT '' COMMENT '角色类型',
  `approve_tree_no` varchar(20) NOT NULL DEFAULT '' COMMENT '审批树名称',
  `table_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '数据表列表',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tenant_id` (`tenant_id`,`flow_no`) USING BTREE,
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_flow_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_flow_node_config
-- ----------------------------
DROP TABLE IF EXISTS `general_flow_node_config`;
CREATE TABLE `general_flow_node_config` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `flow_id` varchar(20) NOT NULL DEFAULT '' COMMENT '流程ID',
  `order_seq` int(10) NOT NULL COMMENT '排序序号',
  `node_no` varchar(30) NOT NULL DEFAULT '' COMMENT '节点编号',
  `node_name` varchar(50) NOT NULL DEFAULT '' COMMENT '节点名称',
  `easy_mode_flag` char(1) NOT NULL DEFAULT '' COMMENT '简单模式标志',
  `is_sub_flow_flag` char(1) NOT NULL DEFAULT '' COMMENT '是否子流程标志',
  `sub_flow_id` varchar(20) DEFAULT '' COMMENT '子流程ID',
  `goto_type` varchar(30) NOT NULL DEFAULT '' COMMENT '跳转类型',
  `goto_condition` varchar(500) DEFAULT '' COMMENT '跳转条件',
  `pass_rate` varchar(5) DEFAULT '' COMMENT '通过比例',
  `control_way` varchar(30) NOT NULL DEFAULT '' COMMENT '控制方式',
  `java_class_name` varchar(200) DEFAULT '' COMMENT 'JAVA类名',
  `is_transfer_flag` char(1) DEFAULT '' COMMENT '是否转派标志',
  `is_delegate_flag` char(1) DEFAULT '' COMMENT '是否委托标志',
  `exec_status_list` varchar(2000) DEFAULT '' COMMENT '执行状态列表',
  `notice_process_class_name` varchar(200) DEFAULT '' COMMENT '通知处理类名',
  `notice_way` varchar(30) DEFAULT '' COMMENT '通知方式',
  `is_overtime_notice_flag` char(1) NOT NULL DEFAULT '' COMMENT '是否超时通知标志',
  `opt_user_list` varchar(2000) DEFAULT '' COMMENT '经办人列表',
  `cc_user_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '抄送用户列表',
  `cc_role_name` varchar(50) NOT NULL DEFAULT '' COMMENT '抄送角色名称',
  `is_rollback_flag` char(1) DEFAULT '' COMMENT '是否回退标志',
  `form_id` varchar(20) DEFAULT '' COMMENT '表单ID',
  `hide_form_group_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '隐藏表单组列表',
  `form_privilege_list` varchar(2000) DEFAULT '' COMMENT '表单权限列表',
  `node_wait_duration` varchar(30) DEFAULT '' COMMENT '节点等待时长',
  `time_unit` varchar(30) NOT NULL DEFAULT '' COMMENT '时间单位',
  `overtime_notice_way` varchar(30) DEFAULT '' COMMENT '超时通知方式',
  `overtime_notice_list` varchar(2000) DEFAULT '' COMMENT '超时通知列表',
  `overtime_notice_role_name` varchar(50) NOT NULL DEFAULT '' COMMENT '超时通知角色名称',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`,`tenant_id`),
  KEY `tenantId` (`tenant_id`),
  KEY `tenant_id` (`tenant_id`,`flow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_flow_node_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_flow_process_cc_record
-- ----------------------------
DROP TABLE IF EXISTS `general_flow_process_cc_record`;
CREATE TABLE `general_flow_process_cc_record` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `flow_id` varchar(20) NOT NULL DEFAULT '' COMMENT '流程ID',
  `process_id` varchar(20) NOT NULL DEFAULT '' COMMENT '处理ID',
  `biz_code` varchar(30) NOT NULL DEFAULT '' COMMENT '业务编码',
  `biz_type` varchar(10) NOT NULL DEFAULT '' COMMENT '业务类型',
  `biz_id` varchar(20) NOT NULL DEFAULT '' COMMENT '业务ID',
  `biz_name` varchar(50) NOT NULL DEFAULT '' COMMENT '业务名称',
  `node_name` varchar(50) DEFAULT '' COMMENT '节点名称',
  `process_real_name` varchar(50) DEFAULT '' COMMENT '处理真实姓名',
  `process_user_name` varchar(50) DEFAULT '' COMMENT '处理用户名',
  `process_stage_status` varchar(30) NOT NULL DEFAULT '' COMMENT '处理阶段状态',
  `process_progress_status` varchar(30) DEFAULT '' COMMENT '处理进度状态',
  `process_begin_datetime` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '处理开始时间',
  `process_finish_datetime` datetime DEFAULT '1990-01-01 00:00:00' COMMENT '处理完成时间',
  `flow_end_flag` char(1) NOT NULL DEFAULT '' COMMENT '流程结束标志',
  `node_wait_duration` varchar(30) DEFAULT '' COMMENT '节点等待时长',
  `form_area` text COMMENT '表单区',
  `data_area` text COMMENT '数据区',
  `biz_data` text COMMENT '业务数据',
  `workbench_data` text COMMENT '工作台数据',
  `launch_user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '发起用户名',
  `launch_real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '发起姓名',
  `launch_datetime` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '发起时间',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_flow_process_cc_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_flow_process_record
-- ----------------------------
DROP TABLE IF EXISTS `general_flow_process_record`;
CREATE TABLE `general_flow_process_record` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `flow_id` varchar(20) NOT NULL DEFAULT '' COMMENT '流程ID',
  `flow_name` varchar(50) NOT NULL DEFAULT '' COMMENT '流程名称',
  `node_type` varchar(30) NOT NULL DEFAULT '' COMMENT '节点类型',
  `node_id` varchar(20) NOT NULL DEFAULT '' COMMENT '节点ID',
  `node_name` varchar(50) NOT NULL DEFAULT '' COMMENT '节点名称',
  `node_order_seq` int(10) NOT NULL COMMENT '节点排序序号',
  `biz_id` varchar(20) NOT NULL DEFAULT '' COMMENT '业务ID',
  `biz_type` varchar(30) NOT NULL DEFAULT '' COMMENT '业务类型',
  `biz_name` varchar(50) NOT NULL DEFAULT '' COMMENT '业务名称',
  `biz_code` varchar(30) NOT NULL DEFAULT '' COMMENT '业务编码',
  `form_type` varchar(30) NOT NULL DEFAULT '' COMMENT '表单类型',
  `form_id` varchar(20) NOT NULL DEFAULT '' COMMENT '表单ID',
  `node_status` varchar(30) DEFAULT '' COMMENT '节点状态',
  `transfer_delegate_prev_id` varchar(20) DEFAULT '' COMMENT '转派委托前ID',
  `node_stage_status` varchar(30) DEFAULT '' COMMENT '节点阶段状态',
  `is_transfer_flag` char(1) DEFAULT '' COMMENT '是否转派标志',
  `transfer_user_name` varchar(50) DEFAULT '' COMMENT '转派用户名',
  `is_delegate_flag` char(1) DEFAULT '' COMMENT '是否委托标志',
  `delegate_user_name` varchar(50) DEFAULT '' COMMENT '委托用户名',
  `cc_flag` char(1) DEFAULT '' COMMENT '抄送标志',
  `flow_end_flag` char(1) DEFAULT '' COMMENT '流程结束标志 0-未结束 1-结束',
  `process_status` varchar(30) DEFAULT '' COMMENT '处理状态',
  `process_result` varchar(50) DEFAULT '' COMMENT '处理结果',
  `process_dept_no` varchar(30) DEFAULT '' COMMENT '处理部门编号',
  `process_dept_name` varchar(50) DEFAULT '' COMMENT '处理部门名称',
  `process_real_name` varchar(50) DEFAULT '' COMMENT '处理人真实姓名',
  `process_user_name` varchar(50) DEFAULT '' COMMENT '处理人用户名',
  `process_opinion` varchar(2000) DEFAULT '' COMMENT '处理意见',
  `att_name` varchar(50) DEFAULT '' COMMENT '附件名称',
  `att_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '附件列表',
  `prev_node_process_id` varchar(20) DEFAULT '' COMMENT '前节点处理ID',
  `prev_node_id_list` varchar(2000) DEFAULT '' COMMENT '前节点ID列表',
  `prev_node_name_list` varchar(2000) DEFAULT '' COMMENT '前节点名称列表',
  `prev_node_user_name` varchar(50) DEFAULT '' COMMENT '前节点用户名',
  `prev_node_opinion` varchar(2000) DEFAULT '' COMMENT '前节点意见',
  `process_begin_datetime` varchar(30) DEFAULT '1990-01-01 00:00:00' COMMENT '处理开始时间',
  `process_finish_datetime` varchar(30) DEFAULT '1990-01-01 00:00:00' COMMENT '处理完成时间',
  `process_stage_status` varchar(30) DEFAULT '' COMMENT '处理阶段状态',
  `process_progress_status` varchar(30) DEFAULT '' COMMENT '处理进度状态',
  `main_flow_id` varchar(20) DEFAULT '' COMMENT '主流程ID',
  `main_node_id` varchar(20) DEFAULT '' COMMENT '主节点ID',
  `rollback_flag` char(1) DEFAULT '' COMMENT '回退标志',
  `overtime_flag` char(1) DEFAULT '' COMMENT '超时标志',
  `is_overtime_notice_flag` char(1) DEFAULT '' COMMENT '是否超时通知标志',
  `node_wait_duration` varchar(30) DEFAULT '' COMMENT '节点等待时长',
  `time_unit` varchar(30) DEFAULT '' COMMENT '时间单位',
  `overtime_notice_way` varchar(30) DEFAULT '' COMMENT '超时通知方式',
  `overtime_notice_list` varchar(2000) DEFAULT '' COMMENT '超时通知列表',
  `form_area` text COMMENT '表单区',
  `data_area` text COMMENT '数据区',
  `biz_data` text COMMENT '业务数据',
  `workbench_data` text COMMENT '工作台数据',
  `launch_user_name` varchar(50) DEFAULT '' COMMENT '发起用户名',
  `launch_real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '发起姓名',
  `launch_datetime` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '发起时间',
  `cc_user_list` varchar(2000) DEFAULT '' COMMENT '抄送用户列表',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `index2` (`tenant_id`,`biz_id`,`node_id`,`process_user_name`) USING BTREE,
  KEY `index3` (`tenant_id`,`biz_id`,`prev_node_id_list`(200)) USING BTREE,
  KEY `index1` (`tenant_id`,`process_user_name`,`process_status`,`flow_name`,`launch_user_name`) USING BTREE,
  KEY `index4` (`tenant_id`,`biz_id`,`prev_node_process_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_flow_process_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_note_book
-- ----------------------------
DROP TABLE IF EXISTS `general_note_book`;
CREATE TABLE `general_note_book` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_id` varchar(20) NOT NULL DEFAULT '' COMMENT '用户ID',
  `category` varchar(30) NOT NULL DEFAULT '' COMMENT '分类',
  `title` varchar(200) DEFAULT '' COMMENT '标题',
  `title_color` varchar(20) NOT NULL DEFAULT '' COMMENT '标题颜色',
  `content` text COMMENT '内容',
  `status` varchar(30) DEFAULT '' COMMENT '状态',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_note_book
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_org_info
-- ----------------------------
DROP TABLE IF EXISTS `general_org_info`;
CREATE TABLE `general_org_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `belong_project_no` varchar(30) NOT NULL DEFAULT '' COMMENT '所属项目编号',
  `org_no` varchar(30) NOT NULL DEFAULT '' COMMENT '机构号',
  `org_type` varchar(30) NOT NULL DEFAULT '' COMMENT '机构类型',
  `org_name` varchar(50) NOT NULL DEFAULT '' COMMENT '机构名称',
  `org_full_name` varchar(50) NOT NULL DEFAULT '' COMMENT '机构全名',
  `parent_org_no` varchar(30) NOT NULL DEFAULT '' COMMENT '上级机构号',
  `belong_org_group` varchar(2000) NOT NULL DEFAULT '' COMMENT '所属机构组',
  `order_seq` int(10) NOT NULL COMMENT '排序序号',
  `has_children` varchar(10) NOT NULL DEFAULT '' COMMENT '是否有子节点',
  `respsb_user_id` varchar(20) DEFAULT '' COMMENT '负责人ID',
  `respsb_user_name` varchar(50) DEFAULT '' COMMENT '负责人名',
  `org_address` varchar(100) DEFAULT '' COMMENT '机构地址',
  `org_post_no` varchar(10) DEFAULT '' COMMENT '机构邮编号',
  `contact_phone_no` varchar(30) DEFAULT '' COMMENT '联系电话号码',
  `office_room_no` varchar(30) DEFAULT '' COMMENT '办公室编号',
  `remark` varchar(5000) DEFAULT '' COMMENT '备注',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tenantIdOrgNO` (`tenant_id`,`org_no`) USING BTREE,
  KEY `tenantId` (`tenant_id`),
  KEY `belong_project_no` (`belong_project_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_org_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_org_staff_relation
-- ----------------------------
DROP TABLE IF EXISTS `general_org_staff_relation`;
CREATE TABLE `general_org_staff_relation` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `belong_org_no` varchar(30) NOT NULL DEFAULT '' COMMENT '所属机构号',
  `belong_org_name` varchar(50) DEFAULT '' COMMENT '所属机构名称',
  `staff_id` varchar(20) NOT NULL DEFAULT '' COMMENT '用户ID',
  `staff_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `real_name` varchar(50) DEFAULT '' COMMENT '真实姓名',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ogrNoStaffId` (`tenant_id`,`belong_org_no`,`staff_id`) USING BTREE,
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_org_staff_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_product_service
-- ----------------------------
DROP TABLE IF EXISTS `general_product_service`;
CREATE TABLE `general_product_service` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `category` varchar(30) NOT NULL DEFAULT '' COMMENT '类别',
  `dcode` varchar(30) NOT NULL DEFAULT '' COMMENT '代码',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `biz_plate` varchar(30) NOT NULL DEFAULT '' COMMENT '业务板块',
  `biz_lines` varchar(30) NOT NULL DEFAULT '' COMMENT '业务条线',
  `biz_category` varchar(30) NOT NULL DEFAULT '' COMMENT '业务种类',
  `cust_type` varchar(30) NOT NULL DEFAULT '' COMMENT '客户类型',
  `belong_dept_name` varchar(30) DEFAULT '' COMMENT '所属部门',
  `respsb_user_name` varchar(50) DEFAULT '' COMMENT '负责人',
  `respsb_real_name` varchar(50) DEFAULT '' COMMENT '负责人名',
  `effect_date` varchar(20) DEFAULT '' COMMENT '生效日期',
  `invalid_date` varchar(20) DEFAULT '' COMMENT '失效日期',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_product_service
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_project_info
-- ----------------------------
DROP TABLE IF EXISTS `general_project_info`;
CREATE TABLE `general_project_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `project_no` varchar(30) NOT NULL DEFAULT '' COMMENT '项目编号',
  `project_name` varchar(50) NOT NULL DEFAULT '' COMMENT '项目名称',
  `project_category` varchar(30) NOT NULL DEFAULT '' COMMENT '项目分类',
  `main_sub_project_flag` varchar(30) DEFAULT '' COMMENT '主子项目标志',
  `main_project_no` varchar(30) DEFAULT '' COMMENT '主项目编号',
  `main_project_name` varchar(50) DEFAULT '' COMMENT '主项目名称',
  `project_scale` varchar(30) DEFAULT '' COMMENT '项目规模',
  `involve_important_system_flag` varchar(1) DEFAULT '' COMMENT '涉及重要系统标志',
  `project_short_name` varchar(30) DEFAULT '' COMMENT '项目简称',
  `demand_no` varchar(30) NOT NULL DEFAULT '' COMMENT '需求编号',
  `demand_name` varchar(50) DEFAULT '' COMMENT '需求名称',
  `make_main_dept_name` varchar(30) DEFAULT '' COMMENT '实施主办部门',
  `make_assist_dept_name` varchar(30) DEFAULT '' COMMENT '实施协办部门',
  `biz_main_dept_name` varchar(30) DEFAULT '' COMMENT '业务主办部门',
  `biz_assist_dept_name` varchar(30) DEFAULT '' COMMENT '业务协办部门',
  `main_dept_name` varchar(30) NOT NULL DEFAULT '' COMMENT '主办部门',
  `main_office_name` varchar(30) DEFAULT '' COMMENT '主办科室',
  `assist_dept_name` varchar(30) DEFAULT '' COMMENT '协办部门',
  `build_way` varchar(30) DEFAULT '' COMMENT '建设方式',
  `make_way` varchar(30) DEFAULT '' COMMENT '实施方式',
  `project_manager_name` varchar(30) DEFAULT '' COMMENT '项目经理',
  `project_manager_real_name` varchar(30) NOT NULL DEFAULT '' COMMENT '项目经理姓名',
  `project_grade` varchar(30) DEFAULT '' COMMENT '项目级别',
  `prior_level` varchar(10) DEFAULT NULL COMMENT '优先等级',
  `expect_workload` varchar(20) DEFAULT '' COMMENT '预计工作量',
  `budget_class` varchar(30) DEFAULT '' COMMENT '预算类目',
  `budget_amt` decimal(15,2) DEFAULT NULL COMMENT '预算金额',
  `project_status` varchar(30) DEFAULT '' COMMENT '项目状态',
  `plan_begin_date` varchar(20) DEFAULT '' COMMENT '计划开始日期',
  `plan_finish_date` varchar(20) DEFAULT '' COMMENT '计划完成日期',
  `actual_begin_date` varchar(20) DEFAULT '' COMMENT '实际开始日期',
  `actual_finish_date` varchar(20) DEFAULT '' COMMENT '实际完成日期',
  `appr_project_file_address` varchar(1000) DEFAULT '' COMMENT '立项文档地址',
  `project_desc` varchar(2000) DEFAULT '' COMMENT '项目描述',
  `remark` varchar(5000) DEFAULT '' COMMENT '备注',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_project_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_project_privilege_relation
-- ----------------------------
DROP TABLE IF EXISTS `general_project_privilege_relation`;
CREATE TABLE `general_project_privilege_relation` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `project_id` varchar(20) NOT NULL DEFAULT '' COMMENT '项目ID',
  `resource_type` varchar(10) NOT NULL DEFAULT '' COMMENT '资源类型',
  `resource_id` varchar(20) NOT NULL DEFAULT '' COMMENT '资源ID',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_project_privilege_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_report_catalog
-- ----------------------------
DROP TABLE IF EXISTS `general_report_catalog`;
CREATE TABLE `general_report_catalog` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `catalog_name` varchar(50) DEFAULT '' COMMENT '目录名称',
  `parent_catalog_id` varchar(20) DEFAULT '' COMMENT '上级目录ID',
  `parent_catalog_name` varchar(50) DEFAULT '' COMMENT '上级目录名称',
  `owner_id` varchar(20) DEFAULT '' COMMENT '拥有人ID',
  `order_seq` int(10) DEFAULT NULL COMMENT '排序序号',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_report_catalog
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_report_catlog
-- ----------------------------
DROP TABLE IF EXISTS `general_report_catlog`;
CREATE TABLE `general_report_catlog` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `catalog_name` varchar(50) DEFAULT '' COMMENT '目录名称',
  `parent_catalog_id` varchar(20) DEFAULT '' COMMENT '上级目录ID',
  `parent_catalog_name` varchar(50) DEFAULT '' COMMENT '上级目录名称',
  `owner_id` varchar(20) DEFAULT '' COMMENT '拥有人ID',
  `order_seq` int(10) DEFAULT NULL COMMENT '排序序号',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `tenantId` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_report_catlog
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_report_file_info
-- ----------------------------
DROP TABLE IF EXISTS `general_report_file_info`;
CREATE TABLE `general_report_file_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `title` varchar(200) NOT NULL DEFAULT '' COMMENT '标题',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `catalog_id` varchar(20) NOT NULL DEFAULT '' COMMENT '目录ID',
  `catalog_name` varchar(50) NOT NULL DEFAULT '' COMMENT '目录名称',
  `content` mediumblob COMMENT '内容',
  `share_flag` char(1) DEFAULT '0' COMMENT '分享标志',
  `share_role_group` varchar(2000) DEFAULT '' COMMENT '分享角色组',
  `share_user_group` varchar(2000) DEFAULT '' COMMENT '分享用户组',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_report_file_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_report_view
-- ----------------------------
DROP TABLE IF EXISTS `general_report_view`;
CREATE TABLE `general_report_view` (
  `id` char(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_report_view
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_schedule_info
-- ----------------------------
DROP TABLE IF EXISTS `general_schedule_info`;
CREATE TABLE `general_schedule_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `calendar_id` varchar(20) NOT NULL DEFAULT '' COMMENT '日历ID',
  `owner_user_name` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `conference_id` varchar(20) DEFAULT NULL COMMENT '会议ID',
  `schedule_subject` varchar(1000) NOT NULL DEFAULT '' COMMENT '日程主题',
  `schedule_begin_datetime` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '日程开始时间',
  `schedule_end_datetime` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '日程结束时间',
  `schedule_content` text NOT NULL COMMENT '日程内容',
  `color` varchar(20) DEFAULT '' COMMENT '颜色',
  `all_day_show_flag` char(1) DEFAULT '0' COMMENT '全天显示标志',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_schedule_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_staff_info
-- ----------------------------
DROP TABLE IF EXISTS `general_staff_info`;
CREATE TABLE `general_staff_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `staff_id` varchar(20) NOT NULL DEFAULT '' COMMENT '员工ID',
  `staff_type` varchar(30) NOT NULL DEFAULT '' COMMENT '员工类型',
  `staff_name` varchar(50) NOT NULL DEFAULT '' COMMENT '员工姓名',
  `head_img_url` varchar(100) DEFAULT '' COMMENT '头像链接',
  `sex` varchar(2) NOT NULL DEFAULT '' COMMENT '性别',
  `birth_date` varchar(20) NOT NULL DEFAULT '' COMMENT '出生日期',
  `id_card_no` varchar(30) NOT NULL DEFAULT '' COMMENT '身份证号码',
  `nation` varchar(30) NOT NULL DEFAULT '' COMMENT '民族',
  `native_place` varchar(30) NOT NULL DEFAULT '' COMMENT '籍贯',
  `birth_address` varchar(100) NOT NULL DEFAULT '' COMMENT '出生地址',
  `political_political_status` varchar(20) NOT NULL DEFAULT '' COMMENT '政治面貌',
  `marriage_cond` varchar(10) NOT NULL DEFAULT '' COMMENT '婚姻状况',
  `healthy_cond` varchar(10) DEFAULT '' COMMENT '健康状况',
  `graduation_college_name` varchar(50) DEFAULT '' COMMENT '毕业院校名称',
  `edu_degree_name` varchar(50) NOT NULL DEFAULT '' COMMENT '学历名称',
  `mobile_no` varchar(30) NOT NULL DEFAULT '' COMMENT '手机号码',
  `email_address` varchar(100) NOT NULL DEFAULT '' COMMENT '邮箱地址',
  `tel_no` varchar(30) DEFAULT '' COMMENT '座机号码',
  `live_address` varchar(100) NOT NULL DEFAULT '' COMMENT '现居地址',
  `lan_info` varchar(500) DEFAULT '' COMMENT '语言信息',
  `edu_info_list` varchar(1000) DEFAULT NULL COMMENT '教育信息列表',
  `major_skill_list` varchar(1000) DEFAULT '' COMMENT '专业技能列表',
  `qual_info_list` varchar(1000) DEFAULT '' COMMENT '资质信息列表',
  `award_info_list` varchar(1000) DEFAULT '' COMMENT '获奖信息列表',
  `society_relation_list` varchar(500) DEFAULT '' COMMENT '社会关系列表',
  `duty_grade` varchar(30) DEFAULT '' COMMENT '职务级别',
  `duty_name` varchar(50) DEFAULT '' COMMENT '职务名称',
  `station_name` varchar(50) DEFAULT '' COMMENT '岗位名称',
  `tech_title_name` varchar(50) DEFAULT '' COMMENT '职称名称',
  `belong_org_no` varchar(30) DEFAULT '' COMMENT '所属机构号',
  `belong_org_name` varchar(50) DEFAULT '' COMMENT '所属机构名称',
  `parent_org_no` varchar(30) DEFAULT '' COMMENT '上级机构号',
  `parent_org_name` varchar(50) DEFAULT '' COMMENT '上级机构名称',
  `staff_status` varchar(30) DEFAULT '' COMMENT '员工状态',
  `entry_date` varchar(20) DEFAULT '' COMMENT '入职日期',
  `entry_opt_name` varchar(30) DEFAULT '' COMMENT '入职经办人',
  `leave_date` varchar(20) DEFAULT '' COMMENT '离职日期',
  `leave_opt_name` varchar(30) DEFAULT '' COMMENT '离职经办人',
  `leave_reason` varchar(100) DEFAULT '' COMMENT '离职原因',
  `agreement_expire_date` varchar(20) DEFAULT '' COMMENT '合同到期日期',
  `agreement_url` varchar(100) DEFAULT '' COMMENT '合同链接',
  `acct_list` varchar(2000) DEFAULT '' COMMENT '账号列表',
  `office_address` varchar(100) DEFAULT '' COMMENT '办公地址',
  `general_label` varchar(100) DEFAULT '' COMMENT '通用标签',
  `company_name` varchar(50) NOT NULL DEFAULT '' COMMENT '公司名称',
  `join_job_date` varchar(20) DEFAULT '' COMMENT '参加工作日期',
  `finance_job_years` varchar(10) DEFAULT '' COMMENT '金融工作年限',
  `core_project_count` int(3) DEFAULT NULL COMMENT '核心项目个数',
  `job_expe_list` varchar(2000) DEFAULT '' COMMENT '工作经历列表',
  `project_expe_list` varchar(1000) DEFAULT '' COMMENT '项目经历列表',
  `own_bank_service_flag` char(1) DEFAULT '' COMMENT '行内服务标志',
  `own_bank_project_list` varchar(500) DEFAULT '' COMMENT '行内项目列表',
  `service_type` varchar(30) NOT NULL DEFAULT '' COMMENT '服务类型',
  `settle_type` varchar(30) NOT NULL DEFAULT '' COMMENT '驻场类型',
  `belong_project_name` varchar(50) DEFAULT '' COMMENT '所属项目名称',
  `level_name` varchar(50) DEFAULT '' COMMENT '级别名称',
  `grade_name` varchar(50) DEFAULT '' COMMENT '档次名称',
  `entrance_date` varchar(20) DEFAULT '' COMMENT '入场日期',
  `entrance_opt_name` varchar(30) DEFAULT '' COMMENT '入场经办人',
  `departure_date` varchar(20) DEFAULT '' COMMENT '离场日期',
  `departure_opt_name` varchar(30) DEFAULT '' COMMENT '离场经办人',
  `departure_reason` varchar(100) DEFAULT '' COMMENT '离场原因',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`,`company_name`) USING BTREE,
  UNIQUE KEY `tenantIdStaff` (`tenant_id`,`staff_id`) USING BTREE,
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_staff_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_staff_station
-- ----------------------------
DROP TABLE IF EXISTS `general_staff_station`;
CREATE TABLE `general_staff_station` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `office_area_no` varchar(30) DEFAULT '' COMMENT '办公区编号',
  `office_area_name` varchar(50) NOT NULL COMMENT '办公区名称',
  `floor_no` varchar(30) NOT NULL DEFAULT '' COMMENT '楼层编号',
  `staff_station_no` varchar(30) NOT NULL DEFAULT '' COMMENT '工位编号',
  `staff_station_properties` varchar(30) NOT NULL DEFAULT '' COMMENT '工位性质',
  `staff_station_type` varchar(30) NOT NULL DEFAULT '' COMMENT '工位类型',
  `belong_project_no` varchar(30) DEFAULT '' COMMENT '所属项目编号',
  `belong_project_name` varchar(50) DEFAULT '' COMMENT '所属项目名称',
  `belong_dept_no` varchar(30) DEFAULT '' COMMENT '所属部门编号',
  `belong_dept_name` varchar(50) DEFAULT '' COMMENT '所属部门名称',
  `use_user_name` varchar(50) DEFAULT '' COMMENT '使用用户名',
  `use_real_name` varchar(50) DEFAULT '' COMMENT '使用人名',
  `use_status_list` varchar(2000) DEFAULT '' COMMENT '使用状态列表',
  `manager_user_name` varchar(50) DEFAULT '' COMMENT '管理员用户名',
  `manager_real_name` varchar(50) DEFAULT '' COMMENT '管理员人名',
  `contact_phone_no` varchar(30) DEFAULT '' COMMENT '联系电话',
  `status` varchar(30) DEFAULT '' COMMENT '状态',
  `staff_type` varchar(10) DEFAULT '' COMMENT '员工类型',
  `attendance_status` varchar(30) DEFAULT '' COMMENT '出勤状态',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `tenantId` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_staff_station
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for general_staff_station_byday
-- ----------------------------
DROP TABLE IF EXISTS `general_staff_station_byday`;
CREATE TABLE `general_staff_station_byday` (
  `id` varchar(20) NOT NULL COMMENT 'id',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `post_date` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `office_area_no` varchar(30) NOT NULL DEFAULT '' COMMENT '办公区编号',
  `floor_no` varchar(30) NOT NULL DEFAULT '' COMMENT '楼层编号',
  `staff_station_no` varchar(30) NOT NULL DEFAULT '' COMMENT '工位编号',
  `staff_station_properties` varchar(30) NOT NULL DEFAULT '' COMMENT '工位性质',
  `staff_station_type` varchar(30) NOT NULL DEFAULT '' COMMENT '工位类型',
  `belong_project_no` varchar(30) DEFAULT '' COMMENT '所属项目编号',
  `belong_project_name` varchar(50) DEFAULT '' COMMENT '所属项目名称',
  `belong_dept_no` varchar(30) DEFAULT '' COMMENT '所属部门编号',
  `belong_dept_name` varchar(50) DEFAULT '' COMMENT '所属部门名称',
  `use_user_name` varchar(50) DEFAULT '' COMMENT '使用用户名',
  `use_real_name` varchar(50) DEFAULT '' COMMENT '使用人名',
  `use_status_list` varchar(2000) DEFAULT '' COMMENT '使用状态列表',
  `manager_user_name` varchar(50) DEFAULT '' COMMENT '管理员用户名',
  `manager_real_name` varchar(50) DEFAULT '' COMMENT '管理员人名',
  `contact_phone_no` varchar(30) DEFAULT '' COMMENT '联系电话',
  `status` varchar(30) DEFAULT '' COMMENT '状态',
  `staff_type` varchar(10) DEFAULT '' COMMENT '员工类型',
  `attendance_status` varchar(30) DEFAULT '' COMMENT '出勤状态',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `belongProjectName` (`tenant_id`,`floor_no`,`post_date`,`belong_project_name`) USING BTREE,
  KEY `departmentName` (`tenant_id`,`floor_no`,`post_date`,`belong_dept_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of general_staff_station_byday
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for seata_account
-- ----------------------------
DROP TABLE IF EXISTS `seata_account`;
CREATE TABLE `seata_account` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户id',
  `total` decimal(10,0) DEFAULT NULL COMMENT '总额度',
  `used` decimal(10,0) DEFAULT NULL COMMENT '已用余额',
  `residue` decimal(10,0) DEFAULT '0' COMMENT '剩余可用额度',
  `frozen` decimal(10,0) DEFAULT '0' COMMENT 'TCC事务锁定的金额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seata_account
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for seata_order
-- ----------------------------
DROP TABLE IF EXISTS `seata_order`;
CREATE TABLE `seata_order` (
  `id` varchar(20) NOT NULL,
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户id',
  `product_id` bigint(11) DEFAULT NULL COMMENT '产品id',
  `count` int(11) DEFAULT NULL COMMENT '数量',
  `money` decimal(11,0) DEFAULT NULL COMMENT '金额',
  `status` int(1) DEFAULT NULL COMMENT '订单状态：0：创建中；1：已完结',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seata_order
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for seata_storage
-- ----------------------------
DROP TABLE IF EXISTS `seata_storage`;
CREATE TABLE `seata_storage` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(11) DEFAULT NULL COMMENT '产品id',
  `total` int(11) DEFAULT NULL COMMENT '总库存',
  `used` int(11) DEFAULT NULL COMMENT '已用库存',
  `residue` int(11) DEFAULT NULL COMMENT '剩余库存',
  `frozen` int(11) DEFAULT '0' COMMENT 'TCC事务锁定的库存',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seata_storage
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sixteen_EightSF
-- ----------------------------
DROP TABLE IF EXISTS `sixteen_EightSF`;
CREATE TABLE `sixteen_EightSF` (
  `id` varchar(20) DEFAULT NULL,
  `tenant_id` varchar(10) DEFAULT NULL,
  `FactorID` char(10) NOT NULL,
  `Factor` char(50) NOT NULL,
  `LowScoreMin` int(11) DEFAULT NULL,
  `LowScoreMax` int(11) DEFAULT NULL,
  `LowFacContent` longtext NOT NULL,
  `HighScoreMin` int(11) DEFAULT NULL,
  `HighScoreMax` int(11) DEFAULT NULL,
  `HighFacContent` longtext,
  `AddID` char(20) NOT NULL,
  `AddTime` date DEFAULT NULL,
  PRIMARY KEY (`FactorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sixteen_EightSF
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sixteen_SixteenPF
-- ----------------------------
DROP TABLE IF EXISTS `sixteen_SixteenPF`;
CREATE TABLE `sixteen_SixteenPF` (
  `id` varchar(20) DEFAULT NULL,
  `tenant_id` varchar(10) DEFAULT NULL,
  `FactorID` char(10) NOT NULL,
  `Factor` char(50) NOT NULL,
  `LowScoreMin` int(11) DEFAULT NULL,
  `LowScoreMax` int(11) NOT NULL,
  `LowScoFeatures` longtext NOT NULL,
  `HighScoreMin` int(11) DEFAULT NULL,
  `HighScoreMax` int(11) NOT NULL,
  `HighScoFeatures` longtext NOT NULL,
  `AddID` char(20) NOT NULL,
  `AddTime` date DEFAULT NULL,
  PRIMARY KEY (`FactorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sixteen_SixteenPF
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sixteen_TopicDB
-- ----------------------------
DROP TABLE IF EXISTS `sixteen_TopicDB`;
CREATE TABLE `sixteen_TopicDB` (
  `id` varchar(20) DEFAULT NULL,
  `tenant_id` varchar(10) DEFAULT NULL,
  `TopicNum` int(11) NOT NULL,
  `FactorID` varchar(10) NOT NULL,
  `TopicCont` longtext NOT NULL,
  `Options` longtext NOT NULL,
  `RightAnswer` char(1) NOT NULL,
  `RightAnsScore` int(11) NOT NULL,
  `PreAnswer` char(1) DEFAULT NULL,
  `PreAnsScore` int(11) DEFAULT NULL,
  `AddID` char(20) NOT NULL,
  `AddTime` datetime DEFAULT NULL,
  PRIMARY KEY (`TopicNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sixteen_TopicDB
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sixteen_User16PFExch
-- ----------------------------
DROP TABLE IF EXISTS `sixteen_User16PFExch`;
CREATE TABLE `sixteen_User16PFExch` (
  `id` varchar(20) DEFAULT NULL,
  `tenant_id` varchar(10) DEFAULT NULL,
  `UserID` char(20) NOT NULL,
  `FactorID` char(10) NOT NULL,
  `UserName` char(30) NOT NULL,
  `Sex` char(2) NOT NULL,
  `Age` int(11) NOT NULL,
  `OriginScore` int(11) NOT NULL,
  `CompaScore` char(50) NOT NULL,
  `StaScore` int(11) NOT NULL,
  `Time` date DEFAULT NULL,
  PRIMARY KEY (`UserID`,`FactorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sixteen_User16PFExch
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sixteen_UserAnalysis
-- ----------------------------
DROP TABLE IF EXISTS `sixteen_UserAnalysis`;
CREATE TABLE `sixteen_UserAnalysis` (
  `id` varchar(20) DEFAULT NULL,
  `tenant_id` varchar(10) DEFAULT NULL,
  `UserID` char(20) NOT NULL,
  `FactorID` char(10) NOT NULL,
  `FacType` char(10) NOT NULL,
  `Factor` char(100) DEFAULT NULL,
  `Score` double NOT NULL,
  `StaScore` double DEFAULT NULL,
  `Status` char(50) DEFAULT NULL,
  `LowScoFeatures` longtext,
  `HighScoFeatures` longtext,
  `Time` date DEFAULT NULL,
  `AnsDuration` char(50) NOT NULL,
  PRIMARY KEY (`UserID`,`FactorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sixteen_UserAnalysis
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sixteen_UserAnswers
-- ----------------------------
DROP TABLE IF EXISTS `sixteen_UserAnswers`;
CREATE TABLE `sixteen_UserAnswers` (
  `id` varchar(20) DEFAULT NULL,
  `tenant_id` varchar(10) DEFAULT NULL,
  `UserID` char(20) NOT NULL,
  `TopicNum` int(11) NOT NULL,
  `UserName` char(30) NOT NULL,
  `Sex` char(2) NOT NULL,
  `Age` int(11) NOT NULL,
  `FactorID` char(10) NOT NULL,
  `TopicCont` longtext NOT NULL,
  `Options` longtext NOT NULL,
  `RightAnswer` char(1) NOT NULL,
  `RightAnsScore` int(11) NOT NULL,
  `PreAnswer` char(1) NOT NULL,
  `PreAnsScore` int(11) NOT NULL,
  `UserAnswer` char(1) NOT NULL,
  `UserAnsScore` int(11) NOT NULL,
  `DateTime` date DEFAULT NULL,
  PRIMARY KEY (`UserID`,`TopicNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sixteen_UserAnswers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sixteen_UserAuth
-- ----------------------------
DROP TABLE IF EXISTS `sixteen_UserAuth`;
CREATE TABLE `sixteen_UserAuth` (
  `id` varchar(20) DEFAULT NULL,
  `tenant_id` varchar(10) DEFAULT NULL,
  `UserID` char(20) NOT NULL,
  `Password` char(50) NOT NULL,
  `BelongLevel` int(11) NOT NULL,
  `UserLevel` int(11) NOT NULL,
  `UserName` char(50) DEFAULT NULL,
  `Phone` char(15) NOT NULL,
  `Email` char(30) NOT NULL,
  `OrgID` char(20) NOT NULL,
  `OrgName` char(100) NOT NULL,
  `Flag` char(5) NOT NULL,
  `LoginTime` datetime DEFAULT NULL,
  `LoginIP` char(50) NOT NULL,
  `LoginCount` int(11) NOT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sixteen_UserAuth
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sixteen_UserInformation
-- ----------------------------
DROP TABLE IF EXISTS `sixteen_UserInformation`;
CREATE TABLE `sixteen_UserInformation` (
  `id` varchar(20) DEFAULT NULL,
  `tenant_id` varchar(10) DEFAULT NULL,
  `UserID` char(20) NOT NULL,
  `OrgName` char(30) DEFAULT NULL,
  `UserName` char(10) NOT NULL,
  `Email` char(50) DEFAULT NULL,
  `Phone` char(15) DEFAULT NULL,
  `Password` char(30) NOT NULL,
  `Sex` char(2) NOT NULL,
  `Age` int(11) NOT NULL,
  `AnsBeginTime` char(20) DEFAULT NULL,
  `AnsEndTime` char(20) DEFAULT NULL,
  `EndFlag` char(1) DEFAULT NULL,
  `RegTime` date DEFAULT NULL,
  `AnalyStatus` char(5) NOT NULL,
  `AnalyDesc` char(200) NOT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sixteen_UserInformation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_api_scope
-- ----------------------------
DROP TABLE IF EXISTS `sys_api_scope`;
CREATE TABLE `sys_api_scope` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `menu_id` varchar(20) NOT NULL DEFAULT '' COMMENT '菜单ID',
  `menu_name` varchar(100) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `interface_category` varchar(30) NOT NULL DEFAULT '' COMMENT '接口分类',
  `interface_name` varchar(50) NOT NULL DEFAULT '' COMMENT '接口名称',
  `interface_address` varchar(100) NOT NULL DEFAULT '' COMMENT '接口地址',
  `interface_code` varchar(30) NOT NULL DEFAULT '' COMMENT '接口编码',
  `remark` varchar(5000) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT NULL COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `interfaceAddress` (`interface_address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_api_scope
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_biz_msg_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_biz_msg_config`;
CREATE TABLE `sys_biz_msg_config` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `biz_name` varchar(50) NOT NULL DEFAULT '' COMMENT '业务名称',
  `msg_type_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '消息类型列表',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_biz_msg_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_burying_point
-- ----------------------------
DROP TABLE IF EXISTS `sys_burying_point`;
CREATE TABLE `sys_burying_point` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `request_channel` varchar(50) NOT NULL DEFAULT '' COMMENT '请求渠道',
  `statistics_type` varchar(30) NOT NULL DEFAULT '' COMMENT '统计类型',
  `menu_id` varchar(20) DEFAULT '' COMMENT '菜单ID',
  `menu_name` varchar(50) DEFAULT '' COMMENT '菜单名称',
  `menu_code` varchar(30) DEFAULT '' COMMENT '菜单编码',
  `request_address` varchar(100) DEFAULT '' COMMENT '请求地址',
  `parent_menu_id` varchar(20) DEFAULT '' COMMENT '父级菜单ID',
  `request_user_name` varchar(50) DEFAULT '' COMMENT '请求用户名',
  `request_real_name` varchar(50) DEFAULT '' COMMENT '请求姓名',
  `request_dept_name` varchar(30) DEFAULT '' COMMENT '请求部门',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_burying_point
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_calender
-- ----------------------------
DROP TABLE IF EXISTS `sys_calender`;
CREATE TABLE `sys_calender` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `year` varchar(10) NOT NULL DEFAULT '' COMMENT '年份',
  `holiday_date` varchar(20) NOT NULL DEFAULT '' COMMENT '日期',
  `holiday_name` varchar(50) NOT NULL DEFAULT '' COMMENT '节假日名称',
  `holiday_remark` varchar(5000) NOT NULL DEFAULT '' COMMENT '节假日备注',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `holiday_date` (`tenant_id`,`year`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_calender
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_clean_record
-- ----------------------------
DROP TABLE IF EXISTS `sys_clean_record`;
CREATE TABLE `sys_clean_record` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `title` varchar(200) NOT NULL DEFAULT '' COMMENT '标题',
  `event_content` text NOT NULL COMMENT '事件内容',
  `peer_user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '对方用户名',
  `address` varchar(100) DEFAULT '' COMMENT '地址',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`,`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='廉政记录表';

-- ----------------------------
-- Records of sys_clean_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_clean_talk
-- ----------------------------
DROP TABLE IF EXISTS `sys_clean_talk`;
CREATE TABLE `sys_clean_talk` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '姓名',
  `title` varchar(200) NOT NULL DEFAULT '' COMMENT '标题',
  `content` text COMMENT '内容',
  `talk_user_name` varchar(50) DEFAULT '' COMMENT '谈话人',
  `record_user_name` varchar(50) DEFAULT '' COMMENT '记录人',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`,`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='谈心谈话记录表';

-- ----------------------------
-- Records of sys_clean_talk
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_custom_org_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_custom_org_info`;
CREATE TABLE `sys_custom_org_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `unified_credit_dcode` varchar(30) NOT NULL DEFAULT '' COMMENT '统一信用代码',
  `org_name` varchar(50) NOT NULL DEFAULT '' COMMENT '机构名称',
  `business_license_url` varchar(1000) NOT NULL DEFAULT '' COMMENT '营业执照链接',
  `legal_person_name` varchar(50) NOT NULL DEFAULT '' COMMENT '法人姓名',
  `legal_person_id_card_no` varchar(30) NOT NULL DEFAULT '' COMMENT '法人身份证号',
  `legal_person_id_card_url` varchar(1000) NOT NULL DEFAULT '' COMMENT '法人身份证链接',
  `regist_address` varchar(100) NOT NULL DEFAULT '' COMMENT '注册地址',
  `contact_person_name` varchar(50) NOT NULL DEFAULT '' COMMENT '联系人姓名',
  `contact_person_id_card_no` varchar(30) NOT NULL DEFAULT '' COMMENT '联系人身份证号',
  `contact_person_id_card_url` varchar(1000) NOT NULL DEFAULT '' COMMENT '联系人身份证链接',
  `belong_area_dcode` varchar(30) NOT NULL DEFAULT '' COMMENT '所属区域代码',
  `belong_area_name` varchar(50) NOT NULL DEFAULT '' COMMENT '所属区域名称',
  `contact_phone_no` varchar(30) DEFAULT '' COMMENT '联系电话',
  `mobile_no` varchar(30) DEFAULT '' COMMENT '手机号码',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `fax_no` varchar(30) DEFAULT '' COMMENT '传真号码',
  `active_mobile_no` varchar(30) DEFAULT '' COMMENT '激活手机号',
  `company_valid_date` varchar(20) DEFAULT '' COMMENT '企业有效期',
  `status` varchar(30) DEFAULT '' COMMENT '状态',
  `check_user_name` varchar(50) DEFAULT '' COMMENT '复核人',
  `reject_reason` varchar(100) DEFAULT '' COMMENT '驳回原因',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_custom_org_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_custom_user_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_custom_user_info`;
CREATE TABLE `sys_custom_user_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `wx_open_id` varchar(50) DEFAULT '' COMMENT '微信openID',
  `app_wx_open_id` varchar(50) DEFAULT '' COMMENT 'APP微信openID',
  `alipay_open_id` varchar(50) DEFAULT '' COMMENT '支付宝openID',
  `app_alipay_open_id` varchar(50) DEFAULT '' COMMENT 'APP支付宝openID',
  `other_open_id` varchar(50) DEFAULT '' COMMENT '其它openID',
  `app_other_open_id` varchar(50) DEFAULT '' COMMENT 'APP其它openID',
  `nick_name` varchar(50) DEFAULT '' COMMENT '昵称',
  `password` varchar(50) DEFAULT '' COMMENT '密码',
  `unified_credit_dcode` varchar(30) NOT NULL DEFAULT '' COMMENT '统一信用代码',
  `org_name` varchar(50) NOT NULL DEFAULT '' COMMENT '机构名称',
  `business_license_url` varchar(1000) DEFAULT '' COMMENT '营业执照链接',
  `legal_person_name` varchar(50) NOT NULL DEFAULT '' COMMENT '法人姓名',
  `legal_person_id_card_no` varchar(30) NOT NULL DEFAULT '' COMMENT '法人身份证号',
  `legal_person_id_card_url` varchar(1000) DEFAULT '' COMMENT '法人身份证链接',
  `regist_address` varchar(100) NOT NULL DEFAULT '' COMMENT '注册地址',
  `contact_person_name` varchar(50) DEFAULT '' COMMENT '联系人姓名',
  `contact_person_id_card_no` varchar(30) DEFAULT '' COMMENT '联系人身份证号',
  `contact_person_id_card_url` varchar(1000) DEFAULT '' COMMENT '联系人身份证链接',
  `belong_area_dcode` varchar(30) DEFAULT '' COMMENT '所属区域代码',
  `belong_area_name` varchar(50) DEFAULT '' COMMENT '所属区域名称',
  `fixed_phone_no` varchar(30) DEFAULT '' COMMENT '固定电话号码',
  `mobile_no` varchar(30) DEFAULT '' COMMENT '手机号码',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `fax_no` varchar(30) DEFAULT '' COMMENT '传真号码',
  `active_mobile_no` varchar(30) DEFAULT '' COMMENT '激活手机号',
  `company_valid_date` varchar(20) DEFAULT '' COMMENT '企业有效期',
  `password_error_times` int(10) DEFAULT NULL COMMENT '密码错误次数',
  `regist_channel` varchar(50) DEFAULT '' COMMENT '注册渠道',
  `head_img_url` varchar(100) DEFAULT '' COMMENT '头像链接',
  `status` varchar(30) DEFAULT '' COMMENT '状态',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_custom_user_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `dict_category` varchar(30) NOT NULL DEFAULT '' COMMENT '字典分类',
  `dict_no` varchar(200) NOT NULL DEFAULT '' COMMENT '字典编号',
  `dict_name` varchar(50) NOT NULL DEFAULT '' COMMENT '字典名称',
  `dict_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '字典列表',
  `dict_status` varchar(30) DEFAULT '' COMMENT '字典状态',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dynamic_form
-- ----------------------------
DROP TABLE IF EXISTS `sys_dynamic_form`;
CREATE TABLE `sys_dynamic_form` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `biz_type` varchar(10) NOT NULL DEFAULT '' COMMENT '业务类型',
  `biz_name` varchar(50) NOT NULL DEFAULT '' COMMENT '业务名称',
  `biz_code` varchar(30) NOT NULL DEFAULT '' COMMENT '业务编码',
  `form_code` varchar(30) DEFAULT '' COMMENT '表单编码',
  `form_name` varchar(50) DEFAULT '' COMMENT '表单名称',
  `parent_id` varchar(20) DEFAULT '' COMMENT '上级ID',
  `form_json` varchar(5000) DEFAULT '' COMMENT '表单JSON',
  `form_json_v1` varchar(5000) DEFAULT '' COMMENT '表单JSONV1',
  `form_json_v2` varchar(5000) DEFAULT '' COMMENT '表单JSONV2',
  `is_flow_flag` char(1) DEFAULT '' COMMENT '是否流程标志',
  `flow_id` varchar(20) DEFAULT '' COMMENT '流程ID',
  `flow_begin_way` varchar(30) DEFAULT '' COMMENT '流程开始方式',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`,`biz_type`) USING BTREE,
  KEY `index1` (`tenant_id`,`form_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dynamic_form
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_error_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_error_code`;
CREATE TABLE `sys_error_code` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `error_grade` varchar(30) NOT NULL DEFAULT '' COMMENT '错误级别',
  `error_dcode` varchar(100) NOT NULL DEFAULT '' COMMENT '错误代码',
  `error_code` varchar(30) NOT NULL DEFAULT '' COMMENT '错误编码',
  `error_info` varchar(2000) NOT NULL DEFAULT '' COMMENT '错误信息',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='错误码表';

-- ----------------------------
-- Records of sys_error_code
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_error_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_error_log`;
CREATE TABLE `sys_error_log` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `error_type` varchar(30) NOT NULL DEFAULT '' COMMENT '错误类型',
  `error_address` varchar(100) NOT NULL DEFAULT '' COMMENT '错误地址',
  `error_summary` varchar(500) DEFAULT '' COMMENT '错误摘要',
  `error_content` text COMMENT '错误内容',
  `error_datetime` datetime DEFAULT '1990-01-01 00:00:00' COMMENT '错误时间',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_error_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_group_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_info`;
CREATE TABLE `sys_group_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `project_id` varchar(20) NOT NULL DEFAULT '' COMMENT '项目ID',
  `user_group_category` varchar(30) NOT NULL DEFAULT '' COMMENT '用户组分类',
  `user_group_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户组名称',
  `parent_user_group_id` varchar(20) NOT NULL DEFAULT '' COMMENT '上级用户组ID',
  `parent_user_group_name` varchar(50) NOT NULL DEFAULT '' COMMENT '上级用户组名称',
  `order_seq` int(10) NOT NULL COMMENT '排序序号',
  `remark` varchar(5000) NOT NULL DEFAULT '' COMMENT '备注',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_group_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_group_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_role_relation`;
CREATE TABLE `sys_group_role_relation` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_group_id` varchar(20) NOT NULL DEFAULT '' COMMENT '用户组ID',
  `role_id` varchar(20) NOT NULL DEFAULT '' COMMENT '角色ID',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_group_role_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_his_report
-- ----------------------------
DROP TABLE IF EXISTS `sys_his_report`;
CREATE TABLE `sys_his_report` (
  `id` varchar(50) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '姓名',
  `sex` varchar(2) NOT NULL DEFAULT '' COMMENT '性别',
  `age` int(4) NOT NULL COMMENT '年龄',
  `lin_chuang_no` varchar(50) NOT NULL DEFAULT '' COMMENT '病床号',
  `bing_li_no` varchar(50) NOT NULL DEFAULT '' COMMENT '病理号',
  `zhu_yuan_no` varchar(50) NOT NULL DEFAULT '' COMMENT '住院号',
  `yi_yuan` varchar(50) NOT NULL DEFAULT '' COMMENT '医院',
  `dong_mai_bian_xing` varchar(50) NOT NULL DEFAULT '' COMMENT '动脉管壁透明变性',
  `dong_mai_zeng_hou` varchar(50) NOT NULL DEFAULT '' COMMENT '动脉内膜增厚',
  `jian_zhi_qian_hua` varchar(50) NOT NULL DEFAULT '' COMMENT '间质纤维化',
  `xi_bao_qing_run` varchar(50) NOT NULL DEFAULT '' COMMENT '间质炎细胞浸润',
  `jie_duan_ying_hua` varchar(50) NOT NULL DEFAULT '' COMMENT '节段硬化',
  `jie_guo_miao_shu` varchar(500) NOT NULL DEFAULT '' COMMENT '结果描述',
  `lin_chuang_zheng_duan` varchar(500) NOT NULL DEFAULT '' COMMENT '临床诊断',
  `mao_xi_gai_bian` varchar(50) NOT NULL DEFAULT '' COMMENT '毛细血管壁改变',
  `mao_xi_zeng_sheng` varchar(50) NOT NULL DEFAULT '' COMMENT '毛细血管内增生',
  `qiu_xing_ying_hua` varchar(50) NOT NULL DEFAULT '' COMMENT '球性硬化',
  `sheng_xiao_qiu_cnt` varchar(50) NOT NULL DEFAULT '' COMMENT '肾小球数目',
  `sheng_xiao_qiu_zhi` varchar(50) NOT NULL DEFAULT '' COMMENT '肾小球直径',
  `song_jian_date` varchar(50) NOT NULL DEFAULT '' COMMENT '送检时间',
  `song_jian_doctor` varchar(50) NOT NULL DEFAULT '' COMMENT '送检医师',
  `xi_mo_zeng_sheng` varchar(50) NOT NULL DEFAULT '' COMMENT '系膜基质增生',
  `xi_mo_xi_bao_zeng_sheng` varchar(50) NOT NULL DEFAULT '' COMMENT '系膜细胞增生',
  `xiao_guan_wei_shuo` varchar(50) NOT NULL DEFAULT '' COMMENT '小管萎缩',
  `xiao_qiu_huai_si` varchar(50) NOT NULL DEFAULT '' COMMENT '小球纤维素样坏死',
  `xin_yue_ti_cnt` varchar(50) NOT NULL DEFAULT '' COMMENT '新月体数目',
  `zheng_duan_date` varchar(50) NOT NULL DEFAULT '' COMMENT '诊断日期',
  `zheng_duan_date2` varchar(50) NOT NULL DEFAULT '' COMMENT '诊断日期2',
  `zheng_duan_yi_sheng` varchar(50) NOT NULL DEFAULT '' COMMENT '诊断医生',
  `pic1` varchar(500) NOT NULL DEFAULT '' COMMENT '图像1',
  `pic2` varchar(500) NOT NULL DEFAULT '' COMMENT '图像2',
  `zoom1` varchar(50) NOT NULL DEFAULT '' COMMENT '放大倍数1',
  `zoom2` varchar(50) NOT NULL DEFAULT '' COMMENT '放大倍数2',
  `pic3` varchar(500) NOT NULL DEFAULT '' COMMENT '图像3',
  `pic4` varchar(500) NOT NULL DEFAULT '' COMMENT '图像4',
  `zoom3` varchar(50) NOT NULL DEFAULT '' COMMENT '放大倍数3',
  `zoom4` varchar(50) NOT NULL DEFAULT '' COMMENT '放大倍数4',
  `jie_guo_miao_shu2` varchar(500) NOT NULL DEFAULT '' COMMENT '结果描述2',
  `bing_li_miao_shu` varchar(500) NOT NULL DEFAULT '' COMMENT '病理诊断',
  `lin_chuang_zheng_duan2` varchar(500) NOT NULL DEFAULT '' COMMENT '临床诊断2',
  `jie_guo_miao_shu3` varchar(500) NOT NULL DEFAULT '' COMMENT '结果描述3',
  `jie_guo_miao_shu4` varchar(500) NOT NULL DEFAULT '' COMMENT '结果描述4',
  `zhen_duan` varchar(500) NOT NULL DEFAULT '' COMMENT '诊断',
  `report_status` char(1) DEFAULT '1' COMMENT '0-草稿，1-待审核，2-完成',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string1` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组件资源表';

-- ----------------------------
-- Records of sys_his_report
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_history_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_history_info`;
CREATE TABLE `sys_history_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `update_data_id` varchar(20) NOT NULL DEFAULT '' COMMENT '修改数据ID',
  `class_name` varchar(200) NOT NULL DEFAULT '' COMMENT '类名',
  `update_before_data_obj` text COMMENT '修改之前数据对象',
  `update_after_data_obj` text COMMENT '修改之后数据对象',
  `update_content` text COMMENT '修改内容',
  `update_before_source_data_obj` text COMMENT '修改之前来源数据对象',
  `update_after_source_data_obj` text COMMENT '修改之后来源数据对象',
  `source_data_sign` varchar(128) NOT NULL DEFAULT '' COMMENT '来源数据签名',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_history_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `log_type` varchar(30) DEFAULT '' COMMENT '日志类型',
  `log_title` varchar(200) DEFAULT '' COMMENT '日志标题',
  `service_name` varchar(50) DEFAULT '' COMMENT '服务名称',
  `handle_address` varchar(100) DEFAULT '' COMMENT '操作地址',
  `user_agent` varchar(1000) DEFAULT '' COMMENT '用户代理',
  `request_address` varchar(100) DEFAULT '' COMMENT '请求地址',
  `request_way` varchar(30) DEFAULT '' COMMENT '请求方式',
  `request_header` text COMMENT '请求头',
  `request_param` text COMMENT '请求参数',
  `request_body` text COMMENT '请求报文体',
  `response_body` text COMMENT '响应报文体',
  `exec_duration` varchar(30) DEFAULT '' COMMENT '执行时长',
  `response_code` varchar(10) DEFAULT NULL COMMENT '返回码',
  `exception_content` text COMMENT '异常内容',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `indx_request_address` (`tenant_id`,`request_address`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `parent_menu_id` varchar(20) NOT NULL DEFAULT '' COMMENT '上级菜单ID',
  `menu_level` int(10) NOT NULL DEFAULT '1' COMMENT '菜单等级',
  `menu_category` varchar(30) DEFAULT '' COMMENT '菜单分类',
  `menu_name` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `menu_nick_name` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单昵称',
  `menu_no` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单编号',
  `route_address` varchar(100) NOT NULL DEFAULT '' COMMENT '路由地址',
  `menu_icon` varchar(100) NOT NULL DEFAULT '' COMMENT '菜单图标',
  `app_icon` varchar(100) NOT NULL DEFAULT '' COMMENT 'APP图标',
  `app_route_address` varchar(100) NOT NULL DEFAULT '' COMMENT 'APP路由地址',
  `menu_seq` int(10) DEFAULT NULL COMMENT '菜单序号',
  `has_children` varchar(10) DEFAULT '' COMMENT '是否有子节点',
  `parent_menu_name` varchar(50) DEFAULT '' COMMENT '上级菜单名称',
  `keep_alive_flag` char(1) DEFAULT '' COMMENT '保持活动标志',
  `project_control_flag` char(1) NOT NULL DEFAULT '0' COMMENT '项目控制标志',
  `remark` varchar(5000) DEFAULT NULL COMMENT '备注',
  `valid_flag` char(1) NOT NULL DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT NULL COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `menuNo` (`menu_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu_top
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_top`;
CREATE TABLE `sys_menu_top` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `menu_name` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `menu_level` int(10) NOT NULL COMMENT '菜单等级',
  `route_address` varchar(100) NOT NULL DEFAULT '' COMMENT '路由地址',
  `menu_icon` varchar(100) NOT NULL DEFAULT '' COMMENT '菜单图标',
  `keep_alive_flag` char(1) NOT NULL DEFAULT '1' COMMENT '保持活动标志',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu_top
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_micro_component
-- ----------------------------
DROP TABLE IF EXISTS `sys_micro_component`;
CREATE TABLE `sys_micro_component` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `belong_app_id` varchar(20) NOT NULL DEFAULT '' COMMENT '所属应用ID',
  `micro_service_code` varchar(50) NOT NULL DEFAULT '' COMMENT '微服务编码',
  `component_code` varchar(50) NOT NULL DEFAULT '' COMMENT '组件编码',
  `micro_service_name` varchar(50) NOT NULL DEFAULT '' COMMENT '微服务名称',
  `component_name` varchar(50) NOT NULL DEFAULT '' COMMENT '组件名称',
  `component_desc` varchar(2000) DEFAULT '' COMMENT '组件描述',
  `component_type` varchar(30) NOT NULL DEFAULT '' COMMENT '组件类型',
  `tcc_flag` char(1) NOT NULL DEFAULT '' COMMENT 'TCC标志',
  `access_path` varchar(500) DEFAULT '' COMMENT '访问路径',
  `interface_package_name` varchar(200) NOT NULL DEFAULT '' COMMENT '接口包名',
  `access_method_name` varchar(50) NOT NULL DEFAULT '' COMMENT '访问方法名称',
  `access_way` varchar(30) DEFAULT '' COMMENT '访问方式',
  `input_msg_body` varchar(5000) DEFAULT '' COMMENT '输入报文体',
  `output_msg_body` varchar(5000) DEFAULT '' COMMENT '输出报文体',
  `show_seq` int(10) NOT NULL COMMENT '显示序号',
  `wipe_account_flag` char(1) NOT NULL DEFAULT '' COMMENT '抹账标志',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组件信息表';

-- ----------------------------
-- Records of sys_micro_component
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_micro_component_condition
-- ----------------------------
DROP TABLE IF EXISTS `sys_micro_component_condition`;
CREATE TABLE `sys_micro_component_condition` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `condition_no` varchar(30) NOT NULL DEFAULT '' COMMENT '条件编号',
  `condition_name` varchar(50) NOT NULL DEFAULT '' COMMENT '条件名称',
  `condition_package_name` varchar(50) NOT NULL DEFAULT '' COMMENT '条件包名',
  `condition_desc` varchar(2000) NOT NULL DEFAULT '' COMMENT '条件描述',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组件执行条件表';

-- ----------------------------
-- Records of sys_micro_component_condition
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_micro_component_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_micro_component_resource`;
CREATE TABLE `sys_micro_component_resource` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `app_id` varchar(20) NOT NULL DEFAULT '' COMMENT '应用ID',
  `resource_name` varchar(50) NOT NULL DEFAULT '' COMMENT '资源名称',
  `resource_type` varchar(10) NOT NULL DEFAULT '' COMMENT '资源类型',
  `resource_desc` varchar(2000) DEFAULT '' COMMENT '资源描述',
  `resource_dcode` varchar(30) DEFAULT '' COMMENT '资源代码',
  `resource_path` varchar(500) DEFAULT '' COMMENT '资源路径',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `sys_micro_component_resource_index0` (`tenant_id`,`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组件资源表';

-- ----------------------------
-- Records of sys_micro_component_resource
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_micro_component_step
-- ----------------------------
DROP TABLE IF EXISTS `sys_micro_component_step`;
CREATE TABLE `sys_micro_component_step` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `component_code` varchar(50) NOT NULL DEFAULT '' COMMENT '组件编码',
  `step_type` varchar(30) NOT NULL DEFAULT '' COMMENT '步骤类型',
  `show_seq` int(10) NOT NULL COMMENT '显示序号',
  `step_code` varchar(50) NOT NULL DEFAULT '' COMMENT '步骤编码',
  `step_name` varchar(50) NOT NULL DEFAULT '' COMMENT '步骤名称',
  `handle_type` varchar(30) NOT NULL DEFAULT '' COMMENT '操作类型',
  `relation_resource_id` varchar(20) DEFAULT '' COMMENT '关联资源ID',
  `relation_resource_name` text COMMENT '关联资源名称',
  `relation_demand_id` varchar(30) DEFAULT '' COMMENT '关联需求ID',
  `relation_demand_desc` varchar(2000) DEFAULT '' COMMENT '关联需求描述',
  `logic_desc` varchar(2000) DEFAULT '' COMMENT '逻辑描述',
  `rule_list` varchar(2000) DEFAULT '' COMMENT '规则列表',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `sys_micro_component_step_index0` (`tenant_id`,`component_code`,`step_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组件步骤配置表';

-- ----------------------------
-- Records of sys_micro_component_step
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_micro_service
-- ----------------------------
DROP TABLE IF EXISTS `sys_micro_service`;
CREATE TABLE `sys_micro_service` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `belong_app_id` varchar(20) NOT NULL DEFAULT '' COMMENT '所属应用ID',
  `micro_service_code` varchar(50) NOT NULL DEFAULT '' COMMENT '微服务编码',
  `micro_service_name` varchar(50) NOT NULL DEFAULT '' COMMENT '微服务名称',
  `micro_service_desc` varchar(2000) NOT NULL DEFAULT '' COMMENT '微服务描述',
  `project_package_name` varchar(50) NOT NULL DEFAULT '' COMMENT '工程包名',
  `project_path` varchar(500) NOT NULL DEFAULT '' COMMENT '工程路径',
  `project_version_no` varchar(30) NOT NULL DEFAULT '' COMMENT '工程版本号',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微服务工程表';

-- ----------------------------
-- Records of sys_micro_service
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_micro_service_component
-- ----------------------------
DROP TABLE IF EXISTS `sys_micro_service_component`;
CREATE TABLE `sys_micro_service_component` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `belong_app_id` varchar(20) NOT NULL DEFAULT '' COMMENT '所属应用ID',
  `service_code` varchar(50) NOT NULL DEFAULT '' COMMENT '服务编码',
  `type` varchar(30) NOT NULL DEFAULT '' COMMENT '类型',
  `component_code` varchar(50) NOT NULL DEFAULT '' COMMENT '组件编码',
  `component_name` varchar(50) NOT NULL DEFAULT '' COMMENT '组件名称',
  `access_path` varchar(500) NOT NULL DEFAULT '' COMMENT '访问路径',
  `interface_package_name` varchar(200) NOT NULL DEFAULT '' COMMENT '接口包名',
  `access_method_name` varchar(50) NOT NULL DEFAULT '' COMMENT '访问方法名称',
  `exec_condition` varchar(500) DEFAULT '' COMMENT '执行条件',
  `support_wipe_account_flag` char(1) NOT NULL DEFAULT '' COMMENT '支持抹账标志',
  `tcc_flag` char(1) NOT NULL DEFAULT '' COMMENT 'TCC标志',
  `concurrent_flag` char(1) NOT NULL DEFAULT '' COMMENT '并发标志',
  `input_mapping_relation` varchar(5000) DEFAULT '' COMMENT '输入映射关系',
  `output_mapping_relation` varchar(5000) DEFAULT '' COMMENT '输出映射关系',
  `show_seq` int(10) NOT NULL COMMENT '显示序号',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务组件配置表';

-- ----------------------------
-- Records of sys_micro_service_component
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_micro_service_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_micro_service_config`;
CREATE TABLE `sys_micro_service_config` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `belong_app_id` varchar(20) NOT NULL DEFAULT '' COMMENT '所属应用ID',
  `service_code` varchar(50) NOT NULL DEFAULT '' COMMENT '服务编码',
  `service_type` varchar(10) NOT NULL DEFAULT '' COMMENT '服务类型',
  `belong_app_name` varchar(50) NOT NULL DEFAULT '' COMMENT '所属应用名',
  `service_name` varchar(50) NOT NULL DEFAULT '' COMMENT '服务名称',
  `service_desc` varchar(2000) NOT NULL DEFAULT '' COMMENT '服务描述',
  `service_bus_data` text COMMENT '服务总线数据',
  `write_log_flag` char(1) NOT NULL DEFAULT '' COMMENT '写日志标识',
  `entry_split_way` varchar(30) DEFAULT '' COMMENT '分录拆分方式',
  `posting_way` varchar(30) DEFAULT '' COMMENT '入账方式',
  `to_mainframe_msg_flag` char(1) DEFAULT '' COMMENT '转主机报文标识',
  `trans_type` varchar(30) DEFAULT '' COMMENT '交易类型',
  `service_category` varchar(30) NOT NULL DEFAULT '' COMMENT '服务分类',
  `allow_wipe_account_flag` char(1) NOT NULL DEFAULT '' COMMENT '允许抹账标志',
  `tcc_flag` char(1) NOT NULL DEFAULT '' COMMENT 'TCC标识',
  `input_msg_body` varchar(5000) DEFAULT '' COMMENT '输入报文体',
  `output_msg_body` varchar(5000) DEFAULT '' COMMENT '输出报文体',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务配置表';

-- ----------------------------
-- Records of sys_micro_service_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_micro_trans_exec_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_micro_trans_exec_detail`;
CREATE TABLE `sys_micro_trans_exec_detail` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `cust_no` varchar(30) NOT NULL DEFAULT '' COMMENT '客户号',
  `biz_serial_no` varchar(30) NOT NULL DEFAULT '' COMMENT '业务流水号',
  `trans_serial_no` varchar(30) NOT NULL DEFAULT '' COMMENT '交易流水号',
  `service_code` varchar(30) NOT NULL DEFAULT '' COMMENT '服务编码',
  `component_code` varchar(30) NOT NULL DEFAULT '' COMMENT '组件编码',
  `service_name` varchar(50) NOT NULL DEFAULT '' COMMENT '服务名称',
  `component_name` varchar(50) NOT NULL DEFAULT '' COMMENT '组件名称',
  `belong_micro_service_code` varchar(50) NOT NULL DEFAULT '' COMMENT '所属微服务编码',
  `begin_datetime` datetime(3) DEFAULT NULL COMMENT '开始时间',
  `end_datetime` datetime(3) DEFAULT NULL COMMENT '结束时间',
  `trans_take_time` varchar(20) DEFAULT '' COMMENT '交易耗时',
  `wipe_account_begin_datetime` datetime(3) DEFAULT NULL COMMENT '抹账开始时间',
  `wipe_account_end_datetime` datetime(3) DEFAULT NULL COMMENT '抹账结束时间',
  `component_config_content` text COMMENT '组件配置内容',
  `input_msg_body` varchar(5000) DEFAULT '' COMMENT '输入报文体',
  `output_msg_body` varchar(5000) DEFAULT '' COMMENT '输出报文体',
  `exec_status` varchar(30) DEFAULT '' COMMENT '执行状态',
  `trans_type` varchar(30) NOT NULL DEFAULT '' COMMENT '交易类型',
  `component_type` varchar(30) DEFAULT '' COMMENT '组件类型',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `sys_micro_trans_exec_detail_index0` (`tenant_id`,`cust_no`,`biz_serial_no`),
  KEY `sys_micro_trans_exec_detail_index1` (`tenant_id`,`cust_no`,`trans_serial_no`),
  KEY `sys_micro_trans_exec_detail_index2` (`tenant_id`,`cust_no`,`service_code`),
  KEY `sys_micro_trans_exec_detail_index3` (`tenant_id`,`cust_no`,`biz_serial_no`,`service_code`,`component_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微服务交易执行明细表';

-- ----------------------------
-- Records of sys_micro_trans_exec_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_micro_trans_record
-- ----------------------------
DROP TABLE IF EXISTS `sys_micro_trans_record`;
CREATE TABLE `sys_micro_trans_record` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `cust_no` varchar(30) NOT NULL DEFAULT '' COMMENT '客户号',
  `biz_serial_no` varchar(30) NOT NULL DEFAULT '' COMMENT '业务流水号',
  `trans_serial_no` varchar(30) NOT NULL DEFAULT '' COMMENT '交易流水号',
  `service_code` varchar(30) NOT NULL DEFAULT '' COMMENT '服务编码',
  `service_name` varchar(50) NOT NULL DEFAULT '' COMMENT '服务名称',
  `begin_datetime` datetime(3) DEFAULT NULL COMMENT '开始时间',
  `end_datetime` datetime(3) DEFAULT NULL COMMENT '结束时间',
  `trans_take_time` varchar(20) DEFAULT '' COMMENT '交易耗时',
  `wipe_account_begin_datetime` datetime(3) DEFAULT NULL COMMENT '抹账开始时间',
  `wipe_account_end_datetime` datetime(3) DEFAULT NULL COMMENT '抹账结束时间',
  `service_config_content` text COMMENT '服务配置内容',
  `input_msg_body` varchar(5000) DEFAULT '' COMMENT '输入报文体',
  `output_msg_body` varchar(5000) DEFAULT '' COMMENT '输出报文体',
  `trans_type` varchar(30) DEFAULT '' COMMENT '交易类型',
  `trans_status` varchar(30) DEFAULT '' COMMENT '交易状态',
  `ip_address` varchar(100) DEFAULT '' COMMENT 'IP地址',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `sys_micro_trans_record_index0` (`tenant_id`,`cust_no`,`biz_serial_no`),
  KEY `sys_micro_trans_record_index1` (`tenant_id`,`cust_no`,`trans_serial_no`),
  KEY `sys_micro_trans_record_index2` (`tenant_id`,`cust_no`,`service_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微服务交易记录表';

-- ----------------------------
-- Records of sys_micro_trans_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_module_config_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_module_config_info`;
CREATE TABLE `sys_module_config_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `module_name` varchar(50) NOT NULL DEFAULT '' COMMENT '模块名称',
  `assoc_table_name_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '关联表名列表',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_module_config_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_msg_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_msg_config`;
CREATE TABLE `sys_msg_config` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `send_scene` varchar(50) NOT NULL DEFAULT '' COMMENT '发送场景',
  `msg_type` varchar(10) NOT NULL DEFAULT '' COMMENT '消息类型',
  `template_id` varchar(100) DEFAULT NULL COMMENT '模板ID',
  `goto_address` varchar(100) NOT NULL DEFAULT '' COMMENT '跳转地址',
  `mini_program_id` varchar(20) NOT NULL DEFAULT '' COMMENT '小程序ID',
  `mini_program_address` varchar(100) NOT NULL DEFAULT '' COMMENT '小程序地址',
  `msg_template_content` varchar(2000) DEFAULT NULL COMMENT '消息模板内容',
  `msg_template_eg_content` varchar(2000) DEFAULT NULL COMMENT '消息模板示例内容',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_msg_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_msg_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_msg_log`;
CREATE TABLE `sys_msg_log` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `msg_type` varchar(30) NOT NULL DEFAULT '' COMMENT '消息类型',
  `receiver_name_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '收件人姓名列表',
  `receiver_email_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '收件人邮箱列表',
  `ccp_name_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '抄送人姓名列表',
  `ccp_email_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '抄送人邮箱列表',
  `bccp_name_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '密送人姓名列表',
  `bccp_email_list` varchar(2000) NOT NULL DEFAULT '' COMMENT '密送人邮箱列表',
  `msg_subject` varchar(1000) NOT NULL DEFAULT '' COMMENT '消息主题',
  `msg_content` text NOT NULL COMMENT '消息内容',
  `att_list` varchar(2000) DEFAULT '' COMMENT '附件列表',
  `msg_send_datetime` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '消息发送时间',
  `send_status` varchar(30) NOT NULL DEFAULT '' COMMENT '发送状态',
  `send_status_desc` varchar(2000) NOT NULL DEFAULT '' COMMENT '发送状态描述',
  `send_success_times` int(10) NOT NULL DEFAULT '0' COMMENT '发送成功次数',
  `send_fail_times` int(10) NOT NULL DEFAULT '0' COMMENT '发送失败次数',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_msg_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_msg_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_msg_notice`;
CREATE TABLE `sys_msg_notice` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) DEFAULT '' COMMENT '租户ID',
  `msg_type` varchar(30) NOT NULL DEFAULT '' COMMENT '消息类型',
  `biz_scene_name` varchar(50) NOT NULL DEFAULT '' COMMENT '业务场景名称',
  `accept_msg_user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '接收消息人',
  `accept_real_name` varchar(50) DEFAULT '' COMMENT '接收人名',
  `send_msg_user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '发送消息人',
  `send_real_name` varchar(50) DEFAULT '' COMMENT '发送人名',
  `sender_head_img_url` varchar(100) DEFAULT '' COMMENT '发送人头像链接',
  `msg_title` varchar(5000) NOT NULL DEFAULT '' COMMENT '消息标题',
  `msg_content` text CHARACTER SET utf8mb4 COMMENT '消息内容',
  `msg_content_json` text CHARACTER SET utf8mb4 COMMENT '消息内容JSON',
  `msg_resource_id` varchar(20) DEFAULT '' COMMENT '消息资源ID',
  `session_flag` char(1) DEFAULT '' COMMENT '会话标识',
  `read_status` varchar(30) DEFAULT '' COMMENT '读取状态',
  `read_date` varchar(20) DEFAULT '' COMMENT '读取日期',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `accept_msg_user_name` (`tenant_id`,`accept_msg_user_name`) USING BTREE,
  KEY `send_msg_user_name` (`tenant_id`,`send_msg_user_name`) USING BTREE,
  KEY `biz_scene_name` (`tenant_id`,`biz_scene_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_msg_notice
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_org_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_org_info`;
CREATE TABLE `sys_org_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `org_no` varchar(30) NOT NULL DEFAULT '' COMMENT '机构号',
  `org_type` varchar(30) NOT NULL DEFAULT '' COMMENT '机构类型',
  `org_name` varchar(50) NOT NULL DEFAULT '' COMMENT '机构名称',
  `org_full_name` varchar(50) NOT NULL DEFAULT '' COMMENT '机构全名',
  `parent_org_no` varchar(20) NOT NULL DEFAULT '' COMMENT '上级机构号',
  `belong_org_group` varchar(2000) NOT NULL DEFAULT '' COMMENT '所属机构组',
  `order_seq` int(10) NOT NULL COMMENT '排序序号',
  `remark` varchar(5000) DEFAULT '' COMMENT '备注',
  `has_children` varchar(10) DEFAULT 'false' COMMENT '是否有子节点',
  `respsb_user_id` varchar(20) DEFAULT '' COMMENT '负责人ID',
  `respsb_user_name` varchar(50) DEFAULT '' COMMENT '负责人名',
  `valid_flag` char(1) DEFAULT '' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT NULL COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `orgNo` (`org_no`(20))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_org_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_org_user_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_org_user_relation`;
CREATE TABLE `sys_org_user_relation` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `belong_org_no` varchar(30) NOT NULL DEFAULT '' COMMENT '所属机构号',
  `belong_org_name` varchar(50) NOT NULL DEFAULT '' COMMENT '所属机构名称',
  `user_id` varchar(20) NOT NULL DEFAULT '' COMMENT '用户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `valid_flag` char(1) DEFAULT '' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `orgNo-userId` (`tenant_id`,`belong_org_no`,`user_id`) USING BTREE,
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_org_user_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `param_type` varchar(30) NOT NULL DEFAULT '' COMMENT '参数类型',
  `param_key` varchar(50) NOT NULL DEFAULT '' COMMENT '参数键',
  `param_value` varchar(2000) NOT NULL DEFAULT '' COMMENT '参数值',
  `param_value_one` varchar(2000) DEFAULT '' COMMENT '参数值1',
  `param_value_tow` varchar(2000) DEFAULT '' COMMENT '参数值2',
  `param_name` varchar(50) NOT NULL DEFAULT '' COMMENT '参数名',
  `remark` varchar(5000) DEFAULT '' COMMENT '备注',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`,`param_key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_param
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `project_id` varchar(20) NOT NULL DEFAULT '' COMMENT '项目ID',
  `role_name` varchar(50) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_nick_name` varchar(50) NOT NULL DEFAULT '' COMMENT '角色昵称',
  `role_group_name` varchar(30) NOT NULL DEFAULT '' COMMENT '角色分组名',
  `role_type` varchar(30) NOT NULL DEFAULT '' COMMENT '角色类型',
  `role_seq` int(10) NOT NULL COMMENT '角色序号',
  `status` varchar(30) DEFAULT '' COMMENT '状态',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT NULL COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `roleName` (`tenant_id`,`role_name`) USING HASH,
  KEY `tenant_id` (`tenant_id`,`role_name`),
  KEY `tenantAndProject` (`tenant_id`,`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role_privilege_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_privilege_relation`;
CREATE TABLE `sys_role_privilege_relation` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `resource_type` varchar(30) NOT NULL DEFAULT '' COMMENT '资源类型',
  `role_id` varchar(20) NOT NULL DEFAULT '' COMMENT '角色ID',
  `resource_id` varchar(20) NOT NULL DEFAULT '' COMMENT '资源ID',
  `read_flag` char(1) NOT NULL DEFAULT '1' COMMENT '读取标志 1-读，2-读写',
  `valid_flag` char(1) DEFAULT '' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT NULL COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `roleIdResourceId` (`tenant_id`,`resource_type`,`role_id`,`resource_id`) USING BTREE,
  KEY `tenantIdAndRoleId` (`tenant_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_privilege_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_send_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_send_log`;
CREATE TABLE `sys_send_log` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `sender_id` varchar(20) DEFAULT '' COMMENT '发送人ID',
  `sender_real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '发送人姓名',
  `biz_scene_name` varchar(50) NOT NULL DEFAULT '' COMMENT '业务场景名称',
  `msg_type` varchar(30) DEFAULT '' COMMENT '消息类型',
  `send_channel` varchar(50) DEFAULT '' COMMENT '发送渠道',
  `send_address` varchar(100) DEFAULT '' COMMENT '发送地址',
  `template_id` varchar(20) DEFAULT '' COMMENT '模板ID',
  `template_name` varchar(50) NOT NULL DEFAULT '' COMMENT '模板名称',
  `sms_param` varchar(2000) DEFAULT '' COMMENT '短信参数',
  `resource_id` varchar(20) DEFAULT '' COMMENT '资源ID',
  `verify_code` varchar(20) DEFAULT '' COMMENT '验证码',
  `sms_sign` varchar(128) DEFAULT '' COMMENT '短信签名',
  `msg_title` varchar(200) DEFAULT '' COMMENT '消息标题',
  `msg_content` text CHARACTER SET utf8mb4 COMMENT '消息内容',
  `msg_content_json` varchar(5000) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '消息内容JSON',
  `send_status` varchar(30) DEFAULT '' COMMENT '发送状态',
  `status_info` varchar(2000) NOT NULL DEFAULT '' COMMENT '状态信息',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_send_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant_info`;
CREATE TABLE `sys_tenant_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_tenant_id` varchar(20) DEFAULT NULL COMMENT '用户租户ID',
  `tenant_name` varchar(50) NOT NULL DEFAULT '' COMMENT '租户名称',
  `contact_name` varchar(50) NOT NULL DEFAULT '' COMMENT '联系人名',
  `contact_phone_no` varchar(30) NOT NULL DEFAULT '' COMMENT '联系电话号码',
  `contact_address` varchar(100) DEFAULT '' COMMENT '联系地址',
  `bind_domain_name` varchar(200) DEFAULT '' COMMENT '绑定域名',
  `client_id` varchar(20) DEFAULT '' COMMENT '客户端ID',
  `cust_secret_key` varchar(200) DEFAULT '' COMMENT '客户秘钥',
  `system_version_no` varchar(30) DEFAULT '' COMMENT '系统版本号',
  `menu_version_no` varchar(30) DEFAULT '' COMMENT '菜单版本号',
  `top_menu_version_no` varchar(30) DEFAULT '' COMMENT '顶部菜单版本号',
  `reset_no` varchar(30) DEFAULT '' COMMENT '重置编号',
  `msg_no` varchar(30) DEFAULT '' COMMENT '消息编号',
  `notice_msg` varchar(2000) DEFAULT '' COMMENT '通知消息',
  `default_system_color` varchar(20) DEFAULT 'blue' COMMENT '默认系统颜色',
  `bg_img_url` varchar(1000) DEFAULT NULL COMMENT '租户背景图片地址',
  `login_bg_url` varchar(1000) DEFAULT '' COMMENT '租户登录背景',
  `logo_url` varchar(1000) DEFAULT NULL COMMENT '租户LOGO',
  `big_logo_url` varchar(1000) DEFAULT NULL COMMENT '租户大LOGO',
  `default_password` varchar(50) DEFAULT '' COMMENT '默认密码',
  `buy_way` char(2) DEFAULT '' COMMENT '购买方式',
  `acct_bal` decimal(12,2) DEFAULT NULL COMMENT '账户余额',
  `effect_date` varchar(20) DEFAULT '' COMMENT '生效日期',
  `invalid_date` varchar(20) DEFAULT '' COMMENT '失效日期',
  `license_content` text COMMENT '许可证内容',
  `valid_flag` char(1) DEFAULT '' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT NULL COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tenantId` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_tenant_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_tenant_info_copy1
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant_info_copy1`;
CREATE TABLE `sys_tenant_info_copy1` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_tenant_id` varchar(20) DEFAULT NULL COMMENT '用户租户ID',
  `tenant_name` varchar(50) NOT NULL DEFAULT '' COMMENT '租户名称',
  `contact_name` varchar(50) NOT NULL DEFAULT '' COMMENT '联系人名',
  `contact_phone_no` varchar(30) NOT NULL DEFAULT '' COMMENT '联系电话号码',
  `contact_address` varchar(100) DEFAULT '' COMMENT '联系地址',
  `bind_domain_name` varchar(200) DEFAULT '' COMMENT '绑定域名',
  `client_id` varchar(20) DEFAULT '' COMMENT '客户端ID',
  `cust_secret_key` varchar(200) DEFAULT '' COMMENT '客户秘钥',
  `system_version_no` varchar(30) DEFAULT '' COMMENT '系统版本号',
  `menu_version_no` varchar(30) DEFAULT '' COMMENT '菜单版本号',
  `top_menu_version_no` varchar(30) DEFAULT '' COMMENT '顶部菜单版本号',
  `reset_no` varchar(30) DEFAULT '' COMMENT '重置编号',
  `msg_no` varchar(30) DEFAULT '' COMMENT '消息编号',
  `notice_msg` varchar(2000) DEFAULT '' COMMENT '通知消息',
  `default_system_color` varchar(20) DEFAULT 'blue' COMMENT '默认系统颜色',
  `bg_img_url` varchar(1000) DEFAULT NULL COMMENT '租户背景图片地址',
  `login_bg_url` varchar(1000) DEFAULT '' COMMENT '租户登录背景',
  `logo_url` varchar(1000) DEFAULT NULL COMMENT '租户LOGO',
  `default_password` varchar(50) DEFAULT '' COMMENT '默认密码',
  `buy_way` char(2) DEFAULT '' COMMENT '购买方式',
  `acct_bal` decimal(12,2) DEFAULT NULL COMMENT '账户余额',
  `effect_date` varchar(20) DEFAULT '' COMMENT '生效日期',
  `invalid_date` varchar(20) DEFAULT '' COMMENT '失效日期',
  `license_content` text COMMENT '许可证内容',
  `valid_flag` char(1) DEFAULT '' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT NULL COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tenantId` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_tenant_info_copy1
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_tenant_privilege_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant_privilege_relation`;
CREATE TABLE `sys_tenant_privilege_relation` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_tenant_id` varchar(20) NOT NULL DEFAULT '' COMMENT '用户租户ID',
  `resource_type` varchar(30) NOT NULL DEFAULT '' COMMENT '资源类型',
  `resource_id` varchar(20) NOT NULL DEFAULT '' COMMENT '资源ID',
  `valid_flag` char(1) DEFAULT '' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tenantIdResource` (`tenant_id`,`user_tenant_id`,`resource_type`,`resource_id`) USING BTREE,
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_tenant_privilege_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_third_party_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_third_party_info`;
CREATE TABLE `sys_third_party_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `type` varchar(30) NOT NULL DEFAULT '' COMMENT '类型',
  `third_party_config_content` text COMMENT '第三方配置内容',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`),
  KEY `type` (`tenant_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方配置表';

-- ----------------------------
-- Records of sys_third_party_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_tool_columns
-- ----------------------------
DROP TABLE IF EXISTS `sys_tool_columns`;
CREATE TABLE `sys_tool_columns` (
  `id` varchar(20) NOT NULL COMMENT 'id',
  `tenant_id` varchar(10) NOT NULL COMMENT '租户号',
  `table_schema` varchar(64) NOT NULL DEFAULT '' COMMENT '数据库名',
  `table_name` varchar(64) NOT NULL DEFAULT '' COMMENT '表名',
  `column_name` varchar(64) NOT NULL DEFAULT '' COMMENT '字段名',
  `ordinal_position` bigint(21) unsigned NOT NULL DEFAULT '0' COMMENT '顺序',
  `nullable` varchar(10) NOT NULL DEFAULT '' COMMENT '是否为空',
  `data_type` varchar(64) NOT NULL DEFAULT '' COMMENT '数据类型',
  `length` int(10) NOT NULL COMMENT '字段长度',
  `decimal_length` int(2) NOT NULL DEFAULT '0' COMMENT '小数长度',
  `column_key` varchar(100) NOT NULL DEFAULT '' COMMENT '是否key值',
  `column_comment` varchar(100) NOT NULL DEFAULT '' COMMENT '字段说明',
  `type` varchar(20) DEFAULT '' COMMENT '控件类型',
  `search` varchar(20) DEFAULT '' COMMENT '是否搜索',
  `search_multiple` varchar(20) DEFAULT '' COMMENT '搜索框多选',
  `search_label_width` int(5) DEFAULT '80' COMMENT '搜索文字宽度',
  `multiple` varchar(20) DEFAULT '' COMMENT '是否多选',
  `dic_data` varchar(500) DEFAULT '' COMMENT '选项值(JSON)',
  `width` varchar(20) DEFAULT '' COMMENT '列宽度',
  `span` varchar(20) DEFAULT '' COMMENT '控件宽度',
  `sortable` varchar(20) DEFAULT '' COMMENT '是否可排序',
  `required` varchar(20) DEFAULT '' COMMENT '是否必输',
  `message` varchar(20) DEFAULT '' COMMENT '必输提示语',
  `hide` varchar(20) DEFAULT '' COMMENT '是否隐藏',
  `value` varchar(20) DEFAULT '' COMMENT '默认值',
  `show_in_form` varchar(20) DEFAULT 'true' COMMENT '表单显示',
  `excel` varchar(20) DEFAULT NULL COMMENT '导出excel',
  `over_hidden` varchar(20) DEFAULT '' COMMENT '超出隐藏',
  `encrypt_flag` varchar(10) DEFAULT NULL COMMENT '加密标志',
  `data_sign` varchar(128) DEFAULT 'newdata' COMMENT '数据签名',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT NULL COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_tool_columns
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_tool_dict_attr
-- ----------------------------
DROP TABLE IF EXISTS `sys_tool_dict_attr`;
CREATE TABLE `sys_tool_dict_attr` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT '租户号',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户号',
  `attr_name` varchar(100) NOT NULL DEFAULT '' COMMENT '定语缩写名',
  `attr_full_name` varchar(200) NOT NULL DEFAULT '' COMMENT '定语全称',
  `attr_comment` varchar(200) NOT NULL DEFAULT '' COMMENT '定语说明',
  `apply_system` varchar(100) NOT NULL DEFAULT '' COMMENT '申请系统',
  `apply_app` varchar(100) NOT NULL DEFAULT '' COMMENT '申请应用',
  `apply_user_name` varchar(100) NOT NULL DEFAULT '' COMMENT '申请用户',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT NULL COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`attr_comment`(100)) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_tool_dict_attr
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_tool_dict_field
-- ----------------------------
DROP TABLE IF EXISTS `sys_tool_dict_field`;
CREATE TABLE `sys_tool_dict_field` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'id',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `column_name` varchar(100) NOT NULL DEFAULT '' COMMENT '字段名',
  `column_comment` varchar(100) NOT NULL DEFAULT '' COMMENT '字段说明',
  `data_type` varchar(30) NOT NULL DEFAULT '' COMMENT '数据类型',
  `length` int(10) NOT NULL COMMENT '字段长度',
  `decimal_length` int(2) NOT NULL COMMENT '小数点长度',
  `column_full_name` varchar(200) NOT NULL DEFAULT '' COMMENT '字段全名',
  `enum_value` varchar(10) DEFAULT '' COMMENT '租户ID',
  `apply_system` varchar(100) NOT NULL DEFAULT '' COMMENT '申请系统',
  `apply_app` varchar(100) NOT NULL DEFAULT '' COMMENT '申请应用',
  `apply_user_name` varchar(100) NOT NULL DEFAULT '' COMMENT '申请人',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT NULL COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`column_comment`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_tool_dict_field
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_tool_dict_root
-- ----------------------------
DROP TABLE IF EXISTS `sys_tool_dict_root`;
CREATE TABLE `sys_tool_dict_root` (
  `id` varchar(20) NOT NULL DEFAULT '0' COMMENT 'id',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `root_type` varchar(30) NOT NULL DEFAULT '' COMMENT '词根类型',
  `root_name` varchar(100) NOT NULL DEFAULT '' COMMENT '词根名称',
  `data_type` varchar(30) NOT NULL DEFAULT '' COMMENT '字段类型',
  `length` int(10) NOT NULL COMMENT '字段长度',
  `decimal_length` int(2) NOT NULL COMMENT '小数点长度',
  `root_full_name` varchar(100) NOT NULL DEFAULT '' COMMENT '词根英文全名',
  `comment` varchar(100) NOT NULL DEFAULT '' COMMENT '字段说明',
  `remark` varchar(500) NOT NULL DEFAULT '' COMMENT '备注说明',
  `enum_value` varchar(500) NOT NULL DEFAULT '' COMMENT '枚举值',
  `apply_system` varchar(100) NOT NULL DEFAULT '' COMMENT '申请系统',
  `apply_app` varchar(100) NOT NULL DEFAULT '' COMMENT '申请应用',
  `apply_user_name` varchar(100) NOT NULL DEFAULT '' COMMENT '申请人姓名',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT NULL COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`comment`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_tool_dict_root
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_tool_tables
-- ----------------------------
DROP TABLE IF EXISTS `sys_tool_tables`;
CREATE TABLE `sys_tool_tables` (
  `id` varchar(20) NOT NULL COMMENT 'id',
  `tenant_id` varchar(10) NOT NULL COMMENT '租户编号',
  `table_catalog` varchar(100) NOT NULL DEFAULT '' COMMENT '系统名',
  `table_schema` varchar(64) NOT NULL DEFAULT '' COMMENT '数据库',
  `table_name` varchar(100) NOT NULL DEFAULT '' COMMENT '表名字',
  `table_comment` varchar(100) NOT NULL DEFAULT '' COMMENT '表备注',
  `table_type` varchar(50) NOT NULL DEFAULT '' COMMENT '表类型',
  `primary_key` varchar(500) NOT NULL DEFAULT '' COMMENT '主键',
  `service_name` varchar(100) DEFAULT NULL COMMENT '服务名称',
  `project_name` varchar(200) DEFAULT '' COMMENT '工程名',
  `module_name` varchar(100) DEFAULT NULL COMMENT '模块名称',
  `package_name` varchar(500) DEFAULT NULL COMMENT '后端包名',
  `back_front_flag` char(1) DEFAULT NULL COMMENT '前后端标志',
  `backend_path` varchar(2000) DEFAULT NULL COMMENT '后端路径',
  `frontend_path` varchar(2000) DEFAULT NULL COMMENT '前端路径',
  `tcc_freeze_column` varchar(2000) DEFAULT NULL COMMENT 'tcc控制字段',
  `tcc_post_column` varchar(255) DEFAULT NULL,
  `tcc_index` varchar(255) DEFAULT NULL,
  `version` bigint(21) unsigned NOT NULL DEFAULT '0' COMMENT '版本',
  `is_deleted` char(1) DEFAULT '0' COMMENT '是否已删除',
  `index1` varchar(500) NOT NULL DEFAULT '' COMMENT '索引1',
  `index2` varchar(500) NOT NULL DEFAULT '' COMMENT '索引2',
  `index3` varchar(500) NOT NULL DEFAULT '' COMMENT '索引3',
  `index4` varchar(500) NOT NULL DEFAULT '' COMMENT '索引4',
  `index5` varchar(500) NOT NULL DEFAULT '' COMMENT '索引5',
  `menu_no` varchar(30) DEFAULT NULL COMMENT '菜单编号',
  `data_sign` varchar(128) DEFAULT NULL COMMENT '数据签名',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT NULL COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_tool_tables
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_account
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_account`;
CREATE TABLE `sys_user_account` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `acct_type` varchar(30) NOT NULL DEFAULT '' COMMENT '账户类型',
  `acct_bal` decimal(12,2) NOT NULL COMMENT '账户余额',
  `freeze_amt` decimal(15,2) DEFAULT NULL COMMENT '冻结金额',
  `cash_apply_acct_type` varchar(30) DEFAULT '' COMMENT '提现申请账户类型',
  `cash_apply_real_name` varchar(50) DEFAULT '' COMMENT '提现申请人名',
  `cash_apply_acct_no` varchar(30) DEFAULT '' COMMENT '提现申请账户号',
  `reward_point_count` int(10) DEFAULT NULL COMMENT '奖励积分数量',
  `invite_count` int(10) DEFAULT NULL COMMENT '邀请数量',
  `left_invite_count` int(10) DEFAULT NULL COMMENT '剩余邀请数量',
  `status` varchar(30) DEFAULT '' COMMENT '状态',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账户表';

-- ----------------------------
-- Records of sys_user_account
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_group_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group_relation`;
CREATE TABLE `sys_user_group_relation` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_group_id` varchar(20) NOT NULL DEFAULT '' COMMENT '用户组ID',
  `user_id` varchar(20) NOT NULL DEFAULT '' COMMENT '用户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `belong_org_no` varchar(30) NOT NULL DEFAULT '' COMMENT '所属机构号',
  `belong_org_name` varchar(50) NOT NULL DEFAULT '' COMMENT '所属机构名称',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_group_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_info`;
CREATE TABLE `sys_user_info` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `nick_name` varchar(50) NOT NULL DEFAULT '' COMMENT '昵称',
  `wx_open_id` varchar(50) NOT NULL DEFAULT '' COMMENT '微信openID',
  `mini_program_open_id` varchar(50) NOT NULL DEFAULT '' COMMENT '小程序openID',
  `app_wx_open_id` varchar(50) NOT NULL DEFAULT '' COMMENT 'APP微信openID',
  `dingtalk_app_id` varchar(20) NOT NULL DEFAULT '' COMMENT '钉钉APPID',
  `dingtalk_user_id` varchar(20) NOT NULL DEFAULT '' COMMENT '钉钉用户ID',
  `alipay_open_id` varchar(50) NOT NULL DEFAULT '' COMMENT '支付宝openID',
  `app_alipay_open_id` varchar(50) NOT NULL DEFAULT '' COMMENT 'APP支付宝openID',
  `client_id` varchar(20) NOT NULL DEFAULT '' COMMENT '客户端ID',
  `belong_org_no` varchar(30) NOT NULL DEFAULT '' COMMENT '所属机构号',
  `belong_org_name` varchar(50) NOT NULL DEFAULT '' COMMENT '所属机构名称',
  `belong_org_group` varchar(2000) DEFAULT '' COMMENT '所属机构组',
  `belong_dept_id` varchar(20) NOT NULL DEFAULT '' COMMENT '所属部门ID',
  `belong_dept_name` varchar(50) NOT NULL DEFAULT '' COMMENT '所属部门名称',
  `company_name` varchar(50) NOT NULL DEFAULT '' COMMENT '公司名称',
  `post_name` varchar(50) DEFAULT '' COMMENT '职位名称',
  `own_role_group` varchar(2000) DEFAULT '' COMMENT '拥有角色组',
  `sex` varchar(2) NOT NULL DEFAULT '' COMMENT '性别 1-男，2-女',
  `age` int(4) NOT NULL COMMENT '年龄',
  `phone_no` varchar(30) NOT NULL DEFAULT '' COMMENT '电话号码',
  `mobile_no` varchar(30) NOT NULL DEFAULT '' COMMENT '手机号码',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '邮箱',
  `head_img_url` varchar(100) NOT NULL DEFAULT '' COMMENT '头像链接',
  `skill_name` varchar(50) DEFAULT '' COMMENT '技能名称',
  `grade` varchar(30) DEFAULT '' COMMENT '级别',
  `personal_sign` varchar(128) DEFAULT '' COMMENT '个人签名',
  `password_status` varchar(30) NOT NULL DEFAULT '' COMMENT '密码状态',
  `user_type` varchar(30) NOT NULL DEFAULT '' COMMENT '用户类型',
  `status` varchar(30) DEFAULT '' COMMENT '状态',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT NULL COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userName` (`user_name`) USING BTREE,
  KEY `orgNo` (`belong_org_no`,`real_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_relation`;
CREATE TABLE `sys_user_role_relation` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_id` varchar(20) NOT NULL DEFAULT '' COMMENT '用户ID',
  `role_id` varchar(20) NOT NULL DEFAULT '' COMMENT '角色ID',
  `valid_flag` char(1) DEFAULT '' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT NULL COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT NULL COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT NULL COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  `level` int(10) NOT NULL DEFAULT '1' COMMENT '等级',
  `role_id1` varchar(20) NOT NULL DEFAULT '111' COMMENT '角色ID',
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`user_id`) USING BTREE,
  KEY `role_id_idx` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_setting
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_setting`;
CREATE TABLE `sys_user_setting` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `real_name` varchar(50) DEFAULT '' COMMENT '真实姓名',
  `system_version_no` varchar(30) DEFAULT '' COMMENT '系统版本号',
  `top_menu_version_no` varchar(30) DEFAULT '' COMMENT '顶部菜单版本号',
  `menu_version_no` varchar(30) DEFAULT '' COMMENT '菜单版本号',
  `reset_no` varchar(30) DEFAULT '' COMMENT '重置编号',
  `msg_no` varchar(30) DEFAULT '' COMMENT '消息编号',
  `notice_msg` varchar(2000) DEFAULT '' COMMENT '通知消息',
  `read_flag` char(1) DEFAULT '' COMMENT '读取标志',
  `client_id` varchar(20) NOT NULL DEFAULT '' COMMENT '客户端ID',
  `cust_secret_key` varchar(200) NOT NULL DEFAULT '' COMMENT '客户秘钥',
  `self_def_menu_content` text NOT NULL COMMENT '自定义菜单内容',
  `self_def_navi_content` text NOT NULL COMMENT '自定义导航内容',
  `self_def_layout_content` text NOT NULL COMMENT '自定义布局内容',
  `personal_setting_content` mediumtext COMMENT '个人设置内容',
  `personal_setting_one_content` mediumtext COMMENT '个人设置内容1',
  `personal_setting_two_content` mediumtext COMMENT '个人设置内容2',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_setting
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_trans_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_trans_detail`;
CREATE TABLE `sys_user_trans_detail` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `acct_id` varchar(20) NOT NULL DEFAULT '' COMMENT '账户ID',
  `trans_serial_no` varchar(30) NOT NULL DEFAULT '' COMMENT '交易流水号',
  `trans_direction_flag` char(1) NOT NULL DEFAULT '' COMMENT '交易方向标识',
  `trans_amt` decimal(15,2) NOT NULL COMMENT '交易金额',
  `bal` decimal(12,2) NOT NULL COMMENT '余额',
  `trans_reason` varchar(100) DEFAULT '' COMMENT '交易原因',
  `resource_id` varchar(20) DEFAULT '' COMMENT '资源ID',
  `resource_title` varchar(200) DEFAULT '' COMMENT '资源标题',
  `peer_acct_id` varchar(20) DEFAULT '' COMMENT '对方账户ID',
  `peer_acct_user_name` varchar(50) DEFAULT '' COMMENT '对方账户用户名',
  `peer_trans_serial_no` varchar(30) DEFAULT '' COMMENT '对方交易流水号',
  `trans_rate` varchar(5) DEFAULT '' COMMENT '交易比例',
  `trans_status` varchar(30) DEFAULT '' COMMENT '交易状态',
  `trans_way` varchar(30) DEFAULT '' COMMENT '交易方式',
  `recharge_card_no` varchar(30) DEFAULT '' COMMENT '充值卡号',
  `recharge_password` varchar(50) DEFAULT '' COMMENT '充值密码',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`),
  KEY `tenantId` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户交易明细表';

-- ----------------------------
-- Records of sys_user_trans_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of undo_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for view_ureport
-- ----------------------------
DROP TABLE IF EXISTS `view_ureport`;
CREATE TABLE `view_ureport` (
  `id` char(36) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of view_ureport
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for visual_background
-- ----------------------------
DROP TABLE IF EXISTS `visual_background`;
CREATE TABLE `visual_background` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `background_name` varchar(50) DEFAULT '' COMMENT '背景名称',
  `img_url` varchar(100) DEFAULT '' COMMENT '图片链接',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `tenantId` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of visual_background
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for visual_category
-- ----------------------------
DROP TABLE IF EXISTS `visual_category`;
CREATE TABLE `visual_category` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `category_key` varchar(50) NOT NULL DEFAULT '' COMMENT '分类键值',
  `category_value` varchar(100) DEFAULT '' COMMENT '分类值',
  `is_deleted` char(2) DEFAULT '1' COMMENT '是否删除',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `tenantId` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of visual_category
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for visual_config
-- ----------------------------
DROP TABLE IF EXISTS `visual_config`;
CREATE TABLE `visual_config` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `visual_id` varchar(20) NOT NULL DEFAULT '' COMMENT '可视化ID',
  `detail` text COMMENT '配置明细JSON',
  `components` longtext COMMENT '组件JSON',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `tenantId` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of visual_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for visual_map
-- ----------------------------
DROP TABLE IF EXISTS `visual_map`;
CREATE TABLE `visual_map` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `data` longtext COMMENT '数据',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `tenantId` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of visual_map
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for visual_view_list
-- ----------------------------
DROP TABLE IF EXISTS `visual_view_list`;
CREATE TABLE `visual_view_list` (
  `id` varchar(20) NOT NULL DEFAULT '' COMMENT 'ID',
  `tenant_id` varchar(10) NOT NULL DEFAULT '' COMMENT '租户ID',
  `title` varchar(200) NOT NULL DEFAULT '' COMMENT '标题',
  `background_url` varchar(100) DEFAULT '' COMMENT '背景链接',
  `category` varchar(30) DEFAULT '' COMMENT '分类',
  `password` varchar(50) DEFAULT '' COMMENT '密码',
  `status` varchar(30) DEFAULT '' COMMENT '状态',
  `is_deleted` char(2) DEFAULT '' COMMENT '是否删除',
  `valid_flag` char(1) DEFAULT '1' COMMENT '有效标志',
  `data_sign` varchar(128) DEFAULT '' COMMENT '数据签名',
  `create_user_name` varchar(50) DEFAULT '' COMMENT '新增人用户名',
  `create_real_name` varchar(50) DEFAULT '' COMMENT '新增人姓名',
  `create_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_user_name` varchar(50) DEFAULT '' COMMENT '修改人用户名',
  `update_real_name` varchar(50) DEFAULT '' COMMENT '修改人姓名',
  `update_datetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `reserve_string` varchar(1000) DEFAULT '' COMMENT '备用1',
  `reserve_string2` varchar(1000) DEFAULT '' COMMENT '备用2',
  `reserve_decimal1` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字1',
  `reserve_decimal2` decimal(10,2) DEFAULT '0.00' COMMENT '备用数字2',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `tenantId` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of visual_view_list
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
