package com.dooleen.common.core.utils;

import java.util.ArrayList;
import java.util.List;

import com.dooleen.common.core.app.general.flow.entity.FlowProcessDataEntity;
import com.dooleen.common.core.common.entity.*;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : dooleen
 * @Version : 1.0.0
 * @CreateDate : 2019-07-11 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description :
 * @Maintainer:liqiuhong
 * @Update:
 */
public class CreateCommonMsg {

	
	public static <S,T> CommonMsg<S,T> getCommonMsg(S s,T t) {
		CommonMsg<S,T> repMsg = new CommonMsg<S,T>();
		HeadEntity head = new HeadEntity();
		MutBean<S,T> mut = new MutBean<S,T>();
		// 设置值
		List<T> list = new ArrayList<T>();
		FlowProcessDataEntity flowArea = new FlowProcessDataEntity();
		list.add(t);
		mut.setFlowArea(flowArea);
		mut.setSingleBody(s);
		mut.setListBody(list);
		List<SQLOrderEntity> orderRule = new ArrayList<>();
		mut.setOrderRule(orderRule);
		List<SQLConditionEntity> sqlCondition = new ArrayList<>();
		mut.setSqlCondition(sqlCondition);
		repMsg.setHead(head);
		repMsg.setBody(mut);
		return repMsg;
	}

}
