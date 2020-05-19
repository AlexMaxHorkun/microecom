package com.microecom.orderservice.model.client;

import com.microecom.orderservice.model.InventoryServiceClient;
import com.microecom.orderservice.model.data.ProductStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RestInventoryServiceClient implements InventoryServiceClient {
    private final String inventoryRestUri;

    private final RestTemplate rest;

    public RestInventoryServiceClient(
            @Value("${order-service.client.inventory.uri}") String inventoryRestUri,
            @Autowired RestTemplateBuilder restBuilder
    ) {
        this.inventoryRestUri = inventoryRestUri;
        this.rest = restBuilder.build();
    }

    @Override
    public Map<String, ProductStock> loadStocks(Set<String> productIds) {
        var response = rest.getForEntity(
                inventoryRestUri + "/V1/stock/calculated?productIds={productIds}",
                RestStockResponse[].class,
                Collections.singletonMap("productIds", productIds.toArray())
        );
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to load stock data");
        }

        var map = new HashMap<String, ProductStock>();
        for (RestStockResponse s : response.getBody()) {
            map.put(s.getProductId(), s);
        }

        return map;
    }
}
