package com.yancey.manager.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

/**
 * 日志切面
 * 
 * */
public class LogAspect {

  private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

  public void doSystemBefore(JoinPoint joinPoint){
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		httpServletRequest.getParameter("money");
		String paramMap = JSONObject.toJSONString(httpServletRequest.getParameterMap());
		logger.info(String.format("request <===url:[%s], class:[%s], method:[%s], paramters:[%s]", 
				httpServletRequest.getRequestURL(), className, methodName, paramMap));
	}

}
