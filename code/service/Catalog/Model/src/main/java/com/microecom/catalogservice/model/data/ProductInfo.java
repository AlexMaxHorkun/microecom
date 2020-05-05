package com.microecom.catalogservice.model.data;

/**
 * Basic product information.
 */
public interface ProductInfo {
    String getSku();

    String getTitle();

    String getDescription();

    Float getPrice();

    String getCategoryId();
}
