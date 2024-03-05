package com.dooleen.service.general.staff.station.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.google.gson.JsonObject;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.service.general.staff.station.entity.GeneralStaffStationBydayEntity;
import com.dooleen.service.general.staff.station.entity.GeneralStaffStationEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-10-10 10:15:54
 * @Description : 工位信息表服务接口
 * @Author : apple
 * @Author : apple
 * @Update: 2020年5月9日 下午9:57:59
 */
public interface GeneralStaffStationReportService extends IService<GeneralStaffStationBydayEntity> {

	/**
	 * 
	 * 按照部门统计记录
	 * 
	 * @Author : apple
	 * @CreateTime : 2020年5月9日 下午9:58:04
	 * @Param : commonMsg 
	 * @Return : CommonMsg
	 */
	JSONObject seatReport(String tenantId,String projectName, String departmentName, String floorNo, String staffType, String flag, String dateDuring);

	 void postByday(String tenantId,String date);
}
