package com.dooleen.service.general.staff.station.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dooleen.common.core.sentinel.CustomerBlockHandler;
import com.dooleen.common.core.aop.annos.EnableSystemLog;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import com.dooleen.service.general.staff.station.service.GeneralStaffStationReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-10-10 10:15:54
 * @Description : 工位信息表Controller
 * @Author : apple
 * @Update: 2020-10-10 10:15:54
 */
@Slf4j
@RestController
@Api(tags = "工位信息表相关接口")
@RequestMapping("/general/generalStaffStation/report")
public class GeneralStaffStationReportController {

	@Autowired
	private GeneralStaffStationReportService generalStaffStationReportService;

	/**
	 * 历史数据入库
	 * @param tenantId
	 * @param date
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@ApiOperation(value ="历史数据入库调度接口")
	@RequestMapping(value = "postByday", method = RequestMethod.GET)
	@SecretAnnotation(decode=false,encode=false)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="postDate")
	public String postByday(@RequestParam("tenantId") String tenantId, @RequestParam("date") String date, HttpServletResponse response,HttpServletRequest request) throws Exception {
		generalStaffStationReportService.postByday(tenantId,date);
		log.info("====历史数据入库调度接口 end ==== ");
		return "===exec success！==";
	}

	/**
	 * 按照部门维度统计
	 * @param departmentName
	 * @param floorNo
	 * @param staffType
	 * @param flag  /统计维度 0-座位利用率  1-出勤状态
	 * @param dateDuring
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@ApiOperation(value ="按照部门维度统计")
	@RequestMapping(value = "seatReport", method = RequestMethod.GET)
    @SecretAnnotation(decode=false,encode=false)
	@SentinelResource(blockHandlerClass= CustomerBlockHandler.class, fallbackClass=CustomerBlockHandler.class, blockHandler = "customBlockHandler",fallback = "customFallback")
	@EnableSystemLog(operatoinType="COMPUTE")
	public JSONObject seatReport(@RequestParam("projectName") String projectName,@RequestParam("departmentName") String departmentName, @RequestParam("floorNo") String floorNo, @RequestParam("staffType") String staffType, @RequestParam("flag") String flag, @RequestParam("dateDuring") String dateDuring, HttpServletResponse response,HttpServletRequest request) throws Exception {
		String tenantId = request.getHeader("TenantId");
		JSONObject jsonObj = generalStaffStationReportService.seatReport(tenantId, projectName,departmentName, floorNo, staffType,flag, dateDuring);
		log.info("====按照部门维度统计处理 end ==== ");
		return jsonObj;
	}    
}