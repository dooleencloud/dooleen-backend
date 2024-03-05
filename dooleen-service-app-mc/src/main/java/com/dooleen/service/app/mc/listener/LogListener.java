package com.dooleen.service.app.mc.listener;

import com.dooleen.common.core.app.system.log.entity.SysLogEntity;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.constant.MqQueueConstant;
import com.dooleen.common.core.mq.vo.LogVO;
import com.dooleen.common.core.utils.ConstantValue;
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.common.core.utils.RespStatus;
import com.dooleen.service.app.mc.log.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zoujin
 * @date 2020/7/14
 */
@Slf4j
@Component
@RabbitListener(queues = MqQueueConstant.LOG_QUEUE)
public class LogListener {
//    private static final String KEY_USER = "user";

	@Autowired
	private SysLogService sysLogService;

	@RabbitHandler
    public void receive(LogVO logVo) {    	
    	try {
    		SysLogEntity sysLog = logVo.getSysLog();
            sysLog.setDataSign(DooleenMD5Util.md5(sysLog.toString(),  ConstantValue.md5Key));
    		
    		NullEntity nullEntity = new NullEntity();
    		CommonMsg<SysLogEntity, NullEntity> commonMsg = CreateCommonMsg.getCommonMsg(sysLog, nullEntity);
    		commonMsg.getHead().setTenantId(logVo.getSysLog().tenantId);
    		commonMsg.getHead().setTransCode("saveSysLog");
    		commonMsg.getHead().setRealName("system");
 			commonMsg = sysLogService.saveSysLog(commonMsg);

    		// 判断结果
    		if(null == commonMsg || !RespStatus.succCode.equals(commonMsg.getHead().getRespCode())) {
    			log.error("插入系统日志失败！");
    		}else {
    			log.info("插入系统日志成功！");
    		}
		} catch (Exception e) {
			log.error("写系统日志报错：{}", e.getMessage());
		}
    }
}
