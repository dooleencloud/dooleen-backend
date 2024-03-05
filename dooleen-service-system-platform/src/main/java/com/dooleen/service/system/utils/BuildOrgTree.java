/**
 * 
 */
package com.dooleen.service.system.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dooleen.service.system.menu.entity.SysMenuEntity;
import com.dooleen.service.system.org.info.entity.SysOrgInfoEntity;
import com.dooleen.service.system.org.info.mapper.SysOrgInfoMapper;

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
	private  SysOrgInfoMapper sysOrgInfoMapper;
	
	/**
	 * 递归构造机构树
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月5日 上午10:57:03
	 * @Param : sysOrgInfo 
	 * @Return : List<JSONObject>
	 */
	public List<JSONObject> getChildren(SysOrgInfoEntity sysOrgInfo){
		List<JSONObject> list = new ArrayList<>();
		List<SysOrgInfoEntity> children = sysOrgInfoMapper.queryByParentOrgNo(sysOrgInfo);
		for (SysOrgInfoEntity sysOrgInfoEntity : children) {
			JSONObject obj = new JSONObject(true);
			obj.put("id", sysOrgInfoEntity.getId());
			obj.put("orgNo", sysOrgInfoEntity.getOrgNo());
			obj.put("orgName", sysOrgInfoEntity.getOrgName());
			obj.put("parentOrgNo", sysOrgInfoEntity.getParentOrgNo());
			obj.put("children", getChildren(sysOrgInfoEntity));
			list.add(obj);
		}
		return list;
	}
	/**
	 * 递归获取所有节点的机构号
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月5日 上午10:57:03
	 * @Param : sysOrgInfo 
	 * @Return : List<JSONObject>
	 */
	public List<String> getAllChildrenOrgNo(SysOrgInfoEntity sysOrgInfo,List<String> tmpList){
		List<SysOrgInfoEntity> children = sysOrgInfoMapper.queryByParentOrgNo(sysOrgInfo);
		for (SysOrgInfoEntity sysOrgInfoEntity : children) {
			tmpList.add(sysOrgInfoEntity.getOrgNo());
			//System.out.println("递归==="+sysOrgInfoEntity.getOrgNo());
			getAllChildrenOrgNo(sysOrgInfoEntity,tmpList);
		}
		return tmpList;
	}
	

	/**
	 * 递归删除子节点
	 */
	public void deleteOrgParentOrgNo(String tenantId, String orgNo){
        //删除该节点
        try { 
        	QueryWrapper<SysOrgInfoEntity> queryWrapper = new QueryWrapper<SysOrgInfoEntity>();
        	queryWrapper.lambda().eq(SysOrgInfoEntity::getTenantId, tenantId);
        	queryWrapper.lambda().eq(SysOrgInfoEntity::getParentOrgNo, orgNo);
            List<SysOrgInfoEntity> sysOrgInfoEntityList = sysOrgInfoMapper.selectList(queryWrapper);
            //递归删除该节点下所有节点
            if (sysOrgInfoEntityList == null||sysOrgInfoEntityList.size()==0){
                return;
            }else {
            	//删除所有子机构
            	sysOrgInfoMapper.delete(queryWrapper);
    			//循环递归删除
    			for (SysOrgInfoEntity sysOrgInfoEntity : sysOrgInfoEntityList){
    				deleteOrgParentOrgNo(tenantId, sysOrgInfoEntity.getOrgNo());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
