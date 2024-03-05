# 🌐独领分布式微服务开发平台
<p align="left">
 <img src="https://img.shields.io/badge/Dooleen-1.0-success.svg" alt="Build Status">
 <img src="https://img.shields.io/badge/Spring%20Cloud-Hoxton.SR2-blue.svg" alt="Coverage Status">
 <img src="https://img.shields.io/badge/Spring%20Boot-2.2.6-blue.svg" alt="Downloads">
 <img src="https://img.shields.io/badge/Vue-2.10-blue.svg" alt="Downloads">
 <img src="https://img.shields.io/badge/lisence-Apache2.0-success.svg"/>
 <img src="https://gitee.com/dooleenCloud/dooleen-backend/badge/star.svg?theme=dark">
</p>

## 项目简介
独领开发平台是基于SpringCloud 开发的微服务框架技术平台，我们整合了众多的技术能力（SpringCloud、ShardingSphere、Nacos、Redis、MongoDB、OnylyOffice等等），可以帮助你快速搭建内部的管理开发平台等；我们希望做到您开箱即用。平台除了整合了认证授权、网关、配置中心等技术套件； 还开放了用户管理、文档管理、OnlyOffice/wps、报表管理、BI报表、智能填报、日历、会议、流程管理、计划管理、动态表单等功能；如果您是做软件开发，相信这里一定有你想要的功能！
#### 最后你会发现我们开源的不只是开发平台，还有很多实用的功能😁！

## 开源说明
我们希望开源所有已开发的功能和技术能力，但整个平台开发过程中也使用了部分开源项目的能力，本着尊重原作者的、远离法律风险的思想；做二开的部分我们不打算也不能放在本开源项目中。如果大家有需要可以添加微信或进入技术群进行交流！

## 适用场景
- 企业管理系统开发及运行平台
- 交易系统开发及运行平台

## 使用技术栈

| 技术组件            | 说明                               | 是否开源      |
|-----------------|----------------------------------|-----------|
| SpringCloud     | 最火最流行的分布式微服务技术框架                 | 是         |
| Nacos           | 阿里开源的分布式注册中心、配置中心                | 是         |
| Redis           | 高性能缓存/内存数据库，支持集群                 | 是         |
| Caffeine        | 高性能本地缓存                          | 是         |
| RabbitMq        | 高性能消息中间件，支持集群                    | 是         |
| Spring OAuth2.0 | 基于分布式的访问鉴权系统                     | 是         |
| Mysql           | 最熟悉的最火的数据库                       | 是         |
| ShardingSphere  | 分布式数据库引擎，支持多库多表、读写分离等功能          | 是         |
| Mybatis plus    | 社区最火的数据库访问中间件                    | 是         |
| MongoDB         | 非结构型文档数据库                        | 是         |
| FastDFS         | 开源的轻量级分布式文件系统                    | 是         |
| OnlyOffice      | 人人可以部署的开源office系统，功能媲美 MS office | 是（社区）     |
| Nginx           | 高性能的HTTP和反向代理web服务器              | 是         |
| Knife4j         | API文档管理系统                        | 是 （二开不开源） |
| EasyPoi         | 简单好用的Excel处理组件                   | 是         |
| Vue             | 前端开发框架                           | 是         |
| Avue            | 数据驱动视图的快速前端开发框架                  | 是         |
| 其他              | 功能介绍中一一介绍                        | 部分开源      |


## 系统功能

| 功能名称     | 功能介绍                                        | 是否开源      |
|----------|---------------------------------------------|-----------|
| 多租户管理    | 支持云部署不同租户的登录页面、LOGO制定义；数据库物理隔离；按功能售卖等       | 是         |
| 租户管秘钥    | 支持对租户独立部署时进行秘钥授权                            | 是         |
| 用户管理     | 不同维度的用户管理（系统维度、机构维度，项目维度）                   | 是         |
| 角色权限管理   | 基于RBAC的访问控制                                 | 是         |
| 菜单管理     | 支持顶部菜单、左侧菜单管理                               | 是         |
| 访问资源管理   | 支持菜单、按钮、数据栏位等资源的访问控制管理                      | 是         |
| 消息通知     | 支持站内消息、微信公众号等平台的消息推送                        | 是         |
| 项目管理     | 支持项目级别的用户、机构、菜单等资源管理                        | 是         |
| 计划管理     | 支持拖拽看板和甘特图两种展现摸索                            | 是         |
| 会议管理     | 基于日历组件的会议管理，支持对接腾讯会议                        | 是         |
| 日历管理     | 节假日、日期管理                                    | 是         |
| 日程管理     | 管理会议、出差等日程，支持日程共享                           | 是         |
| 工位管理     | 整合可视化大屏，展现企业工位信息                            | 是         |
| 参数、枚举值管理 | 支持系统级和功能级的参数值、枚举管理                          | 是         |
| 在线文档管理   | 支持文档目录结构，文档共享、文档在线编辑等功能（支持OnlyOffice和wps整合） | 是         |
| 报表管理     | 二开开源报表平台，高度集成到独领开发平台                        | 二开不开源     |
| 企业调查表单   | 支持调查表单的自定义，适合管理机构对企业的问卷表单生成和问卷收集            | 是         |
| 自定义首页    | 模块化可拖拽自定义个性首页                               | 是         |
| 动态表单     | 通过拖拽表单元素，数据驱动生成表单。真正做到0代码开发表单               | 是 |
| 审批流程定义   | 适合中国人的审批流程定义；                               | 是         |
| BI报表     | 二开达芬奇开源BI平台                                 | 二开不开源       |
| 可视化大屏    | 拖拽试大屏开发                                     | 二开不开源         |
| 智能数据标准   | 基于词根的数据标准，可以自动根据语义生成标准变量                    | 是         |
| 一键代码     | 一键生成前后端可运行的代码，标准的CRUD功能、文件导入导出功能            | 是         |
| API文档管理  | 基于Knife4j的文档管理系统（二开）                        | 二开不开源      |
| 第三方集成    | 支持第三方功能的集成配置（公众号、短信、腾讯会议等等）                 | 部分开源      |
| ...      | 更多功能关注后面章节功能介绍                              | 部分开源      |

### 登陆-支持背景图片自定义
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/登陆.png">

### 首页
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/登陆后首页.png">

### 站内消息中心
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/站内消息中心.png">

### 主题设置
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/主题设置.png">

### 同步个人信息到云端
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/同步个人信息到云端.png">

### 多租户管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/租户管理.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/租户配置.png">

### 顶部菜单管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/顶部菜单管理.png">

### 左侧菜单管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/菜单管理.png">

### 机构管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/机构管理.png">

### 人员信息管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/人员信息管理.png">


### 系统用户管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/系统用户管理.png">


### 角色管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/角色管理.png">


### 系统字典参数管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/系统字典参数管理.png">


### 系统参数管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/系统参数管理.png">

### 消息模板配置
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/消息模板配置.png">

### 第三方参数配置
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/第三方参数配置.png">

### 工作日历
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/工作日历.png">

### 日程管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/日程管理.png">

### 文档管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/文档管理.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/文档模板管理.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/文档模板选择.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/文档编辑.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/文档历史.png">

### 报表管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/报表管理.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/报表设计器.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/报表数据查看.png">

### 会议管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/会议室管理.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/会议管理-1.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/会议管理-2.png">

### 工位管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/工位管理.png">
  整合可视化拖拽设计
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/工位管理系统-1.png">

### 项目管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/项目管理.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/项目级系统配置.png">

### 计划管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/计划管理-1.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/计划管理-2.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/计划管理-3.png">

### 审批流程管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/流程管理.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/审批流程-1.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/审批流程-2.png">

### 标准变量
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/标准变量.png">

### 一键代码
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/一键代码.png">


### 智能问卷
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/智能问卷-1.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/智能问卷-问卷统计.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/智能问卷-问卷收集.png">

### API文档管理
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/api管理-1.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/API管理-微服务列表.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/API管理-API文档.png">
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/API管理-执行记录.png">

### 可视化大屏首页.png
 <img src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/可视化大屏首页.png">

***** 更多功能 请本地部署体验！******

## 后端工程列表
```lua
dooleen-backend  ----父工程
├──dooleen-common-core ----功能核心功能
├──dooleen-common-parent ----微服务核心依赖打包公共父工程
├──dooleen-common-parent-normal ----普通JAR包依赖打包公共父工程
├──dooleen-service-api-doc ----API文档后台服务（不开源）
├──dooleen-service-app-gateway ----网关服务
├──dooleen-service-app-mc ----消息服务
├──dooleen-service-app-oauth ----访问鉴权服务
├──dooleen-service-batch ----批量处理服务
├──dooleen-service-biz-manage ----业务管理开发
├──dooleen-service-data-visual ----可视化报表服务（不开源）
├──dooleen-service-file-manage ----文件管理服务
├──dooleen-service-general-manage ----综合应用服务
├──dooleen-service-server-admin ----SpringCloud后台管理微服务
├──dooleen-service-server-turbine ----监控数据聚合服务
├──dooleen-service-server-websocket ----前端消息socket 服务
├──dooleen-service-smart-bi ---- 智能分析报表服务（不开源）
├──dooleen-service-smart-report ---- 数据报表服务（不开源）
├──dooleen-service-system-platform ----系统基础平台
├──dooleen-service-system-tool ----系统基础平台工具（数据字典、一键代码等）
```

## 配套前端工程地址
https://gitee.com/dooleencloud/dooleen-frontend

##  演示地址
- 由于开源项目访问量大，无法提供大容量的在线服务器，固我们暂不提供外网访问功能；建议大家本地部署了看运行效果；
- 若必须查看演示效果，请联系作者可给您本地远程演示！（微信号：cacabook）

## 安装运行教程
- https://blog.csdn.net/pinkfantasy/article/details/136310916 （独领分布式微服开发平台安装手册）
## 开源推荐
- `Avue` 基于 vue 可配置化的前端框架：[https://gitee.com/smallweigit/avue](https://gitee.com/smallweigit/avue)
- `ShardingSphere` 分布式数据库访问框架（架构必备）：[https://gitee.com/Sharding-Sphere/sharding-sphere](https://gitee.com/Sharding-Sphere/sharding-sphere)
- `Mybatis-plus` 数据库访问ORM组件：[https://gitee.com/baomidou/mybatis-plus](https://gitee.com/baomidou/mybatis-plus)

## 开源说明
### 开源协议
独领开发平台开源遵循 [Apache 2.0 协议](https://www.apache.org/licenses/LICENSE-2.0.html)。
即：允许商业使用，但务必保留类作者、Copyright 等信息。
### 提交PR说明

1. 欢迎提交 [PR]()，注意对应提交对应 `feature` 分支
   代码规范 [spring-javaformat](https://github.com/spring-io/spring-javaformat)

2. 欢迎提交 [issue](https://gitee.com/dooleencloud/dooleen-backend/issues)，请写清楚遇到问题的原因、开发环境、复现步骤。
## 选择优势
- 本项目的每一行功能代码都是我们敲出来的，给你最有保障的支持！

## 微信
 ### 作者微信二维码 (wx:cacabook)
 <img width='300' src="https://doooleen-1314948468.cos.ap-chengdu.myqcloud.com/dooleen-open/system/作者二维码.png">
