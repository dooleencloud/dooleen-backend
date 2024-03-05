package com.dooleen.common.core.mq.vo;

import lombok.Data;

import java.io.Serializable;

import com.dooleen.common.core.app.system.log.entity.SysLogEntity;

/**
 * @author zoujin
 * @date 2020/7/14
 */
@Data
public class LogVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private SysLogEntity sysLog;
    private String username;
}
