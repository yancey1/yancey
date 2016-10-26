package com.yancey.manager.aop;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yancey.manager.annotation.Action;
import com.yancey.manager.domain.User;
import com.yancey.manager.domain.log.SystemLog;
import com.yancey.manager.service.log.ISystemLogService;

/**
 * 日志切面
 * 
 * */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LogAspect {

  private ISystemLogService   systemLogService;

  private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

  public Object doSystemLog(ProceedingJoinPoint point) throws Throwable {

    String methodName = point.getSignature().getName();

    // 目标方法不为�?
    if (StringUtils.isNotEmpty(methodName)) {
      // set与get方法除外
      if (!(methodName.startsWith("set") || methodName.startsWith("get"))) {

        Class targetClass = point.getTarget().getClass();
        Method method = targetClass.getMethod(methodName);

        if (method != null) {

          boolean hasAnnotation = method.isAnnotationPresent(Action.class);

          if (hasAnnotation) {
            Action annotation = method.getAnnotation(Action.class);

            String methodDescp = annotation.description();
            if (logger.isDebugEnabled()) {
              logger.debug("Action method:" + method.getName() + " Description:" + methodDescp);
            }
            // 取到当前的操作用�?
            User appUser = new User();
            appUser.setUserId(1);
            appUser.setUserName("test");
            if (appUser != null) {
              try {
                SystemLog sysLog = new SystemLog();

                sysLog.setCreatetime(new Date());
                sysLog.setUserId(appUser.getUserId());
                sysLog.setUsername(appUser.getUserName());
                sysLog.setExeOperation(methodDescp);

                boolean isSuccess = systemLogService.saveSystemLog(sysLog);
                System.out.println("isSuccess-->" + isSuccess);
              } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
              }
            }
          }
        }

      }
    }

    return point.proceed();
  }

  public void setSystemLogService(ISystemLogService systemLogService) {
    this.systemLogService = systemLogService;
  }

}
