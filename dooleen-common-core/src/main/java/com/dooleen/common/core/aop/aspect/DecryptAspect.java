package com.dooleen.common.core.aop.aspect;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dooleen.common.core.aop.annos.EncryptField;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.List;

/**
 * @CopyRight : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @ProjectNo : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-04-21 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description : 解密切面实现
 * @author liqh
 * @Update:
 */
@Slf4j
@Aspect
@Component
public class DecryptAspect {
 
    @Autowired
    private StringEncryptor stringEncryptor;

    //解密切入
    @Pointcut("execution(* com.dooleen..*.*Mapper.*(..))")
    public void decryptAspectCut() {
    }
    // 后置通知
    @AfterReturning(returning = "res", pointcut = "decryptAspectCut()")
    public void decode(JoinPoint joinPoint, Object res) throws Exception {
        log.debug("==>>开始数据库字段解密=====");
        if (res instanceof IPage) {
            if (((IPage) res).getRecords() instanceof List) {
                List list = (List) ((IPage) res).getRecords();
                list.forEach(o -> {
                    try {
                        decodeSet(o);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        else {
            try {
                if(res != null) {
                    decodeSet(res);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void decodeSet(Object data) throws Exception {
        Field[] declaredFields = data.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            EncryptField annotation = declaredField.getAnnotation(EncryptField.class);
            if (null != annotation) {
                Object val = declaredField.get(data);
                if (null != val) {
                    byte[] decodeBytes = Base64.getDecoder().decode((String)val);
                    declaredField.set(data, new String(decodeBytes));
                }
            }
        }
    }
}