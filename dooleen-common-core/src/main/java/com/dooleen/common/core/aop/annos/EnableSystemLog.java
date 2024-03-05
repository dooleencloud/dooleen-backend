package com.dooleen.common.core.aop.annos;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dooleen.common.core.common.entity.NullEntity;
/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-04-21 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description : 记录编辑详细信息的标注
 * @Maintainer:liqiuhong
 * @Update:
 */
 
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface EnableSystemLog {
    /**
     * @return 操作的类型 可以直接调用ModifyName 不传时根据METHOD自动确定
     */
    String operatoinType() default "UPDATE";
    /**
     * @return 查询数据库所调用的class文件
     */
    Class<?> serviceclass() default ServiceImpl.class;

    /**
     * @return 查询数据库所调用的class文件
     */
    Class<?> entityClass() default NullEntity.class;
    /**
     * @return 具体业务操作名称
     */
    String handleName() default "";
    
    /**
     * @return 具体业务操作名称
     */
    String queryMethodName() default "";

    /**
     * @return 是否需要默认的改动比较
     */
    boolean needDefaultCompare() default false;

    /**
     * @return id的类型
     */
    Class<?> idType() default String.class;
    /**
     * @return 是否使用默认本地缓存
     */
    boolean defaultCache() default false;
}
