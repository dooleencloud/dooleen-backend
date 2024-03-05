package com.dooleen.common.core.aop.service;

import com.dooleen.common.core.common.entity.HistoryLog;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-04-21 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description : 写日志接口
 * @Maintainer:liqiuhong
 * @Update:
 */
public interface OperatelogService {
    void insert(HistoryLog operatelog);
}
