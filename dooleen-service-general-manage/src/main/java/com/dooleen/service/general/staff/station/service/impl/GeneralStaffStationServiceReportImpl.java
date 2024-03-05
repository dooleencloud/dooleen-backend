package com.dooleen.service.general.staff.station.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.JsonObject;
import com.dooleen.common.core.common.entity.HeadEntity;
import com.dooleen.common.core.enums.TableEntityORMEnum;
import com.dooleen.common.core.utils.*;
import com.dooleen.service.general.staff.station.entity.GeneralStaffStationBydayEntity;
import com.dooleen.service.general.staff.station.entity.GeneralStaffStationEntity;
import com.dooleen.service.general.staff.station.mapper.GeneralStaffStationBydayMapper;
import com.dooleen.service.general.staff.station.mapper.GeneralStaffStationMapper;
import com.dooleen.service.general.staff.station.service.GeneralStaffStationReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-10-10 10:15:54
 * @Description : 工位信息表服务实现
 * @Author : apple
 * @Update: 2020-10-10 10:15:54
 */

/**
 * 注意事项，本服务程序运行时会报错，因为工程限制有3处需要手工修改1、TableEntityORMEnum需要手工添加常量，2、若需要流程，请根据实际情况添加标题
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GeneralStaffStationServiceReportImpl extends ServiceImpl<GeneralStaffStationBydayMapper, GeneralStaffStationBydayEntity> implements GeneralStaffStationReportService {

   @Autowired
   private  GeneralStaffStationMapper generalStaffStationMapper;

   @Autowired
   private  GeneralStaffStationBydayMapper generalStaffStationBydayMapper;

   @Autowired
   private  GeneralStaffStationReportService generalStaffStationReportService;

   private  static String seqPrefix= TableEntityORMEnum.GENERAL_STAFF_STATION_BYDAY.getSeqPrefix();
   private  static String tableName = TableEntityORMEnum.GENERAL_STAFF_STATION_BYDAY.getEntityName();

   @Autowired
   private RedisTemplate<String, ?> redisTemplate;

   /**
    * 历史入库
    * @param tenantId
    * @param date
    */
   @Override
   public void postByday(String tenantId, String date) {
      /**
       * 读取所有工位实时记录
       */
      QueryWrapper<GeneralStaffStationEntity> queryWrapper = new QueryWrapper<GeneralStaffStationEntity>();
      if(!StringUtil.isEmpty(tenantId)) {
         queryWrapper.lambda().eq(GeneralStaffStationEntity::getTenantId, tenantId);
      }
      List<GeneralStaffStationEntity> staffStationEntityList = generalStaffStationMapper.selectList(queryWrapper);
      for (GeneralStaffStationEntity generalStaffStationEntity : staffStationEntityList) {
         GeneralStaffStationBydayEntity generalStaffStationBydayEntity =  new GeneralStaffStationBydayEntity();
         generalStaffStationBydayEntity.setTenantId(generalStaffStationEntity.getTenantId());
         generalStaffStationBydayEntity.setOfficeAreaNo(generalStaffStationEntity.getOfficeAreaNo());
         generalStaffStationBydayEntity.setAttendanceStatus(generalStaffStationEntity.getAttendanceStatus());
         generalStaffStationBydayEntity.setBelongDeptName(generalStaffStationEntity.getBelongDeptName());
         generalStaffStationBydayEntity.setBelongDeptNo(generalStaffStationEntity.getBelongDeptNo());
         generalStaffStationBydayEntity.setBelongProjectName(generalStaffStationEntity.getBelongProjectName());
         generalStaffStationBydayEntity.setOfficeAreaNo(generalStaffStationEntity.getOfficeAreaNo());
         generalStaffStationBydayEntity.setBelongProjectNo(generalStaffStationEntity.getBelongProjectNo());
         generalStaffStationBydayEntity.setContactPhoneNo(generalStaffStationEntity.getContactPhoneNo());
         generalStaffStationBydayEntity.setFloorNo(generalStaffStationEntity.getFloorNo());
         generalStaffStationBydayEntity.setManagerRealName(generalStaffStationEntity.getManagerRealName());
         generalStaffStationBydayEntity.setManagerUserName(generalStaffStationEntity.getManagerUserName());
         generalStaffStationBydayEntity.setStaffStationNo(generalStaffStationEntity.getStaffStationNo());
         generalStaffStationBydayEntity.setStaffStationProperties(generalStaffStationEntity.getStaffStationProperties());
         generalStaffStationBydayEntity.setStaffStationType(generalStaffStationEntity.getStaffStationType());
         generalStaffStationBydayEntity.setStaffType(generalStaffStationEntity.getStaffType());
         generalStaffStationBydayEntity.setStatus(generalStaffStationEntity.getStatus());
         generalStaffStationBydayEntity.setUseRealName(generalStaffStationEntity.getUseRealName());
         generalStaffStationBydayEntity.setUseStatusList(generalStaffStationEntity.getUseStatusList());
         generalStaffStationBydayEntity.setUseUserName(generalStaffStationEntity.getUseUserName());
         generalStaffStationBydayEntity.setDataSign(generalStaffStationEntity.getDataSign());
         generalStaffStationBydayEntity.setCreateDatetime(generalStaffStationEntity.getCreateDatetime());
         generalStaffStationBydayEntity.setCreateRealName(generalStaffStationEntity.getCreateRealName());
         generalStaffStationBydayEntity.setCreateUserName(generalStaffStationEntity.getCreateUserName());
         generalStaffStationBydayEntity.setUpdateDatetime(generalStaffStationEntity.getUpdateDatetime());
         generalStaffStationBydayEntity.setUpdateRealName(generalStaffStationEntity.getUpdateRealName());
         generalStaffStationBydayEntity.setUpdateUserName(generalStaffStationEntity.getUpdateUserName());
         if(StringUtil.isEmpty(date)) {
            generalStaffStationBydayEntity.setPostDate(DateUtils.getReqDate());
         }
         else
         {
            generalStaffStationBydayEntity.setPostDate(date);
         }
         //写入数据

         generalStaffStationBydayEntity.setDataSign(DooleenMD5Util.md5(generalStaffStationBydayEntity.toString(),  ConstantValue.md5Key));
         HeadEntity head =new HeadEntity();
         head.setTenantId(generalStaffStationEntity.getTenantId());
         // 执行保存
         boolean result =  super.save(EntityInitUtils.initEntityPublicInfoForInsertOrUpdate(generalStaffStationBydayEntity, head,tableName, seqPrefix, redisTemplate));
      }
   }

   /**
    * 按照部门统计
    * @param tenantId
    * @param departmentName
    * @param floorNo
    * @param staffType
    * @param flag
    * @param dateDuring
    * @return
    */
   @Override
   public JSONObject seatReport(String tenantId,String projectName, String departmentName, String floorNo, String staffType, String flag, String dateDuring) {
      if(flag.equals("1")) {
         return reportByStaffStatus(tenantId, projectName, departmentName, floorNo, staffType, flag, dateDuring);
      }
      else {
         return reportByUseStatus(tenantId, projectName, departmentName, floorNo, staffType, flag, dateDuring);
      }
   }
   //按照使用状态统计
   public JSONObject reportByUseStatus(String tenantId,String projectName, String departmentName, String floorNo, String staffType, String flag, String dateDuring){
      DateUtilPro.DateTime date2 = DateUtilPro.date().addDays(Integer.parseInt(dateDuring) * -1);
      //正常
      QueryWrapper<GeneralStaffStationBydayEntity> queryWrapper = new QueryWrapper<GeneralStaffStationBydayEntity>();
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getTenantId, tenantId);
      queryWrapper.lambda().ge(GeneralStaffStationBydayEntity::getPostDate, DateUtils.getReqDateyyyyMMdd(date2));
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getFloorNo, floorNo);
      if(!StringUtil.isEmpty(departmentName)){
         queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getBelongDeptName, departmentName);
      }
      if(!StringUtil.isEmpty(projectName)){
         queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getBelongProjectName, projectName);
      }
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getStatus, "1");
      int  normal= generalStaffStationBydayMapper.selectCount(queryWrapper);
      System.out.println("===normalCnt===： "+normal);

      //空置
      queryWrapper.lambda().clear();
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getTenantId, tenantId);
      queryWrapper.lambda().ge(GeneralStaffStationBydayEntity::getPostDate, DateUtils.getReqDateyyyyMMdd(date2));
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getFloorNo, floorNo);
      if(!StringUtil.isEmpty(departmentName)){
         queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getBelongDeptName, departmentName);
      }
      if(!StringUtil.isEmpty(projectName)){
         queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getBelongProjectName, projectName);
      }
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getStatus, "2");
      int  empty= generalStaffStationBydayMapper.selectCount(queryWrapper);
      System.out.println("===empty===： "+empty);

      JSONObject returnJson = new JSONObject();
      //柱状数据
      //返回JSON
      JSONObject barJson = new JSONObject();
      List<String> categories = new ArrayList<>();
      categories.add("正常");
      categories.add("空置");

      JSONArray seriesArray = new JSONArray();
      JSONObject seriesJson = new JSONObject();
      List<Integer> seriesData = new ArrayList<>();
      seriesData.add(normal);
      seriesData.add(empty);
      seriesJson.put("name","使用状态");
      seriesJson.put("data",seriesData);
      seriesArray.add(seriesJson);
      barJson.put("categories",categories);
      barJson.put("series",seriesArray);

      returnJson.put("bar",barJson);

      //处理饼状数据
      JSONArray pieArray = new JSONArray();
      pieArray.add(JSONObject.parse("{\"name\":\"正常\",\"value\":"+normal+"}"));
      pieArray.add(JSONObject.parse("{\"name\":\"空置\",\"value\":"+empty+"}"));
      returnJson.put("pie",pieArray);
      return returnJson;
   }

   // 按照出勤状态统计
   public JSONObject reportByStaffStatus(String tenantId,String projectName, String departmentName, String floorNo, String staffType, String flag, String dateDuring){
      DateUtilPro.DateTime date2 = DateUtilPro.date().addDays(Integer.parseInt(dateDuring) * -1);
      //正常
      QueryWrapper<GeneralStaffStationBydayEntity> queryWrapper = new QueryWrapper<GeneralStaffStationBydayEntity>();
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getTenantId, tenantId);
      queryWrapper.lambda().ge(GeneralStaffStationBydayEntity::getPostDate, DateUtils.getReqDateyyyyMMdd(date2));
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getFloorNo, floorNo);
      if(!StringUtil.isEmpty(departmentName)){
         queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getBelongDeptName, departmentName);
      }
      if(!StringUtil.isEmpty(projectName)){
         queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getBelongProjectName, projectName);
      }
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getAttendanceStatus, "正常");
      int  normal= generalStaffStationBydayMapper.selectCount(queryWrapper);
      System.out.println("===normalCnt===： "+normal);

      //迟到
      queryWrapper.lambda().clear();
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getTenantId, tenantId);
      queryWrapper.lambda().ge(GeneralStaffStationBydayEntity::getPostDate, DateUtils.getReqDateyyyyMMdd(date2));
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getFloorNo, floorNo);
      if(!StringUtil.isEmpty(departmentName)){
         queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getBelongDeptName, departmentName);
      }
      if(!StringUtil.isEmpty(projectName)){
         queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getBelongProjectName, projectName);
      }
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getAttendanceStatus, "迟到");
      int  later= generalStaffStationBydayMapper.selectCount(queryWrapper);
      System.out.println("===later===： "+later);

      //早退
      queryWrapper.lambda().clear();
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getTenantId, tenantId);
      queryWrapper.lambda().ge(GeneralStaffStationBydayEntity::getPostDate, DateUtils.getReqDateyyyyMMdd(date2));
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getFloorNo, floorNo);
      if(!StringUtil.isEmpty(departmentName)){
         queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getBelongDeptName, departmentName);
      }
      if(!StringUtil.isEmpty(projectName)){
         queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getBelongProjectName, projectName);
      }
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getAttendanceStatus, "早退");
      int  early= generalStaffStationBydayMapper.selectCount(queryWrapper);
      System.out.println("===later===： "+early);

      //缺卡
      queryWrapper.lambda().clear();
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getTenantId, tenantId);
      queryWrapper.lambda().ge(GeneralStaffStationBydayEntity::getPostDate, DateUtils.getReqDateyyyyMMdd(date2));
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getFloorNo, floorNo);
      if(!StringUtil.isEmpty(departmentName)){
         queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getBelongDeptName, departmentName);
      }
      if(!StringUtil.isEmpty(projectName)){
         queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getBelongProjectName, projectName);
      }
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getAttendanceStatus, "缺卡");
      int  lost= generalStaffStationBydayMapper.selectCount(queryWrapper);
      System.out.println("===later===： "+lost);

      //请假
      queryWrapper.lambda().clear();
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getTenantId, tenantId);
      queryWrapper.lambda().ge(GeneralStaffStationBydayEntity::getPostDate, DateUtils.getReqDateyyyyMMdd(date2));
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getFloorNo, floorNo);
      if(!StringUtil.isEmpty(departmentName)){
         queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getBelongDeptName, departmentName);
      }
      if(!StringUtil.isEmpty(projectName)){
         queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getBelongProjectName, projectName);
      }
      queryWrapper.lambda().eq(GeneralStaffStationBydayEntity::getAttendanceStatus, "请假");
      int  apply= generalStaffStationBydayMapper.selectCount(queryWrapper);
      System.out.println("===later===： "+apply);

      JSONObject returnJson = new JSONObject();
      //柱状数据
      //返回JSON
      JSONObject barJson = new JSONObject();
      List<String> categories = new ArrayList<>();
      categories.add("正常");
      categories.add("迟到");
      categories.add("早退");
      categories.add("缺卡");
      categories.add("请假");

      JSONArray seriesArray = new JSONArray();
      JSONObject seriesJson = new JSONObject();
      List<Integer> seriesData = new ArrayList<>();
      seriesData.add(normal);
      seriesData.add(later);
      seriesData.add(early);
      seriesData.add(lost);
      seriesData.add(apply);
      seriesJson.put("name","出勤状态");
      seriesJson.put("data",seriesData);
      seriesArray.add(seriesJson);
      barJson.put("categories",categories);
      barJson.put("series",seriesArray);

      returnJson.put("bar",barJson);

      //处理饼状数据
      JSONArray pieArray = new JSONArray();
      pieArray.add(JSONObject.parse("{\"name\":\"正常\",\"value\":"+normal+"}"));
      pieArray.add(JSONObject.parse("{\"name\":\"迟到\",\"value\":"+later+"}"));
      pieArray.add(JSONObject.parse("{\"name\":\"早退\",\"value\":"+early+"}"));
      pieArray.add(JSONObject.parse("{\"name\":\"缺卡\",\"value\":"+lost+"}"));
      pieArray.add(JSONObject.parse("{\"name\":\"请假\",\"value\":"+apply+"}"));
      returnJson.put("pie",pieArray);
      return returnJson;
   }
}
