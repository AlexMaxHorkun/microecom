package com.microecom.orderservice.model.data;

/**
 * New order to place.
 */
public interface NewOrder extends OrderInfo {
    PaymentDetails getPaymentDetails();
}
