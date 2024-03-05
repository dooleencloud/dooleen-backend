package com.dooleen.service.system.inter.impl;

import org.springframework.stereotype.Component;

import com.dooleen.common.core.app.general.org.info.entity.GeneralOrgInfoEntity;
import com.dooleen.common.core.app.general.org.staff.entity.GeneralOrgStaffRelationEntity;
import com.dooleen.common.core.app.general.staff.info.entity.GeneralStaffInfoEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.service.system.inter.IGeneralHystrix;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GeneralHystrixImpl implements IGeneralHystrix {
	
	@Override
	public String saveGeneralOrgInfo(String commonMsgStr) {
		log.error("saveGeneralOrgInfo（加密）-新增组织机构接口，进入熔断，返回null");
		return null;
	}

	@Override
	public CommonMsg<GeneralOrgInfoEntity, NullEntity> saveGeneralOrgInfo(
			CommonMsg<GeneralOrgInfoEntity, NullEntity> commonMsg) {
		log.error("saveGeneralOrgInfo（未加密）-新增组织机构接口，进入熔断，返回null");
		return null;
	}

	@Override
	public String saveGeneralOrgStaffRelation(String commonMsgStr) {
		log.error("saveGeneralOrgStaffRelation（加密）-新增系统组织用户关系接口，进入熔断，返回null");
		return null;
	}

	@Override
	public CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> saveGeneralOrgStaffRelation(
			CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> commonMsg) {
		log.error("saveGeneralOrgStaffRelation（未加密）-新增系统组织用户关系接口，进入熔断，返回null");
		return null;
	}

	@Override
	public String queryGeneralStaffInfoListPage(String commonMsgStr) {
		log.error("queryGeneralStaffInfoListPage(加密)-分页查询人员信息接口，进入熔断，返回null");
		return null;
	}

	@Override
	public CommonMsg<GeneralStaffInfoEntity, GeneralStaffInfoEntity> queryGeneralStaffInfoListPage(
			CommonMsg<GeneralStaffInfoEntity, GeneralStaffInfoEntity> commonMsg) {
		log.error("queryGeneralStaffInfoListPage(非加密)-分页查询人员信息接口，进入熔断，返回null");
		return null;
	}

	
}
