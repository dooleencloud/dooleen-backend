package com.dooleen.common.core.global.exception;

import com.alibaba.fastjson.JSONObject;
import com.dooleen.common.core.common.entity.CommonMsg;
import com.dooleen.common.core.common.entity.NullEntity;
import com.dooleen.common.core.utils.CreateCommonMsg;
import com.dooleen.common.core.utils.RedisUtil;
import com.dooleen.common.core.utils.RespStatus;
import com.dooleen.common.core.utils.StringUtil;
import com.dooleen.common.core.utils.aes.SecretAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;
import java.util.*;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    private static final String logExceptionFormat = "Capture Exception By GlobalExceptionHandler: Code: %s Detail: %s";

    @Autowired
    private RedisUtil redisUtil;

    //运行时异常
    @ExceptionHandler(RuntimeException.class)
     @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> runtimeExceptionHandler(RuntimeException ex) {
        return resultFormat(1, ex);
    }
 
    //空指针异常
    @ExceptionHandler(NullPointerException.class)
     @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> nullPointerExceptionHandler(NullPointerException ex) {
        log.error("NullPointerException:");
        return resultFormat(2, ex);
    }
 
    //类型转换异常
    @ExceptionHandler(ClassCastException.class)
     @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> classCastExceptionHandler(ClassCastException ex) {
        return resultFormat(3, ex);
    }
 
    //IO异常
    @ExceptionHandler(IOException.class)
     @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> iOExceptionHandler(IOException ex) {
        return resultFormat(4, ex);
    }
 
    //未知方法异常
    @ExceptionHandler(NoSuchMethodException.class)
     @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        return resultFormat(5, ex);
    }
 
    //数组越界异常
    @ExceptionHandler(IndexOutOfBoundsException.class)
     @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        return resultFormat(6, ex);
    }
 
    // 400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
     @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> requestNotReadable(HttpMessageNotReadableException ex) {
        log.error("400..requestNotReadable");
        return resultFormat(7, ex);
    }
 
    //400错误
    @ExceptionHandler({TypeMismatchException.class})
     @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> requestTypeMismatch(TypeMismatchException ex) {
		log.error("400..TypeMismatchException");
        return resultFormat(8, ex);
    }
 
    //400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> requestMissingServletRequest(MissingServletRequestParameterException ex) {
    	log.error("400..MissingServletRequest");
        return resultFormat(9, ex);
    }
 
    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> request405(HttpRequestMethodNotSupportedException ex) {
        return resultFormat(10, ex);
    }
 
    //406错误
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
     @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> request406(HttpMediaTypeNotAcceptableException ex) {
    	log.error("406...");
        return resultFormat(11, ex);
    }
 
    //500错误
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
     @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> server500(RuntimeException ex) {
    	log.error("500...");
        return resultFormat(12, ex);
    }
 
    //栈溢出
    @ExceptionHandler({StackOverflowError.class})
     @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> requestStackOverflow(StackOverflowError ex) {
        return resultFormat(13, ex);
    }
 
    //除数不能为0
    @ExceptionHandler({ArithmeticException.class})
     @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> arithmeticException(ArithmeticException ex) {
        return resultFormat(13, ex);
    }


 
    //其他错误
    @ExceptionHandler({Exception.class})
     @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> exception(Exception ex) {
        return resultFormat(14, ex);
    }
    
    //键值重复
    @ExceptionHandler({DuplicateKeyException.class})
     @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> duplicateKeyException(Exception ex) {
        return resultFormat(15, ex);
    }
    
    //非法参数
    @ExceptionHandler({IllegalArgumentException.class})
     @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> illegalArgumentException(Exception ex) {
        return resultFormat(16, ex);
    }
    

    //业务异常
    @ExceptionHandler({BizException.class})
    @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> businessException(Exception ex) {
        return resultFormat(17, ex);
    }

    //文件上传大小异常
    @ExceptionHandler({MaxUploadSizeExceededException.class})
    @SecretAnnotation(decode=true,encode=true)
    public CommonMsg<JSONObject, NullEntity> maxUploadSizeExceededException(Exception ex) {
        return resultFormat(18, ex);
    }

    private <T extends Throwable> CommonMsg<JSONObject, NullEntity> resultFormat(Integer code, T ex) {
    	Map<String, String> map = new HashMap<String, String>(2);
    	NullEntity nullEntity = new NullEntity();
    	JSONObject jsonObj = new JSONObject();
		CommonMsg<JSONObject, NullEntity> exceptionCommonMsg = CreateCommonMsg.getCommonMsg(jsonObj, nullEntity);
    	// 设置失败返回值
    	exceptionCommonMsg.getHead().setRespCode(RespStatus.errorCode);
    	//处理异常类型
    	String errorMsg = "";
    	switch (code) {
    		case 1:
    			errorMsg = "运行时异常";
    			break;
    		case 2:
                errorMsg = "空值异常，请修改后重试！";
                break;
    		case 3:
    			errorMsg = "类型转换异常";
    			break;
    		case 4:
    			errorMsg = "输入输出异常";
    			break;
    		case 5:
    			errorMsg = "未知方法异常";
    			break;
    		case 6:
    			errorMsg = "数组越界异常";
    			break;
    		case 7:
    			errorMsg = "400错误";
    			break;
    		case 8:
    			errorMsg = "400错误";
    			break;
    		case 9:
    			errorMsg = "丢失请求参数";
    			break;
    		case 10:
    			errorMsg = "不支持该请求方法";
    			break;
    		case 11:
    			errorMsg = "不支持请求类型";
    			break;
    		case 12:
    			errorMsg = "服务不可用";
    			break;
    		case 13:
    			errorMsg = "除数不能为0";
    			break;
    		case 14:
    			errorMsg = "其他异常";
    			break;
    		case 15:
    			String str = ex.getCause().getMessage();
    			int count = 0;
    			boolean flag = false;
    			StringBuffer sb = new StringBuffer();
    			List<String> data = new ArrayList<String>();
    			for(int i=0; i < str.length(); i++) {
    				if('\'' == str.charAt(i)) {
    					count ++;
    					sb.append(str.charAt(i));
    					if(count % 2 == 0) {
    						flag = true;
    					}
    					if(flag) {
    						data.add(sb.toString());
    						sb = new StringBuffer();
    						flag = false;
    					}
    				}else {
    					if(count % 2 != 0) {
    						sb.append(str.charAt(i));
    					}
    				}
    			}
    			errorMsg = "键值重复 {索引【" + data.get(1) + "】; 数据【" + data.get(0) + "】}";
    			break;
    		case 16:
    			errorMsg = "输入参数错误";
    			break;
            case 18:
                errorMsg = "文件大小超过设置限制！";
                break;
    		default :
    			errorMsg = "系统未知错误，请联系管理员";
    			break;
    	}
    	// 处理业务错误
    	if(code == 17) {
    	    BizException bizException = (BizException) ex;
            exceptionCommonMsg = (CommonMsg)bizException.getArgs()[0];
    		String msgStr = bizException.getArgs().length > 1 ? bizException.getArgs()[1].toString() : "";
            String errCodeMsg = (String) redisUtil.get("ERRORCODE:"+exceptionCommonMsg.getHead().getTenantId()+":"+bizException.getResponseEnum().getCode());
    		if(StringUtil.isEmpty(errCodeMsg)){
                errCodeMsg = bizException.getResponseEnum().getMessage();
            }
            map.put(bizException.getResponseEnum().getCode(), errCodeMsg+msgStr);
            exceptionCommonMsg.getHead().setRespCode(bizException.getResponseEnum().getCode());
            exceptionCommonMsg.getHead().setRespMsg(map);
    	}
    	else {
    		map.put("E9997", "error:"+errorMsg);
    		exceptionCommonMsg.getHead().setRespMsg(map);
    		log.error("==errorMsg== "+ex.getMessage());
    		ex.printStackTrace();
    	}
        exceptionCommonMsg.getBody().setSingleBody(new JSONObject());
        return exceptionCommonMsg;
    }
}