package com.dooleen.common.core.aop.annos;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @CopyRight : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @ProjectNo : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-04-21 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description : 字段加解密注解
 * @author liqh
 * @Update:
 */
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptField {
    String[] value() default "";
}