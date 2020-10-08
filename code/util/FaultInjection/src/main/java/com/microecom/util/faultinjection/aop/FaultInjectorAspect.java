package com.microecom.util.faultinjection.aop;

import com.microecom.util.faultinjection.FaultInjector;
import com.microecom.util.faultinjection.aop.annotation.InjectDelay;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FaultInjectorAspect {
    private final FaultInjector faultInjector;

    public FaultInjectorAspect(@Autowired FaultInjector faultInjector) {
        this.faultInjector = faultInjector;
    }

    @Before("execution(@com.microecom.util.faultinjection.aop.annotation.InjectExceptionBefore * *(..))")
    public void injectExceptionBefore(JoinPoint joinPoint) {
        var signature = (MethodSignature) joinPoint.getSignature();

        faultInjector.injectExceptionBeforeMethod(extractMethodName(joinPoint));
    }

    @After("execution(@com.microecom.util.faultinjection.aop.annotation.InjectExceptionAfter * *(..))")
    public void injectExceptionAfter(JoinPoint joinPoint) {
        var signature = (MethodSignature) joinPoint.getSignature();

        faultInjector.injectExceptionAfterMethod(extractMethodName(joinPoint));
    }

    @Before("execution(@com.microecom.util.faultinjection.aop.annotation.InjectDelay * *(..))")
    public void injectDelay(JoinPoint joinPoint) {
        var method = extractMethodName(joinPoint);
        var signature = (MethodSignature) joinPoint.getSignature();
        var delay = signature.getMethod().getAnnotation(InjectDelay.class).delay();
        if (delay > 0) {
            faultInjector.injectDelay(method, delay);
        } else {
            faultInjector.injectDelay(method);
        }
    }

    private String extractMethodName(JoinPoint joinPoint) {
        var signature = (MethodSignature) joinPoint.getSignature();
        return signature.getDeclaringType().getSimpleName() + "." + signature.getName() + "()";
    }
}
