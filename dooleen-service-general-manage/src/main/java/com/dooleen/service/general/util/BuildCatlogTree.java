/**
 * 
 */
package com.dooleen.service.general.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dooleen.service.general.file.catalog.entity.GeneralFileCatalogEntity;
import com.dooleen.service.general.file.catalog.mapper.GeneralFileCatalogMapper;
import com.dooleen.service.general.smart.report.catalog.entity.GeneralReportCatalogEntity;
import com.dooleen.service.general.smart.report.catalog.mapper.GeneralReportCatalogMapper;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020年6月6日 下午8:13:50
 * @Description : 数据库字段表接口
 * @Author : apple
 * @Update: 2020年6月6日 下午8:13:50
 */
@Service
public class BuildCatlogTree {
	@Autowired
	private  GeneralFileCatalogMapper generalFileCatalogMapper;
	@Autowired
	private  GeneralReportCatalogMapper generalReportCatalogMapper;
	
	/**
	 * 递归构造目录树
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月5日 上午10:57:03
	 * @Param : generalOrgInfo 
	 * @Return : List<JSONObject>
	 */
	public List<JSONObject> getChildren(GeneralFileCatalogEntity generalOrgInfo,String readFlag){
		List<JSONObject> list = new ArrayList<>();
		List<GeneralFileCatalogEntity> children = generalFileCatalogMapper.queryByParentCatalogId(generalOrgInfo);
		for (GeneralFileCatalogEntity generalFileCatalogEntity : children) {
			JSONObject obj = new JSONObject(true);
			obj.put("id", generalFileCatalogEntity.getId());
			obj.put("catalogName", generalFileCatalogEntity.getCatalogName());
			obj.put("parentCatalogId", generalFileCatalogEntity.getParentCatalogId());
			obj.put("readFlag", readFlag);
			obj.put("ownerId", generalFileCatalogEntity.getOwnerId());
			obj.put("children", getChildren(generalFileCatalogEntity,readFlag));
			list.add(obj);
		}
		return list;
	}
	
	/**
	 * 递归构造报表目录树
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月5日 上午10:57:03
	 * @Param : generalOrgInfo 
	 * @Return : List<JSONObject>
	 */
	public List<JSONObject> getReportChildren(GeneralReportCatalogEntity generalOrgInfo,String readFlag){
		List<JSONObject> list = new ArrayList<>();
		List<GeneralReportCatalogEntity> children = generalReportCatalogMapper.queryByParentCatalogId(generalOrgInfo);
		for (GeneralReportCatalogEntity generalReportCatalogEntity : children) {
			JSONObject obj = new JSONObject(true);
			obj.put("id", generalReportCatalogEntity.getId());
			obj.put("catalogName", generalReportCatalogEntity.getCatalogName());
			obj.put("parentCatalogId", generalReportCatalogEntity.getParentCatalogId());
			obj.put("readFlag", readFlag);
			obj.put("ownerId", generalReportCatalogEntity.getOwnerId());
			obj.put("children", getReportChildren(generalReportCatalogEntity,readFlag));
			list.add(obj);
		}
		return list;
	}
}
