package com.dooleen.common.core.global.exception;

import com.dooleen.common.core.enums.ServiceErrCode;

/**
 * 
 */
public class BaseServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private int code;

    public BaseServiceException(String message, ServiceErrCode serviceErrCode) {
        super(message);
        this.code = serviceErrCode.getCode();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
