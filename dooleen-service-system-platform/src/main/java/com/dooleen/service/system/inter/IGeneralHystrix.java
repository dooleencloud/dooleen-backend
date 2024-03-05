package com.dooleen.service.system.inter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dooleen.common.core.app.general.org.info.entity.GeneralOrgInfoEntity;
import com.dooleen.common.core.app.general.org.staff.entity.GeneralOrgStaffRelationEntity;
import com.dooleen.common.core.app.general.staff.info.entity.GeneralStaffInfoEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.service.system.inter.impl.GeneralHystrixImpl;

@FeignClient(value = "dooleen-service-general-manage", fallback = GeneralHystrixImpl.class)
public interface IGeneralHystrix {

	/**
	 * 新增组织机构(加密)
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/general/generalOrgInfo/saveGeneralOrgInfo",method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	String saveGeneralOrgInfo(@RequestBody String commonMsgStr);
	
	/**
	 * 新增组织机构(非加密)
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/general/generalOrgInfo/saveGeneralOrgInfo",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	CommonMsg<GeneralOrgInfoEntity, NullEntity> saveGeneralOrgInfo(@RequestBody CommonMsg<GeneralOrgInfoEntity, NullEntity> commonMsg);
	
	/**
	 * 新增系统组织用户关系(加密)
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/general/generalOrgStaffRelation/saveGeneralOrgStaffRelation",method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	String saveGeneralOrgStaffRelation(@RequestBody String commonMsgStr);
	
	/**
	 * 新增系统组织用户关系(非加密)
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/general/generalOrgStaffRelation/saveGeneralOrgStaffRelation",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> saveGeneralOrgStaffRelation(@RequestBody CommonMsg<GeneralOrgStaffRelationEntity, NullEntity> commonMsg);

	/**
	 * 分页查询人员信息(加密)
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/general/generalStaffInfo/queryGeneralStaffInfoListPage",method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	String queryGeneralStaffInfoListPage(@RequestBody String commonMsgStr);
	
	/**
	 * 分页查询人员信息(非加密)
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/general/generalStaffInfo/queryGeneralStaffInfoListPage",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	CommonMsg<GeneralStaffInfoEntity, GeneralStaffInfoEntity> queryGeneralStaffInfoListPage(@RequestBody CommonMsg<GeneralStaffInfoEntity, GeneralStaffInfoEntity> commonMsg);
}
