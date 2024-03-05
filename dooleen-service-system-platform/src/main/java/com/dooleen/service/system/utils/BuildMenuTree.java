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
import com.dooleen.service.system.api.scope.entity.SysApiScopeEntity;
import com.dooleen.service.system.menu.entity.SysMenuEntity;
import com.dooleen.service.system.menu.mapper.SysMenuMapper;
import com.dooleen.service.system.tenant.privilege.entity.SysTenantPrivilegeRelationEntity;

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
public class BuildMenuTree {
	@Autowired
	private  SysMenuMapper sysMenuMapper;
	
	/**
	 * 递归构造菜单树（弃用）
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月5日 上午10:57:03
	 * @Param : sysMenu 
	 * @Return : List<JSONObject>
	 */
	public List<JSONObject> getChildren(SysMenuEntity sysMenu,boolean isSuper,String resourceType,String menuCategory){
		List<JSONObject> list = new ArrayList<>();
		List<SysMenuEntity> children = new ArrayList();
		if(isSuper) {
			children= sysMenuMapper.queryByParentMenuIdForSuper(sysMenu,menuCategory);
			isSuper = true;
		}
		else {
			SysTenantPrivilegeRelationEntity sysTenantPrivilegeRelationEntity = new SysTenantPrivilegeRelationEntity();
			sysTenantPrivilegeRelationEntity.setId(sysMenu.getId());
			sysTenantPrivilegeRelationEntity.setTenantId(sysMenu.getTenantId());
			sysTenantPrivilegeRelationEntity.setResourceType(resourceType);
			sysTenantPrivilegeRelationEntity.setId(sysMenu.getId());
			children= sysMenuMapper.queryByParentMenuId(sysTenantPrivilegeRelationEntity,menuCategory);
		}
		for (SysMenuEntity sysMenuEntity : children) {
			JSONObject obj = new JSONObject(true);
			JSONObject metaObject = new JSONObject();
			if(sysMenuEntity.getKeepAliveFlag().equals("1")) {
				metaObject.put("i18n","cache");
			}
			else {
				metaObject.put("i18n","");
			}
			metaObject.put("keepAlive",sysMenuEntity.getKeepAliveFlag().equals("1")? true : false);
		
			obj.put("id", sysMenuEntity.getId());
			obj.put("parentId", sysMenuEntity.getParentMenuId());
			obj.put("code", sysMenuEntity.getMenuNo());
			obj.put("name", sysMenuEntity.getMenuName());
			obj.put("alias", sysMenuEntity.getMenuNickName());
			obj.put("path", sysMenuEntity.getRouteAddress());
			obj.put("source", sysMenuEntity.getMenuIcon());
			obj.put("sort", sysMenuEntity.getMenuSeq());
			obj.put("category", sysMenuEntity.getMenuCategory());
			obj.put("category", 1);
			obj.put("meta", metaObject);
			obj.put("children", getChildren(sysMenuEntity,isSuper,resourceType,menuCategory));
			list.add(obj);
		}
		return list;
	}
	/**
	 * 递归删除子节点
	 */
	public void deleteMenuByParentId(String tenantId, String id){
        //删除该节点
        try { 
        	QueryWrapper<SysMenuEntity> queryWrapper = new QueryWrapper<SysMenuEntity>();
        	queryWrapper.lambda().eq(SysMenuEntity::getTenantId, tenantId);
        	queryWrapper.lambda().eq(SysMenuEntity::getParentMenuId, id);
            List<SysMenuEntity> sysMenuEntityList = sysMenuMapper.selectList(queryWrapper);
            //递归删除该节点下所有节点
            if (sysMenuEntityList == null||sysMenuEntityList.size()==0){
                return;
            }else {
            	//删除所有子菜单
    			sysMenuMapper.delete(queryWrapper);
    			//循环递归删除
    			for (SysMenuEntity sysMenuEntity : sysMenuEntityList){
    				deleteMenuByParentId(tenantId, sysMenuEntity.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	/**
	 * 递归构造用户菜单树
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年6月5日 上午10:57:03
	 * @Param : sysMenu 
	 * @Return : List<JSONObject>
	 */
	public List<JSONObject> getChildrenByUserId(String rootId,List<SysMenuEntity> sysMenu){
		List<JSONObject> list = new ArrayList<>();
		List<SysMenuEntity> children = getMenus(rootId,sysMenu);
		for (SysMenuEntity sysMenuEntity : children) {
			JSONObject obj = new JSONObject(true);
			JSONObject metaObject = new JSONObject();
			metaObject.put("i18n","cache");
			metaObject.put("keepAlive",sysMenuEntity.getKeepAliveFlag().equals("1")? true : false);
			
			obj.put("id", sysMenuEntity.getId());
			obj.put("parentId", sysMenuEntity.getParentMenuId());
			obj.put("code", sysMenuEntity.getMenuNo());
			obj.put("name", sysMenuEntity.getMenuName());
			obj.put("alias", sysMenuEntity.getMenuNickName());
			obj.put("path", sysMenuEntity.getRouteAddress());
			obj.put("source", sysMenuEntity.getMenuIcon());
			obj.put("sort", sysMenuEntity.getMenuSeq());
			obj.put("appIcon", sysMenuEntity.getAppIcon());
			obj.put("appRouteAddress", sysMenuEntity.getAppRouteAddress());
			obj.put("category", 1);
			obj.put("action", 0);
			obj.put("isOpen", 1);
			obj.put("remark", sysMenuEntity.getRemark());
			obj.put("isDeleted", 0);
			obj.put("parentName", "");
			obj.put("categoryName", "");
			obj.put("actionName", "");
			obj.put("isOpenName", "");
			obj.put("meta", metaObject);
			rootId = sysMenuEntity.getId();
			obj.put("children", getChildrenByUserId(rootId,sysMenu));
			list.add(obj);
		}
		return list;
	}
	public List<SysMenuEntity> getMenus(String rootId,List<SysMenuEntity> sysMenu){
		List<SysMenuEntity> menuList = new ArrayList();
		for(int i = 0;i < sysMenu.size(); i++) {
			if(sysMenu.get(i).getParentMenuId().equals(rootId)) {
				menuList.add(sysMenu.get(i));
			}
		}
		return menuList;
	}
}
