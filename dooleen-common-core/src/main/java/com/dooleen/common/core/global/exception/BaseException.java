package com.dooleen.common.core.global.exception;


import com.dooleen.common.core.constant.IResponseEnum;

import lombok.Getter;

/**
 * <p>基础异常类，所有自定义异常类都需要继承本类</p>
 *
 * @author liqh
 * @date 2020/5/2
 */
@Getter
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     */
    protected IResponseEnum responseEnum;
    /**
     * 异常消息参数
     */
    protected Object[] args;

    public BaseException(IResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public BaseException(String code, String msg) {
        super(msg);
        this.responseEnum = new IResponseEnum() {
            @Override
            public String getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return msg;
            }
        };
    }

    public BaseException(IResponseEnum responseEnum, Object[] args, String message) {
        super(message);
        this.responseEnum = responseEnum;
        this.args = args;
    }

    public BaseException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(message, cause);
        this.responseEnum = responseEnum;
        this.args = args;
    }
}
