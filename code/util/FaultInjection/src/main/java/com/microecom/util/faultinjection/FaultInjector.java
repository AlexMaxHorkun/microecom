package com.microecom.util.faultinjection;

import com.microecom.util.faultinjection.exception.InjectedFaultException;

/**
 * Emulate unexpected failures.
 */
public interface FaultInjector {
    /**
     * Call to inject or not inject an exception before a service method was executed.
     *
     * @param methodName Service method currently being executed.
     * @throws InjectedFaultException
     */
    void injectExceptionBeforeMethod(String methodName) throws InjectedFaultException;

    /**
     * Call to inject or not inject an exception after a service method has been executed.
     *
     * Useful for idempotency testing when retries are configured.
     *
     * @param methodName Service method currently being executed.
     * @throws InjectedFaultException
     */
    void injectExceptionAfterMethod(String methodName) throws InjectedFaultException;

    /**
     * Inject or not eject a delay.
     *
     * @param methodName Service method currently being executed.
     */
    void injectDelay(String methodName);

    /**
     * Inject or not eject a specific delay.
     *
     * @param ms Microseconds
     * @param methodName Service method currently being executed.
     */
    void injectDelay(String methodName, int ms);
}
