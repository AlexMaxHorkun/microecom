package com.microecom.orderservice.model.data;

import java.util.Set;

/**
 * Order data.
 */
public interface OrderInfo {
    String getCustomerId();

    Set<OrderedQuantity> getOrdered();
}
