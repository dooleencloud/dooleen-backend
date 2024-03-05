package com.dooleen.service.general.biz.plan.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.utils.DateUtils;
import com.dooleen.service.general.biz.plan.entity.BizPlanManageEntity;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020年8月31日 下午8:13:50
 * @Description : 计划相关工具
 * @Author : apple
 * @Update: 2020年8月31日 下午8:13:50
 */
@Service
public class BuildPlanTree {
	
	/**
	 * 递归构造计划菜单树
	 */
	public List<JSONObject> getChildrenByPlanId(String rootId,List<BizPlanManageEntity> planList){
		List<JSONObject> list = new ArrayList<>();
		List<BizPlanManageEntity> children = getPlans(rootId, planList);
		for (BizPlanManageEntity plan: children) {
			JSONObject obj = (JSONObject) JSONObject.toJSON(plan);
			
			Integer duration = DateUtils.countDays(plan.getPlanBeginDatetime(), plan.getPlanFinishDatetime());
			
			obj.put("id", plan.getId());
			obj.put("parent", plan.getParentPlanId());
			obj.put("text", plan.getPlanName());
			obj.put("start_date", plan.getPlanBeginDatetime());
			obj.put("duration", duration);
			obj.put("progress", plan.getProgress());
			
			rootId = plan.getId();
			obj.put("children", getChildrenByPlanId(rootId, planList));
			list.add(obj);
		}
		return list;
	}
	public List<BizPlanManageEntity> getPlans(String rootId,List<BizPlanManageEntity> plan){
		if(StringUtils.isBlank(rootId)) {
			rootId = "";
		}
		
		List<BizPlanManageEntity> planList = new ArrayList<BizPlanManageEntity>();
		for(int i = 0;i < plan.size(); i++) {
			if(rootId.equals(plan.get(i).getParentPlanId()) 
					|| (StringUtils.isBlank(plan.get(i).getParentPlanId()) && StringUtils.isBlank(rootId))) {
				planList.add(plan.get(i));
			}
		}
		return planList;
	}
}
