package com.microecom.orderservice.model;

import com.microecom.orderservice.model.data.ProductInfo;

import java.util.Map;
import java.util.Set;

public interface CatalogServiceClient {
    Map<String, ProductInfo> loadProducts(Set<String> ids);
}
