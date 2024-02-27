package com.backend.practice.aop;


import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class AopSetting {
	
	
	  	@Pointcut("execution(* com.backend.practice.controller.*.*(..)))")
	    public void pointCut(){}
	    @Pointcut("execution(* com.backend.practice.service.*.*(..)))")
	    public void serviceCut(){}
	    @Before("pointCut()")
	    public void before(JoinPoint joinPoint){
	        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
	        Method method = methodSignature.getMethod();
	        Object[] objects = joinPoint.getArgs();
	        Arrays.stream(objects).forEach(obj -> log.info("들어온 파라미터 : "+obj));
	        log.info("Controller에서 실행된 메소드 명 : "+method.getName());
	    }


	    @AfterReturning(value = "serviceCut()",returning = "returnValue")
	    public void afterReturning(JoinPoint joinPoint,Object returnValue){
	        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
	        Method method = methodSignature.getMethod();
	        log.info("Service에서 실행된 메소드:" +method.getName());
	        log.info(method.getName()+" 메소드가 리턴한 값  : "+ returnValue);
	    }

}
