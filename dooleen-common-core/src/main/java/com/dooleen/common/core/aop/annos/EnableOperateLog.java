package com.dooleen.common.core.aop.annos;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.dooleen.common.core.aop.aspect.ModifyAspect;
import org.springframework.context.annotation.Import;

import cn.hutool.extra.spring.SpringUtil;

/**
 * @Copy Right Information : 成都独领创新科技有限公司
 * @Project : Dooleen平台
 * @Project No : Dooleen
 * @Version : 1.0.0
 * @CreateDate : 2020-04-21 10:00:01 +++++++++++++maintainer1 info+++++++++++++
 * @Description :  
 * @Maintainer:liqiuhong
 * @Update:
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({ModifyAspect.class, SpringUtil.class})
public @interface EnableOperateLog {
}