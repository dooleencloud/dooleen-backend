package com.dooleen.common.core.aop.aspect;

import com.dooleen.common.core.aop.annos.EncryptField;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.Objects;

/**
 * @CopyRight : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @ProjectNo : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-04-21 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description : 字段加密实现
 * @author liqh
 * @Update:
 */
@Slf4j
@Aspect
@Component
public class EncryptAspect {
 
    @Autowired
    private StringEncryptor stringEncryptor;

    //加密切入
    @Pointcut("execution(* com.dooleen..*.*Mapper.*(..))")
    public void encryptAspectCut() {
    }
    // 前置通知
    @Before("encryptAspectCut()")
    public void before(JoinPoint joinPoint) throws Exception{
        log.debug("==>>开始数据库字段加密=====");
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (null == arg) {
                continue;
            }
            Class<?> aClass = arg.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                EncryptField annotation = declaredField.getAnnotation(EncryptField.class);
                if (null != annotation) {
                    String stringType = declaredField.getType().getName();
                    declaredField.setAccessible(true);
                    Object value = declaredField.get(arg);
                    if (value != null) {
                        String valueStr = value.toString();
                        String encodedStr =Base64.getEncoder().encodeToString(valueStr.getBytes());
                        declaredField.set(arg, encodedStr);
                     }
                }
            }
        }
    }

    /**
     * 加密对象
     * @param obj
     * @throws IllegalAccessException
     */
    private void encryptObject(Object obj) throws IllegalAccessException {
 
        if (Objects.isNull(obj)) {
            log.info("当前需要加密的object为null");
            return;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean containEncryptField = field.isAnnotationPresent(EncryptField.class);
            if (containEncryptField) {
                //获取访问权
                field.setAccessible(true);
                String value = stringEncryptor.encrypt(String.valueOf(field.get(obj)));
                field.set(obj, value);
            }
        }
    }
 
    /**
     * 加密单个值
     * @param realValue
     * @return
     */
    public String encryptValue(Object realValue) {
        try {
            realValue = stringEncryptor.encrypt(String.valueOf(realValue));
        } catch (Exception e) {
            log.info("加密异常={}",e.getMessage());
        }
        return String.valueOf(realValue);
    }
 
 
}