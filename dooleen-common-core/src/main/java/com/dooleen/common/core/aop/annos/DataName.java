package com.dooleen.common.core.aop.annos;


import java.lang.annotation.*;
/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-04-21 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description : DataName
 * @Maintainer:liqiuhong
 * @Update:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface DataName {
    /**
     * @return 字段名称
     */
    String name() default "";

    /**
     * @return 是否记录历史
     */
    boolean isRecordHistory() default false;
    
    /**
     * @return 是否主键ID
     */
    boolean isKeyId() default false;
 }
