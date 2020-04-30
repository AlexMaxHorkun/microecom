package com.microecom.customerservice.model.data;

/**
 * Customer that registers through Customer-service.
 */
public interface SigningUp extends CustomerInfo {
    String getLogin();

    String getPassword();
}
