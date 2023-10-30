package com.bw.petclinic.frontend.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class FrontendAspect {

    private static final Logger LOG = LoggerFactory.getLogger(FrontendAspect.class);

    @Pointcut("execution(public * com.bw.petclinic.frontend.controller.*Controller.*(..))")
    private void forControllers() {}

    @Before("forControllers()")
    public void beforeController(JoinPoint joinPoint) {
        LOG.info("Accessing Controller: " + joinPoint.getSignature() + "] with args: " + Arrays.asList(joinPoint.getArgs()));
    }
}
