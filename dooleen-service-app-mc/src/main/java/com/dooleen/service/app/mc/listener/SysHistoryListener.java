package com.dooleen.service.app.mc.listener;

import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.app.system.history.entity.SysHistoryInfoEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.constant.MqQueueConstant;
import com.dooleen.common.core.mq.msg.history.UpdateHistoryMq;
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.common.core.utils.RespStatus;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.service.app.mc.history.service.SysHistoryInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SysHistoryListener {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysHistoryInfoService sysHistoryInfoService;
	
	/**
	 * concurrency: 多线程消费，防止数据积压
	 */
	@RabbitListener(queues = MqQueueConstant.HISTORY_ADD_QUEUE, concurrency = "5")
	public void receiveAddHistory(UpdateHistoryMq updateHistoryMq) {
		try {
			logger.info("执行 HISTORY_ADD_QUEUE 队列监听");
			
			String historyData = updateHistoryMq.getHistoryRecordData();
			
			if(StringUtils.isNotBlank(historyData)) {			
				logger.info("新增【ADD】历史记录start......");
				JSONObject historyJson = JSONObject.parseObject(historyData);
				
				SysHistoryInfoEntity entity = new SysHistoryInfoEntity();
				entity.setClassName(historyJson.getString("className"));
				entity.setTenantId(historyJson.getString("tenantId"));
				if(StringUtil.isEmpty(entity.getTenantId())){
					entity.setTenantId("LEARN1001");
				}
				entity.setUpdateDataId(historyJson.getString("updateDataId"));
				entity.setUpdateBeforeDataObj(historyJson.getString("updateBeforeDataObj"));
				entity.setUpdateAfterDataObj(historyJson.getString("updateAfterDataObj"));
				entity.setUpdateContent(historyJson.getString("updateContent"));
				entity.setUpdateBeforeSourceDataObj(historyJson.getString("updateBeforeSourceDataObj"));
				entity.setUpdateAfterSourceDataObj(historyJson.getString("updateAfterSourceDataObj"));
				entity.setSourceDataSign(historyJson.getString("sourceDataSign"));
				
				NullEntity nullEntity = new NullEntity();
				CommonMsg<SysHistoryInfoEntity, NullEntity> commonMsg = CreateCommonMsg.getCommonMsg(entity, nullEntity);
				commonMsg.getHead().setTenantId(entity.getTenantId());
				commonMsg.getHead().setTransCode("saveUpdateHistoryInfo");
				commonMsg.getHead().setRealName(historyJson.getString("updateRealName"));

				commonMsg = sysHistoryInfoService.saveSysHistoryInfo(commonMsg);

				// 判断结果
				if(null == commonMsg || !RespStatus.succCode.equals(commonMsg.getHead().getRespCode())) {
					logger.error("插入历史记录失败！");
				}else {
					logger.info("插入历史记录成功！");
				}
			}else {			
				logger.info("队列数据为空，处理结束！");
			}
		} catch (Exception e) {
			log.error("写入修改历史报错：{}", e.getMessage());
		}
	}
}
