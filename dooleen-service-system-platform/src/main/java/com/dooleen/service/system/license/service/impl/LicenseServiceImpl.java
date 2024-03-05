package com.dooleen.service.system.license.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.config.license.LicenseManagerHolder;
import com.dooleen.common.core.enums.BizResponseEnum;
import com.dooleen.common.core.utils.CommonUtil;
import com.dooleen.common.core.utils.DateUtils;
import com.dooleen.service.system.license.service.LicenseService;
import com.dooleen.service.system.license.entity.LicenseEntity;
import com.dooleen.service.system.tenant.info.entity.SysTenantInfoEntity;
import com.dooleen.service.system.tenant.info.mapper.SysTenantInfoMapper;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-06-04 15:51:08
 * @Description : 租户管理服务实现
 * @Author : apple
 * @Update: 2020-06-04 15:51:08
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class LicenseServiceImpl extends ServiceImpl<SysTenantInfoMapper, SysTenantInfoEntity> implements LicenseService {
	
    public static final String CHECK_CONTENT = "checkContent";
    public static final String DELETE = "delete";
	@Autowired
	private  SysTenantInfoMapper sysTenantInfoMapper;

	@Override
	public CommonMsg<LicenseEntity, NullEntity> checkTenantLicense(CommonMsg<LicenseEntity, NullEntity> commonMsg) {
		CommonUtil.service(commonMsg);

		List<NullEntity> nullEntityList = new ArrayList<NullEntity>();

		//1、检查license 是否有效：
		LicenseManager licenseManager = LicenseManagerHolder.getInstance(null);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat nowFormat = new SimpleDateFormat("yyyyMMddHHmmss");


		QueryWrapper<SysTenantInfoEntity> queryWrapper = new QueryWrapper<SysTenantInfoEntity>();
		queryWrapper.lambda().eq(SysTenantInfoEntity::getTenantId, commonMsg.getHead().getTenantId());
		SysTenantInfoEntity sysTenantInfoEntity = sysTenantInfoMapper.selectOne(queryWrapper);
		BizResponseEnum.TENANT_NOT_FOUND.assertNotNull(sysTenantInfoEntity,commonMsg);
		String validDate = DateUtils.getCurrTimestamp(DateUtils.getDateByStr(sysTenantInfoEntity.getInvalidDate()));
		String nowDate = nowFormat.format(new Date());
		if(Long.parseLong(validDate) < Long.parseLong(nowDate)){
			BizResponseEnum.TENANT_SETTINGS_EXPIRE.assertIsTrue(false,commonMsg);
			log.info(MessageFormat.format("租户有效期已过，租户有效期为：{0}",validDate));
		}

		//2、校验证书
		try {
			LicenseContent licenseContent = licenseManager.verify();
			log.info(MessageFormat.format("证书校验通过，证书有效期：{0} - {1}",format.format(licenseContent.getNotBefore()),format.format(licenseContent.getNotAfter())));
			LicenseEntity licenseEntity = new LicenseEntity();
			licenseEntity.setReturnMsg("证书校验通过");
			licenseEntity.setEffectDate(format.format(licenseContent.getNotBefore()));
			licenseEntity.setInvalidDate(format.format(licenseContent.getNotAfter()));
			commonMsg.getBody().setSingleBody(licenseEntity);
			//System.out.println(licenseContent.getSubject());
		}catch (Exception e){
			log.error("证书校验失败！",e);
			BizResponseEnum.LICENSE_INVALID.assertIsTrue(false,commonMsg);
		}

		commonMsg.getBody().setListBody(nullEntityList);
		//设置返回值
		CommonUtil.successReturn(commonMsg);
		return commonMsg;
	}
}
