package com.microecom.util.faultinjection.exception;

/**
 * Emulated exception.
 */
public class InjectedFaultException extends RuntimeException {
    public InjectedFaultException() {
        super("Injected Exception");
    }
}
