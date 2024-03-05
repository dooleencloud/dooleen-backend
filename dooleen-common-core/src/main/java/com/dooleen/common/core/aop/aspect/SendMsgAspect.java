package com.dooleen.common.core.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.dooleen.common.core.aop.annos.SendMsg;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class SendMsgAspect {

	@Pointcut("@annotation(com.dooleen.common.core.aop.annos.SendMsg)")
	private void pointcut() {
	}
	
	// 执行方法前的拦截方法
	@Before("pointcut()&&@annotation(sendMsg)")
	public void before(JoinPoint joinPoint, SendMsg sendMsg) {
		System.out.println("SendMsg===>before");
	}
	
	/**
	 * 后置返回通知 这里需要注意的是: 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
	 * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数 , returning = "keys"
	 * 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
	 */
	@AfterReturning(value = "pointcut()&&@annotation(sendMsg)", returning = "keys")
	public void doAfterReturningAdvice(JoinPoint joinPoint, Object keys, SendMsg sendMsg) {
		System.out.println("SendMsg===>doAfterReturningAdvice");
	}
}
