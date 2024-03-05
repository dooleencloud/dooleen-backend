package com.dooleen.common.core.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dooleen.common.core.common.entity.SQLConditionEntity;

public class QueryWrapperUtil {
	public static <T> QueryWrapper<T> parseWhereSql(List<SQLConditionEntity> conditionList, Class<T> className) {
		QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
		if (conditionList != null) {
			for (SQLConditionEntity conditionEntity : conditionList) {
				switch (conditionEntity.getType()) {
				case "=":
					queryWrapper.eq(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					break;
				case "≠":
					queryWrapper.ne(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					break;
				case "like":
					queryWrapper.like(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					break;
				case "leftlike":
					queryWrapper.likeLeft(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					break;
				case "rightlike":
					queryWrapper.likeRight(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					break;
				case "notlike":
					queryWrapper.notLike(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					break;
				case ">":
					queryWrapper.gt(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					break;
				case "<":
					queryWrapper.lt(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					break;
				case "in":
					JSONArray conditionArray = JSON.parseArray(conditionEntity.getValue());
					if(conditionArray.size() > 0) {
						queryWrapper.in(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionArray);
					}
					break;
				case "≥":
					queryWrapper.ge(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					break;
				case "≤":
					queryWrapper.le(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					break;
				}
			}
		}
		return queryWrapper;
	}
	public static <T> QueryWrapper<T> parseWhereOrSql(List<SQLConditionEntity> conditionList, Class<T> className) {
		QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
		if (conditionList != null) {
			for (SQLConditionEntity conditionEntity : conditionList) {
			 if(conditionEntity.getColumn().equals("tenantId")) {
				queryWrapper.eq(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
			 }
			 else {
				switch (conditionEntity.getType()) {
				case "=":
					queryWrapper.eq(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					queryWrapper.or();
					break;
				case "≠":
					queryWrapper.ne(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					queryWrapper.or();
					break;
				case "like":
					queryWrapper.like(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					queryWrapper.or();
					break;
				case "leftlike":
					queryWrapper.likeLeft(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					queryWrapper.or();
					break;
				case "rightlike":
					queryWrapper.likeRight(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					queryWrapper.or();
					break;
				case "notlike":
					queryWrapper.notLike(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					queryWrapper.or();
					break;
				case ">":
					queryWrapper.gt(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					queryWrapper.or();
					break;
				case "<":
					queryWrapper.lt(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					queryWrapper.or();
					break;
				case "≥":
					queryWrapper.ge(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					queryWrapper.or();
					break;
				case "≤":
					queryWrapper.le(StringUtil.humpToUnderline(conditionEntity.getColumn()), conditionEntity.getValue());
					queryWrapper.or();
					break;
				}
			 }
			}
		}
		return queryWrapper;
	}
}
