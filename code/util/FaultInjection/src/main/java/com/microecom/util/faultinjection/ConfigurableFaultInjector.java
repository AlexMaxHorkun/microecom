package com.microecom.util.faultinjection;

import com.microecom.util.faultinjection.exception.InjectedFaultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class ConfigurableFaultInjector implements FaultInjector {
    private final Integer exceptionChance;

    private final Integer delayChance;

    private final Integer defaultDelay;

    private final Logger logger;

    public ConfigurableFaultInjector(
            @Value("${microecom.fault-injection.exception-chance}") Integer exceptionChance,
            @Value("${microecom.fault-injection.delay-chance}") Integer delayChance,
            @Value("${microecom.fault-injection.default-delay}") Integer defaultDelay
    ) {
        if (exceptionChance < 0 || exceptionChance > 100 || delayChance < 0 || delayChance > 100 || defaultDelay < 1) {
            throw new IllegalArgumentException("Fault Injection was not configured properly");
        }
        this.exceptionChance = exceptionChance;
        this.delayChance = delayChance;
        this.defaultDelay = defaultDelay;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public void injectExceptionBeforeMethod(String methodName) throws InjectedFaultException {
        if (shouldI(exceptionChance)) {
            logger.info(String.format("[Fault Injection] Emulated exception before method \"%s\"", methodName));
            throw new InjectedFaultException();
        } else {
            logger.info(String.format("[Fault Injection] Method \"%s\" allowed to execute", methodName));
        }
    }

    @Override
    public void injectExceptionAfterMethod(String methodName) throws InjectedFaultException {
        if (shouldI(exceptionChance)) {
            logger.info(String.format("[Fault Injection] Emulated exception after method \"%s\"", methodName));
            throw new InjectedFaultException();
        } else {
            logger.info(String.format("[Fault Injection] Method \"%s\" allowed to execute", methodName));
        }
    }

    @Override
    public void injectDelay(String methodName) {
        injectDelay(methodName, defaultDelay);
    }

    @Override
    public void injectDelay(String methodName, int ms) {
        if (shouldI(delayChance)) {
            logger.info(String.format("[Fault Injection] Emulated delay for method \"%s\" for %d ms", methodName, ms));
            try {
                Thread.sleep(ms);
            } catch (InterruptedException ex) {
                logger.warn(
                        String.format("[Fault Injection] FAILED to emulated delay for method \"%s\" for %d ms",
                                methodName, ms)
                );
            }
        } else {
            logger.info(String.format("[Fault Injection] Method \"%s\" allowed to execute without a delay", methodName));
        }
    }

    private boolean shouldI(int chance) {
        if (chance == 100) {
            return true;
        } else if (chance == 0) {
            return false;
        }

        var rand = new SecureRandom();

        return rand.nextInt(100) < chance;
    }
}
