package com.dooleen.common.core.global.exception;

import com.dooleen.common.core.constant.IResponseEnum;

/**
 * <p>业务异常</p>
 * <p>业务处理时，出现异常，可以抛出该异常</p>
 *
 * @author liqh
 * @date 2019/5/2
 */
public class BizException extends  BaseException {

    private static final long serialVersionUID = 1L;

    public BizException(IResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    public BizException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }
}