package com.dooleen.common.core.utils;

import com.dooleen.common.core.common.entity.ValidationResult;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : dooleen
 * @Version : 1.0.0
 * @CreateDate : 2019-07-11 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description :
 * @Maintainer:liqiuhong
 * @Update:
 */  
public class ValidationUtils {  
  
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
  
    public static <T> ValidationResult validateEntity(T obj) {
        ValidationResult result = new ValidationResult();  
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);  
        // if( CollectionUtils.isNotEmpty(set) ){  
        if (set != null && set.size() != 0) {  
            result.setHasErrors(true);  
            Map<String, String> errorMsg = new HashMap<String, String>();  
            for (ConstraintViolation<T> cv : set) {  
                errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());  
            }  
            result.setRespMsg(errorMsg);  
        }  
        return result;  
    }  
  
    public static <T> ValidationResult validateProperty(T obj, String propertyName) {  
        ValidationResult result = new ValidationResult();  
        Set<ConstraintViolation<T>> set = validator.validateProperty(obj, propertyName, Default.class);  
        if (set != null && set.size() != 0) {  
            result.setHasErrors(true);  
            Map<String, String> errorMsg = new HashMap<String, String>();  
            for (ConstraintViolation<T> cv : set) {  
                errorMsg.put(propertyName, cv.getMessage());  
            }  
            result.setRespMsg(errorMsg);  
        }  
        return result;  
    }  
    
    // 手机号码前三后四脱敏
    public static String mobileEncrypt(String mobile) {
        if (StringUtils.isEmpty(mobile) || (mobile.length() != 11)) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    //身份证前三后四脱敏
    public static String idEncrypt(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
    }

    //护照前2后3位脱敏，护照一般为8或9位
    public static String idPassport(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.substring(0, 2) + new String(new char[id.length() - 5]).replace("\0", "*") + id.substring(id.length() - 3);
    }
    
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		String s2 = "^[1](([3][0-9])|([4][5,7,9])|([5][0-9])|([6][6])|([7][3,5,6,7,8])|([8][0-9])|([9][8,9]))[0-9]{8}$";// 验证手机号
		if (StringUtils.isNotBlank(str)) {
			p = Pattern.compile(s2);
			m = p.matcher(str);
			b = m.matches();
		}
		return b;
	}
} 
