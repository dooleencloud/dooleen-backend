package com.dooleen.common.core.global.exception.assertion;

import java.text.MessageFormat;

import com.dooleen.common.core.constant.IResponseEnum;
import com.dooleen.common.core.global.exception.BaseException;
import com.dooleen.common.core.global.exception.BizException;

/**
 * <p>业务异常断言</p>
 *
 * @author liqh
 * @date 2019/5/2
 */
public interface BizExceptionAssert extends IResponseEnum, Assert {

	 @Override
	    default BaseException newException(Object... args) {
	        String msg = this.getCode()+"-"+MessageFormat.format(this.getMessage(), args);
	        return new BizException(this, args, msg);
	    }

	    @Override
	    default BaseException newException(Throwable t, Object... args) {
	        String msg = this.getCode()+"-"+MessageFormat.format(this.getMessage(), args);
	        return new BizException(this, args, msg, t);
	    }

}
