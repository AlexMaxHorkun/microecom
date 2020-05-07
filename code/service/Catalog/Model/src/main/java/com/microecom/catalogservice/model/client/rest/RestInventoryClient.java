package com.microecom.catalogservice.model.client.rest;

import com.microecom.catalogservice.model.client.InventoryClient;
import com.microecom.catalogservice.model.client.data.Stock;
import com.microecom.catalogservice.model.client.rest.data.StockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RestInventoryClient implements InventoryClient {
    private final String inventoryRestUri;

    private final RestTemplate rest;

    public RestInventoryClient(
            @Value("${catalog-service.client.inventory.uri}") String inventoryRestUri,
            @Autowired RestTemplateBuilder restBuilder
    ) {
        this.inventoryRestUri = inventoryRestUri;
        this.rest = restBuilder.build();
    }

    @Override
    public Map<String, Stock> retrieveCalculatedStock(Collection<String> forProductIds) {
        var response = rest.getForEntity(
                inventoryRestUri + "/V1/stock/calculated?productIds={productIds}",
                StockResponse[].class,
                Collections.singletonMap("productIds", forProductIds.toArray())
        );
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to load stock data");
        }

        var map = new HashMap<String, Stock>();
        for (StockResponse s : response.getBody()) {
            map.put(s.getProductId(), s);
        }

        return map;
    }
}
