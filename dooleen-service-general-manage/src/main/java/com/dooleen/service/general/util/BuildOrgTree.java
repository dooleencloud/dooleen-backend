/**
 * 
 */
package com.dooleen.service.general.util;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dooleen.common.core.app.general.org.info.entity.GeneralOrgInfoEntity;
import com.dooleen.common.core.app.general.org.info.mapper.GeneralOrgInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class BuildOrgTree {
	@Autowired
	private GeneralOrgInfoMapper generalOrgInfoMapper;
	
	/**
	 * 递归构造机构树
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月5日 上午10:57:03
	 * @Param : generalOrgInfo 
	 * @Return : List<JSONObject>
	 */
	public List<JSONObject> getChildren(GeneralOrgInfoEntity generalOrgInfo, List<String> fieldList){
		List<JSONObject> list = new ArrayList<>();
		List<GeneralOrgInfoEntity> children = generalOrgInfoMapper.queryByParentOrgNo(generalOrgInfo);
		for (GeneralOrgInfoEntity generalOrgInfoEntity : children) {
			JSONObject obj = new JSONObject(true);
			Map entityMap = JSONObject.parseObject(JSONObject.toJSONString(generalOrgInfoEntity), Map.class);
			for (String field: fieldList){
				if(!field.equals("serialVersionUID")){
					obj.put(field, entityMap.get(field));
				}
			}
			List<JSONObject> childrenData = getChildren(generalOrgInfoEntity, fieldList);
			if(null == childrenData || childrenData.isEmpty()){
				obj.put("leaf", true);
			}
			obj.put("children", childrenData);
			list.add(obj);
		}
		return list;
	}
	/**
	 * 递归获取所有节点的机构号
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月5日 上午10:57:03
	 * @Param : generalOrgInfo 
	 * @Return : List<JSONObject>
	 */
	public List<String> getAllChildrenOrgNo(GeneralOrgInfoEntity generalOrgInfo,List<String> tmpList){
		List<GeneralOrgInfoEntity> children = generalOrgInfoMapper.queryByParentOrgNo(generalOrgInfo);
		for (GeneralOrgInfoEntity generalOrgInfoEntity : children) {
			tmpList.add(generalOrgInfoEntity.getOrgNo());
			//System.out.println("递归==="+generalOrgInfoEntity.getOrgNo());
			getAllChildrenOrgNo(generalOrgInfoEntity,tmpList);
		}
		return tmpList;
	}
	/**
	 * 递归删除子节点
	 */
	public void deleteOrgParentOrgNo(String tenantId, String orgNo){
        //删除该节点
        try { 
        	QueryWrapper<GeneralOrgInfoEntity> queryWrapper = new QueryWrapper<GeneralOrgInfoEntity>();
        	queryWrapper.lambda().eq(GeneralOrgInfoEntity::getTenantId, tenantId);
        	queryWrapper.lambda().eq(GeneralOrgInfoEntity::getParentOrgNo, orgNo);
            List<GeneralOrgInfoEntity> generalOrgInfoEntityList = generalOrgInfoMapper.selectList(queryWrapper);
            //递归删除该节点下所有节点
            if (generalOrgInfoEntityList == null||generalOrgInfoEntityList.size()==0){
                return;
            }else {
            	//删除所有子机构
            	generalOrgInfoMapper.delete(queryWrapper);
    			//循环递归删除
    			for (GeneralOrgInfoEntity generalOrgInfoEntity : generalOrgInfoEntityList){
    				deleteOrgParentOrgNo(tenantId, generalOrgInfoEntity.getOrgNo());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
