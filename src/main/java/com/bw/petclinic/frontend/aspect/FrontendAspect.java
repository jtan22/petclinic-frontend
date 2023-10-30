package com.bw.petclinic.frontend.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class FrontendAspect {

    private static final Logger LOG = LoggerFactory.getLogger(FrontendAspect.class);

    @Pointcut("execution(public * com.bw.petclinic.frontend.controller.*Controller.*(..))")
    private void forControllers() {}

    @Before("forControllers()")
    public void loggingController(JoinPoint joinPoint) {
        LOG.info("Logging Controller [" + joinPoint.getSignature() + "] " +
                "with args [" + Arrays.asList(joinPoint.getArgs()) + "]");
    }

    @Before("forControllers()")
    public void auditController() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LOG.info("Audit Controller [" + auth.getName() + "] " +
                "with Roles [" + auth.getAuthorities() + "]");
    }

    @Around("forControllers()")
    public Object profileController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        LOG.info("Profile Controller [" + proceedingJoinPoint.getSignature() + "] " +
                "with args [" + Arrays.asList(proceedingJoinPoint.getArgs()) + "] " +
                "took [" + duration + "]");
        return result;
    }
}
