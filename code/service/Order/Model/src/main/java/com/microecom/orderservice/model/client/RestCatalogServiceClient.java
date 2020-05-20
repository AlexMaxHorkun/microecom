package com.microecom.orderservice.model.client;

import com.microecom.orderservice.model.CatalogServiceClient;
import com.microecom.orderservice.model.data.NewPayment;
import com.microecom.orderservice.model.data.ProductInfo;
import com.microecom.orderservice.model.data.payment.CardPaymentDetails;
import com.microecom.orderservice.model.exception.InvalidPaymentDetailsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RestCatalogServiceClient implements CatalogServiceClient {
    private final String catalogRestUri;

    private final RestTemplate rest;

    public RestCatalogServiceClient(
            @Value("${order-service.client.catalog.uri}") String catalogRestUri,
            @Autowired RestTemplateBuilder restBuilder
    ) {
        this.catalogRestUri = catalogRestUri;
        this.rest = restBuilder.build();
    }

    @Override
    public Map<String, ProductInfo> loadProducts(Set<String> ids) {
        var response = rest.getForEntity(catalogRestUri + "/V1/product", RestProductInfoResponse[].class, Map.of("productIds", ids));
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            throw new RuntimeException("Failed to load product list");
        }

        var result = new HashMap<String, ProductInfo>();
        for (ProductInfo i : response.getBody()) {
            result.put(i.getId(), i);
        }

        return result;
    }
}
